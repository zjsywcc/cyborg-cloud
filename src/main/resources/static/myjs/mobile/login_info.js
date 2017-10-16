/**
 * Created by user on 17/3/28.
 */

$(document).ready(function(){
    loginType();
    firstDate();
    lastDate();
});

function loginType() {

    $("#login_type").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
    <button class="button button-link pull-right close-picker">确定</button>\
    <h1 class="title">选择登陆方式</h1>\
    </header>',
        cols: [
            {
                textAlign: 'center',
                values: ['登陆后退出', '无法登陆']
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