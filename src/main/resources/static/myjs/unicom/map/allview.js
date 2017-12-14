var map;
var marker;
var allClientMarker = {};
var allClientInfoWinodws = {};
var clientStatus=new Array();//故障客户列表
$(document).ready(function(){

    //添加故障表单验证
    addReportFormValidation();
    //初始化地图
    initMap();
    //客户信息搜索
    initGaoDeMapSearch();

    ajaxClient();

    //selectClient();
    

    window.setInterval(ajaxUnchecked,1000*10);

});


/**
 * 初始化地图
 */
function initMap() {

    map = new AMap.Map('container', {
        resizeEnable: true
    });
    map.setCity("长沙市");

    var scale = new AMap.Scale({
        visible: true
    }),
        toolBar = new AMap.ToolBar({
            visible: true
        }),
        overView = new AMap.OverView({
            visible: true
        });

    map.addControl(scale);
    map.addControl(toolBar);
    map.addControl(overView);
    map.setMapStyle('blue_night');

    scale.show();
    
   

    
}


/**
 * 实例化地图标记
 */
function addMarker(data) {

    var marker = new AMap.Marker({
        icon: "https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
        position: [data.lng, data.lat]
    });

    //全局变量存储
    allClientMarker[data.id]=marker;

    marker.setMap(map);
    //map.add(marker);

    marker.content='我是第个信息窗体的内容';
   //信息窗体
    var title=data.name,
        content=[];
    content.push("地址："+data.address);
    content.push("联系人："+data.contact);
    content.push("电话："+data.mobPhone);
    content.push("IP："+data.ip);
    content.push("OLT："+data.OLT);
    content.push("接入号码："+data.accessNo);
    content.push("<a href=\"javascript:viewReportModal('"+data.id+"');\"><span style='color: red'>查看故障</span></a>&nbsp;&nbsp;<a href=\"javascript:addReportModal('"+data.id+"');\"><span style='color: red'>添加故障</span></a> ");
    var infoWindow = new AMap.InfoWindow({
        isCustom: true,  //使用自定义窗体
        content: createInfoWindow(title,content.join("<br>")),
        offset: new AMap.Pixel(16, -50)//-113, -140
    });
    allClientInfoWinodws[data.id]=infoWindow;
    
    //给Marker绑定单击事件
    marker.on('click', function (e) {
        //实例化信息窗体
       
        infoWindow.open(map, e.target.getPosition())
    });

    //allClientMarker.pu(marker);




    //map.setFitView();//设定视野
    map.setZoom(14);
}

/**
 * 查询全部客户数据
 */
function ajaxClient() {

    $.ajax({
        url : "../client/getAll",
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
                allClient = data;
                for(var obj in data){
                    addMarker(data[obj]);
                }


            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '查询信息失败',
                    class_name: 'danger'
                });
            }

            map.setFitView();//设定视野


        }

    });
}

//构建自定义信息窗体
function createInfoWindow(title, content) {
    var info = document.createElement("div");
    info.className = "info-map";

/*
<div class="alert alert-success">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
    <i class="fa fa-check sign"></i><strong>Success!</strong> Changes has been saved successfully!
    </div>
*/


    //可以通过下面的方式修改自定义窗体的宽高
    //info.style.width = "400px";
    // 定义顶部标题
    var top = document.createElement("div");
    var titleD = document.createElement("div");
    var closeX = document.createElement("img");
    top.className = "info-top-map";
    titleD.innerHTML = title;
    closeX.src = "https://webapi.amap.com/images/close2.gif";
    closeX.onclick = closeInfoWindow;

    top.appendChild(titleD);
    top.appendChild(closeX);
    info.appendChild(top);

    // 定义中部内容
    var middle = document.createElement("div");
    middle.className = "info-middle-map";
    middle.style.backgroundColor = 'white';
    middle.innerHTML = content;
    info.appendChild(middle);

    // 定义底部内容
    var bottom = document.createElement("div");
    bottom.className = "info-bottom-map";
    bottom.style.position = 'relative';
    bottom.style.top = '0px';
    bottom.style.margin = '0 auto';
    var sharp = document.createElement("img");
    sharp.src = "https://webapi.amap.com/images/sharp.png";
    bottom.appendChild(sharp);
    info.appendChild(bottom);
    return info;
}

//关闭信息窗体
function closeInfoWindow() {
    map.clearInfoWindow();
}

/**
 * 获取未处理的信息
 */
function ajaxUnchecked() {
    $.ajax({
        url : "../report/unchecked_all",
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

                for(var obj in data){
                    //如果未定义则设在为故障
                    for(var id in clientStatus){
                        allClientMarker[data[obj].clienId].setIcon("https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png");
                    }

                  allClientMarker[data[obj].clienId].setIcon("https://webapi.amap.com/theme/v1.3/markers/n/mark_r.png");
                }

                //map.setFitView();//设定视野
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


function mapClientSelect(e) {
    alert(1);
}





/**
 * 选择客户信息下拉列表
 */
function selectClient() {
    $("#map_client_search").select2({
        placeholder:"输入客户名称",//文本框的提示信息
        minimumInputLength:1,   //至少输入n个字符，才去加载数据
        allowClear: true,  //是否允许用户清除文本信息
        ajax: {
            url: "../client/likeByName",
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
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

                if(data.total>0){   //如果没有查询到数据，将会返回空串

                    var more = (pageNo*15)<data.total; //用来判断是否还有更多数据可以加载
                    return {
                        results:data.result,more:more
                    };
                }else{
                    return {results:data};
                }
            }
            //cache: true
        },
        initSelection:function(element,callback){           //初始化，其中doName是自定义的一个属性，用来存放text的值
            var id=$(element).val();
            var text=$(element).attr("doName");
            if(id!=''&&text!=""){
                callback({id:id,text:text});
            }
        },
        escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
        formatResult: formatAsText, // omitted for brevity, see the source of this page
        formatSelection:formatSelection//选中格式化
    });
}


//格式化查询结果,将查询回来的id跟name放在两个div里并同行显示，后一个div靠右浮动
function formatAsText(item){
    var itemFmt ="<div style='display:inline;'>"+item.name+"</div><br/><div style='display:inline'>"+item.address+"</div>"
    return itemFmt;
}

function formatSelection(item){
    var itemFmt ="<div>"+item.name+"</div>"
    return itemFmt;
}
//客户选中选中事件
function clientSelected(e) {
    var position = allClientMarker[e.value].getPosition();
    allClientInfoWinodws[e.value].open(map,position);
    
    map.setCenter(position);
   
}


/**
 * 添加故障对话框
 */
function addReportModal(id) {
    $("#add_client").attr("value",id);
    $("#addReportModal").modal("show");
}

/**
 * 添加故障跟踪对话框
 */
function addTraceModal(id) {
    $("#add_trace_report").attr("value",id);
    getTrace(id);
    
    $("#addTraceModal").modal("show");
}

/**
 * 故障列表对话框
 */
function viewReportModal(id) {

    $.ajax({
        url : "../report/uncheckedByClientID",
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
               $("#view_table_body").empty();
                for(var obj in data){
                    var myDate=new Date(data[obj].createTime);
                    var datetime = myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+" "+myDate.getHours()+":"+myDate.getMinutes();

                    var html="<tr>" + 
                        "<td>"+data[obj].clientLink+"</td>" +
                        "<td>"+data[obj].faultShow+"</td>" +
                        "<td>"+datetime+"</td>" +
                        "<td>"+data[obj].worker+"</td>" +
                        "<td><a href=\"javascript:addTraceModal(\'"+data[obj].id+"\');\"><span style='color: red'>添加跟踪</span></a></td>" +
                        "</tr>";
                    $("#view_table_body").append(html);
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

    
    $("#viewReportModal").modal("show");
}



/**
 * 添加故障表单验证
 */
function addReportFormValidation() {

    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        var mobile = /^1[34578]\d{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");

    $('#add_report_form').validate({
        //errorElement : 'div',
        //errorClass : 'help-block',
        focusInvalid : true,
        ignore : "",
        rules : {
            client : {
                required : true,
            },

            phone : {
                required : true,
                isMobile:true,

            },
            time : {
                required : true,
                date:true

            },
            reasion : {
                required : false,
                maxlength : 200,

            },
            worker : {
                required : true,
                maxlength : 100,

            },
            olt : {
                required : false,
                maxlength : 200,
            }
        },

        messages : {
            client : {
                required : "<br/><span style='color: red;'>请输入客户名称</span>",
                maxlength : "<br/>><span style='color: red;'>最大长度不能超过100个字符！</span>"
            },
            phone : {
                required : "<br/><span style='color: red;'>请输入联系电话</span>",
                isMobile:"<span style='color: red;'>请输入正确的手机号码！</span>"
            },
            time: {
                required : "<span style='color: red;'>请选择时间！</span>",
                date : "<span style='color: red;'>请输入正确的时间格式</span>",
            },
            reasion: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            worker: {
                required : "<span style='color: red;'>请输入受理人</span>",
                maxlength : "<span style='color: red;'>最大长度不能超过100个字符！</span>"
            },
            olt: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
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
 * 添加故障
 */
function addReport() {

    var option = {
        dataType : 'json',

        success : function(value) {
            $('#add_report_form').resetForm();// 重置表单
            // $("#edit_userbutton").button('reset');
            $('#addReportModal').modal('hide');

            // 重新初始化tree

            if(value.code == 0){

                $.gritter.add({
                    title: '执行成功',
                    text: '故障信息删除成功',
                    class_name: 'success'
                });
                oTable1.fnClearTable(); //清空数据
                oTable1.fnDraw();
            } else {
                $.gritter.add({
                    title : '执行失败',
                    text : '故障信息添加失败！',
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
    $("#add_report_form").ajaxForm(option).submit();

}


/**
 * 添加故障跟踪
 */
function addTrace() {
    var reportid=$("#add_trace_report").val();
    var content=$("#add_trace_show").val();
    $.ajax({
        url : "../report/addTrace",
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
        url : "../report/getTraceByID",
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


function initGaoDeMapSearch() {
    AMap.plugin('AMap.Autocomplete',function(){//回调函数
        //实例化Autocomplete
        var autoOptions = {
            city: "changsha", //城市，默认全国
            input:"map_client_search"//使用联想输入的input的id
        };
        autocomplete= new AMap.Autocomplete(autoOptions);
        //TODO: 使用autocomplete对象调用相关功能
        AMap.event.addListener(autocomplete, "select", function(e){
            //TODO 针对选中的poi实现自己的功能


            $("#add_adcode").attr("value",e.poi.adcode);
            $("#add_geo").attr("value",e.poi.id);
            $("#add_address").attr("value",e.poi.district+e.poi.address);

        });
    });



}