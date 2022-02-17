package com.yemei.image.util;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.imageseg20191230.Client;
import com.aliyun.imageseg20191230.models.SegmentCommodityRequest;
import com.aliyun.imageseg20191230.models.SegmentCommodityResponse;
import com.aliyun.oss.common.utils.StringUtils;
import com.aliyun.teaopenapi.models.Config;
import com.yemei.image.log.LoggerUtil;


public class AlyImageUtil {
    public static final String WHITE_BK = "whiteBK";
    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.imageseg20191230.Client createClient(String endpoint, String accessKeyId, String accessKeySecret) {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = endpoint;
        try {
            return new com.aliyun.imageseg20191230.Client(config);
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.error(AlyImageUtil.class, "createClient 失败", e);
        }
        return null;
    }

    /**
     * 商品抠图
     *
     * @param key
     * @param secret
     * @return
     * @throws Exception
     */
    public static String productMatting(String endpoint, String key, String secret, String url, String form) {
        Client client = createClient(endpoint, key, secret);
        if (client != null) {
            SegmentCommodityRequest request = new SegmentCommodityRequest()
                    .setImageURL(url);
            if (!StringUtils.isNullOrEmpty(form) && ("mask".equals(form) || "whiteBK".equals(form))) {
                request.setReturnForm(form);
            }
//            RLock lock = RedisUtil.getLock("alibaba-service:product-matting");
            try {
//                boolean locked = lock.tryLock(20000, 500, TimeUnit.MILLISECONDS);
//                if (locked) {
                SegmentCommodityResponse result = client.segmentCommodity(request);
                LoggerUtil.info(AlyImageUtil.class, url + "_" + form + "商品抠图处理结果" + JSONObject.toJSONString(result));
                return result.getBody().getData().getImageURL();
//                }
            } catch (Exception e) {
                LoggerUtil.error(AlyImageUtil.class, url + "_" + form + "商品处理失败", e);
            }
        }
        return null;

    }

}
