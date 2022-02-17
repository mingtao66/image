package com.yemei.image.http;


import com.alibaba.fastjson.JSONObject;
import com.yemei.image.log.LoggerUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mutao
 */
public abstract class HttpRequestUtil {
    private static String APPLICATION_JSON = "application/json;charset=utf-8";
    private static String DEFAULT_CONTENT_TYPE = "application/json";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final RequestConfig requestConfig;
    private static final RequestConfig requestConfigDownload;
    private static CloseableHttpClient client;
    private static final Map<String, String> DEFAULT_HEADER = new HashMap<>();

    static {
        requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).setConnectionRequestTimeout(100000).build();
        requestConfigDownload = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).setConnectionRequestTimeout(15000).build();
        client = HttpClientBuilder.create().build();
        DEFAULT_HEADER.put(HTTP.CONTENT_TYPE, APPLICATION_JSON);
    }

    public static Response get(String url) {

        return get(url, DEFAULT_HEADER);
    }

    public static Response post(String url, String form, Map<String, String> header) {
        return post(url, form, header, DEFAULT_CONTENT_TYPE);
    }

    public static Response patch(String url, String form, Map<String, String> header) {
        return patch(url, form, header, DEFAULT_CONTENT_TYPE);
    }

    public static Response post(String url, Map<String, Object> form, Map<String, String> header) {
        return post(url, JSONObject.toJSONString(form), header, DEFAULT_CONTENT_TYPE);
    }

    public static Response patch(String url, Map<String, Object> form, Map<String, String> header) {
        return patch(url, JSONObject.toJSONString(form), header, DEFAULT_CONTENT_TYPE);
    }

    public static Response post(String url, Map<String, Object> form) {
        return post(url, form, DEFAULT_HEADER);
    }

    public static Response patch(String url, Map<String, Object> form) {
        return patch(url, form, DEFAULT_HEADER);
    }

    public static Response post(String url, LinkedHashMap<String, String> form, File file, String fileName, Map<String, String> header, String contentType) {
        HttpPost request = new HttpPost(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        form.forEach((key, value) -> {
            multipartEntityBuilder.addPart(key, new StringBody(value, ContentType.create("text/plain", Consts.UTF_8)));
        });

        multipartEntityBuilder.addPart(fileName, new FileBody(file));
        multipartEntityBuilder.setBoundary(contentType);
        multipartEntityBuilder.setContentType(ContentType.create(DEFAULT_CONTENT_TYPE, Consts.UTF_8));
        HttpEntity build = multipartEntityBuilder.build();
        request.setEntity(build);
        request.setConfig(requestConfig);
        if (header != null) {
            for (String key : header.keySet()) {
                request.addHeader(key, header.get(key));
            }
        }
        return execute(url, JSONObject.toJSONString(form), request);
    }


    public static Response put(String url, Map<String, Object> form, Map<String, String> header) {
        return put(url, form, header, DEFAULT_CONTENT_TYPE);
    }

    public static Response put(String url, Map<String, Object> form, Map<String, String> header, String contentType) {
        return put(url, JSONObject.toJSONString(form), header, contentType);
    }

    public static String doPost(String url, NameValuePair[] data) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            String result = postMethod.getResponseBodyAsString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public static Response delete(String url) {
        return delete(url, DEFAULT_HEADER);
    }

    public static Response post(String url, String form, Map<String, String> header, String contentType) {
        HttpPost request = new HttpPost(url);
        request.setConfig(requestConfig);
        request.setEntity(stringEntity(form, contentType));
        if (header != null) {
            for (String key : header.keySet()) {
                request.addHeader(key, header.get(key));
            }
        }
        return execute(url, form, request);
    }

    public static Response patch(String url, String form, Map<String, String> header, String contentType) {
        HttpPatch request = new HttpPatch(url);
        request.setConfig(requestConfig);
        request.setEntity(stringEntity(form, contentType));
        if (header != null) {
            for (String key : header.keySet()) {
                request.addHeader(key, header.get(key));
            }
        }
        return execute(url, form, request);
    }

    public static Response put(String url, String form, Map<String, String> header, String contentType) {
        HttpPut request = new HttpPut(url);
        request.setEntity(stringEntity(form, contentType));
        request.setConfig(requestConfig);
        if (header != null) {
            for (String key : header.keySet()) {
                request.addHeader(key, header.get(key));
            }
        }
        return execute(url, form, request);
    }

    public static Response delete(String url, Map<String, String> header) {
        HttpDelete request = new HttpDelete(url);
        request.setConfig(requestConfig);
        if (header != null) {
            for (String key : header.keySet()) {
                request.addHeader(key, header.get(key));
            }
        }
        return execute(url, null, request);
    }

    public static Response get(String url, Map<String, String> headers) {
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                request.addHeader(key, headers.get(key));
            }
        }
        return execute(url, null, request);
    }

    public static InputStream getStream(String url) {
        return getStream(url, null);
    }

    public static InputStream getStream(String url, Map<String, String> headers) {
        long start = System.currentTimeMillis();
        Response res = new Response();
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfigDownload);
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                request.addHeader(key, headers.get(key));
            }
        }
        CloseableHttpResponse execute;
        try {
            execute = client.execute(request);
            System.out.println(execute.getStatusLine().getStatusCode());
            return execute.getEntity().getContent();
        } catch (IOException e) {
            res.setLocalizedMessage(e.getLocalizedMessage());
            res.setMessage(e.getMessage());
            res.setThrowable(e.getCause());
        } finally {
            JSONObject data = new JSONObject();
            data.put("url", url);
            data.put("form", null);
            data.put("method", request.getMethod());
            data.put("header", request.getAllHeaders());
            data.put("result", res);
            data.put("runTime", System.currentTimeMillis() - start);
            data.put("startTime", start);
            data.put("endTime", System.currentTimeMillis());
            data.put("e", res.getThrowable());
            LoggerUtil.info(HttpRequestUtil.class, data.toJSONString());
        }

        return null;
    }

    public static Response execute(String url, String from, HttpUriRequest request) {
        long start = System.currentTimeMillis();
        Response res = new Response();
        try {
            CloseableHttpResponse execute = client.execute(request);
            res.setResult(EntityUtils.toString(execute.getEntity(), DEFAULT_CHARSET));
            res.setHeaders(execute.getAllHeaders());
            res.setStatusCode(execute.getStatusLine().getStatusCode());
        } catch (IOException e) {
            res.setLocalizedMessage(e.getLocalizedMessage());
            res.setMessage(e.getMessage());
            res.setThrowable(e.getCause());
        } finally {
            JSONObject data = new JSONObject();
            data.put("url", url);
            data.put("form", from);
            data.put("method", request.getMethod());
            data.put("header", request.getAllHeaders());
            data.put("result", res);
            data.put("runTime", System.currentTimeMillis() - start);
            data.put("startTime", start);
            data.put("endTime", System.currentTimeMillis());
            data.put("e", res.getThrowable());
            LoggerUtil.info(HttpRequestUtil.class, data.toJSONString());
        }
        return res;
    }


    public static Response postByByte(String url, String form, Map<String, String> header) {
        HttpPost request = new HttpPost(url);
        request.setConfig(requestConfig);
        request.setEntity(byteArrayEntity(form));
        if (header != null) {
            for (String key : header.keySet()) {
                request.addHeader(key, header.get(key));
            }
        }
        return execute(url, form, request);
    }

    private static ByteArrayEntity byteArrayEntity(String form) {
        ByteArrayEntity entity = new ByteArrayEntity(form.getBytes());
        entity.setContentEncoding(DEFAULT_CHARSET);
        entity.setContentType(DEFAULT_CONTENT_TYPE);
        return entity;
    }

    private static StringEntity stringEntity(String form, String contentType) {
        StringEntity entity = new StringEntity(form, DEFAULT_CHARSET.toLowerCase());
        entity.setContentEncoding(DEFAULT_CHARSET);
        entity.setContentType(contentType);
        return entity;
    }
}
