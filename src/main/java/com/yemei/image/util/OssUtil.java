package com.yemei.image.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.yemei.image.log.LoggerUtil;

import java.io.InputStream;

public class OssUtil {


    /**
     * @param endpoint
     * @param key
     * @param secret
     * @return
     */
    public static OSS getOSSClient(String endpoint, String key, String secret) {
        return new OSSClientBuilder().build(endpoint, key, secret);
    }


    /**
     * 上传文件至oss
     *
     * @param endpoint    Endpoint以华东1（杭州）为例https://oss-cn-hangzhou.aliyuncs.com，其它Region请按实际情况填写。
     * @param key         阿里云密钥key
     * @param secret      阿里云密钥secret
     * @param bucketName  填写Bucket名称 例如examplebucket。
     * @param fileName    填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
     * @param inputStream
     * @return
     */
    public static void upload(String endpoint, String key, String secret, String bucketName, String fileName, InputStream inputStream) {
        OSS ossClient = getOSSClient(endpoint, key, secret);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
            ossClient.putObject(putObjectRequest);
        } catch (Exception e) {
            LoggerUtil.error(OssUtil.class, "oss 文件上传失败", e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }

        }

    }

}
