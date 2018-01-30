package com.zhonggu.controller.reqmodel;

import java.io.Serializable;

/**
 * @author yangkai on 2018/1/30.
 */
public class User implements Serializable{
    private static final long serialVersionUID = -3134819176410530718L;
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
