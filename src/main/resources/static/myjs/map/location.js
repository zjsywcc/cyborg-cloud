// 百度地图API功能
$(document).ready(function () {
    var map = new BMap.Map("chartEMG");
    var point = new BMap.Point(120.135858, 30.256759);
    map.centerAndZoom(point, 18);
    map.enableScrollWheelZoom(true); //启用滚轮放大缩小
    //向地图中添加缩放控件
    var ctrlNav = new window.BMap.NavigationControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        type: BMAP_NAVIGATION_CONTROL_LARGE
    });
    map.addControl(ctrlNav);

    //向地图中添加缩略图控件
    var ctrlOve = new window.BMap.OverviewMapControl({
        anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
        isOpen: 1
    });
    map.addControl(ctrlOve);

    //向地图中添加比例尺控件
    var ctrlSca = new window.BMap.ScaleControl({
        anchor: BMAP_ANCHOR_BOTTOM_LEFT
    });
    map.addControl(ctrlSca);


// 编写自定义函数,创建标注
    function addMarker(point,i) {
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
        var name="电子人";
        var label = new BMap.Label(i,{offset:new BMap.Size(20,-10)});
        marker.setLabel(label);
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
                        addMarker(point,i);
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