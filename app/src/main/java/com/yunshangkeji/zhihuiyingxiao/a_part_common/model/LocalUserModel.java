package com.yunshangkeji.zhihuiyingxiao.a_part_common.model;

/**
 * Created by yellow on 2016/11/16.
 * 本地用户Model
 */

public class LocalUserModel {

    //账号
    private String account;

    //密码
    private String password;

    //token值
    private String token;

    //身份
    private String role;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
