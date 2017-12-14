package com.ese.cloud.client.controller.core;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统错误控制页面
 * Created by rencong on 16/10/2.
 */
public class SystemErrorController implements ErrorController{
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public String handleError(){
        return "pages/404";
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return ERROR_PATH;
    }

}
