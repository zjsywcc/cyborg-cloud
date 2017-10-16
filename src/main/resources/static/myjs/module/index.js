/**
 * 树形结构数据
 */
var tree_data;

/**
 * 模块js
 * Created by rencong on 16/10/8.
 */
$(document).ready(function(){
   
    initTree();
    
    $('.md-trigger').modalEffects();
});


/**
 * 添加功能模块信息
 */
function add() {
    
    var name=$("#add_name").val();
    var url=$("#add_url").val();
    var key=$("#add_key").val();
    var ico=$("#add_ico").val();
    var remark=$("#add_remark").val();
    var level=$("#add_level").val();
    var father=$("#add_father").val();
    var sort=$("#add_sort").val();

    $.ajax({
        url : "../module/add",
        dataType : "json",
        type : "POST",
        async : false,
        data : {name:name, url:url, key:key, ico:ico, remark:remark,level:level,father:father,sort:sort},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                $.gritter.add({
                    title: '执行成功',
                    text: '系统模块信息添加成功',
                    class_name: 'success'
                });
                initTree();
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '系统模块信息添加失败',
                    class_name: 'danger'
                });
            }


        }

    });
}
/**
 * 修改功能模块信息
 */
function update(){
    $.ajax({
        url : "../alarm/allgroup",
        dataType : "json",
        type : "POST",
        async : false,
        data : null,
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {
            for (var i=0;i<e.length;i++) {


                $("#add_group").append("<option value='" + e[i].id + "'>" + e[i].name+ "</option>");
                $("#edit_group").append("<option value='" + e[i].id + "'>" + e[i].name+ "</option>");
                selectData.set(e[i].id,e[i].name);

            }
            //$('#add_group').multiselect();

        }

    });
}

/**
 * 删除功能模块信息
 */
function del(){
    $.ajax({
        url : "../alarm/allgroup",
        dataType : "json",
        type : "POST",
        async : false,
        data : null,
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {
            for (var i=0;i<e.length;i++) {


                $("#add_group").append("<option value='" + e[i].id + "'>" + e[i].name+ "</option>");
                $("#edit_group").append("<option value='" + e[i].id + "'>" + e[i].name+ "</option>");
                selectData.set(e[i].id,e[i].name);

            }
            //$('#add_group').multiselect();

        }

    });
}



function findByLevel(id){

    var level=$("#"+id).val();

    $.ajax({
        url : "../module/find_by_level",
        dataType : "json",
        type : "POST",
        async : false,
        data : {level:level-1},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {
            $("#add_father").empty();
            if(e.code == 0){
                var data = eval(e.data);
                for(var obj in data){

                    $("#add_father").append("<option value='" + data[obj].id + "'>" + data[obj].name+ "</option>");
                    //$("#edit_father").append("<option value='" + data[obj].id + "'>" + data[obj].name+ "</option>");
                }
            }else{

                $("#add_father").append("<option value='0'>无</option>");
                //$("#edit_father").append("<option value='0'>无</option>");
            }
        }

    });
}

/**
 * 修改级别选择
 * @param id
 */
function findByLevelEdit(id){

    var level=$("#"+id).val();
    for(var i in tree_data){
        if(tree_data[i].level == (level-1)){
            $("#edit_father").append("<option value='" + tree_data[i].id + "'>" + tree_data[i].name+ "</option>");
        }

    }
}


function initTree() {

    findAll();

}

/**
 * 查询全部模块信息
 */
function findAll(){

    $.ajax({
        url : "../module/all",
        dataType : "json",
        type : "POST",
        async : false,
        data : null,
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {
            $("#add_father").empty();
            if(e.code == 0){
                var data = eval(e.data);
                tree_data = data;
                creatTree(data);
            }else{


            }
        }

    });
}

/**
 * 生成tree 
 */
function creatTree(list) {


    $("#module_tree").empty();

    var li_one = '';

    var level1 = new Array();
    var level2 = new Array();
    var level3 = new Array();
    //遍历分类
    for(var i in list){
        if(list[i].level == 0){
            level1.push(list[i]);
        }
        if(list[i].level == 1){
            level2.push(list[i]);
        }
        if(list[i].level == 2){
            level3.push(list[i]);
        }
    }
    //生成一级
    for(var obj in level1){
        var html = '<li>'
            +'<label class="tree-toggler nav-header"><i class="fa fa-folder-o"></i>'+level1[obj].name+'</label>'
            +'<ul id="module_tree_'+level1[obj].id+'" class="nav nav-list tree"></ul>'
            +'</li>';
        $("#module_tree").append(html);
    }

    //生成二级
    for(var obj in level2){
        var html = '<li>'
        +'<label class="tree-toggler nav-header"><i class="fa fa-folder-o"></i>'+level2[obj].name+'</label>'
        +'<ul id="module_tree_'+level2[obj].id+'" class="nav nav-list tree"></ul>'
        +'</li>';

        $("#module_tree_"+level2[obj].parentId).append(html);

    }
    //生成三级
    for(var obj in level3){
        var html = '<li>'
            +'<label class="tree-toggler nav-header"><i class="fa fa-folder-o"></i>'+level3[obj].name+'</label>'
            +'<ul id="module_tree_'+level3[obj].id+'" class="nav nav-list tree"></ul>'
            +'</li>';

        $("#module_tree_"+level3[obj].parentId).append(html);

    }


    //初始化动作
    $('label.tree-toggler').click(function () {
        var icon = $(this).children(".fa");
        if(icon.hasClass("fa-folder-o")){
            icon.removeClass("fa-folder-o").addClass("fa-folder-open-o");
        }else{
            icon.removeClass("fa-folder-open-o").addClass("fa-folder-o");
        }

        $(this).parent().children('ul.tree').toggle(300,function(){
            $(this).parent().toggleClass("open");
            $(".tree .nscroller").nanoScroller({ preventPageScrolling: true });
        });

        edit(this);

    });

}

/**
 * 修改信息
 * @param id
 */
function edit(e) {
    var divID = e.nextSibling.id;
    
    var divIDS = divID.split("_");
    $.ajax({
        url : "../module/findById",
        dataType : "json",
        type : "POST",
        async : false,
        data : {id:divIDS[2]},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                var data = JSON.parse(e.data);
                $("#edit_id").attr("value",data.id);
                $("#edit_level").val(data.level);
                $("#edit_father").val(data.parentId);
                $("#edit_name").attr("value",data.name);
                $("#edit_url").attr("value",data.url);
                $("#edit_ico").attr("value",data.ico);
                $("#edit_key").attr("value",data.key);
                $("#edit_sort").attr("value",data.sort);
                $("#edit_remark").attr("value",data.remark);

            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '系统模块信息查询失败',
                    class_name: 'danger'
                });
            }

            findByLevelEdit('edit_level');


        }

    });
    
    
    
    
}



/**
 * 修改
 */
function update() {
    var option = {
        dataType : 'json',

        success : function(value) {
            $('#edit_form').resetForm();// 重置表单

            if(value.code == 0){

                $.gritter.add({
                    title: '执行成功',
                    text: '信息修改成功',
                    class_name: 'success'
                });
                initTree();
            } else {
                $.gritter.add({
                    title : '执行失败',
                    text : '信息修改失败！',
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
    $("#edit_form").ajaxForm(option).submit();
}


/**
 * 打开删除框
 * @param id
 */
function deleteModal() {
    

    $("#deleteModal").modal('show');
}

/**
 * 确认删除
 */
function deleteOk() {

    var id= $("#edit_id").val();

    $.ajax({
        url : "../module/delete",
        dataType : "json",
        type : "POST",
        async : false,
        data : {id:id},
        error : function(error) {
            console.log(error.responseText);
        },
        success : function(e) {

            if(e.code == 0){
                $.gritter.add({
                    title: '执行成功',
                    text: '信息删除成功',
                    class_name: 'success'
                });
                initTree();
            }else{
                $.gritter.add({
                    title: '执行失败',
                    text: '信息删除失败',
                    class_name: 'danger'
                });
            }


        }

    });
}

