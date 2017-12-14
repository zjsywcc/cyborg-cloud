/**
 * Created by user on 16/12/25.
 */


$(document).ready(function(){
    
   // App.dashBoard();
    App.charts();
    getRealTimeDate();

    getSevenData();


});


function initTimeChart(data) {

    var plot_statistics2 = $.plot($("#show_time"), [{
        data: data,
        label: "Unique Visits"
    }
    ], {
        series: {
            bars: {
                show: true,
                barWidth: 0.6,
                lineWidth: 0,
                fill: true,
                hoverable: true,
                fillColor: {
                    colors: [{
                        opacity: 1
                    }, {
                        opacity: 1
                    }
                    ]
                }
            },
            shadowSize: 2
        },
        legend:{
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
        colors: ["#FD6A5E", "#FFFFFF", "#52e136"],
        xaxis: {
            ticks: 11,
            tickDecimals: 0
        },
        yaxis: {
            ticks: 6,
            tickDecimals: 0
        }
    });

}

/**
 * 饼形图展示故障类别
 */
function initPieChart(data) {
    /*Pie Chart*/
    /*var data = [
        { label: "Google", data: 50},
        { label: "Dribbble", data: 7},
        { label: "Twitter", data: 8},
        { label: "Youtube", data: 9},
        { label: "Microsoft", data: 14},
        { label: "Apple", data: 13},
        { label: "Amazon", data: 10},
        { label: "Facebook", data: 5}
    ];*/

    $.plot('#show_pie', data, {
        series: {
            pie: {
                show: true,
                innerRadius: 0.27,
                shadow:{
                    top: 5,
                    left: 15,
                    alpha:0.3
                },
                stroke:{
                    width:0
                },
                label: {
                    show: true,
                    formatter: function (label, series) {
                        return '<div style="font-size:12px;text-align:center;padding:2px;color:#333;">' + label + '</div>';

                    }
                },
                highlight:{
                    opacity: 0.08
                }
            }
        },
        grid: {
            hoverable: true,
            clickable: true
        },
        colors: ["#5793f3", "#dd4d79", "#bd3b47","#dd4444","#fd9c35","#fec42c","#d4df5a","#5578c2"],
        legend: {
            show: false
        }
    });
}

/**
 * 获得实时数据
 */
function getRealTimeDate() {

    $.ajax({
        url : "../report_statistics/get_day_realtime",
        dataType : "json",
        type : "POST",
        async : false,
        data : null,
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                var data = JSON.parse(e.data);

                $("#today_count").html(data.count_all);
                $("#today_finish").html(data.count_finish);
                $("#today_unfinish").html(data.count_all-data.count_finish);

                $("#today_avgTime").html(data.avg_time);

            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '查询信息失败',
                    class_name: 'danger'
                });
            }


        }

    });
    
    
}


/**
 * 查询7天数据
 */
function getSevenData() {

    $.ajax({
        url : "../report_statistics/get_day_statistics",
        dataType : "json",
        type : "POST",
        async : false,
        data : {day:7},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            var data=new Array();

            if(e.code == 0){
                var data = JSON.parse(e.data);
                var report_sum=0;
                var report_finish=0;
                for(var obj in  data){
                    if(data[obj].dayReportSum!=undefined && data[obj].dayReportSum!=null){
                        report_sum = report_sum + data[obj].dayReportSum;
                    }
                    if(data[obj].dayFinishSum!=undefined && data[obj].dayFinishSum!=null) {
                        report_finish = report_finish + data[obj].dayFinishSum;
                    }
                }
                $("#seven_day_sum").html(report_sum);
                if(report_sum != 0){
                    $("#seven_day_finish").html(report_finish/report_sum*100);
                }else{
                    $("#seven_day_finish").html(0);
                }
                

                

            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '查询信息失败',
                    class_name: 'danger'
                });
            }


        }

    });




    $.ajax({
        url : "../report_statistics/get_report_type_chart",
        dataType : "json",
        type : "POST",
        async : false,
        data : {day:7},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            var chardata=new Array();

            if(e.code == 0){
                var data = JSON.parse(e.data);

                chardata.push({label:"网络缓慢",data:data.report_type["网络缓慢"]});
                chardata.push({label:"断网",data:data.report_type["断网"]});
                chardata.push({label:"指定IP无法访问",data:data.report_type["指定IP无法访问"]});
                chardata.push({label:"端口异常",data:data.report_type["端口异常"]});
                chardata.push({label:"DNS异常",data:data.report_type["DNS异常"]});
                chardata.push({label:"其他",data:data.report_type["其他"]});
                initPieChart(chardata);


                //平均处理时间
                var timeData = new Array();
                for(var i=0;i<data.report_seven_time.length;i++){
                    timeData[i]=new Array(i,data.report_seven_time[i].dayTimeAvg);
                }

                initTimeChart(timeData);



            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '查询信息失败',
                    class_name: 'danger'
                });
            }


        }

    });


}

