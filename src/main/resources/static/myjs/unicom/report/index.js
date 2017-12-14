var oTable1;
$(document).ready(function () {

    $('.md-trigger').modalEffects();
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
        //'sPaginationType':'two_button',//分页样式，支持两种内置方式，two_button 和 full_numbers, 默认使用 two_button。
        "bLengthChange": true,
        "bAutoWidth": true,//自动计算宽度 
        "bFilter": true,
        "sAjaxSource": 'list',
        "bServerSide": true,//启用服务器导入
        "sDom": "<'row'<'col-sm-6'l>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "order": [[1, 'asc']],
        "aoColumns": [
            {"mDataProp": "clientName", "bVisible": true},
            {"mDataProp": "clientLinkMob", "bVisible": true},
            {"mDataProp": "faultShow", "bVisible": true},
            {
                "mDataProp": "createTime", "bVisible": true,
                'fnRender': function (oObj, sVal) {

                    return new Date(sVal).toLocaleString();

                }
            },
            {"mDataProp": "clientLinkName", "bVisible": true},
            {
                "mDataProp": "status", "bVisible": true,
                'fnRender': function (oObj, sVal) {

                    var result = "";

                    switch (sVal) {
                        case 0:
                            result = '<span class="label label-danger">未处理</span>';
                            break;
                        case 1:
                            result = '<span class="label label-warning">处理中</span>';
                            break;
                        case 2:
                            result = '<span class="label label-success">处理完成</span>';
                            break;

                    }
                    return result;

                }

            },

            {
                "mDataProp": "id", "bVisible": true,
                'fnRender': function (oObj, sVal) {
                    var toolcol = '<div class="hidden-sm hidden-xs action-buttons">';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:addTraceModal(\'' + sVal + '\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-plus-circle bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:checkedModal(\'' + oObj.aData.id + '\',\'' + oObj.aData.name + '\',\'' + oObj.aData.faultshow + '\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-check-circle bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol + ' <shiro:hasPermission name="alarm:update"><a class="green" href="javascript:editinfo(\'' + sVal + '\',\'' + oObj.aData.name + '\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-pencil bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
                    toolcol = toolcol + '<shiro:hasPermission name="alarm:delete"><a class="red" href="javascript:deleteInfo(\'' + sVal + '\',\'' + oObj.aData.name + '\')">';
                    toolcol = toolcol + '<i class="ace-icon fa fa-trash-o bigger-130"></i>';
                    toolcol = toolcol + '</a></shiro:hasPermission>';
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
}


/**
 * 打开添加故障跟踪窗口
 * @param id
 */
function addTraceModal(id) {

    $("#add_trace_report").attr("value", id);
    getTrace(id);
    $("#addTraceModal").niftyModal('show');


}

/**
 * 添加故障跟踪
 */
function addTrace() {
    var reportid = $("#add_trace_report").val();
    var content = $("#add_trace_show").val();
    $.ajax({
        url: "addTrace",
        dataType: "json",
        type: "POST",
        async: false,
        data: {reportid: reportid, content: content},
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '添加跟踪信息失败,请重新登录!',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {
                getTrace(reportid);
            } else {
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
        url: "getTraceByID",
        dataType: "json",
        type: "POST",
        async: false,
        data: {id: id},
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '查询跟踪信息失败,请重新登录!',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {
                //清空list
                $("#tracelist").empty();
                var data = eval(e.data);
                for (var obj in data) {

                    var date = new Date(data[obj].time);

                    var content = '<p><strong>时间:</strong>' + date.toTimeString() + '<br/><strong>描述:</strong>' + data[obj].content + '<br/><strong>报告人:</strong>' + data[obj].reporterId + '</p>';
                    var li = '<li><i class="fa fa-comment"></i><span class="date">' + date.getHours() + ":" + date.getMinutes() + '</span><div class="content">' + content + '</div> </li>';

                    $("#tracelist").append(li);
                }
            } else {
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
function checkedModal(id, name, faultshow) {

    $("#check_id").attr("value", id);

    $("#check_msg").attr("客户名称:" + name + "故障描述:" + faultshow);

    $("#checkModal").modal('show');
}

/**
 * 故障修复确认
 */
function checkedOK() {

    var id = $("#check_id").val();

    $.ajax({
        url: "report_status_update",
        dataType: "json",
        type: "POST",
        async: false,
        data: {id: id},
        error: function (error) {
            $.gritter.add({
                title: '执行失败',
                text: '确认修复失败,请重新登录!',
                class_name: 'danger'
            });
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {
                $.gritter.add({
                    title: '执行成功',
                    text: '确认修复成功',
                    class_name: 'sucess'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();//重新获取数据
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '确认修复失败',
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
    var start = $("#search_start").val();
    var end = $("#search_end").val();
    var search = '';

    if ((start == '') && (end == '')) {
        search = '';
    } else {
        if (start == '' || end == '') {
            alert('请输入完整的时间范围');
            return;
        } else {
            search = start + ',' + end;
        }
    }


    //用空格隔开，达到多条件搜索的效果，相当于两个关键字
    //table.search(args1+" "+args2).draw();
    oTable1.fnFilter(search);

    //oTabsle1.search

}


/**
 * 导出故障
 */
function exportExcel() {
    var start = $("#search_start").val();
    var end = $("#search_end").val();

    if (start == '' || end == '') {
        alert("请选择查询日期范围");
        return true;
    }

    location.href = "../report/export?start=" + start + "&end=" + end;
}


/**
 * 选择客户信息下拉列表
 */
function selectClient() {
    $("#add_client").select2({
        placeholder: "输入客户名称",//文本框的提示信息
        minimumInputLength: 1,   //至少输入n个字符，才去加载数据
        allowClear: true,  //是否允许用户清除文本信息
        ajax: {
            url: "../client/likeByName",
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            type: 'POST',
            delay: 250,
            data: function (params) {
                return {
                    //发送给服务端的参数
                    q: params,
                    page: 20
                };
            },
            results: function (data, pageNo) {

                if (data.total > 0) {   //如果没有查询到数据，将会返回空串

                    var more = (pageNo * 15) < data.total; //用来判断是否还有更多数据可以加载
                    return {
                        results: data.result, more: more
                    };
                } else {
                    return {results: data};
                }
            }
            //cache: true
        },
        initSelection: function (element, callback) {           //初始化，其中doName是自定义的一个属性，用来存放text的值
            var id = $(element).val();
            var text = $(element).attr("doName");
            if (id != '' && text != "") {
                callback({id: id, text: text});
            }
        },
        escapeMarkup: function (markup) {
            return markup;
        }, // let our custom formatter work
        formatResult: formatAsText, // omitted for brevity, see the source of this page
        formatSelection: formatSelection//选中格式化
    });
}


//格式化查询结果,将查询回来的id跟name放在两个div里并同行显示，后一个div靠右浮动
function formatAsText(item) {
    var itemFmt = "<div style='display:inline;'>" + item.name + "</div><br/><div style='display:inline'>" + item.address + "</div>"
    return itemFmt;
}

function formatSelection(item) {
    var itemFmt = "<div>" + item.name + "</div>"
    return itemFmt;
}

/**
 * 添加表单验证
 */
function addFormValidation() {

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
            client: {
                required: true,
            },

            phone: {
                required: true,
                isMobile: true,

            },
            time: {
                required: true,
                date: true

            },
            reasion: {
                required: false,
                maxlength: 200,

            },
            worker: {
                required: true,
                maxlength: 100,

            },
            olt: {
                required: false,
                maxlength: 200,
            }
        },

        messages: {
            client: {
                required: "<br/><span style='color: red;'>请输入客户名称</span>",
                maxlength: "<br/>><span style='color: red;'>最大长度不能超过100个字符！</span>"
            },
            phone: {
                required: "<br/><span style='color: red;'>请输入联系电话</span>",
                isMobile: "<span style='color: red;'>请输入正确的手机号码！</span>"
            },
            time: {
                required: "<span style='color: red;'>请选择时间！</span>",
                date: "<span style='color: red;'>请输入正确的时间格式</span>",
            },
            reasion: {
                maxlength: "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            worker: {
                required: "<span style='color: red;'>请输入受理人</span>",
                maxlength: "<span style='color: red;'>最大长度不能超过100个字符！</span>"
            },
            olt: {
                maxlength: "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            }
        },

        /*highlight : function(e) {
         $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
         },

         success : function(e) {
         $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
         //$(e).remove();
         }*/
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
            // $("#edit_userbutton").button('reset');
            $('#addModal').modal('hide');

            // 重新初始化tree

            if (value.code == 0) {

                $.gritter.add({
                    title: '执行成功',
                    text: '故障信息删除成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '故障信息添加失败！',
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
 * 更新
 */
function update() {

    var option = {
        dataType: 'json',

        success: function (value) {
            $('#edit_form').resetForm();// 重置表单
            // $("#edit_userbutton").button('reset');
            $('#editModal').modal('hide');

            // 重新初始化tree

            if (value.code == 0) {

                $.gritter.add({
                    title: '执行成功',
                    text: '故障信息修改成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '故障信息修改失败！',
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
 * 打开编辑对话框
 */
function editinfo(id, name) {

    $.ajax({
        url: "findById",
        dataType: "json",
        type: "POST",
        async: false,
        data: {id: id},
        error: function (error) {
            console.log(error.responseText);
        },
        success: function (e) {

            if (e.code == 0) {
                var data = JSON.parse(e.data);
                $("#edit_id").attr('value', data.id);
                $("#edit_clientid").attr('value', data.clienId);
                $("#edit_client").attr('value', name);
                $("#edit_phone").attr('value', data.clientLink);
                var date = new Date(data.createTime);
                $("#edit_time").attr('value', date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes());
                //$(".selector").val("pxx");
                $("#edit_show").val(data.faultShow);
                $("#edit_other").html(data.otherShow);
                $("#edit_reasion").html(data.fault);
                $("#edit_worker").attr('value', data.worker);
                $("#edit_olt").attr('value', data.upOLT);

            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '查询信息失败',
                    class_name: 'danger'
                });
            }


        }

    });

    $('#editModal').modal('show');
}


/**
 * 打开删除对话框
 * @param id
 * @param name
 */
function deleteInfo(id, name) {

    $("#del_msg").html("您将要删除:" + name + ",请确认?");
    $("#del_id").attr("value", id);

    $("#deleteModal").modal('show');


}


function del() {

    var id = $("#del_id").val();

    $.ajax({
        url: "del",
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
                    text: '故障信息删除成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title: '执行失败',
                    text: '故障信息删除失败',
                    class_name: 'danger'
                });
            }


        }

    });

    $('#deleteModal').modal('hide');
}

//收拾下拉框
function initGaoDeMapSearch() {
    AMap.plugin('AMap.Autocomplete', function () {//回调函数
        //实例化Autocomplete
        var autoOptions = {
            city: "changsha", //城市，默认全国
            input: "add_client2",//使用联想输入的input的id
            count: 10 //条数
        };
        autocomplete = new AMap.Autocomplete(autoOptions);
        //TODO: 使用autocomplete对象调用相关功能
        autocomplete.search(keywords, function (status, result) {
            //TODO:开发者使用result自己进行下拉列表的显示与交互功能

        });
    });


}
