var idurl=new Array();
var driverurl=new Array();
var moburl=new Array();
var otherurl = new Array();

/**
 * 上传图片
 * Created by rencong on 16/10/8.
 */
$(document).ready(function(){

    var identity_url='../upload/identity';
    var driver_url='../upload/driver';
    var mobile_url='../upload/mob';
    //var other_url='../upload/other';

    //上传身份证
    updateID('addIdentity','identityList',identity_url);
    //上传驾驶证
    updateID('addDriver','driverList',driver_url);
    //上传手机号
    updateID('addMob','mobList',mobile_url);
    //其他
    //updateID('addOther','otherList',other_url);


});


/**
 * 上传照片地址
 */
function submitpic() {


    var r=confirm("确认提交?");
    if(r==true){
        submitAjax(); 
    }


    




}


function submitAjax(){
    var id=$("#pic_id").val();

    if(id == '' || id == undefined || id == null){
        alert("提交失败,请重试或重新打开链接");
        return;
    }


    /*if(idurl.length == 0 || driverurl.length == 0){

     alert("请上传手持身份证和驾驶证照片");

     return ;
     }*/


    $.ajax({
        url : "../mobile/submit_info",
        dataType : "json",
        traditional:true,
        type : "POST",
        data : {
            id:id,
            idcard:idurl,
            mob: moburl,
            other: otherurl,
            driver: driverurl
        },
        error : function(error) {
            alert("提交失败失败");
            console.log(error.responseText);
        },
        success : function(e) {



            if(e.code == 200){
                window.location.href="../mobile/success";

            }else{

                alert("上传失败");
            }
        }

    });
}


/**
 * 身份证照片
 */
function updateID(picker,list,url) {


    //初始化Web Uploader
    var uploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: true,
        // 文件接收服务端。
        server: url,
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#'+picker,
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        },
        resize: true,
        chunked: true,
        chunkSize: 1024*1024
    });


    
    var $list=$("#"+list);
    //当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file) {


        var $li = $('<div id="' + file.id + '" class="file-item"><img></div>'),
            $img = $li.find('img');
            // $list为容器jQuery实例
            $list.append( $li );

        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr( 'src', src );
        }, 60, 50 );
    });
    //文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<p class="progress"><span></span></p>')
                .appendTo( $li )
                .find('span');
        }

        $percent.css( 'width', percentage * 100 + '%' );
    });

    //文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file ,response) {

        $( '#'+file.id ).find('p.state').text('已上传');

        //console.log(response);

        if(picker == 'addIdentity'){
            idurl.push(response.data);
        }
        if(picker == 'addDriver'){
            driverurl.push(response.data);
        }
        if(picker == 'addMob'){
            moburl.push(response.data);
        }
        if(picker == 'addOther'){
            otherurl.push(response.data);
        }



        $( '#'+file.id ).addClass('upload-state-done');
    });

    //文件上传失败，显示上传出错。
    uploader.on('uploadError',function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');
        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div></div>').appendTo( $li );
        }

        $error.text('上传失败');
    });

    //完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file) {
       
        $( '#'+file.id ).find('.progress').remove();
    });
}


