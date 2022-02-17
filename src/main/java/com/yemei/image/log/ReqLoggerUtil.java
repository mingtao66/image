package com.yemei.image.log;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 数据记录日志
 */
public class ReqLoggerUtil {
    private static final String NULL_STRING = "-";
    private static final int NULL_INTGERT = -1;
    private static final JSONObject NULL_DATA = new JSONObject();
    private static final Logger logger = LogManager.getLogger(ReqLoggerUtil.class.getName());


    /**
     * @param event   事件名
     * @param version 版本号
     * @param uid     用户id
     * @param appId   应用id
     * @param storeId 店铺id
     * @param shop    店铺名
     * @param data    数据信息
     * @param ip      ip
     * @param ua      user-agent
     */
    public static void error(String event, String version, Integer platform, Integer uid, Integer appId, Integer storeId, String shop, Object data, String ip, String ua, String param) {
        JSONObject log = new JSONObject();
        log.put("event", event);
        log.put("v", version);
        log.put("eventTime", System.currentTimeMillis());
        log.put("ip", StringUtils.isNotBlank(ip) ? ip : NULL_STRING);
        log.put("ua", StringUtils.isNotBlank(ua) ? ua : NULL_STRING);
        log.put("uid", uid != null ? uid : NULL_INTGERT);
        log.put("appId", appId != null ? appId : NULL_INTGERT);
        log.put("storeId", storeId != null ? storeId : NULL_INTGERT);
        log.put("shop", StringUtils.isNotBlank(shop) ? shop : NULL_STRING);
        log.put("data", data != null ? data : NULL_DATA);
        log.put("platform", platform != null ? platform : NULL_INTGERT);
        log.put("param", StringUtils.isNotBlank(param) ? param : NULL_STRING);
        try {
            logger.log(Level.ERROR, log.toJSONString());
        } catch (Exception e) {
            LoggerUtil.error(ReqLoggerUtil.class, event + "事件记录失败", e);
        }
    }


    public static void error(String event, String version, Integer uid, Integer appId, Integer storeId, String shop, Object data) {
        error(event, version, NULL_INTGERT, uid, appId, storeId, shop, data, NULL_STRING, NULL_STRING, NULL_STRING);
    }


    public static void error(String event, String version, Object data) {
        error(event, version, NULL_INTGERT, NULL_INTGERT, NULL_INTGERT, NULL_INTGERT, NULL_STRING, data, NULL_STRING, NULL_STRING, NULL_STRING);
    }

}
