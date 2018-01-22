package com.ese.cloud.client.controller.core;

import com.ese.cloud.client.service.MonitorEMGInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("tiredness")
public class TirednessController {
    Logger logger = Logger.getLogger(MonitorController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "tiredness/index";
    }
    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public String index2() {
        return "tiredness/index2";
    }
}
