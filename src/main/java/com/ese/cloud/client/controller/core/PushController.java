package com.ese.cloud.client.controller.core;

import com.ese.cloud.client.service.MonitorEMGInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by huarui on 2017/12/12
 */
@Controller
@RequestMapping("push")
public class PushController {

    Logger logger = Logger.getLogger(MonitorController.class);

    @Autowired
    MonitorEMGInfoService monitorEMGInfoService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {

        System.out.println("here is push!");
        return "push/index";
    }
}