var oTable1;
$(document).ready(function(){
   
    $('.md-trigger').modalEffects();
    initGroup();
});



/***
 * 初始化管理列表
 */
function initGroup(){
    oTable1 = $('#datatable').dataTable({
        "bSort": true,//true每列可以排序，false不允许排序
        "bProcessing": true,//载入数据时显示进度条
        'bPaginate': true, //是否显示分页menus
        //'sPaginationType':'two_button',//分页样式，支持两种内置方式，two_button 和 full_numbers, 默认使用 two_button。
        "bLengthChange": true,
        "bAutoWidth":true,//自动计算宽度 
        "bFilter":true,
        "sAjaxSource":'list_uncheck',
        "bServerSide":true,//启用服务器导入
        "sDom":"<'row'<'col-sm-6'l>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "order": [[ 1, 'asc' ]],
        "aoColumns" : [
            {"mDataProp":"name" ,"bVisible": true},
            {"mDataProp":"phone" ,"bVisible": true},
            {"mDataProp":"faultshow" ,"bVisible": true},
            {"mDataProp":"time" ,"bVisible": true,
                'fnRender': function (oObj, sVal) {

                return new Date(sVal).toLocaleString();

            }},
            {"mDataProp":"worker" ,"bVisible": true},
            {"mDataProp":"status" ,"bVisible": true,
                'fnRender' : function(oObj, sVal) {

                    var result = "";

                    switch (sVal){
                        case 0:
                            result='<span class="label label-danger">未处理</span>';
                            break;
                        case 1:
                            result='<span class="label label-warning">处理中</span>';
                            break;
                        case 2:
                            result='<span class="label label-success">处理完成</span>';
                            break;

                    }
                    return result;

                }

            },

            {"mDataProp":"id" ,"bVisible": true,
                'fnRender' : function(oObj, sVal) {
                    var toolcol = '<div class="hidden-sm hidden-xs action-buttons">';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:addTraceModal(\''+sVal+'\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-plus-circle bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:checkedModal(\''+oObj.aData.id+'\',\''+oObj.aData.name+'\',\''+oObj.aData.faultshow+'\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-check-circle bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol +	'</div>';
                    toolcol = toolcol +	'<div class="hidden-md hidden-lg">';
                    toolcol = toolcol +		'<div class="inline position-relative">';
                    toolcol = toolcol +			'<button';
                    toolcol = toolcol +				'class="btn btn-minier btn-yellow dropdown-toggle"';
                    toolcol = toolcol +				'data-toggle="dropdown" data-position="auto">';
                    toolcol = toolcol +				'<i';
                    toolcol = toolcol +					'class="ace-icon fa fa-caret-down icon-only bigger-120"></i>';
                    toolcol = toolcol +			'</button>';
                    toolcol = toolcol +			'<ul';
                    toolcol = toolcol +			'class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">';
                    toolcol = toolcol +				'<li><a href="#" class="tooltip-info"';
                    toolcol = toolcol +				'data-rel="tooltip" title="跟踪"> <span';
                    toolcol = toolcol +					'class="blue"> <i';
                    toolcol = toolcol +						'class="ace-icon fa  fa-plus-circle bigger-120"></i>';
                    toolcol = toolcol +					'</span>';
                    toolcol = toolcol +				'</a></li>';
                    toolcol = toolcol +				'<li><a href="#" class="tooltip-info"';
                    toolcol = toolcol +				'data-rel="tooltip" title="View"> <span';
                    toolcol = toolcol +					'class="blue"> <i';
                    toolcol = toolcol +						'class="ace-icon fa fa-search-plus bigger-120"></i>';
                    toolcol = toolcol +					'</span>';
                    toolcol = toolcol +				'</a></li>';
                    toolcol = toolcol +				'<li><a href="#" class="tooltip-success"';
                    toolcol = toolcol +				'data-rel="tooltip" title="Edit"> <span';
                    toolcol = toolcol +					'class="green"> <i';
                    toolcol = toolcol +						'class="ace-icon fa fa-pencil-square-o bigger-120"></i>';
                    toolcol = toolcol +				'</span>';
                    toolcol = toolcol +			'</a></li>';
                    toolcol = toolcol +			'<li>';
                    toolcol = toolcol +					'<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">';
                    toolcol = toolcol +						'<span class="red">';
                    toolcol = toolcol +						'<i class="ace-icon fa fa-trash-o bigger-120"></i>';
                    toolcol = toolcol +					'</span>';
                    toolcol = toolcol +				'</a>';
                    toolcol = toolcol +			'</li>';
                    toolcol = toolcol +	'</ul>';
                    toolcol = toolcol +'</div>';
                    toolcol = toolcol +'</div>';
                    return toolcol;
                }

            } ],
        "oLanguage" : {
            "sProcessing" : "正在加载中......",
            "sLengthMenu" : "每页显示 _MENU_ 条记录",
            "sZeroRecords" : "对不起，查询不到相关数据！",
            "sEmptyTable" : "表中无数据存在！",
            "sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
            "sInfoEmpty": "当前显示 0 到 0 条,共 0 条记录",
            "sSearch" : "搜索",
            "oPaginate" : {
                "sFirst" : "首页",
                "sPrevious" : "上一页",
                "sNext" : "下一页",
                "sLast" : "末页"
            }
        }
    });
}




/**
 * 打开添加故障跟踪窗口
 * @param id
 */
function addTraceModal(id) {

    $("#add_trace_report").attr("value",id);
    getTrace(id);
    $("#addTraceModal").niftyModal('show');


}

/**
 * 添加故障跟踪
 */
function addTrace() {
    var reportid=$("#add_trace_report").val();
    var content=$("#add_trace_show").val();
    $.ajax({
        url : "addTrace",
        dataType : "json",
        type : "POST",
        async : false,
        data : {reportid:reportid,content:content},
        error : function(error) {
            $.gritter.add({
                title: '执行失败',
                text: '添加跟踪信息失败,请重新登录!',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                getTrace(reportid);
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '查询跟踪信息失败',
                    class_name: 'danger'
                });
            }


        }

    });
    //$("#addTraceModal").niftyModal('hide');


}

/**
 * 获得故障
 * @param id
 */
function getTrace(id) {



    $.ajax({
        url : "getTraceByID",
        dataType : "json",
        type : "POST",
        async : false,
        data : {id:id},
        error : function(error) {
            $.gritter.add({
                title: '执行失败',
                text: '查询跟踪信息失败,请重新登录!',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                //清空list
                $("#tracelist").empty();
                var data = eval(e.data);
                for(var obj in data){

                    var date = new Date(data[obj].time);

                    var content = '<p><strong>时间:</strong>'+date.toTimeString()+'<br/><strong>描述:</strong>'+data[obj].content+'<br/><strong>报告人:</strong>'+data[obj].reporterId+'</p>';
                    var li = '<li><i class="fa fa-comment"></i><span class="date">'+date.getHours()+":"+date.getMinutes()+'</span><div class="content">'+content+'</div> </li>';

                    $("#tracelist").append(li);
                }
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '查询跟踪信息失败',
                    class_name: 'danger'
                });
            }


        }

    });


}

/**
 * 打开确认窗口
 * @param data
 */
function checkedModal(id,name,faultshow) {

    $("#check_id").attr("value",id);

    $("#check_msg").attr("客户名称:"+name+"故障描述:"+faultshow);

    $("#checkModal").modal('show');
}

/**
 * 故障修复确认
 */
function checkedOK() {

    var id = $("#check_id").val();

    $.ajax({
        url : "report_status_update",
        dataType : "json",
        type : "POST",
        async : false,
        data : {id:id},
        error : function(error) {
            $.gritter.add({
                title: '执行失败',
                text: '确认修复失败,请重新登录!',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                $.gritter.add({
                    title: '执行成功',
                    text: '确认修复成功',
                    class_name: 'sucess'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();//重新获取数据
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '确认修复失败',
                    class_name: 'danger'
                });
            }


        }

    });
}

