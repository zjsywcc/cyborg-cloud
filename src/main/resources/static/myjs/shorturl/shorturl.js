var oTable1;
var aSelected = [];
$(document).ready(function () {
    initGroup();
});


/***
 * 初始化管理列表
 */
function initGroup() {
    oTable1 = $('#datatable').dataTable({
        "bSort": false,//true每列可以排序，false不允许排序
        "bProcessing": true,//载入数据时显示进度条
        'bPaginate': true, //是否显示分页menus
        'sPaginationType': 'input',
        //'sPaginationType':'two_button',//分页样式，支持两种内置方式，two_button 和 full_numbers, 默认使用 two_button。
        "bLengthChange": true,
        "bAutoWidth": true,//自动计算宽度 
        "bFilter": true,
        "sAjaxSource": '../s/list',
        "bServerSide": true,//启用服务器导入
        "sDom": "<'row'<'col-sm-6'l>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "order": [[1, 'asc']],
        "aoColumns": [
            {"mDataProp": "realURL", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "shortURL", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "createTime", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "status", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "driverType", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "sendType", "bVisible": true, "sDefaultContent": "",
                'fnRender' : function(oObj, sVal) {
                    if(sVal == 0) {
                        return "平台";
                    } else {
                        return "业务方";
                    }
                }},
            {"mDataProp": "smsContent", "bVisible": true, "sDefaultContent": ""},
            {"mDataProp": "sendUser", "bVisible": true, "sDefaultContent": ""},
            {
                "mDataProp": "id", "bVisible": true,
                'fnRender': function (oObj, sVal) {
                    var toolcol = '<div class="hidden-sm hidden-xs action-buttons">';
                    toolcol = toolcol + '<shiro:hasPermission name="alarm:update">';
                    toolcol = toolcol + '<input type="checkbox"/>';
                    toolcol = toolcol + '</shiro:hasPermission>';
                    toolcol = toolcol + '<shiro:hasPermission name="alarm:delete"><a class="red" href="javascript:deleteInfo(\'' + sVal + '\',\'' + oObj.aData.realURL + '\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-trash-o bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    // toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:editInfo(\''+sVal+'\')">';
                    // toolcol = toolcol + '<i class="ace-icon fa fa-pencil bigger-130"></i>';
                    // toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:sendMsgInfo(\'' + sVal + '\',\'' + oObj.aData.realURL + '\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-comments bigger-130"></i>';
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
        },
        "fnDrawCallback": function (oSettings) {
            $("#checkAll").prop('checked', false);
        }
    });
    $("#searchPhone").keyup(function () {
        /* Filter on the column (the index) of this element */
        oTable1.fnFilter(this.value, 0);
    });
    $("#selectStatus").change(function () {
        var value = $("#selectStatus").find("option:selected").text();
        oTable1.fnFilter(value, 1);
    });
    $("#searchCreateDate").change(function () {
        var value = $("#searchCreateDate").val();
        oTable1.fnFilter(value, 2);
    });
    $("#selectDriverType4S").change(function () {
        var value = $("#selectDriverType4S").find("option:selected").text();
        oTable1.fnFilter(value, 3);
    });
    $("#updateSelected").click(function () {
        var selected = new Array();
        $(oTable1.fnGetNodes()).find(':checkbox').each(function () {
            if ($(this).prop('checked')) {
                var shortURL = oTable1.fnGetData($(this).closest('tr')[0])["shortURL"];
                selected.push(shortURL);
            }
        });
        if (selected.length == 0) {
            $.gritter.add({
                title: '执行失败',
                text: '请先选择至少一条记录发送短信',
                class_name: 'danger'
            });
        } else {
            updateStatus(selected.join());
        }
    });
    $("#checkAll").click(function () {
        $(oTable1.fnGetNodes()).find(':checkbox').each(function () {
            if ($("#checkAll").prop('checked')) {
                $(this).prop('checked', true);
            } else {
                $(this).prop('checked', false);
            }
        });
    });
    $("#add_realURL").keyup(function () {
        $.ajax({
            url: "../s/encodeByPhone",
            dataType: "json",
            type: "POST",
            async: false,
            data: {phone: $("#add_realURL").val()},
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
                    var shortURL = JSON.parse(e.data);
                    $("#add_sURL").attr("value", shortURL);
                } else {
                    $.gritter.add({
                        title: '执行失败',
                        text: e.msg,
                        class_name: 'danger'
                    });
                }
            }
        })
    });
    $("#edit_realURL").keyup(function () {
        $.ajax({
            url: "../s/encodeByPhone",
            dataType: "json",
            type: "POST",
            async: false,
            data: {phone: $("#edit_realURL").val()},
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
                    var shortURL = JSON.parse(e.data);
                    $("#edit_sURL").attr("value", shortURL);
                } else {
                    $.gritter.add({
                        title: '执行失败',
                        text: e.msg,
                        class_name: 'danger'
                    });
                }
            }
        })
    })
}

function deleteSelected() {
    var selected = new Array();
    $(oTable1.fnGetNodes()).find(':checkbox').each(function () {
        if ($(this).prop('checked')) {
            var shortURL = oTable1.fnGetData($(this).closest('tr')[0])["shortURL"];
            selected.push(shortURL);
        }
    });
    deleteAll(selected.join());
}

/**
 * 打开编辑
 */
function updateStatus(sURL) {

    $.ajax({
        url: "../s/updateStatus",
        dataType: "json",
        type: "POST",
        async: true,
        data: {sURL: sURL},
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
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
                $("#checkAll").prop('checked', false);
                openProgressBar();
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
 * 打开编辑
 */
function deleteAll(sURL) {

    $.ajax({
        url: "../s/deleteAll",
        dataType: "json",
        type: "POST",
        async: false,
        data: {sURL: sURL},
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
                $.gritter.add({
                    title: '执行成功',
                    text: e.msg,
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
                $("#checkAll").prop('checked', false);
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
function deleteInfo(id, realURL) {

    $("#del_id").attr("value", id);
    $("#del_info").html(realURL);

    $("#deleteModal").modal('show');
}

/**
 * 打开发送短信框
 * @param id
 */
function sendMsgInfo(id, realURL) {

    $("#sendMsgId").attr("value", id);
    $("#sendMsgInfo").html(realURL);

    $("#sendMsgModal").modal('show');
}

/**
 * 打开批量删除框
 * @param id
 */
function deleteSelectedInfo() {
    var selected = new Array();
    $(oTable1.fnGetNodes()).find(':checkbox').each(function () {
        if ($(this).prop('checked')) {
            var shortURL = oTable1.fnGetData($(this).closest('tr')[0])["shortURL"];
            selected.push(shortURL);
        }
    });
    if (selected.length == 0) {
        $.gritter.add({
            title: '执行失败',
            text: '请先选择至少一条记录再删除',
            class_name: 'danger'
        });
    } else {
        console.log(selected.length);
        $("#del_selected_info").html("确认批量删除？");
        $("#deleteSelectedModal").modal('show');
    }
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
                    text: '短址删除成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
                $("#checkAll").prop('checked', false);
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '短址删除失败',
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
                    text: value.msg,
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
                $("#checkAll").prop('checked', false);
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: value.msg,
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
    $("#add_form").ajaxForm(option).submit();
}

/**
 * 打开上传短链modal
 */
function uploadInfo() {
    $("#uploadModal").modal('show');
}

/**
 * 添加
 */
function upload() {
    var option = {
        dataType: 'json',
        success: function (value) {
            $('#upload_form').resetForm();// 重置表单
            if (value.code == 0) {
                $.gritter.add({
                    title: '执行成功',
                    text: value.msg,
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
                $("#checkAll").prop('checked', false);
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: value.msg,
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
    $("#upload_form").ajaxForm(option).submit();
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
                    text: value.msg,
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
                $("#checkAll").prop('checked', false);
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: value.msg,
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
    $("#edit_form").ajaxForm(option).submit();
}

/**
 * 打开progress bar modal
 */
function openProgressBar() {
    $('#progressModal').modal('show');
    $('#progressConfirm').hide();
    $('#progressConfirm').click(function () {
        $("#progressBar").css('width', 0 + '%');
    });
    var interval = setInterval(function () {
        $.ajax({
            url: "../s/progress",
            dataType: "json",
            type: "POST",
            async: true,
            data: null,
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
                    $("#progressInfo").text(obj.status);
                    $("#totalCount").text(obj.total);
                    $("#successCount").text(obj.success);
                    $("#failureCount").text(obj.failure);
                    $("#progressBar").css('width', (obj.progress / obj.total) * 100 + '%');
                    if (obj.progress >= obj.total) {
                        clearTimeout(interval);
                        $('#progressConfirm').show();
                        $.gritter.add({
                            title: '执行成功',
                            text: '发送短信成功',
                            class_name: 'success'
                        });
                    }
                    console.log(obj.progress);
                } else {
                    $.gritter.add({
                        title: '执行失败',
                        text: e.msg,
                        class_name: 'danger'
                    });
                }
            }
        });
    }, 1000);
}

/**
 * 打开编辑
 */
function editInfo(id) {

    $.ajax({
        url: "../s/findById",
        dataType: "json",
        type: "POST",
        async: false,
        data: {id: id},
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

                $("#edit_id").attr("value", obj.id);
                $("#edit_realURL").attr("value", obj.realURL);
                $("#edit_sURL").attr("value", obj.shortURL);
                $("#edit_createTime").attr("value", obj.createTime);
                $("#edit_status").attr("value", obj.status);
                $("#edit_role").val(obj.roleId);

                $("#editModal").modal('show');

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

function sendMsg() {
    var id = $("#sendMsgId").val();
    $.ajax({
        url: "../s/sendMsg",
        dataType: "json",
        type: "POST",
        async: false,
        data: {id: id},
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
                $.gritter.add({
                    title: '执行成功',
                    text: e.msg,
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
                $("#checkAll").prop('checked', false);
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
