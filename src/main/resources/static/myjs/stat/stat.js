$(document).ready(function () {
    setDate();
    show();
});

function setDate() {
    Date.prototype.yyyymmdd = function () {
        var mm = this.getMonth() + 1; // getMonth() is zero-based
        var dd = this.getDate();

        return [this.getFullYear(),
            (mm > 9 ? '' : '0') + mm,
            (dd > 9 ? '' : '0') + dd
        ].join('-');
    };
    var date = new Date(new Date().getTime());
    console.log(date.yyyymmdd());
    $('#date6').val(date.yyyymmdd());
}

function show() {
    var DATE = $("#date6").val();
    var ITEM = $("#item6").val();
    var DANWEI = $("#danwei6").val();
    $.ajax({
        url: "../stat/getStatistics",
        dataType: "json",
        type: "POST",
        async: true,
        data: {
            item: ITEM,
            date: DATE,
            danwei: DANWEI
        },
        error: function (error) {
            console.log(error.responseText);
        },
        success: function (e) {
            console.log("e=");
            console.log(e);
            if (e.code === 0) {
                console.log("data=" + e.data);
                var statByHour = JSON.parse(e.data);
                if (statByHour.length > 0) {
                    var x = [], y = [], z = [];
                    $.each(statByHour, function (value) {
                        x.push(statByHour[value].hour);
                        y.push(statByHour[value].accuracy);
                        z.push(statByHour[value].timing);
                    });
                    drawChart(x, y, z);
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

function drawChart(x, y, z) {
    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('com_stats'));

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
            data: ['疲劳预测的准确率', '疲劳预测的耗时']
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
                data: x
            }
        ],
        yAxis: [
            {
                type: 'value',
                scale: true,
                name: '准确率(%)',
                max: 100,
                min: 0,
                boundaryGap: [0.2, 0.2]
            },
            {
                type: 'value',
                scale: true,
                name: '耗时(ms)',
                max: 1200,
                min: 0,
                boundaryGap: [0.2, 0.2]
            }
        ],
        series: [
            {
                name: "疲劳预测的准确率",
                type: "line",
                yAxisIndex: 0,
                data: y,
                smooth: true,
                itemStyle: {
                    normal: {
                        color: '#1da2d4'
                    }
                }
            },
            {
                name: "疲劳预测的耗时",
                type: "bar",
                yAxisIndex: 1,
                data: z,
                itemStyle: {
                    normal: {
                        color: '#11d474'
                    }
                }
            }

        ]
    };

    // 为echarts对象加载数据
    myChart.setOption(option);

    // option = {
    //     title: {
    //         text: '动态数据',
    //         subtext: '纯属虚构'
    //     },
    //     tooltip: {
    //         trigger: 'axis',
    //         axisPointer: {
    //             type: 'cross',
    //             label: {
    //                 backgroundColor: '#283b56'
    //             }
    //         }
    //     },
    //     legend: {
    //         data: ['最新成交价', '预购队列']
    //     },
    //     toolbox: {
    //         show: true,
    //         feature: {
    //             dataView: {readOnly: false},
    //             restore: {},
    //             saveAsImage: {}
    //         }
    //     },
    //     dataZoom: {
    //         show: false,
    //         start: 0,
    //         end: 100
    //     },
    //     xAxis: [
    //         {
    //             type: 'category',
    //             boundaryGap: true,
    //             data: (function () {
    //                 var now = new Date();
    //                 var res = [];
    //                 var len = 10;
    //                 while (len--) {
    //                     res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
    //                     now = new Date(now - 2000);
    //                 }
    //                 return res;
    //             })()
    //         },
    //         {
    //             type: 'category',
    //             boundaryGap: true,
    //             data: (function () {
    //                 var res = [];
    //                 var len = 10;
    //                 while (len--) {
    //                     res.push(len + 1);
    //                 }
    //                 return res;
    //             })()
    //         }
    //     ],
    //     yAxis: [
    //         {
    //             type: 'value',
    //             scale: true,
    //             name: '价格',
    //             max: 30,
    //             min: 0,
    //             boundaryGap: [0.2, 0.2]
    //         },
    //         {
    //             type: 'value',
    //             scale: true,
    //             name: '预购量',
    //             max: 1200,
    //             min: 0,
    //             boundaryGap: [0.2, 0.2]
    //         }
    //     ],
    //     series: [
    //         {
    //             name: '预购队列',
    //             type: 'bar',
    //             xAxisIndex: 1,
    //             yAxisIndex: 1,
    //             data: (function () {
    //                 var res = [];
    //                 var len = 10;
    //                 while (len--) {
    //                     res.push(Math.round(Math.random() * 1000));
    //                 }
    //                 return res;
    //             })()
    //         },
    //         {
    //             name: '最新成交价',
    //             type: 'line',
    //             data: (function () {
    //                 var res = [];
    //                 var len = 0;
    //                 while (len < 10) {
    //                     res.push((Math.random() * 10 + 5).toFixed(1) - 0);
    //                     len++;
    //                 }
    //                 return res;
    //             })()
    //         }
    //     ]
    // };
    //
    // count = 11;
    // setInterval(function () {
    //     axisData = (new Date()).toLocaleTimeString().replace(/^\D*/, '');
    //
    //     var data0 = option.series[0].data;
    //     var data1 = option.series[1].data;
    //     data0.shift();
    //     data0.push(Math.round(Math.random() * 1000));
    //     data1.shift();
    //     data1.push((Math.random() * 10 + 5).toFixed(1) - 0);
    //
    //     option.xAxis[0].data.shift();
    //     option.xAxis[0].data.push(axisData);
    //     option.xAxis[1].data.shift();
    //     option.xAxis[1].data.push(count++);
    //
    //     myChart.setOption(option);
    // }, 2100);




}



