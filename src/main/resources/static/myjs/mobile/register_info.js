/**
 * 用户基础信息
 * Created by rencong on 16/10/8.
 */
$(document).ready(function(){
   
    bindCity();

});

/**
 * 添加
 */
function add() {

    if(addFormVaildate()){
        document.add_form.submit();
    }

    
    
}


function bindCity() {
    $("#add_city").cityPicker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">选择注册城市</h1>\
    </header>'
    });
}


/**
 * 进行表单验证
 */
function addFormVaildate() {
    
    var status = true;

    var msg="";

    var name = $("#add_name").val();
    var city = $("#add_city").val();
    var regmob = $("#add_reg_mob").val();
    var newmob = $("#add_new_mob").val();
    var car = $("#add_car_num").val();
    var identity = $("#add_identity_num").val();
    var bank = $("#add_bank_num").val();


    if(!check_name(name)){
        msg = msg + "请输入正确的姓名<br/>";
        status = false;
    }
    if(status && !check_mob_string(regmob)){
        msg = msg + "请输入正确的注册手机号<br/>";
        status = false;
    }
    if(status && !check_mob_string(newmob)){
        msg = msg + "请输入正确的新手机号<br/>";
        status = false;
    }
    if(status && !check_carnum(car)){
        msg = msg + "请输入正确的车牌号码<br/>";
        status = false;
    }
    if(status && !check_icard(identity)){
        msg = msg + "请输入正确的身份证号码<br/>";
        status = false;
    }

    $.toast(msg, 2345, 'success top');
    
    return status;
    
    
}





/**
 * 检查手机号是否符合格式要求
 * @param value
 * @returns {boolean}
 */
function check_mob_string(value) {
    var length = value.length;
    var mobile = /^1[34578]\d{9}$/;

    if(length == 11 && mobile.test(value)){
        return true;
    }else {
        return false;
    }
}

/**
 * 检查车牌号是否符合要求
 * @param value
 */
function check_carnum(value) {

    var exp = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;

    if(exp.test(value)){
        return true;
    }else {
        return false;
    }
}

/**
 * 检查身份证
 */
function check_icard(value) {

    var exp = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;

    if(exp.test(value)){
        return true;
    }else {
        return false;
    }
}

/**
 * 检查姓名
 * @param value
 * @returns {boolean}
 */
function check_name(value) {
    var length = value.length;

    if(length <5 && length >0){
        return true;
    }else {
        return false;
    }
}





