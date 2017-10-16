$(document).ready(function () {

    initGroup();

});


/***
 * 初始化页面
 */
function initGroup() {
    $("#selectDriverType").change(function () {
        var driverType = $("#selectDriverType").find("option:selected").text();
        $("#searchDriverType").val(driverType);
        var date = $("#searchCreateDate").val();
        getReport(driverType);
        getPieChart(date, driverType);
        getDailyReport(date, driverType);
    });
    var driverType = $("#selectDriverType").find("option:selected").text();
    getReportPeriod(driverType);
    Date.prototype.yyyymmdd = function () {
        var mm = this.getMonth() + 1; // getMonth() is zero-based
        var dd = this.getDate();

        return [this.getFullYear(),
            (mm > 9 ? '' : '0') + mm,
            (dd > 9 ? '' : '0') + dd
        ].join('-');
    };
    var date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
    console.log(date.yyyymmdd());
    $('#searchCreateDate').val(date.yyyymmdd());
    getDailyReport(date.yyyymmdd(), driverType);
    getPieChart(date.yyyymmdd(), driverType);
    $("#searchCreateDate").change(function () {
        var driverType = $("#selectDriverType").find("option:selected").text();
        var value = $("#searchCreateDate").val();
        getDailyReport(value, driverType);
        getPieChart(value, driverType);
    });
}

/**
 *  定时获取申请记录数目
 */
function getReportPeriod(driverType) {
    getReport(driverType);
    var interval = setInterval(function () {
        getReport(driverType)
    }, 30000);
}

/**
 * 根据date(YYYY-MM-DD)获取日报表
 * @param date
 */
function getDailyReport(date, driverType) {
    $.ajax({
        url: "../report/getDailyReport",
        dataType: "json",
        type: "POST",
        async: true,
        data: {
            date: date,
            driverType: driverType
        },
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '服务器异常',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {

                var obj = JSON.parse(e.data);

                $("#smsDaily").text(obj.smsTotalDaily);
                $("#smsSentDaily").text(obj.smsSentDaily);
                $("#submitDaily").text(obj.submitTotal);
                $("#submitRatioDaily").text(((obj.submitSuccess / obj.submitTotal) * 100).toFixed(0) + "%");
                $("#submitSuccessDaily").text(obj.submitSuccess);
                $("#strategyRatioDaily").text(((obj.strategyCount / obj.submitSuccess) * 100).toFixed(0) + "%");
                $("#strategyDaily").text(obj.strategyCount);
                $("#passRatioDaily").text(((obj.passDaily / obj.strategyCount) * 100).toFixed(0) + "%");
                $("#passDaily").text(obj.passDaily);
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

function getReport(driverType) {
    $.ajax({
        url: "../report/getReport",
        dataType: "json",
        type: "POST",
        async: true,
        data: {
            driverType: driverType
        },
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '服务器异常',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {

                var obj = JSON.parse(e.data);

                $("#totalCount").text(obj.totalCount);
                $("#submitCount").text(obj.submitCount);
                $("#submitRatio").text(((obj.submitCount / obj.totalCount) * 100).toFixed(0) + "%");
                $("#permitCount").text(obj.permitCount);
                $("#permitRatio").text(((obj.permitCount / obj.submitCount) * 100).toFixed(0) + "%");
                $("#smsSentCount").text(obj.smsSentCount);
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
 * 根据date(YYYY-MM-DD)获取原因分布饼形图
 * @param date
 */
function getPieChart(date, driverType) {
    $.ajax({
        url: "../report/getPieChart",
        dataType: "json",
        type: "POST",
        async: true,
        data: {
            date: date,
            driverType: driverType
        },
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '服务器异常',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {

                var obj = JSON.parse(e.data);
                // var data = [];
                // for(i in obj) {
                //     data.push({"name":i,"y":obj[i]});
                // }
                setPieChart(obj, date);
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

function setPieChart(data, date) {
    Highcharts.chart('pieChart', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: date + ' 不通过原因分布'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' +
            '<br>数量: <b>{point.y:.0f}</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: '占比',
            colorByPoint: true,
            data: data
        }]
    });
}





