package com.yemei.image.controller;


import com.yemei.image.http.HttpRequestUtil;
import com.yemei.image.util.AlyImageUtil;
import com.yemei.image.util.AlyParamUtil;
import com.yemei.image.util.OssUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductController extends BaseController {


    /**
     * 商品抠图接口
     *
     * @param image 图像URL地址
     * @param form  如果不设置，则返回四通道PNG图。
     *              如果设置为mask，则返回单通道mask。
     *              如果设置为whiteBK，则返回白底图
     * @return
     */
    @GetMapping("matting")
    public Object productMatting(@RequestParam String image, @RequestParam(required = false) String form) {
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm-dd");
        String[] split = image.split("\\.");
        String suffix = split[split.length - 1];
        String fileName = sdf.format(new Date()) + "/" + UUID.randomUUID().toString() + '.' + suffix;

        if (image.contains("cn-shanghai.aliyuncs.com")) {
            InputStream stream = HttpRequestUtil.getStream(image, header);
            if (stream != null) {
                //将image上传至上海区域OSS
                String sourceName = "source/" + fileName;
                OssUtil.upload(AlyParamUtil.ossEndPoint, AlyParamUtil.key, AlyParamUtil.secret, AlyParamUtil.ossBucket, sourceName, stream);
                //使用上海区域的image地址进行抠图
                image = AlyParamUtil.ossDomain + sourceName;
            } else {
                return getFailResult();
            }
        }


        String imageURL = AlyImageUtil.productMatting(AlyParamUtil.imageEndPoint, AlyParamUtil.key, AlyParamUtil.secret, image, form);

        //接口返回的imageURL图片地址是有有效期的
        if (imageURL != null) {
            //将返回的图片地址上传至上海区域
            InputStream stream = HttpRequestUtil.getStream(imageURL, header);
            String targetName = "target/" + fileName;
            OssUtil.upload(AlyParamUtil.ossEndPoint, AlyParamUtil.key, AlyParamUtil.secret, AlyParamUtil.ossBucket, targetName, stream);

            String targetUrl = AlyParamUtil.ossDomain + targetName;
            HashMap<String, Object> result = getSuccessResult();
            result.put("url", targetUrl);
            return result;
        }


        return getFailResult();
    }
}
