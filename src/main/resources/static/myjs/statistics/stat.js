$(document).ready(function () {
console.log("enter!")
 function show() {
var DATE=$("#date6").val();
var ITEM=$("#item6").val();
var DANWEI=$("#danwei6").val();

        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
    require(
        [
            'echarts',
            'echarts/chart/bar'
        ],
        DrawChart(ITEM,DATE,DANWEI)
    );
 }
    function DrawChart(item,date,danwei) {
     console.log("ready to draw");
        $.ajax({
            url:"../statistics/getStatistics",
            dateType:"json",
            type:"POST",
            async:false,
            data:{
                item:item,
                date:date,
                danwei:danwei
            },
            error:function (error) {
                console.log("wrong");
                console.log(error.responseText);
            },
            success:function (e) {
                console.log(e.code);
                if(e.code===0){
                    var statPack=JSON.parse(e.data);
                    if (statPack.length>0){
                        console.log(statPack);
                            // 基于准备好的dom，初始化echarts图表
                            var myChart = ec.init(document.getElementById('com_stats'));

                            var option = {
                                tooltip: {
                                    show: true
                                },
                                legend: {
                                    data: ['销量']
                                },
                                xAxis: [
                                    {
                                        type: 'category',
                                        data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: 'value'
                                    }
                                ],
                                series: [
                                    {
                                        "name": "销量",
                                        "type": "bar",
                                        "data": [5, 20, 40, 10, 10, 20]
                                    }
                                ]
                            };
                            // 为echarts对象加载数据
                            myChart.setOption(option);
                        }
                    else {
                        console.log("没有符合要求的数据，请重新选择");
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
        $.ajax({
            url: "../statistics/getCyborgEMG",
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
                if (e.code == 0) {
                    // console.log(e.data)
                    var emgPacket = JSON.parse(e.data);
                    if (emgPacket.length > 0) {
                        // lastStartTime = emgPacket[emgPacket.length - 1].timestamp - dataSetLength * dataInterval;
                        console.log("emgPacket");
                        console.log(emgPacket);
                        refreshLoop(update, emgPacket, emgPacket.length);
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
            console.log(array[index].timestamp);
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function update(value) {
        if (emgArray.length > dataSetLength) {
            emgArray = emgArray.slice(1);
        }
        if (emgArray.length === 0 || value.timestamp > emgArray[emgArray.length - 1][0]) {
            emgArray.push([value.timestamp, value.value]);
            console.log("update " + value);
            $.plot($("#StatisticChart"), [{
                data: emgArray,
                label: "Sales"
            }
            ], {
                series: {
                    lines: {
                        show: true,
                        lineWidth: 2,
                        fill: true,
                        fillColor: {
                            colors: [{
                                opacity: 0.25
                            }, {
                                opacity: 0.25
                            }
                            ]
                        }
                    },
                    points: {
                        show: false
                    },
                    shadowSize: 2
                },
                legend: {
                    show: false
                },
                grid: {
                    labelMargin: 10,
                    axisMargin: 500,
                    hoverable: true,
                    clickable: true,
                    tickColor: "rgba(0,0,0,0.15)",
                    borderWidth: 0
                },
                colors: ["#B450B2", "#4A8CF7", "#52e136"],
                xaxis: {
                    mode: "time",
                    tickSize: [1, "second"],
                    tickFormatter: function (v, axis) {
                        var date = new Date(v);

                        if (date.getSeconds() % 5 == 0) {
                            var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                            var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                            var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

                            return hours + ":" + minutes + ":" + seconds;
                        } else {
                            return "";
                        }
                    },
                    axisLabel: "Time",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 10
                },
                yaxis: {
                    ticks: 5,
                    tickDecimals: 0
                }
            });
        }
    }

    /**
     * 获取比当前时间的时间戳
     * @returns {number}
     */
    function getCurrentTimestamp() {
        var timestamp = new Date().getTime();
        return timestamp;
    }


});