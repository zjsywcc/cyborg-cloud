var oTable1;
var allCompany;//全部公司信息
var allInterface;//全部接口人信息
var map;//地图对象
var district;//行政区
var selectObj;
$(document).ready(function(){

    selectObj = $('#interface_all').multiSelect({
        selectableOptgroup: true,
        selectableHeader: "<div class='custom-header'>可选接口人</div>",
        selectionHeader: "<div class='custom-header'>已选接口人</div>"
    });

    //获取全部外包公司信息
    allCompany();
    //全部外包人员信息
    allInterfaceFun();

    //初始化省级行政区域
    initProvicen();
    
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








/**
 * 获取全部接口人信息
 */
function allInterfaceFun() {
    $.ajax({
        url : "../technician/all_interface",
        dataType : "json",
        type : "POST",
        async : true,
        data : null,
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {
            if(e.code == 0){
                var obj = JSON.parse(e.data);
                allInterface = obj;
            } 
        }

    });
}

/**
 * 获取全部外包公司信息
 */
function allCompany() {
    $.ajax({
        url : "../outsourced/all",
        dataType : "json",
        type : "POST",
        async : true,
        data : null,
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                var obj = JSON.parse(e.data);
                allCompany = obj;
                countyChange();
            } else {

            }

        }

    });
}

/**
 * 县区选择动作
 */
function cityChange() {
    


}


/**
 * 初始化省级行政区域选择
 */
function initProvicen() {
    //行政区划查询
    var opts = {
        subdistrict: 1,   //返回下一级行政区
        level: 'province',//查询行政级别为 省
        showbiz:false  
    };
    district = new AMap.DistrictSearch(opts);//注意：需要使用插件同步下发功能才能这样直接使用
    district.search('中国', function(status, result) {
        if(status=='complete'){

            var subDistricts = result.districtList[0].districtList;
            var select = document.getElementById('add_province');
            for(var i = 0;i < subDistricts.length; i += 1){
                var name = subDistricts[i].name;
                var value = subDistricts[i].adcode;
                var option = document.createElement('option');
                option.value = value;
                option.innerText = name;
                select.appendChild(option);
            }

            provinceChange();
            
        }
    });
}

/**
 * 下拉选择,省级
 */
function provinceChange(){
    
    var abcode = $("#add_province option:selected").val();
    
    //行政区划查询
    var opts = {
        subdistrict: 1,   //返回下一级行政区
        level: 'city',//查询行政级别为 省
        showbiz:false
    };
    district = new AMap.DistrictSearch(opts);//注意：需要使用插件同步下发功能才能这样直接使用
    district.search(abcode, function(status, result) {
        if(status=='complete'){
            $("#add_city").empty();
            var subDistricts = result.districtList[0].districtList;
            var select = document.getElementById('add_city');
            for(var i = 0;i < subDistricts.length; i += 1){
                var name = subDistricts[i].name;
                var value = subDistricts[i].adcode;
                var option = document.createElement('option');
                option.value = value;
                option.innerText = name;
                select.appendChild(option);
            }

            cityChange();

        }
    });
}


/**
 * 下拉选择,市级
 */
function cityChange(){

    var abcode = $("#add_city").val();

    //行政区划查询
    var opts = {
        subdistrict: 1,   //返回下一级行政区
        level: 'district',//查询行政级别为 省
        showbiz:false
    };
    district = new AMap.DistrictSearch(opts);//注意：需要使用插件同步下发功能才能这样直接使用
    district.search(abcode, function(status, result) {
        if(status=='complete'){

            $("#add_county").empty();
            var subDistricts = result.districtList[0].districtList;
            var select = document.getElementById('add_county');
            for(var i = 0;i < subDistricts.length; i += 1){
                var name = subDistricts[i].name;
                var value = subDistricts[i].adcode;
                var option = document.createElement('option');
                option.value = value;
                option.innerText = name;
                select.appendChild(option);
            }

        }
    });
}

/**
 * 县区选择
 */
function countyChange() {

    var findByCode = $("#add_county").val();
    $.ajax({
        url : "findByCode",
        dataType : "json",
        type : "POST",
        async : false,
        data : {code:findByCode},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {


            var interfaceId = '';//接口人id
            var interfaceName='';//接口人姓名
            //遍历接口人信息
            for(var i=0;i<allInterface.length;i++){

                for(var j=0;j<allCompany.length;j++){
                    if(allInterface[i].companyId == allCompany[j].id){
                        interfaceName = allInterface[i].name+"("+allCompany[j].name+")";
                        interfaceId = allInterface[i].id;
                    }
                }
                $('#interface_all').multiSelect('addOption', { value: interfaceId, text: interfaceName});
            }
            $('#interface_all').multiSelect('deselect_all');
            
            if(e.code == 0){
                var list = JSON.parse(e.data);
                $('#interface_all').multiSelect('select', list.technicians);
            }

        }

    });


}

