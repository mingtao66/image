package com.yemei.image.controller;


import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 基础方法
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    public static final String SUCCESS = "success";
    public static final int SUCCESS_CODE = 200;
    public static final int FAILED_CODE = 201;
    public static final int PARAM_ERROR_CODE = 400;
    public static final String CODE = "code";
    public static final String DATA = "data";
    public static final String MSG = "msg";


    protected HashMap<String, Object> getSuccessResult() {
        if (response != null) {
            response.setStatus(SUCCESS_CODE);
        }

        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put(SUCCESS, Boolean.TRUE);
        return hashMap;
    }

    protected HashMap<String, Object> getSuccessResult(String msg) {
        HashMap<String, Object> hashMap = getSuccessResult();
        hashMap.put(MSG, msg);
        return hashMap;
    }

    protected HashMap<String, Object> getSuccessResult(Object result) {
        HashMap<String, Object> hashMap = getSuccessResult();
        hashMap.put(DATA, result);
        return hashMap;
    }

    protected HashMap<String, Object> getFailResult() {
        if (response != null) {
            response.setStatus(FAILED_CODE);
        }
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put(SUCCESS, Boolean.FALSE);
        return hashMap;
    }

    protected HashMap<String, Object> getFailResult(String msg) {
        HashMap<String, Object> hashMap = getFailResult();
        hashMap.put(MSG, msg);
        return hashMap;
    }

    protected HashMap<String, Object> getFailResult(int code, String msg) {
        HashMap<String, Object> hashMap = getFailResult(msg);
        hashMap.put(CODE, code);
        return hashMap;
    }

    protected HashMap<String, Object> paramsErrorResult() {
        if (response != null) {
            response.setStatus(PARAM_ERROR_CODE);
        }
        HashMap<String, Object> hashMap = getFailResult();
        hashMap.put(CODE, PARAM_ERROR_CODE);
        return hashMap;
    }

    protected HashMap<String, Object> paramsErrorResult(String error) {
        HashMap<String, Object> hashMap = paramsErrorResult();
        hashMap.put(MSG, error);
        return hashMap;
    }


}
