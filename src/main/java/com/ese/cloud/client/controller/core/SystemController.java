package com.ese.cloud.client.controller.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统设置
 * Created by rencong on 16/9/13.
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @RequestMapping("/index")
    public String index() {
        return "system/index";
    }


}
