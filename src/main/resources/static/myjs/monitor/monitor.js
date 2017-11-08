$(document).ready(function () {

    var emgArray = [], now = 0, updateInterval = 500;

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
                    var obj = JSON.parse(e.data);
                    var emgValue = obj.value;
                    update(emgValue);
                    console.log(emgValue);
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
    function update(value) {
        if (emgArray.length > 200)
            emgArray = emgArray.slice(1);
        now += 1;
        emgArray.push([now, value]);
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
                ticks: 11,
                tickDecimals: 0
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
    setInterval(function () {
        getCyborgEMG()
    }, updateInterval);

});










