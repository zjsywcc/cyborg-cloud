package com.ese.cloud.client.controller.core;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 *
 *
 */
@Controller
@RequestMapping("index")
public class IndexController {



    @RequestMapping("/register")
    public String register() {
        return "register";
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String main() {
        return "index";
    }

}