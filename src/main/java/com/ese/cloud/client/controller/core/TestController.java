package com.ese.cloud.client.controller.core;

import com.ese.cloud.client.service.RecordInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by user on 17/5/27.
 */
@Controller
@RequestMapping("test")
public class TestController {

    Logger logger = Logger.getLogger(TestController.class);

    @Autowired
    RecordInfoService recordInfoService;


    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestParam("sSearch") String sSearch){





        return "";
    }


}
