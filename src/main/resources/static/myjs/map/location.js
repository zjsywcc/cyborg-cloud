// 百度地图API功能
$(document).ready(function () {
    var map = new BMap.Map("chartEMG");
    var point = new BMap.Point(120.135858, 30.256759);
    map.centerAndZoom(point, 16);

// 编写自定义函数,创建标注
    function addMarker(point) {
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
    }

    function getLocationData() {
        $.ajax({
            url: "../map/getLocationData",
            dataType: "json",
            type: "POST",
            async: true,
            data: null,
            error: function (error) {
                console.log(error.responseText);
            },
            success: function (e) {
                if (e.code == 0) {
                    console.log(e);
                    var LocationData = JSON.parse(e.data);
                    for(var i=0;i<LocationData.length;i++)
                    {
                        console.log(LocationData[i].x);
                        var point = new BMap.Point(LocationData[i].x, LocationData[i].y);
                        addMarker(point);
                    }

                } else {
                    $.gritter.add({
                        title: '执行失败',
                        text: e.msg,
                        class_name: 'danger'
                    });
                }
            }
        });
    }
    getLocationData();
});