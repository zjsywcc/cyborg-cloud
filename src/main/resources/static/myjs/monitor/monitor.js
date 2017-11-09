$(document).ready(function () {

    var emgArray = [], ajaxInterval = 60 * 1000, updateInterval = 500;

    /**
     * 由于后台数据是假的 所以定时器请求可能得到比正在绘制的更旧的时间戳
     * 暂时丑陋地加上了一个正在绘制的状态
     * 目前会导致最多慢一分钟的更新
     * TODO 等待数据库建立后 增加数据已经获取的状态 就不怕重新获取了
     * @type {boolean}
     */
    var drawing = false;
    /**
     *实时获取电子人肌电信号数据
     */
    function getCyborgEMG() {
        $.ajax({
            url: "../monitor/getCyborgEMG",
            dataType: "json",
            type: "POST",
            async: true,
            data: null,
            error: function (error) {
                console.log(error.responseText);
            },
            success: function (e) {
                if (e.code == 0) {
                    var emgPacket = JSON.parse(e.data);
                    refreshLoop(update, emgPacket, emgPacket.length);
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
     * @param func
     * @param array
     * @param loopTimes
     */
    function refreshLoop(func, array, loopTimes) {
        var index = 0;
        var interv = function () {
            drawing = true;
            console.log(array[index].timestamp);
            func(array[index]);
            var now = array[index].timestamp;
            index++;
            if(index >= loopTimes) {
                drawing = false;
                return;
            }
            setTimeout(interv, array[index].timestamp - now);
        };
        var now = new Date().getTime();
        var startTime = array[0].timestamp;
        if(startTime > now) {
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
        if (emgArray.length > 120)
            emgArray = emgArray.slice(1);
        emgArray.push([value.timestamp, value.value]);
        console.log("update " + value);
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

    /**
     * 设置定时器，每隔一定时间发送异步请求
     */
    getCyborgEMG();
    setInterval(function () {
        if(!drawing) {
            getCyborgEMG()
        }
    }, ajaxInterval);

});










