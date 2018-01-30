package com.ese.cloud.client.controller.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangchengcheng on 2018/1/3.
 */
@Controller
@RequestMapping("screen")
public class ScreenController {

    Logger logger = LoggerFactory.getLogger(ScreenController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "screen/index";
    }
}
