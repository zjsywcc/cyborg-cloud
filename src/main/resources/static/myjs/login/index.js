/**
 * 模块js
 * Created by rencong on 16/10/8.
 */
$(document).ready(function(){
    //初始化
    $("#login_info_modal").hide();
});


/**
 * 用户登录
 */
function login() {

    var username=$("#username").val();
    var password=$("#password").val();
    
    if(username == '' || password == ''){
        $("#login_info").html("用户名或者密码不能为空!");
        $("#login_info_modal").css("display","block");
        return;
    }

    
    

    $.ajax({
        url : "../login/login",
        dataType : "json",
        type : "POST",
        async : false,
        data : {username:username,password:password},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {
             //登录成功
             if(e.code == 0){
                 location.href = "../cyborg/index";
             }
             //登录失败
             if(e.code == -1){
                 $("#login_info").html("用户名或者密码错误!");
                 $("#login_info_modal").css("display","block");
             }

        }

    });
}
