package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.entity.CyborganCoordinate;
//import com.ese.cloud.client.service.CyborgCoordinateService;
import com.ese.cloud.client.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("map")
public class LocationController {
    Logger logger=Logger.getLogger(LocationController.class);

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(){
        return "map/index";
    }

    /**
     * 获取电子人坐标
     */
    @RequestMapping(value = "/getLocationData", method = RequestMethod.POST)
    @ResponseBody
    public String getLocationData() {

      //  List<CyborganCoordinate> LocationInfos = CyborgCoordinateService.all();

        try {
            List<CyborganCoordinate> LocationInfos = new ArrayList<>();
            long timestamp = new Date().getTime();
            double fakeX=120.135858;
            double fakeY=30.256759;
            for(int i = 0; i < 5; i++) {
                fakeX =fakeX+0.001;
                fakeY=fakeY+0.002;
               // System.out.println(fakeX);
                CyborganCoordinate LocationInfo = new CyborganCoordinate();
                LocationInfo.setValues(fakeX,fakeY);
                System.out.println(LocationInfo.x);
                LocationInfos.add(LocationInfo);
            }
            return ReturnData.result(0, "获取申请统计数据成功", JSON.toJSONString(LocationInfos));
        } catch (Exception e) {
            logger.error("获取申请统计数据失败：", e);
            return ReturnData.result(-1, "获取申请统计数据失败", null);
        }
    }

}