$(document).ready(function () {
    /**-----------------------------------------------------------------**/
    /**
     * 肌电信号处理开始
     * @type {Array}
     */
    var emgTimeArray = [], emgArray = [], ajaxInterval = 1000, dataInterval = 1000, dataSetLength = 20;

    /**
     * 调节界面chart更新速度和后台数据获取速度不一致的偏差
     */
    var updateDelta = 100;


    /**
     * 由于后台数据是假的 所以定时器请求可能得到比正在绘制的更旧的时间戳
     * 暂时丑陋地加上了一个正在绘制的状态
     * 目前会导致最多慢一分钟的更新
     * TODO 等待数据库建立后 增加数据已经获取的状态 就不怕重新获取了 DONE
     * @type {boolean}
     */
    // var drawing = false;

    /**
     * 保证每次运行从较新的数据开始更新
     * 这样使得之后的数据的起始时间戳一直是该值
     * @type {number}
     */
    var lastStartTime = getCurrentTimestamp() - dataSetLength * dataInterval * 8;

    function getBiosignal(url, updateFunction, startTime) {
        $.ajax({
            url: url,
            dataType: "json",
            type: "POST",
            async: true,
            data: {
                startTime: startTime
            },
            error: function (error) {
                console.log(error.responseText);
            },
            success: function (e) {
                if (e.code === 0) {
                    // console.log(e.data);
                    var packet = JSON.parse(e.data);
                    if (packet.length > 0) {
                        refreshLoop(updateFunction, packet, packet.length);
                    } else {
                        console.log("没有新的数据");
                    }
                } else {
                    $.gritter.add({
                        title: '执行失败',
                        text: e.msg,
                        class_name: 'danger'
                    });
                }
            }
        });
    }

    /**
     *实时获取电子人肌电信号数据
     */
    function getCyborgEMG(startTime) {
        getBiosignal('../monitor/getCyborgEMG', updateEMG, startTime);
    }

    /**
     * 将封装的实时数据数组按照时间戳定时输出
     * @param func 更新实时图表函数
     * @param array 实时数据数组
     * @param loopTimes 重绘次数 即循环次数
     */
    function refreshLoop(func, array, loopTimes) {
        var index = 0;
        /**
         * 递归执行 按时间戳的间隔进行定时
         */
        var interv = function () {
            // drawing = true;
            // console.log(array[index].timestamp);
            func(array[index]);
            var now = array[index].timestamp;
            index++;
            if (index >= loopTimes) {
                // drawing = false;
                return;
            }
            // /**
            //  * 解决数据刷新和获取速度不同步的问题
            //  */
            // if ((getCurrentTimestamp() - lastStartTime) > dataSetLength * dataInterval) {
            //     setTimeout(interv, updateDelta);
            // } else {
            setTimeout(interv, array[index].timestamp - now);
            // }
        };
        var now = new Date().getTime();
        var startTime = array[0].timestamp;
        if (startTime > now) {
            setTimeout(interv, startTime - now);
        } else {
            setTimeout(interv, 0);
        }
    }

    /**
     * 使用echart绘制实时折线图
     * @param x
     * @param y
     * @param yName
     * @param yUnit
     * @param domName
     */
    function drawChart(x, y, yName, yUnit, domName) {
        // 基于准备好的dom，初始化echarts图表
        var myChart = echarts.init(document.getElementById(domName));
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    animation: true,
                    label: {
                        backgroundColor: '#1da2d4'
                    }

                }
            },
            legend: {
                data: [yName]
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: {
                show: false,
                start: 0,
                end: 100
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: true,
                    data: x,
                    axisLabel: {
                        formatter: function (value) {
                            // 格式化成月/日，只在第一个刻度显示年份
                            return timestampToString(parseInt(value));
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    scale: true,
                    name: yUnit,
                    boundaryGap: [0.2, 0.2]
                }
            ],
            series: [
                {
                    name: yName,
                    type: "line",
                    data: y,
                    smooth: true,
                    itemStyle: {
                        normal: {
                            color: '#1da2d4'
                        }
                    },
                    areaStyle: {}
                }
            ]
        };

        // 为echarts对象加载数据
        myChart.setOption(option);
    }

    function timestampToString(timestamp) {
        return new Date(timestamp).toLocaleTimeString().replace(/^\D*/, '');
    }


    /**
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEMG(value) {
        if (emgArray.length > dataSetLength) {
            emgArray = emgArray.slice(1);
            emgTimeArray = emgTimeArray.slice(1);
        }
        if (emgArray.length === 0 || value.timestamp > emgTimeArray[emgTimeArray.length - 1]) {
            emgArray.push(value.emgValue);
            emgTimeArray.push(value.timestamp);
            drawChart(emgTimeArray, emgArray, '放大后的肌电信号', '电压', 'chartEMG');
        }
    }

    /**
     * 获取当前时间的时间戳
     * @returns {number}
     */
    function getCurrentTimestamp() {
        var timestamp = new Date().getTime();
        return timestamp;
    }


    /**-----------------------------------------------------------------**/
    /**
     * 呼吸信号处理开始
     * @type {Array}
     */

    var rrTimeArray = [], rrArray = [];


    /**
     *实时获取电子人呼吸信号数据
     */
    function getCyborgRR(startTime) {
        getBiosignal('../monitor/getCyborgRR', updateRR, startTime);
    }


    /**
     * 根据获取得到的呼吸信号的值重绘实时图
     * @param value
     */
    function updateRR(value) {
        if (rrArray.length > dataSetLength) {
            rrArray = rrArray.slice(1);
            rrTimeArray = rrTimeArray.slice(1);
        }
        if (rrArray.length === 0 || value.timestamp > rrTimeArray[rrTimeArray.length - 1]) {
            rrArray.push(value.rrValue);
            rrTimeArray.push(value.timestamp);
            drawChart(rrTimeArray, rrArray, '放大后的呼吸信号', '呼吸压', 'chartRR');
        }
    }


    /**-----------------------------------------------------------------**/
    /**
     * 体温信号处理开始
     * @type {Array}
     */
    var tempTimeArray = [], tempArray = [];


    /**
     *实时获取电子人体温信号数据
     */
    function getCyborgTemp(startTime) {
        getBiosignal('../monitor/getCyborgTemp', updateTemp, startTime);
    }


    /**
     * 根据获取得到的体温信号的值重绘实时图
     * @param value
     */
    function updateTemp(value) {
        if (tempArray.length > dataSetLength) {
            tempArray = tempArray.slice(1);
            tempTimeArray = tempTimeArray.slice(1);
        }
        if (tempArray.length === 0 || value.timestamp > tempTimeArray[tempTimeArray.length - 1]) {
            tempArray.push(value.tempValue);
            tempTimeArray.push(value.timestamp);
            drawChart(tempTimeArray, tempArray, '体温信号', '温度', 'chartTemp');
        }
    }


    /**-----------------------------------------------------------------**/
    /**
     * 脑电信号处理开始
     * @type {Array}
     */
    var eegDeltaTimeArray = [];
    var eegDeltaArray = [];
    var eegThetaTimeArray = [];
    var eegThetaArray = [];
    var eegLowalphaTimeArray = [];
    var eegLowalphaArray = [];
    var eegHighalphaTimeArray = [];
    var eegHighalphaArray = [];
    var eegLowbetaTimeArray = [];
    var eegLowbetaArray = [];
    var eegHighbetaTimeArray = [];
    var eegHighbetaArray = [];
    var eegLowgammaTimeArray = [];
    var eegLowgammaArray = [];
    var eegMidgammaTimeArray = [];
    var eegMidgammaArray = [];
    var eegAttentionTimeArray = [];
    var eegAttentionArray = [];
    var eegMediationTimeArray = [];
    var eegMediationArray = [];


    /**
     *实时获取电子人脑电信号数据
     */
    function getCyborgEEG(startTime) {
        $.ajax({
            url: "../monitor/getCyborgEEG",
            dataType: "json",
            type: "POST",
            async: true,
            data: {
                startTime: startTime
            },
            error: function (error) {
                console.log(error.responseText);
            },
            success: function (e) {
                if (e.code === 0) {
                    // console.log(e.data)
                    var eegPacket = JSON.parse(e.data);
                    if (eegPacket.length > 0) {
                        // lastStartTime = eegPacket[eegPacket.length - 1].timestamp - dataSetLength * dataInterval;
                        refreshLoop(updateEEGDelta, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGTheta, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGLowalpha, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGHighalpha, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGLowbeta, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGHighbeta, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGLowgamma, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGMidgamma, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGAttention, eegPacket, eegPacket.length);
                        refreshLoop(updateEEGMediation, eegPacket, eegPacket.length);
                    } else {
                        console.log("没有新的数据");
                    }
                } else {
                    $.gritter.add({
                        title: '执行失败',
                        text: e.msg,
                        class_name: 'danger'
                    });
                }
            }
        });
    }


    /**
     * 根据获取得到的脑电信号Delta波的值重绘实时图
     * @param value
     */
    function updateEEGDelta(value) {
        if (eegDeltaArray.length > dataSetLength) {
            eegDeltaArray = eegDeltaArray.slice(1);
            eegDeltaTimeArray = eegDeltaArray.slice(1);
        }
        if (eegDeltaArray.length === 0 || value.timestamp > eegDeltaTimeArray[eegDeltaTimeArray.length - 1]) {
            eegDeltaArray.push(value.eegDelta);
            eegDeltaTimeArray.push(value.timestamp);
            drawChart(eegDeltaTimeArray, eegDeltaArray, '脑电Delta波', 'Delta波', 'chartEEGDelta');
        }
    }

    /**
     * 根据获取得到的脑电信号Theta波的值重绘实时图
     * @param value
     */
    function updateEEGTheta(value) {
        if (eegThetaArray.length > dataSetLength) {
            eegThetaArray = eegThetaArray.slice(1);
            eegThetaTimeArray = eegThetaTimeArray.slice(1);
        }
        if (eegThetaArray.length === 0 || value.timestamp > eegThetaTimeArray[eegThetaTimeArray.length - 1]) {
            eegThetaArray.push(value.eegTheta);
            eegThetaTimeArray.push(value.timestamp);
            drawChart(eegThetaTimeArray, eegThetaArray, '脑电Theta波', 'Theta波', 'chartEEGTheta');
        }
    }

    /**
     * 根据获取得到的脑电信号Lowalpha波的值重绘实时图
     * @param value
     */
    function updateEEGLowalpha(value) {
        if (eegLowalphaArray.length > dataSetLength) {
            eegLowalphaArray = eegLowalphaArray.slice(1);
            eegLowalphaTimeArray = eegLowalphaTimeArray.slice(1);
        }
        if (eegLowalphaArray.length === 0 || value.timestamp > eegLowalphaTimeArray[eegLowalphaTimeArray.length - 1]) {
            eegLowalphaArray.push(value.eegLowalpha);
            eegLowalphaTimeArray.push(value.timestamp);
            drawChart(eegLowalphaTimeArray, eegLowalphaArray, '脑电Lowalpha波', 'Lowalpha波', 'chartEEGLowalpha');
        }
    }

    /**
     * 根据获取得到的脑电信号Highalpha波的值重绘实时图
     * @param value
     */
    function updateEEGHighalpha(value) {
        if (eegHighalphaArray.length > dataSetLength) {
            eegHighalphaArray = eegHighalphaArray.slice(1);
            eegHighalphaTimeArray = eegHighalphaTimeArray.slice(1);
        }
        if (eegHighalphaArray.length === 0 || value.timestamp > eegHighalphaTimeArray[eegHighalphaTimeArray.length - 1]) {
            eegHighalphaArray.push(value.eegHighalpha);
            eegHighalphaTimeArray.push(value.timestamp);
            drawChart(eegHighalphaTimeArray, eegHighalphaArray, '脑电Highalpha波', 'Highalpha波', 'chartEEGHighalpha');
        }
    }

    /**
     * 根据获取得到的脑电信号Lowbeta波的值重绘实时图
     * @param value
     */
    function updateEEGLowbeta(value) {
        if (eegLowbetaArray.length > dataSetLength) {
            eegLowbetaArray = eegLowbetaArray.slice(1);
            eegLowbetaTimeArray = eegLowbetaTimeArray.slice(1);
        }
        if (eegLowbetaArray.length === 0 || value.timestamp > eegLowbetaTimeArray[eegLowbetaTimeArray.length - 1]) {
            eegLowbetaArray.push(value.eegLowbeta);
            eegLowbetaTimeArray.push(value.timestamp);
            drawChart(eegLowbetaTimeArray, eegLowbetaArray, '脑电Lowbeta波', 'Lowbeta波', 'chartEEGLowbeta');
        }
    }

    /**
     * 根据获取得到的脑电信号Highbeta波的值重绘实时图
     * @param value
     */
    function updateEEGHighbeta(value) {
        if (eegHighbetaArray.length > dataSetLength) {
            eegHighbetaArray = eegHighbetaArray.slice(1);
            eegHighbetaTimeArray = eegHighbetaTimeArray.slice(1);
        }
        if (eegHighbetaArray.length === 0 || value.timestamp > eegHighbetaTimeArray[eegHighbetaTimeArray.length - 1]) {
            eegHighbetaArray.push(value.eegHighbeta);
            eegHighbetaTimeArray.push(value.timestamp);
            drawChart(eegHighbetaTimeArray, eegHighbetaArray, '脑电Highbeta波', 'Highbeta波', 'chartEEGHighbeta');
        }
    }

    /**
     * 根据获取得到的脑电信号Lowgamma波的值重绘实时图
     * @param value
     */
    function updateEEGLowgamma(value) {
        if (eegLowgammaArray.length > dataSetLength) {
            eegLowgammaArray = eegLowgammaArray.slice(1);
            eegLowgammaTimeArray = eegLowgammaTimeArray.slice(1);
        }
        if (eegLowgammaArray.length === 0 || value.timestamp > eegLowgammaTimeArray[eegLowgammaTimeArray.length - 1]) {
            eegLowgammaArray.push(value.eegLowgamma);
            eegLowgammaTimeArray.push(value.timestamp);
            drawChart(eegLowgammaTimeArray, eegLowgammaArray, '脑电Lowgamma波', 'Lowgamma波', 'chartEEGLowgamma');
        }
    }

    /**
     * 根据获取得到的脑电信号Midgamma波的值重绘实时图
     * @param value
     */
    function updateEEGMidgamma(value) {
        if (eegMidgammaArray.length > dataSetLength) {
            eegMidgammaArray = eegMidgammaArray.slice(1);
            eegMidgammaTimeArray = eegMidgammaTimeArray.slice(1);
        }
        if (eegMidgammaArray.length === 0 || value.timestamp > eegMidgammaTimeArray[eegMidgammaTimeArray.length - 1]) {
            eegMidgammaArray.push(value.eegMidgamma);
            eegMidgammaTimeArray.push(value.timestamp);
            drawChart(eegMidgammaTimeArray, eegMidgammaArray, '脑电Midgamma波', 'Midgamma波', 'chartEEGMidgamma');
        }
    }

    /**
     * 根据获取得到的脑电信号专注度的值重绘实时图
     * @param value
     */
    function updateEEGAttention(value) {
        if (eegAttentionArray.length > dataSetLength) {
            eegAttentionArray = eegAttentionArray.slice(1);
            eegAttentionTimeArray = eegAttentionTimeArray.slice(1);
        }
        if (eegAttentionArray.length === 0 || value.timestamp > eegAttentionTimeArray[eegAttentionTimeArray.length - 1]) {
            eegAttentionArray.push(value.eegAttention);
            eegAttentionTimeArray.push(value.timestamp);
            drawChart(eegAttentionTimeArray, eegAttentionArray, '脑电专注度', '专注度', 'chartEEGAttention');
        }
    }

    /**
     * 根据获取得到的脑电信号冥想度的值重绘实时图
     * @param value
     */
    function updateEEGMediation(value) {
        if (eegMediationArray.length > dataSetLength) {
            eegMediationArray = eegMediationArray.slice(1);
            eegMediationTimeArray = eegMediationTimeArray.slice(1);
        }
        if (eegMediationArray.length === 0 || value.timestamp > eegMediationTimeArray[eegMediationTimeArray.length - 1]) {
            eegMediationArray.push(value.eegMediation);
            eegMediationTimeArray.push(value.timestamp);
            drawChart(eegMediationTimeArray, eegMediationArray, '脑电冥想度', '冥想度', 'chartEEGMediation');
        }
    }


    /**
     * 设置定时器，每隔一定时间发送异步请求
     */
    getCyborgEMG(lastStartTime);
    getCyborgRR(lastStartTime);
    getCyborgTemp(lastStartTime);
    getCyborgEEG(lastStartTime);
    setInterval(function () {
        // if(!drawing) {
        getCyborgEMG(lastStartTime);
        getCyborgRR(lastStartTime);
        getCyborgTemp(lastStartTime);
        getCyborgEEG(lastStartTime);
        // }
    }, ajaxInterval);


});








