var oTable1;
$(document).ready(function(){
    initGroup();
    //初始化搜索
    initGaoDeMapSearch();
});



/***
 * 初始化管理列表
 */
function initGroup(){
    oTable1 = $('#datatable').dataTable({
        "bSort": false,//true每列可以排序，false不允许排序
        "bProcessing": true,//载入数据时显示进度条
        'bPaginate': true, //是否显示分页menus
        //'sPaginationType':'two_button',//分页样式，支持两种内置方式，two_button 和 full_numbers, 默认使用 two_button。
        "bLengthChange": true,
        "bAutoWidth":true,//自动计算宽度 
        "bFilter":true,
        "sAjaxSource":'list',
        "bServerSide":true,//启用服务器导入
        "sDom":"<'row'<'col-sm-6'l>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "order": [[ 1, 'asc' ]],
        "aoColumns" : [
            {"mDataProp":"type" ,"bVisible": true,
                'fnRender' : function(oObj, sVal) {

                    var result = "";

                    switch (sVal){
                        case 1:
                            result="A";
                            break;
                        case 2:
                            result="B";
                            break;
                        case 3:
                            result="C";
                            break;

                    }
                    return result;

                }

            },
            {"mDataProp":"name" ,"bVisible": true},
            {"mDataProp":"contact" ,"bVisible": true},
            {"mDataProp":"mob" ,"bVisible": true},
            {"mDataProp":"address" ,"bVisible": true},
            {"mDataProp":"id" ,"bVisible": true,
                'fnRender' : function(oObj, sVal) {
                    var toolcol = '<div class="hidden-sm hidden-xs action-buttons">';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:editinfo(\''+sVal+'\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-pencil bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol + '<shiro:hasPermission name="alarm:delete"><a class="red" href="javascript:deleteInfo(\''+sVal+'\',\''+oObj.aData.name+'\')">';
                    toolcol = toolcol +			'<i class="ace-icon fa fa-trash-o bigger-130"></i>';
                    toolcol = toolcol +		'</a></shiro:hasPermission>';
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
 * 添加功能模块信息
 */
function add() {

    var name=$("#add_client").val();
    var type=$("#add_type").val();
    var contact=$("#add_contact").val();
    var address=$("#add_address").val();
    var mob=$("#add_mob").val();
    var tel=$("#add_telphone").val();
    var ip=$("#add_ip").val();
    var access=$("#add_accessNo").val();
    var olt=$("#add_olt").val();
    var remark=$("#add_remark").val();
    
    $.ajax({
        url : "../client/add",
        dataType : "json",
        type : "POST",
        async : false,
        data : {name:name, type:type, mobPhone:mob, telPhone:tel, address:address,contact:contact,OLT:olt,ip:ip,accessNo:access,remark:remark},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                $.gritter.add({
                    title: '执行成功',
                    text: '客户信息添加成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '客户信息添加失败',
                    class_name: 'danger'
                });
            }


        }

    });
}

/**
 *打开编辑框
 */
function editinfo(id) {
    $.ajax({
        url : "../client/getById",
        dataType : "json",
        type : "POST",
        async : false,
        data : {id:id},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){

                var data = JSON.parse(e.data);

                $("#edit_id").attr("value",data.id);
                $("#edit_client").attr("value",data.name);
                $("#edit_type").attr("value",data.type);
                $("#edit_contact").attr("value",data.contact);
                $("#edit_address").attr("value",data.address);
                $("#edit_mob").attr("value",data.mobPhone);
                $("#edit_telphone").attr("value",data.telePhone);
                $("#edit_ip").attr("value",data.ip);
                $("#edit_accessNo").attr("value",data.accessNo);
                $("#edit_olt").attr("value",data.oLT);
                $("#edit_remark").attr("value",data.remark);
                $("#edit_lat").attr("value",data.lat);
                $("#edit_lng").attr("value",data.lng);

                $("#editModal").modal('show');
                //$("#editModal").modal('show');

            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '客户信息查询失败',
                    class_name: 'danger'
                });
            }


        }

    });
}

/**
 * 更新信息
 */
function update() {

    var id = $("#edit_id").val();
    var name = $("#edit_client").val();
    var type = $("#edit_type").val();
    var contact = $("#edit_contact").val();
    var address = $("#edit_address").val();
    var mob = $("#edit_mob").val();
    var tel = $("#edit_telphone").val();
    var ip = $("#edit_ip").val();
    var accessNo = $("#edit_accessNo").val();
    var olt = $("#edit_olt").val();
    var remark = $("#edit_remark").val();
    var lat = $("#edit_lat").val();
    var lng = $("#edit_lng").val();

    $.ajax({
        url : "../client/update",
        dataType : "json",
        type : "POST",
        async : false,
        data : {lat:lat,lng:lng,id:id,name:name, type:type, mobPhone:mob, telPhone:tel, address:address,contact:contact,OLT:olt,ip:ip,accessNo:accessNo,remark:remark},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                $.gritter.add({
                    title: '执行成功',
                    text: '客户信息修改成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '客户信息修改失败',
                    class_name: 'danger'
                });
            }


        }

    });

}

/**
 * 打开删除框
 * @param id
 */
function deleteInfo(id,name) {

    $("#del_id").attr("value",id);
    $("#del_info").attr("value",name);

    $("#deleteModal").modal('show');
}

/**
 * 确认删除
 */
function deleteOk() {

    var id= $("#del_id").val();

    $.ajax({
        url : "../client/del",
        dataType : "json",
        type : "POST",
        async : false,
        data : {id:id},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                $.gritter.add({
                    title: '执行成功',
                    text: '客户信息删除成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '客户信息删除失败',
                    class_name: 'danger'
                });
            }


        }

    });
}




/**
 * 搜索查询接口
 */
function search() {

    //自己定义的搜索框，可以是时间控件，或者checkbox 等等
    var client = $("#search_name").val();
    var contact = $("#search_contact").val();
    var mob = $("#search_mob").val();

    var param = {client:client,contact:contact,mob:mob};

    //用空格隔开，达到多条件搜索的效果，相当于两个关键字
    oTable1.fnFilter(JSON.stringify(param));

}



function initGaoDeMapSearch() {
    AMap.plugin('AMap.Autocomplete',function(){//回调函数
        //实例化Autocomplete
        var autoOptions = {
            city: "", //城市，默认全国
            input:"search_name"//使用联想输入的input的id
        };
        autocomplete= new AMap.Autocomplete(autoOptions);
        //TODO: 使用autocomplete对象调用相关功能
    })


    var autoOptions = {
        city: "" //城市，默认全国
    };
    autocomplete= new AMap.Autocomplete(autoOptions);
    autocomplete.search(keywords, function(status, result){
        //TODO:开发者使用result自己进行下拉列表的显示与交互功能
    });

}
