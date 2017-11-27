package com.ese.cloud.client.controller.core;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("map")
public class LocationController {
    Logger logger=Logger.getLogger(LocationController.class);
    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(){
        return "map/index";
    }
}