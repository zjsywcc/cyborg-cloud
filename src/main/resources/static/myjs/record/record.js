var oTable1;
$(document).ready(function () {

    // getSmsStatus();

    initGroup();

    //添加表单验证
    addFormVaildate();
    //修改表单验证
    editFormVaildate();


});

/**
 * 短信开关触发
 */
function taskSwitch() {

   
    
    var status = $('#taskSwitch').val();
    
    
    $.ajax({
        url: "smsSwitch",
        dataType: "json",
        type: "POST",
        async: false,
        data: {status:status},
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '更改状态失败',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {
            $.gritter.add({
                title: '执行成功',
                text: '更改状态成功',
                class_name: 'success'
            });

        }

    });

}


/**
 * 打开短信设置
 */
function openSmsSetting() {
    
    $("#smsModal").modal('show');
}

/**
 * 查询sms状态
 */
function getSmsStatus() {
    $.ajax({
        url: "getSmsSwitch",
        dataType: "json",
        type: "POST",
        async: false,
        data: null,
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '发送信息失败',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 200) {


                $('#taskSwitch').val(e.data);

            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '查询开关状态失败',
                    class_name: 'danger'
                });
            }


        }});
}


/**
 * 开始发送短信通知
 */
function sendSMS(){
    var start=$("#start_time").val();
    var end=$("#end_time").val();

    $.ajax({
        url: "../record/sendResultSms",
        dataType: "json",
        type: "POST",
        async: false,
        data: {start:start,end:end},
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '发送信息失败',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 200) {

                $.gritter.add({
                    title: '执行成功',
                    text: '发送信息成功',
                    class_name: 'success'
                });

            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '发送信息失败',
                    class_name: 'danger'
                });
            }


        }

    });
    
    
}


/***
 * 初始化管理列表
 */
function initGroup() {
    oTable1 = $('#datatable').dataTable({
        "ordering": true,
        "searching": true,
        "bSort": false,//true每列可以排序，false不允许排序
        "bProcessing": true,//载入数据时显示进度条
        'bPaginate': true, //是否显示分页menus
        "sPaginationType": "input",
        //'sPaginationType':'two_button',//分页样式，支持两种内置方式，two_button 和 full_numbers, 默认使用 two_button。
        "bLengthChange": true,
        "bAutoWidth": false,//自动计算宽度 
        "bFilter": true,
        "sAjaxSource": '../record/list',
        "bServerSide": true,//启用服务器导入
        "sDom": "<'row'<'col-sm-6'l>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "order": [[1, 'asc']],
        "aaSorting": [[2, 'asc']],
        "aoColumns": [
            {"mDataProp": "name", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "loginMob", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "oldPhone", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "idCardNo", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "driverType", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "city", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "plateNo", "bVisible": true, "sDefaultContent": ""},
            {
                "mDataProp": "result",
                "bVisible": true,
                "sDefaultContent": '<span class="label label-warning">未定义</span>',
                'fnRender': function (oObj, sVal) {
                    if (sVal == 0) {
                        return '<span class="label label-danger">不能更改</span>';
                    } else if (sVal == 1) {
                        return '<span class="label label-success">可以更改</span>';
                    } else {
                        return '<span class="label label-warning">未定义</span>';
                    }
                }
            },
            {"mDataProp": "reason", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "createTime", "bVisible": true, "sDefaultContent": "",
                'fnRender': function(oObj, sVal) {
                    Date.prototype.yyyymmddhhmmss = function() {
                        var yyyy = this.getFullYear();
                        var mm = this.getMonth() < 9 ? "0" + (this.getMonth() + 1) : (this.getMonth() + 1); // getMonth() is zero-based
                        var dd  = this.getDate() < 10 ? "0" + this.getDate() : this.getDate();
                        var hh = this.getHours() < 10 ? "0" + this.getHours() : this.getHours();
                        var min = this.getMinutes() < 10 ? "0" + this.getMinutes() : this.getMinutes();
                        var ss = this.getSeconds() < 10 ? "0" + this.getSeconds() : this.getSeconds();
                        return "".concat(yyyy + "-").concat(mm + "-").concat(dd + " ").concat(hh + ":").concat(min + ":").concat(ss);
                    };
                    var date = new Date(sVal);
                    return date.yyyymmddhhmmss();;
                }},
            {"mDataProp": "sendMsg", "bVisible": true, "sDefaultContent": "",
                'fnRender': function(oObj, sVal) {
                    if(sVal == 0) {
                        return "未发送";
                    } else if(sVal == 1) {
                        return "已发送";
                    } else {
                        return "";
                    }
                }},
            {
                "mDataProp": "id", "bVisible": true,
                'fnRender': function (oObj, sVal) {
                    var row = $(this).closest('tr').get(0);
                    var toolcol = '<div class="hidden-sm hidden-xs action-buttons">';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:editInfo(\'' + sVal + '\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-search-plus bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol + '<shiro:hasPermission name="alarm:delete"><a class="red" href="javascript:deleteInfo(\'' + sVal + '\',\'' + oObj.aData.name + '\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-trash-o bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol + '</div>';
                    toolcol = toolcol + '<div class="hidden-md hidden-lg">';
                    toolcol = toolcol + '<div class="inline position-relative">';
                    toolcol = toolcol + '<button';
                    toolcol = toolcol + 'class="btn btn-minier btn-yellow dropdown-toggle"';
                    toolcol = toolcol + 'data-toggle="dropdown" data-position="auto">';
                    toolcol = toolcol + '<i';
                    toolcol = toolcol + 'class="ace-icon fa fa-caret-down icon-only bigger-120"></i>';
                    toolcol = toolcol + '</button>';
                    toolcol = toolcol + '<ul';
                    toolcol = toolcol + 'class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">';
                    toolcol = toolcol + '<li><a href="#" class="tooltip-info"';
                    toolcol = toolcol + 'data-rel="tooltip" title="View"> <span';
                    toolcol = toolcol + 'class="blue"> <i';
                    toolcol = toolcol + 'class="ace-icon fa fa-search-plus bigger-120"></i>';
                    toolcol = toolcol + '</span>';
                    toolcol = toolcol + '</a></li>';
                    toolcol = toolcol + '<li><a href="#" class="tooltip-success"';
                    toolcol = toolcol + 'data-rel="tooltip" title="Edit"> <span';
                    toolcol = toolcol + 'class="green"> <i';
                    toolcol = toolcol + 'class="ace-icon fa fa-pencil-square-o bigger-120"></i>';
                    toolcol = toolcol + '</span>';
                    toolcol = toolcol + '</a></li>';
                    toolcol = toolcol + '<li>';
                    toolcol = toolcol + '<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">';
                    toolcol = toolcol + '<span class="red">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-trash-o bigger-120"></i>';
                    toolcol = toolcol + '</span>';
                    toolcol = toolcol + '</a>';
                    toolcol = toolcol + '</li>';
                    toolcol = toolcol + '</ul>';
                    toolcol = toolcol + '</div>';
                    toolcol = toolcol + '</div>';
                    return toolcol;
                }

            }],
        "oLanguage": {
            "sProcessing": "正在加载中......",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "对不起，查询不到相关数据！",
            "sEmptyTable": "表中无数据存在！",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
            "sInfoEmpty": "当前显示 0 到 0 条,共 0 条记录",
            "sSearch": "搜索",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上一页",
                "sNext": "下一页",
                "sLast": "末页"
            }
        }
    });
    // $.fn.dataTableExt.afnFiltering.push(
    //     function( oSettings, aData, iDataIndex ) {
    //         var name = document.getElementById('searchName').value;
    //         var phone = document.getElementById('searchPhone').value;
    //         var idCardNo = document.getElementById('searchIDCard').value;
    //         var city = document.getElementById('searchCity').value;
    //         var matchName = aData[1].match(/.*?name.*?/);
    //         var matchRegistPhone = aData[2].match(/.*?phone.*?/);
    //         var matchIDCard = aData[4].match(/.*?idCardNo.*?/);
    //         var matchCity = aData[5].match(/.*?city.*?/);
    //         if(matchName && matchRegistPhone && matchIDCard && matchCity) {
    //             return true;
    //         }
    //         return false;
    //     }
    // );
    $("#searchName").keyup(function () {
        /* Filter on the column (the index) of this element */
        oTable1.fnFilter(this.value, 0);
    });
    $("#searchPhone").keyup(function () {
        /* Filter on the column (the index) of this element */
        oTable1.fnFilter(this.value, 1);
    });
    $("#searchLoginPhone").keyup(function () {
        /* Filter on the column (the index) of this element */
        oTable1.fnFilter(this.value, 2);
    });
    $("#searchIDCard").keyup(function () {
        /* Filter on the column (the index) of this element */
        oTable1.fnFilter(this.value, 3);
    });
    $("#selectDriverType").change(function () {
        var value = $("#selectDriverType").find("option:selected").text();
        $("#searchDriverType").val(value);
        /* Filter on the column (the index) of this element */
        oTable1.fnFilter(value, 4);
    });
    $("#selectSendStatus").change(function () {
        var value = $("#selectSendStatus").val();
        oTable1.fnFilter(value, 5);
    });
    $("#searchCreateDate").change(function () {
        var value = $("#searchCreateDate").val() + ' 00:00:00';
        oTable1.fnFilter(value, 6);
    });
    $("#selectStrategyStatus").change(function () {
        var value = $("#selectStrategyStatus").val();
        oTable1.fnFilter(value, 7);
    });
    $("#selectResult").change(function () {
        var value = $("#selectResult").val();
        oTable1.fnFilter(value, 8);
    });
    $("#selectNewPhoneProof").change(function () {
        var value = $("#selectNewPhoneProof").val();
        oTable1.fnFilter(value, 9);
    });
    $("#editForbidden").click(function () {
        var id = $("#edit_id").val();
        updateStatus(id, false);
    });
    $("#editPermit").click(function () {
        var id = $("#edit_id").val();
        updateStatus(id, true);
    });
}
/**
 * 获取全部角色
 */
function intiRole() {
    $.ajax({
        url: "../role/all",
        dataType: "json",
        type: "POST",
        async: false,
        data: null,
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '查询角色信息失败',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {

                var list = JSON.parse(e.data);
                for (var obj in  list) {
                    $("#add_role").append("<option value='" + list[obj].id + "'>" + list[obj].role + "</option>");
                    $("#edit_role").append("<option value='" + list[obj].id + "'>" + list[obj].role + "</option>");
                }

            } else {
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
function deleteInfo(id, name) {

    $("#del_id").attr("value", id);
    $("#del_info").html(name);

    $("#deleteModal").modal('show');
}

/**
 * 确认删除
 */
function deleteOk() {

    var id = $("#del_id").val();

    $.ajax({
        url: "delete",
        dataType: "json",
        type: "POST",
        async: false,
        data: {id: id},
        error: function (error) {
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {
                $.gritter.add({
                    title: '执行成功',
                    text: '记录信息删除成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '记录信息删除失败',
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
        dataType: 'json',

        success: function (value) {
            $('#add_form').resetForm();// 重置表单

            if (value.code == 0) {

                $.gritter.add({
                    title: '执行成功',
                    text: '记录信息添加成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '记录信息添加失败！',
                    class_name: 'danger'
                });
            }
        },
        error: function () {
            $.gritter.add({
                title: '服务器错误',
                text: '对不起，服务器错误，请联系系统管理员！',
                class_name: 'danger'
            });
            // $("#useradd_from").button('reset');
        }
    };
    var bindStatus = $("#add_bindStatus").is(":checked");
    if (bindStatus) {
        $("#addCheckBoxHidden").val(0);
    } else {
        $("#addCheckBoxHidden").val(1);
    }
    var status = $("#add_status").is(":checked");
    if (status) {
        $("#addStatusHidden").val(0);
    } else {
        $("#addStatusHidden").val(1);
    }
    $("#add_form").ajaxForm(option).submit();
}

/**
 * 提交修改
 */
function update() {
    var option = {
        dataType: 'json',

        success: function (value) {
            $('#edit_form').resetForm();// 重置表单

            if (value.code == 0) {

                $.gritter.add({
                    title: '执行成功',
                    text: '记录信息修改成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '记录信息修改失败！',
                    class_name: 'danger'
                });
            }
        },
        error: function () {
            $.gritter.add({
                title: '服务器错误',
                text: '对不起，服务器错误，请联系系统管理员！',
                class_name: 'danger'
            });
            // $("#useradd_from").button('reset');
        }
    };
    if ($("#edit_bindStatus").prop("checked")) {
        $("#editCheckBoxHidden").val(0);
    } else {
        $("#editCheckBoxHidden").val(1);
    }
    if ($("#edit_status").prop("checked")) {
        $("#editStatusHidden").val(0);
    } else {
        $("#editStatusHidden").val(1);
    }
    $("#edit_form").ajaxForm(option).submit();
}

/**
 * 添加表单验证
 */
function addFormVaildate() {
    jQuery.validator.addMethod("isMobile", function (value, element) {
        var length = value.length;
        var mobile = /^1[34578]\d{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");


    $('#add_form').validate({
        //errorElement : 'div',
        //errorClass : 'help-block',
        focusInvalid: true,
        ignore: "",
        rules: {

            topic: {
                required: false,
                maxlength: 100,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {
                        id: null,
                        type: 1,
                        value: function () {
                            return $("#add_code").val();
                        }
                    }
                }
            },

            email: {
                required: true,
                // email : true,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: null,
                        type: 2,
                        value: function () {
                            return $("#add_email").val();
                        }
                    }
                }

            },
            wechat: {
                required: false,
                maxlength: 200,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: null,
                        type: 3,
                        value: function () {
                            return $("#add_wechat").val();
                        }
                    }
                }
            },
            remarks: {
                required: false,
                maxlength: 200,

            },
            mobile: {
                required: false,
                // isMobile : true,
                maxlength: 100,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: null,
                        type: 1,
                        value: function () {
                            return $("#add_mobile").val();
                        }
                    }
                }

            }
        },

        messages: {
            topic: {
                required: "<br/><span style='color: red;'>请输入应用识别码</span>",
                maxlength: "<br/><span style='color: red;'>最大长度不能超过100个字符！</span>",
                remote: "<span style='color: red;'>应用识别码已经存在</span>"
            },
            email: {
                required: "<br/><span style='color: red;'>请输入邮箱</span>",
                email: "<br/><span style='color: red;'>请输入正确的邮箱！</span>",
                remote: "<span style='color: red;'>电子邮箱已经存在</span>"
            },
            wechat: {
                maxlength: "<br/><span style='color: red;'>请输入字符串长度在200以内！</span>",
                remote: "<span style='color: red;'>微信号已经存在</span>"
            },
            remarks: {
                maxlength: "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            mobile: {

                maxlength: "<span style='color: red;'>最大长度不能超过100个字符</span>",
                isMobile: "<span style='color: red;'>请输入正确的手机号码</span>",
                remote: "<span style='color: red;'>手机号已经存在</span>"
            }
        }


    });

}

/**
 *修改表单验证
 */
function editFormVaildate() {
    jQuery.validator.addMethod("isMobile", function (value, element) {
        var length = value.length;
        var mobile = /^1[34578]\d{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");

    $('#edit_form').validate({
        //errorElement : 'div',
        //errorClass : 'help-block',
        focusInvalid: true,
        ignore: "",
        rules: {

            topic: {
                required: false,
                maxlength: 100,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {
                        id: function () {
                            return $("#edit_id").val();
                        },
                        type: 1,
                        value: function () {
                            return $("#edit_code").val();
                        }
                    }
                }
            },

            email: {
                required: true,
                // email : true,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: function () {
                            return $("#edit_id").val();
                        },
                        type: 2,
                        value: function () {
                            return $("#edit_email").val();
                        }
                    }
                }

            },
            wechat: {
                required: false,
                maxlength: 200,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: function () {
                            return $("#edit_id").val();
                        },
                        type: 3,
                        value: function () {
                            return $("#edit_wechat").val();
                        }
                    }
                }
            },
            remarks: {
                required: false,
                maxlength: 200,

            },
            mobile: {
                required: false,
                // isMobile : true,
                maxlength: 100,
                remote: {
                    url: "../user/validateUserInfo",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        id: function () {
                            return $("#edit_id").val();
                        },
                        type: 1,
                        value: function () {
                            return $("#edit_mobile").val();
                        }
                    }
                }

            }
        },

        messages: {
            topic: {
                required: "<br/><span style='color: red;'>请输入应用识别码</span>",
                maxlength: "<br/><span style='color: red;'>最大长度不能超过100个字符！</span>",
                remote: "<span style='color: red;'>应用识别码已经存在</span>"
            },
            email: {
                required: "<br/><span style='color: red;'>请输入邮箱</span>",
                email: "<br/><span style='color: red;'>请输入正确的邮箱！</span>",
                remote: "<span style='color: red;'>电子邮箱已经存在</span>"
            },
            wechat: {
                maxlength: "<br/><span style='color: red;'>请输入字符串长度在200以内！</span>",
                remote: "<span style='color: red;'>微信号已经存在</span>"
            },
            remarks: {
                maxlength: "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            mobile: {

                maxlength: "<span style='color: red;'>最大长度不能超过100个字符</span>",
                isMobile: "<span style='color: red;'>请输入正确的手机号码</span>",
                remote: "<span style='color: red;'>手机号已经存在</span>"
            }
        }
    });

}

/**
 * 打开编辑
 */
function editInfo(id) {

    $.ajax({
        url: "../record/findById",
        dataType: "json",
        type: "POST",
        async: false,
        data: {id: id},
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '查询角色信息失败',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {
                var obj = JSON.parse(e.data);
                $("#edit_id").attr("value", obj.id);
                $("#edit_name").attr("value", obj.name);
                $("#edit_phone").attr("value", obj.oldPhone);
                $("#edit_idCardNo").attr("value", obj.idCardNo);
                $("#edit_city").attr("value", obj.city);
                $("#edit_plateNo").attr("value", obj.plateNo);
                $("#edit_bankCard").attr("value", obj.bankCard);
                $("#edit_newPhone").attr("value", obj.newPhone);
                if (obj.bindStatus == 0) {
                    $("#edit_bindStatus").prop('checked', true);
                } else {
                    $("#edit_bindStatus").prop('checked', false);
                }
                if (obj.loginStatus == 0) {
                    $("#edit_loginStatus").attr("value", "重新登录成功");
                } else {
                    $("#edit_loginStatus").attr("value", "重新登录失败");
                }
                $("#edit_startTime").attr("value", obj.startTime);
                $("#edit_endTime").attr("value", obj.endTime);
                $("#edit_phoneDisableTime").attr("value", obj.phoneDisableTime);
                $("#edit_phoneDisableLoc").attr("value", obj.phoneDisableLoc);
                $("#edit_phoneDisableReason").attr("value", obj.phoneDisableReason);
                $("#picWithIDCard").html('');
                if (obj.picWithIDCard != undefined) {
                    $.each(obj.picWithIDCard, function () {
                        $("#picWithIDCard").append('<img alt="picWithIDCard" src="' + this + '" width="60%" height="60%" style="margin:auto;" /><br/><br/>');
                    });
                }
                $("#picDriveLicense").html('');
                if (obj.picDriveLicense != undefined) {
                    $.each(obj.picDriveLicense, function () {
                        $("#picDriveLicense").append('<img alt="picDriveLicense" src="' + this + '" width="60%" height="60%" style="margin:auto;" /><br/><br/>');
                    });
                }
                $("#picNewPhoneProof").html('');
                if (obj.picNewPhoneProof != undefined) {
                    $.each(obj.picNewPhoneProof, function () {
                        $("#picNewPhoneProof").append('<img alt="picNewPhoneProof" src="' + this + '" width="60%" height="60%" style="margin:auto;" /><br/><br/>');
                    });
                }
                $("#picOtherProof").html('');
                if (obj.picOtherProof != undefined) {
                    $.each(obj.picOtherProof, function () {
                        $("#picOtherProof").append('<img alt="picOtherProof" src="' + this + '" width="60%" height="60%" style="margin:auto;" /><br/><br/>');
                    });
                }
                if (obj.status == 1) {
                    $("#edit_status").prop('checked', true);
                } else {
                    $("#edit_status").prop('checked', false);
                }
                if (obj.checked == 0) {
                    $("#edit_checked").prop('checked', false);
                } else {
                    $("#edit_checked").prop('checked', true);
                }
                if (obj.result == 0) {
                    $("#edit_result").prop('checked', false);
                } else {
                    $("#edit_result").prop('checked', true);
                }
                if (obj.updateMobStatus == 0) {
                    $("#edit_updateMobStatus").prop('checked', false);
                } else {
                    $("#edit_updateMobStatus").prop('checked', true);
                }
                $("#edit_driverType").attr("value", obj.driverType);
                $("#edit_reason").attr("value", obj.reason);
                $("#edit_loginTime").attr("value", obj.reLoginTime);
                $("#edit_role").val(obj.roleId);
                $("#editModal").modal('show');
                // $("#editModal").on('shown', function() { editor2.refresh() });

            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '查询角色信息失败',
                    class_name: 'danger'
                });
            }


        }

    });
}

function updateStatus(id, flag) {
    $.ajax({
        url: "../record/updateStatus",
        dataType: "json",
        type: "POST",
        async: false,
        data: {
            id: id,
            flag: flag
        },
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '查询角色信息失败',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {
            if (e.code == 0) {
                $.gritter.add({
                    title: '执行成功',
                    text: e.msg,
                    class_name: 'success'
                });
                //oTable1.fnClearTable(); //清空数据
                //oTable1.fnDraw();
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

// function getCityList() {
//     $.ajax({
//         url : "../record/getCityList",
//         dataType : "json",
//         type : "POST",
//         async : false,
//         data : null,
//         error : function(error) {
//             $.gritter.add({
//                 title: '执行失败',
//                 text: '查询角色信息失败',
//                 class_name: 'danger'
//             });
//             console.log(error.responseText);
//         },
//         success : function(e) {
//
//             if(e.code == 0){
//                 var obj = JSON.parse(e.data);
//                 $("#add_city").html('');
//                 $("#searchCity").html('');
//                 $.each(obj, function(){
//                     $("#add_city").append('<option value="'+ this.key +'">'+ this.value +'</option>')
//                     $("#searchCity").append('<option value="'+ this.key +'">'+ this.value +'</option>')
//                 })
//             } else{
//                 $.gritter.add({
//                     title: '执行失败',
//                     text: '查询角色信息失败',
//                     class_name: 'danger'
//                 });
//             }
//         }
//     });
// }

// function getLoginStatusList() {
//     $.ajax({
//         url : "../record/getLoginStatusList",
//         dataType : "json",
//         type : "POST",
//         async : false,
//         data : null,
//         error : function(error) {
//             $.gritter.add({
//                 title: '执行失败',
//                 text: '查询角色信息失败',
//                 class_name: 'danger'
//             });
//             console.log(error.responseText);
//         },
//         success : function(e) {
//
//             if (e.code == 0) {
//                 var obj = JSON.parse(e.data);
//                 $("#add_loginStatus").html('');
//                 $.each(obj, function () {
//                     $("#add_loginStatus").append('<option value="' + this.key + '">' + this.value + '</option>')
//                 })
//             } else {
//                 $.gritter.add({
//                     title: '执行失败',
//                     text: '查询角色信息失败',
//                     class_name: 'danger'
//                 });
//             }
//         }
//     });
// }

// function getCity(key) {
//     $.ajax({
//         url : "../record/getCityList",
//         dataType : "json",
//         type : "POST",
//         async : false,
//         data : null,
//         error : function(error) {
//             $.gritter.add({
//                 title: '执行失败',
//                 text: '查询角色信息失败',
//                 class_name: 'danger'
//             });
//             console.log(error.responseText);
//             return "";
//         },
//         success : function(e) {
//             if(e.code == 0){
//                 console.log(e.data);
//                 var obj = JSON.parse(e.data);
//                 $("#add_city").html('');
//                 $("#searchCity").html('');
//                 $.each(obj, function(){
//                     if(this.key == key) {
//                         return this.value;
//                     }
//                 });
//                 return "";
//             } else{
//                 $.gritter.add({
//                     title: '执行失败',
//                     text: '查询角色信息失败',
//                     class_name: 'danger'
//                 });
//                 return "";
//             }
//
//         }
//     });
// }

// function getLoginStatus(key) {
//     $.ajax({
//         url : "../record/getLoginStatusList",
//         dataType : "json",
//         type : "POST",
//         async : false,
//         data : null,
//         error : function(error) {
//             $.gritter.add({
//                 title: '执行失败',
//                 text: '查询角色信息失败',
//                 class_name: 'danger'
//             });
//             console.log(error.responseText);
//         },
//         success : function(e) {
//
//             if (e.code == 0) {
//                 console.log(e.data);
//                 var obj = JSON.parse(e.data);
//                 $("#add_loginStatus").html('');
//                 $.each(obj, function () {
//                     if(this.key == key) {
//                         return this.value;
//                     }
//                 });
//                 return "";
//             } else {
//                 $.gritter.add({
//                     title: '执行失败',
//                     text: '查询角色信息失败',
//                     class_name: 'danger'
//                 });
//                 return "";
//             }
//         }
//     });
// }



