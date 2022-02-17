package com.yemei.image.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlyParamUtil {

    public static String key;
    public static String secret;
    public static String ossEndPoint;
    public static String ossDomain;
    public static String ossBucket;
    public static String imageEndPoint;


    @Value("${aly.auth.key}")
    public void setKey(String key) {
        AlyParamUtil.key = key;
    }

    @Value("${aly.auth.secret}")
    public void setSecret(String secret) {
        AlyParamUtil.secret = secret;
    }

    @Value("${aly.oss.endpoint}")
    public void setOssEndPoint(String ossEndPoint) {
        AlyParamUtil.ossEndPoint = ossEndPoint;
    }

    @Value("${aly.oss.domain}")
    public void setOssDomain(String ossDomain) {
        AlyParamUtil.ossDomain = ossDomain;
    }

    @Value("${aly.oss.bucket}")
    public void setOssBucket(String ossBucket) {
        AlyParamUtil.ossBucket = ossBucket;
    }

    @Value("${aly.image.endpoint}")
    public void setImageEndPoint(String imageEndPoint) {
        AlyParamUtil.imageEndPoint = imageEndPoint;
    }

}
