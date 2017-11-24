package com.ese.cloud.client.api;

/**
 * Created by wangchengcheng on 2017/11/24.
 */
public class LoginRequest extends BaseMobileRequest {


    private String username;

    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
