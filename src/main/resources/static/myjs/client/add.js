var oTable1;
$(document).ready(function(){

    //初始化搜索
    initGaoDeMapSearch();
    formVaildate();
});






/**
 * 表单验证
 */
function formVaildate() {
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
            name : {
                required : true,
                maxlength : 200,
            },

            type : {
                required : true,

            },
            contact : {
                required : false,
                maxlength : 200
            },
            address : {
                required : false,
                maxlength : 200,

            },
            mobPhone : {
                required : false,
                isMobile : true,
                maxlength : 100,

            },
            telPhone : {
                required : false,
                maxlength : 200,
            },
            ip : {
                required : false,
            },
            accessNo : {
                required : false,
                maxlength : 200,
            },
            OLT : {
                required : false,
                maxlength : 200,
            },
            remark : {
                required : false,
                maxlength : 500,
            }
        },

        messages : {
            name : {
                required : "<br/><span style='color: red;'>请输入客户名称</span>",
                maxlength : "<br/>><span style='color: red;'>最大长度不能超过100个字符！</span>"
            },
            type : {

            },
            contact: {

            },
            address: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            mobPhone: {

                maxlength : "<span style='color: red;'>最大长度不能超过100个字符</span>",
                isMobile : "<span style='color: red;'>请输入正确的手机号码</span>"
            },
            telPhone: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            ip: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            accessNo: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            OLT: {
                maxlength : "<span style='color: red;'>最大长度不能超过200个字符！</span>"
            },
            remark: {
                maxlength : "<span style='color: red;'>最大长度不能超过500个字符！</span>"
            }
        },


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
                    text: '客户信息添加成功',
                    class_name: 'success'
                });
            } else {
                $.gritter.add({
                    title : '执行失败',
                    text : '客户信息添加失败！',
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






function initGaoDeMapSearch() {
    AMap.plugin('AMap.Autocomplete',function(){//回调函数
        //实例化Autocomplete
        var autoOptions = {
            city: "changsha", //城市，默认全国
            input:"add_client"//使用联想输入的input的id
        };
        autocomplete= new AMap.Autocomplete(autoOptions);
        //TODO: 使用autocomplete对象调用相关功能
        AMap.event.addListener(autocomplete, "select", function(e){
            //TODO 针对选中的poi实现自己的功能
           


            $("#add_address").attr("value",e.poi.district+e.poi.address);
            $("#add_lat").attr("value",e.poi.location.lat);
            $("#add_lng").attr("value",e.poi.location.lng);
        });
    });



}
