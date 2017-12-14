var loginMob;//登录手机号

/**
 * 模块js
 * Created by rencong on 16/10/8.
 */
$(document).ready(function(){

    if(loginMob == null || loginMob == undefined){
        $.router.load("#routers-index");
    }


    bindCity("base_city","选择注册城市");
    //bindCity("old_city","选择失效地");
    loseBindCity();
    selectChannel();
    loginType();
    firstDate();
    lastDate();
    loseDate();
    reloginDate();

    /*document.addEventListener('touchstart', function(event) {
        // 判断默认行为是否可以被禁用
        if (event.cancelable) {
            // 判断默认行为是否已经被禁用
            if (!event.defaultPrevented) {
                event.preventDefault();
            }
        }
    }, false);*/


});

/**
 * 基础信息-下一步-点击
 */
function base_next() {

    var name=$("#base_name").val();
    var city=$("#base_city").val();
    var regmob=$("#base_reg_mob").val();
    //var newmob=$("#base_new_mob").val();
    var carnum=$("#base_car_num").val();
    var identity=$("#base_identity_num").val();
    //var banknum=$("#base_bank_num").val();
    //var role=$("#base_role").val();
    
    var flag = true;


    if(flag == true && (name == null || name == '' || name == undefined)){
        flag = false;
        $.toast('请输入姓名', 2345, '');
    }

    if(flag == true && (city == null || city == '' || city == undefined)){
        flag = false;
        $.toast('请选择城市', 2345, '');
    }
    if(flag == true && (regmob == null || regmob == '' || regmob == undefined) ){
        flag = false;
        $.toast('请输入原手机号', 2345, '');
    }

    if(flag == true && (!check_mob_string(regmob) || (regmob == loginMob))){
        flag = false;
        $.toast('请输入正确的原手机号', 2345, '');
    }


    /*if(flag == true && (newmob == null || newmob == '' || newmob == undefined)){
        flag = false;
        $.toast('请输入新的手机号', 2345, '');
    }

    if(flag == true && !check_mob_string(newmob)){
        flag = false;
        $.toast('请输入正确的新手机号', 2345, '');
    }*/

    if(flag == true && (carnum == null || carnum == '' || carnum == undefined)){
        flag = false;
        $.toast('请输入车牌号', 2345, '');
    }

    if(flag == true && !check_carnum(carnum)){
        flag = false;
        $.toast('请输入正确的车牌号', 2345, '');
    }

    if(flag == true && (identity == null || identity == '' || identity == undefined)){
        flag = false;
        $.toast('请输入身份证号码', 2345, '');
    }
    
    if(flag == true && !check_icard(identity)){
        flag = false;
        $.toast('请输入正确的身份证号码', 2345, '');
    }
    
    /*if(flag == true && (banknum == null || banknum == '' || banknum == undefined)){
        flag = false;
        $.toast('请输入银行卡后6位', 2345, '');
    }*/

    /*if(flag == true && (role == null || role == '' || role == undefined)){
        flag = false;
        $.toast('请输选择业务线', 2345, '');
    }*/
    
   /* if(flag == true && (regmob == newmob)){
        flag = false;
        $.toast('原手机号不能与新手机号相同', 2345, '');
    }*/
    

    if(flag){
        $.router.load("#register_info");
    }

}

/**
 * 登陆信息-下一步-点击
 */
function login_next() {

    var login_type=$("#login_type").val();
    /*var first_time=$("#first_time").val();
    var last_time=$("#last_time").val();
    var relogin_time=$("#relogin_time").val();*/


    var flag = true;
    if(login_type == null || login_type == '' || login_type == undefined){
        flag = false;
        $.toast('请选择重新登录结果', 2345, '');
    }

    /*if(relogin_time == null || relogin_time == '' || relogin_time == undefined){
        flag = false;
        $.toast('请选择重新登录时间', 2345, '');
    }
    if(first_time == null || first_time == '' || first_time == undefined){
        flag = false;
        $.toast('请选择首次使用时间', 2345, '');
    }
    if(last_time == null || last_time == '' || last_time == undefined){
        flag = false;
        $.toast('请选择最后使用时间', 2345, '');
    }*/

    if(flag){
        $.router.load("#register_info");
    }


}

/**
 * 注册手机号信息-下一步-点击
 */
function register_next() {

    
    
    
}


/**
 * 检查手机号是否存在
 */
function check_mob() {
   
    var mob=$("#mob").val();
    loginMob = mob;
    
    
    var short = $("#short").val();
    if(check_mob_string(mob)){
        $.ajax({
            url : "../mobile/check_mob",
            dataType : "json",
            type : "POST",
            async : false,
            data : {mob:mob,shortUrl:short},
            error : function(error) {
                console.log(error.responseText);
            },
            success : function(e) {
                
                if(e.code == 200){
                    
                    checkMobLogin();
                    
                    

                }
                if(e.code == 201){
                    $.toast('该手机号已提交过申请,请勿重复操作', 2345, '');
                }
                if(e.code == 202){
                    $.toast('该手机号已经修改成功,请打开滴滴出行司机端直接登录', 2345, '');
                }
                if(e.code == -1){
                    $.toast('请输入正确的手机号', 2345, '');
                }
            }

        });
    }else{
        $.toast('手机号码格式错误', 2345, 'success top');
    }

}


/**
 * 绑定城市
 */
function bindCity(id,name) {
    $("#"+id).cityPicker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">'+name+'</h1>\
    </header>'
    });
}


function loseBindCity() {
    $("#old_city").cityPicker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">选择失效地</h1>\
    </header>'
    });
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

    var exp = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Za-z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;

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

    var exp = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9X]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;

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

/**
 * 业务线
 */
function selectChannel() {

    $("#base_role").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
  <button class="button button-link pull-right close-picker">确定</button>\
  <h1 class="title">业务线</h1>\
  </header>',

        cols: [
            {
                textAlign: 'center',
                values: ['快车','顺风车']
            }
        ]
    });

    //$("#base_role").picker("formatValue",);



}



function loginType() {

    $("#login_type").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">选择登录结果</h1>\
    </header>',
        cols: [
            {
                textAlign: 'center',
                values:['重新登录成功', '重新登录失败']
            }
        ]
    });

    

}



function firstDate() {

    $("#first_time").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">选择登陆方式</h1>\
    </header>',
        cols: [
            {
                textAlign: 'center',
                values: ['2017', '2016', '2015', '2014', '2013', '2012']
            },
            {
                textAlign: 'center',
                values: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
            }
        ]
    });
}


/**
 * 重新登录时间
 */
function reloginDate() {

    $("#relogin_time").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">选择登陆方式</h1>\
    </header>',
        cols: [
            {
                textAlign: 'center',
                values: ['2017', '2016', '2015', '2014', '2013', '2012']
            },
            {
                textAlign: 'center',
                values: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
            }
        ]
    });
}


function lastDate() {

    $("#last_time").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">选择登陆方式</h1>\
    </header>',
        cols: [
            {
                textAlign: 'center',
                values: ['2017', '2016', '2015', '2014', '2013', '2012']
            },
            {
                textAlign: 'center',
                values: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
            }
        ]
    });
}

/**
 * 原号失效时间
 */
function loseDate() {

    $("#loseDate").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">选择失效时间</h1>\
    </header>',
        cols: [
            {
                textAlign: 'center',
                values: ['2017', '2016', '2015', '2014', '2013', '2012']
            },
            {
                textAlign: 'center',
                values: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
            }
        ]
    });
}

/**
 * 保存信息
 */
function  saveinfo() {

    //基础信息
    var code=$("#short").val();
    var name=$("#base_name").val();
    var basecity=$("#base_city").val();
    var regmob=$("#base_reg_mob").val();
    //var newmob=$("#base_new_mob").val();
    var car=$("#base_car_num").val();
    var identity=$("#base_identity_num").val();
    //var bank=$("#base_bank_num").val();
    var bind=$("#base_bind_car").val();
    var role=$("#base_role").val();
    //登录信息
   // var relogin= $("#login_type").val();
   /* var reloginTime = $("#relogin_time").val();//重新登录时间
    var firstUserTime=$("#first_time").val();
    var lastUserTime=$("#last_time").val();*/
    //注册信息
    //var loseTime=$("#loseDate").val();
    //var loseCity=$("#old_city").val();
    var loseReason=$("#loseReason").val();



    var flag=true;
    /*if(flag==true && (loseTime == null || loseTime == '' || loseTime == undefined)){
        flag = false;
        $.toast('请选择原号失效时间', 2345, '');
    }
    if(flag==true && (loseCity == null || loseCity == '' || loseCity == undefined)){
        flag = false;
        $.toast('请选择原号失效地', 2345, '');
    }*/
    if(flag==true && (loseReason == null || loseReason == '' || loseReason == undefined)){
        flag = false;
        $.toast('请输入原号失败原因', 2345, '');
    }

    if(!flag){
       return;
    }
    

    
    $.ajax({
        url : "../mobile/save_info",
        dataType : "json",
        type : "POST",
        data : {
            code:code,
            name:name,
            basecity:basecity,
            regmob:regmob,
            //newmob:newmob,
            car:car,
            identity:identity,
            //bank:bank,
            bind:bind,
            role:role,
            //relogin:relogin,
            //reloginTime:reloginTime,
            //firstUserTime:firstUserTime,
            //lastUserTime:lastUserTime,
            //loseTime:loseTime,
            //loseCity:loseCity,
            loseReason:loseReason
        },
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {
            $.hidePreloader();
            if(e.code == 200){
                window.location.href="../mobile/photo?id="+e.data;
            }
            if(e.code == 400){
                alert("填写信息不完整,请重新填写");
            }
            if(e.code == -1){
                alert("上传失败");
            }
        }

    });
}

/**
 * 检查手机号是否登陆过
 */
function checkMobLogin() {
    var mob=$("#mob").val();
    $.ajax({
        url : "../mobile/check_login_status",
        dataType : "json",
        type : "POST",
        data : {
            mob:mob
        },
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.data == "false"){

                $.modal({
                    title:  '警告',
                    text: '请确认今日是否在滴滴司机端使用原手机号进行重新登录操作,如果已经操作,请点击继续。',
                    buttons: [
                        {
                            text: '取消',
                            onClick: function() {
                                return false;
                            }
                        },
                        {
                            text: '继续',
                            onClick: function() {
                                $.router.load("#base_info");
                                return true;
                            }
                        }
                    ]
                })
                
                
            }else {
                $.router.load("#base_info");
                return true;
            }

        }

    });
}

/**
 * 检查是否是微信
 * @returns {boolean} true是微信,false不是微信
 */
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}