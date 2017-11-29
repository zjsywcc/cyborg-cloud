var oTable1;
$(document).ready(function(){
    initGroup();
    //添加表单验证
    addFormVaildate();
    //修改表单验证
    editFormVaildate();

    

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
        "sAjaxSource":'../app/list',
        "bServerSide":true,//启用服务器导入
        "sDom":"<'row'<'col-sm-6'l>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "order": [[ 1, 'asc' ]],
        "aoColumns" : [
            {"mDataProp":"name" ,"bVisible": true},
            {"mDataProp":"code" ,"bVisible": true},
            {"mDataProp":"owner" ,"bVisible": true},
            {"mDataProp":"mob" ,"bVisible": true},
            {"mDataProp":"email" ,"bVisible": true},
            {"mDataProp":"id" ,"bVisible": true,
                'fnRender' : function(oObj, sVal) {
                    var toolcol = '<div class="hidden-sm hidden-xs action-buttons">';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:editInfo(\''+sVal+'\')">';
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
 * 获取全部角色
 */
function intiRole() {
    $.ajax({
        url : "../role/all",
        dataType : "json",
        type : "POST",
        async : false,
        data : null,
        error : function(error) {
            $.gritter.add({
                title: '执行失败',
                text: '查询角色信息失败',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){

                var list = JSON.parse(e.data);
                for(var obj in  list){
                    $("#add_role").append("<option value='" + list[obj].id + "'>" + list[obj].role+ "</option>");
                    $("#edit_role").append("<option value='" + list[obj].id + "'>" + list[obj].role+ "</option>");
                }

            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '查询角色信息失败',
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
    $("#del_info").html(name);

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
                    text: 'App信息删除成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: 'App信息删除失败',
                    class_name: 'danger'
                });
            }


        }

    });
}



/**
 * 添加
 */
function add() {
    var option = {
        dataType : 'json',

        success : function(value) {
            $('#add_form').resetForm();// 重置表单

            if(value.code == 0){

                $.gritter.add({
                    title: '执行成功',
                    text: '信息添加成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title : '执行失败',
                    text : '信息添加失败！',
                    class_name : 'danger'
                });
            }
        },
        error : function() {
            $.gritter.add({
                title : '服务器错误',
                text : '对不起，服务器错误，请联系系统管理员！',
                class_name : 'danger'
            });
            // $("#useradd_from").button('reset');
        }
    };
    $("#add_form").ajaxForm(option).submit();
}

/**
 * 提交修改
 */
function update() {
    var option = {
        dataType : 'json',

        success : function(value) {
            $('#edit_form').resetForm();// 重置表单

            if(value.code == 0){

                $.gritter.add({
                    title: '执行成功',
                    text: 'App信息修改成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title : '执行失败',
                    text : 'App信息修改失败！',
                    class_name : 'danger'
                });
            }
        },
        error : function() {
            $.gritter.add({
                title : '服务器错误',
                text : '对不起，服务器错误，请联系系统管理员！',
                class_name : 'danger'
            });
            // $("#useradd_from").button('reset');
        }
    };
    $("#edit_form").ajaxForm(option).submit();
}

/**
 * 添加表单验证
 */
function addFormVaildate() {
    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        var mobile = /^1[34578]\d{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");


    $('#add_form').validate({
        //errorElement : 'div',
        //errorClass : 'help-block',
        focusInvalid : true,
        ignore : "",
        rules : {
            username : {
                required : true,
                maxlength : 200,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {
                        id:null,
                        type:0,
                        value: function() {
                            return $("#add_name").val();
                        }
                    }
                }
            },

            code : {
                required : true,
                maxlength : 100,
                remote: {
                    url: "../app/validateAppInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {
                        id: null,
                        type:1,
                        value: function() {
                            return $("#add_code").val();
                        }
                    }
                }
            },

            email : {
                required : true,
                email : true,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id:null,
                        type:2,
                        value: function() {
                            return $("#add_email").val();
                        }
                    }
                }

            },
            wechat : {
                required : false,
                maxlength : 200,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id:null,
                        type:3,
                        value: function() {
                            return $("#add_wechat").val();
                        }
                    }
                }
            },
            remarks : {
                required : false,
                maxlength : 200,

            },
            mobile : {
                required : false,
                isMobile : true,
                maxlength : 100,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id:null,
                        type:1,
                        value: function() {
                            return $("#add_mobile").val();
                        }
                    }
                }

            }
        },

        messages : {
            username : {
                required : "<br/><span style='color: red;'>请输入用户名称</span>",
                maxlength : "<br/><span style='color: red;'>最大长度不能超过100个字符！</span>",
                remote:"<span style='color: red;'>用户名已经存在</span>"
            },
            code: {
                required : "<br/><span style='color: red;'>请输入应用识别码</span>",
                maxlength : "<br/><span style='color: red;'>最大长度不能超过100个字符！</span>",
                remote:"<span style='color: red;'>应用识别码已经存在</span>"
            },
            email : {
                required : "<br/><span style='color: red;'>请输入邮箱</span>",
                email : "<br/><span style='color: red;'>请输入正确的邮箱！</span>",
                remote:"<span style='color: red;'>电子邮箱已经存在</span>"
            },
            wechat: {
                maxlength : "<br/><span style='color: red;'>请输入字符串长度在200以内！</span>",
                remote:"<span style='color: red;'>微信号已经存在</span>"
            },
            remarks: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            mobile: {

                maxlength : "<span style='color: red;'>最大长度不能超过100个字符</span>",
                isMobile : "<span style='color: red;'>请输入正确的手机号码</span>",
                remote:"<span style='color: red;'>手机号已经存在</span>"
            }
        },


    });

}

/**
 *修改表单验证
 */
function editFormVaildate() {
    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        var mobile = /^1[34578]\d{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");

    $('#edit_form').validate({
        //errorElement : 'div',
        //errorClass : 'help-block',
        focusInvalid : true,
        ignore : "",
        rules : {
            username : {
                required : true,
                maxlength : 200,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {
                        id: function() {
                            return $("#edit_id").val();
                        },
                        type:0,
                        value: function() {
                            return $("#edit_name").val();
                        }
                    }
                }
            },

            code : {
                required : true,
                maxlength : 100,
                remote: {
                    url: "../app/validateAppInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {
                        id: function() {
                            return $("#edit_id").val();
                        },
                        type:1,
                        value: function() {
                            return $("#edit_code").val();
                        }
                    }
                }
            },

            email : {
                required : true,
                email : true,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: function() {
                            return $("#edit_id").val();
                        },
                        type:2,
                        value: function() {
                            return $("#edit_email").val();
                        }
                    }
                }

            },
            wechat : {
                required : false,
                maxlength : 200,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: function() {
                            return $("#edit_id").val();
                        },
                        type:3,
                        value: function() {
                            return $("#edit_wechat").val();
                        }
                    }
                }
            },
            remarks : {
                required : false,
                maxlength : 200,

            },
            mobile : {
                required : false,
                isMobile : true,
                maxlength : 100,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: function() {
                            return $("#edit_id").val();
                        },
                        type:1,
                        value: function() {
                            return $("#edit_mobile").val();
                        }
                    }
                }

            }
        },

        messages : {
            code: {
                required : "<br/><span style='color: red;'>请输入应用识别码</span>",
                maxlength : "<br/><span style='color: red;'>最大长度不能超过100个字符！</span>",
                remote:"<span style='color: red;'>应用识别码已经存在</span>"
            },
            username : {
                required : "<br/><span style='color: red;'>请输入用户名称</span>",
                maxlength : "<br/><span style='color: red;'>最大长度不能超过100个字符！</span>",
                remote:"<span style='color: red;'>用户名已经存在</span>"
            },
            email : {
                required : "<br/><span style='color: red;'>请输入邮箱</span>",
                email : "<br/><span style='color: red;'>请输入正确的邮箱！</span>",
                remote:"<span style='color: red;'>电子邮箱已经存在</span>"
            },
            wechat: {
                maxlength : "<br/><span style='color: red;'>请输入字符串长度在200以内！</span>",
                remote:"<span style='color: red;'>微信号已经存在</span>"
            },
            remarks: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            mobile: {

                maxlength : "<span style='color: red;'>最大长度不能超过100个字符</span>",
                isMobile : "<span style='color: red;'>请输入正确的手机号码</span>",
                remote:"<span style='color: red;'>手机号已经存在</span>"
            }
        },


    });

}

/**
 * 打开编辑
 */
function editInfo(id) {

    $.ajax({
        url : "../app/findById",
        dataType : "json",
        type : "POST",
        async : false,
        data : {id:id},
        error : function(error) {
            $.gritter.add({
                title: '执行失败',
                text: '查询角色信息失败',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){

                var obj = JSON.parse(e.data);

                $("#edit_id").attr("value",obj.id);
                $("#edit_name").attr("value",obj.name);
                $("#edit_code").attr("value",obj.code);
                $("#edit_owner").attr("value",obj.owner);
                $("#edit_mobile").attr("value",obj.mob);
                $("#edit_email").attr("value",obj.email);
                $("#edit_remark").attr("value",obj.remark);
                $("#edit_role").val(obj.roleId);

                $("#editModal").modal('show');

            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '查询角色信息失败',
                    class_name: 'danger'
                });
            }


        }

    });
}
