$(document).ready(function () {

    var emgArray = [], ajaxInterval = 1000, dataInterval = 1000, dataSetLength = 20;

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

    /**
     *实时获取电子人肌电信号数据
     */
    function getCyborgEMG(startTime) {
        $.ajax({
            url: "../monitor/getCyborgEMG",
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
                        refreshLoop(updateEMG, emgPacket, emgPacket.length);
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
    function updateEMG(value) {
        if (emgArray.length > dataSetLength) {
            emgArray = emgArray.slice(1);
        }
        if (emgArray.length === 0 || value.timestamp > emgArray[emgArray.length - 1][0]) {
            emgArray.push([value.timestamp, value.emgValue]);
            console.log("update " + value.timestamp + value.emgValue);
            $.plot($("#chartEMG"), [{
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

    /**
     * 设置定时器，每隔一定时间发送异步请求
     */
    getCyborgEMG(lastStartTime);
    setInterval(function () {
        // if(!drawing) {
        getCyborgEMG(lastStartTime);
        // }
    }, ajaxInterval);




    var rrArray = [];


    /**
     *实时获取电子人呼吸信号数据
     */
    function getCyborgRR(startTime) {
        $.ajax({
            url: "../monitor/getCyborgRR",
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
                    var rrPacket = JSON.parse(e.data);
                    if (rrPacket.length > 0) {
                        // lastStartTime = rrPacket[rrPacket.length - 1].timestamp - dataSetLength * dataInterval;
                        console.log("rrPacket");
                        console.log(rrPacket);
                        refreshLoop(updateRR, rrPacket, rrPacket.length);
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateRR(value) {
        if (rrArray.length > dataSetLength) {
            rrArray = rrArray.slice(1);
        }
        if (rrArray.length === 0 || value.timestamp > rrArray[rrArray.length - 1][0]) {
            rrArray.push([value.timestamp, value.rrValue]);
            console.log("update " + value.timestamp + value.rrValue);
            $.plot($("#chartRR"), [{
                data: rrArray,
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

    /**
     * 设置定时器，每隔一定时间发送异步请求
     */
    getCyborgRR(lastStartTime);
    setInterval(function () {
        // if(!drawing) {
        getCyborgRR(lastStartTime);
        // }
    }, ajaxInterval);

    var tempArray = [];


    /**
     *实时获取电子人呼吸信号数据
     */
    function getCyborgTemp(startTime) {
        $.ajax({
            url: "../monitor/getCyborgTemp",
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
                    var tempPacket = JSON.parse(e.data);
                    if (tempPacket.length > 0) {
                        // lastStartTime = tempPacket[tempPacket.length - 1].timestamp - dataSetLength * dataInterval;
                        console.log("tempPacket");
                        console.log(tempPacket);
                        refreshLoop(updateTemp, tempPacket, tempPacket.length);
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateTemp(value) {
        if (tempArray.length > dataSetLength) {
            tempArray = tempArray.slice(1);
        }
        if (tempArray.length === 0 || value.timestamp > tempArray[tempArray.length - 1][0]) {
            tempArray.push([value.timestamp, value.tempValue]);
            console.log("update " + value.timestamp + value.tempValue);
            $.plot($("#chartTemp"), [{
                data: tempArray,
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

    /**
     * 设置定时器，每隔一定时间发送异步请求
     */
    getCyborgTemp(lastStartTime);
    setInterval(function () {
        // if(!drawing) {
        getCyborgTemp(lastStartTime);
        // }
    }, ajaxInterval);
    
    

    var eegDeltaArray = [];
    var eegThetaArray = [];
    var eegLowalphaArray = [];
    var eegHighalphaArray = [];
    var eegLowbetaArray = [];
    var eegHighbetaArray = [];
    var eegLowgammaArray = [];
    var eegMidgammaArray = [];
    var eegAttentionArray = [];
    var eegMediationArray = [];


    /**
     *实时获取电子人呼吸信号数据
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
                if (e.code == 0) {
                    // console.log(e.data)
                    var eegPacket = JSON.parse(e.data);
                    if (eegPacket.length > 0) {
                        // lastStartTime = eegPacket[eegPacket.length - 1].timestamp - dataSetLength * dataInterval;
                        console.log("eegPacket");
                        console.log(eegPacket);
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGDelta(value) {
        if (eegDeltaArray.length > dataSetLength) {
            eegDeltaArray = eegDeltaArray.slice(1);
        }
        if (eegDeltaArray.length === 0 || value.timestamp > eegDeltaArray[eegDeltaArray.length - 1][0]) {
            eegDeltaArray.push([value.timestamp, value.eegDelta]);
            console.log("update " + value.timestamp + value.eegDelta);
            $.plot($("#chartEEGDelta"), [{
                data: eegDeltaArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGTheta(value) {
        if (eegThetaArray.length > dataSetLength) {
            eegThetaArray = eegThetaArray.slice(1);
        }
        if (eegThetaArray.length === 0 || value.timestamp > eegThetaArray[eegThetaArray.length - 1][0]) {
            eegThetaArray.push([value.timestamp, value.eegTheta]);
            console.log("update " + value.timestamp + value.eegTheta);
            $.plot($("#chartEEGTheta"), [{
                data: eegThetaArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGLowalpha(value) {
        if (eegLowalphaArray.length > dataSetLength) {
            eegLowalphaArray = eegLowalphaArray.slice(1);
        }
        if (eegLowalphaArray.length === 0 || value.timestamp > eegLowalphaArray[eegLowalphaArray.length - 1][0]) {
            eegLowalphaArray.push([value.timestamp, value.eegLowalpha]);
            console.log("update " + value.timestamp + value.eegLowalpha);
            $.plot($("#chartEEGLowalpha"), [{
                data: eegLowalphaArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGHighalpha(value) {
        if (eegHighalphaArray.length > dataSetLength) {
            eegHighalphaArray = eegHighalphaArray.slice(1);
        }
        if (eegHighalphaArray.length === 0 || value.timestamp > eegHighalphaArray[eegHighalphaArray.length - 1][0]) {
            eegHighalphaArray.push([value.timestamp, value.eegHighalpha]);
            console.log("update " + value.timestamp + value.eegHighalpha);
            $.plot($("#chartEEGHighalpha"), [{
                data: eegHighalphaArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGLowbeta(value) {
        if (eegLowbetaArray.length > dataSetLength) {
            eegLowbetaArray = eegLowbetaArray.slice(1);
        }
        if (eegLowbetaArray.length === 0 || value.timestamp > eegLowbetaArray[eegLowbetaArray.length - 1][0]) {
            eegLowbetaArray.push([value.timestamp, value.eegLowbeta]);
            console.log("update " + value.timestamp + value.eegLowbeta);
            $.plot($("#chartEEGLowbeta"), [{
                data: eegLowbetaArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGHighbeta(value) {
        if (eegHighbetaArray.length > dataSetLength) {
            eegHighbetaArray = eegHighbetaArray.slice(1);
        }
        if (eegHighbetaArray.length === 0 || value.timestamp > eegHighbetaArray[eegHighbetaArray.length - 1][0]) {
            eegHighbetaArray.push([value.timestamp, value.eegHighbeta]);
            console.log("update " + value.timestamp + value.eegHighbeta);
            $.plot($("#chartEEGHighbeta"), [{
                data: eegHighbetaArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGLowgamma(value) {
        if (eegLowgammaArray.length > dataSetLength) {
            eegLowgammaArray = eegLowgammaArray.slice(1);
        }
        if (eegLowgammaArray.length === 0 || value.timestamp > eegLowgammaArray[eegLowgammaArray.length - 1][0]) {
            eegLowgammaArray.push([value.timestamp, value.eegLowgamma]);
            console.log("update " + value.timestamp + value.eegLowgamma);
            $.plot($("#chartEEGLowgamma"), [{
                data: eegLowgammaArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGMidgamma(value) {
        if (eegMidgammaArray.length > dataSetLength) {
            eegMidgammaArray = eegMidgammaArray.slice(1);
        }
        if (eegMidgammaArray.length === 0 || value.timestamp > eegMidgammaArray[eegMidgammaArray.length - 1][0]) {
            eegMidgammaArray.push([value.timestamp, value.eegMidgamma]);
            console.log("update " + value.timestamp + value.eegMidgamma);
            $.plot($("#chartEEGMidgamma"), [{
                data: eegMidgammaArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGAttention(value) {
        if (eegAttentionArray.length > dataSetLength) {
            eegAttentionArray = eegAttentionArray.slice(1);
        }
        if (eegAttentionArray.length === 0 || value.timestamp > eegAttentionArray[eegAttentionArray.length - 1][0]) {
            eegAttentionArray.push([value.timestamp, value.eegAttention]);
            console.log("update " + value.timestamp + value.eegAttention);
            $.plot($("#chartEEGAttention"), [{
                data: eegAttentionArray,
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
     * 根据获取得到的肌电信号的值重绘实时图
     * @param value
     */
    function updateEEGMediation(value) {
        if (eegMediationArray.length > dataSetLength) {
            eegMediationArray = eegMediationArray.slice(1);
        }
        if (eegMediationArray.length === 0 || value.timestamp > eegMediationArray[eegMediationArray.length - 1][0]) {
            eegMediationArray.push([value.timestamp, value.eegMediation]);
            console.log("update " + value.timestamp + value.eegMediation);
            $.plot($("#chartEEGMediation"), [{
                data: eegMediationArray,
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
     * 设置定时器，每隔一定时间发送异步请求
     */
    getCyborgEEG(lastStartTime);
    setInterval(function () {
        // if(!drawing) {
        getCyborgEEG(lastStartTime);
        // }
    }, ajaxInterval);


});








