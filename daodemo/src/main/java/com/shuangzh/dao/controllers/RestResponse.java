package com.shuangzh.dao.controllers;

/**
 * Created by admin on 2017/3/22.
 */
public class RestResponse {
    int code = 0;
    String message = "success";
    Object data = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
