var oTable1;
$(document).ready(function(){
    initGroup();
    //initPermission();
    addForm();
    editForm();
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
        "sAjaxSource":'list',
        "bServerSide":true,//启用服务器导入
        "sDom":"<'row'<'col-sm-6'l>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "order": [[ 1, 'asc' ]],
        "aoColumns" : [
            {"mDataProp":"name" ,"bVisible": true},
            {"mDataProp":"desc" ,"bVisible": true},

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
 * 初始化权限
 */
function initPermission() {
    $.ajax({
        url : "../module/find_by_level",
        dataType : "json",
        type : "POST",
        async : false,
        data : {level:0},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                var data = eval(e.data);
                for(var obj in  data){
                var html = '<tr>'
                    +'<td><strong>'+data[obj].name+'</strong></td>'
                    +'<td><div class="switch switch-small"><input name="create" type="checkbox" /></div></td>'
                    +'<td><div class="switch switch-small"><input name="delete" type="checkbox" /></div></td>'
                    +'<td><div class="switch switch-small"><input name="update" type="checkbox" /></div></td>'
                    +'<td><div class="switch switch-small"><input name="view" type="checkbox" /></div></td>'
                    +'</tr>';

                    $("#add_permission").append(html);
                }


            }


        }

    });
}


/**
 * 添加表单提交
 */
function addForm() {

    var ajaxFormOption = {
        type: "post",  //提交方式
        dataType: "json", //数据类型
        //data: myData,//自定义数据参数，视情况添加
        url: "add", //请求url
        error:function (data) {
            $.gritter.add({
                title: '执行失败',
                text: '角色信息添加失败',
                class_name: 'danger'
            });
        },
        success: function (data) { //提交成功的回调函数
            $.gritter.add({
                title: '执行成功',
                text: '角色信息添加成功',
                class_name: 'success'
            });
            oTable1.fnClearTable(); //清空数据
            oTable1.fnDraw();
            $("#addModal").modal('hide');
        }
    };
    
    $("#add_form").ajaxForm(ajaxFormOption);
}



/**
 * 添加表单提交
 */
function editForm() {

    var ajaxFormOption = {
        type: "post",  //提交方式
        dataType: "json", //数据类型
        //data: myData,//自定义数据参数，视情况添加
        url: "update", //请求url
        error:function (data) {
            $.gritter.add({
                title: '执行失败',
                text: '角色信息修改失败',
                class_name: 'danger'
            });
        },
        success: function (data) { //提交成功的回调函数
            $.gritter.add({
                title: '执行成功',
                text: '角色信息修改成功',
                class_name: 'success'
            });
            oTable1.fnClearTable(); //清空数据
            oTable1.fnDraw();
            $("#editModal").modal('hide');
        }
    };

    $("#edit_form").ajaxForm(ajaxFormOption);
}



/**
 * 打开删除框
 * @param id
 */
function deleteInfo(id,name) {

    $("#del_id").attr("value",id);
    $("#del_name").html(name);

    $("#deleteModal").modal('show');
}

/**
 * 确认删除
 */
function deleteOk() {

    var id= $("#del_id").val();

    $.ajax({
        url : "delete",
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
                    text: '角色信息删除成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
                $("#deleteModal").modal('hide');
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '角色信息删除失败',
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
        url : "findById",
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

                $.each(data.permissions,function(name,value) {
                    for(var obj in value){
                        //var id = "edit_"+value[obj]+"_"+name;
                        //$("#"+id).iCheck('check');
                        //$('#input-1, #input-3').iCheck('check');
                        /* $("#edit_view_585e01acd4c614adb857df7c").attr("checked","true");
                         var chk = document.getElementById('edit_delete_585e01acd4c614adb857df7c');
                         chk.checked = "true";*/
                    }


                });

                $("#edit_id").attr("value",data.id);
                $("#edit_level").val(data.level);

                $("#edit_name").attr("value",data.role);
                if(data.description != null){
                    $("#edit_des").attr("value",data.description);
                }
                if(data.remark != null){
                    $("#edit_remark").attr("value",data.remark);
                }


                $("#editModal").modal('show');

            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '角色信息查询失败',
                    class_name: 'danger'
                });
            }


        }

    });
}