package com.example.springbootlearn.util;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-07-13 17:26
 */

import com.alibaba.fastjson.JSONObject;
import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * UnirestUtils工具类，封装常用的get和post请求，并支持代理设置
 */
@Data
public class UnirestUtils {
    private final static Logger logger = LogManager.getLogger(UnirestUtils.class);

    /**
     * 默认编码格式
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 代理是否开启
     */
    private static boolean openProxy = false;

    /**
     * 代理主机
     */
    private static String proxyHost;

    /**
     * 代理端口
     */
    private static int proxyPort;

    /**
     * URL前缀
     */
    private static String urlPrefix;

    /**
     * 默认请求头
     */
    private static Map<String, String> defaultHeader = new HashMap<>();

    /**
     * json请求头，Content-Type: application/json
     */
    public static Map<String, String> headerJson;

    /**
     * form请求头，Content-Type: application/x-www-form-urlencoded
     */
    public static Map<String, String> headerForm;

    static {
        headerJson = new HashMap<String, String>() {
        };
        headerJson.put("Content-Type", "application/json");
        headerJson.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.114 Safari/537.36 Edg/103.0.1264.49");

        headerForm = new HashMap<String, String>() {
        };
        headerForm.put("Content-Type", "application/x-www-form-urlencoded");

        Unirest.config().verifySsl(false);
        Unirest.config().connectTimeout(30000);
    }

    /**
     * get请求
     *
     * @param url 请求地址
     * @return
     */
    public static JSONObject get(String url) {
        return get(url, null, defaultHeader);
    }

    /**
     * get请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     */
    public static JSONObject get(String url, Map<String, Object> params) {
        return get(url, params, defaultHeader);
    }

    /**
     * get请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static JSONObject get(String url, Map<String, Object> params, Map<String, String> headers) {
        long startTime = System.currentTimeMillis();
        url = processUrl(url);
        GetRequest getRequest = Unirest.get(url);
        if (openProxy) {
            getRequest.proxy(proxyHost, proxyPort);
        }
        if (params != null && params.size() > 0) {
            for (String s : params.keySet()) {
                getRequest.queryString(s, params.get(s));
            }
        }
        if (headers != null && headers.size() > 0) {
            for (String s : headers.keySet()) {
                getRequest.header(s, headers.get(s));
            }
        }
        JSONObject response = JSONObject.parseObject(getRequest.asString().getBody());
        RcLoggerUtils.recordReq(logger, "get", startTime, url, headers, params, response);
        return response;
    }

    /**
     * post请求
     *
     * @param url 请求地址
     * @return
     */
    public static JSONObject post(String url) {
        return post(url, null, headerJson);
    }

    /**
     * post请求
     *
     * @param url     请求地址
     * @param payload 请求body
     * @return
     */
    public static JSONObject post(String url, String payload) {
        return post(url, payload, headerJson);
    }

    /**
     * post请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return
     */
    public static JSONObject post(String url, Map<String, String> headers) {
        return post(url, null, headers);
    }

    /**
     * post请求
     *
     * @param url     请求地址
     * @param payload 请求body
     * @param headers 请求头
     * @return
     */
    public static JSONObject post(String url, String payload, Map<String, String> headers) {
        String res;
        url = processUrl(url);
        long startTime = System.currentTimeMillis();
        HttpRequestWithBody httpRequestWithBody = Unirest.post(url);
        if (openProxy) {
            httpRequestWithBody.proxy(proxyHost, proxyPort);
        }
        if (headers != null && headers.size() > 0) {
            for (String s : headers.keySet()) {
                httpRequestWithBody.header(s, headers.get(s));
            }
        }

        if (payload == null) {
            res = httpRequestWithBody.asString().getBody();
        } else {
            res = httpRequestWithBody.body(payload).asString().getBody();
        }
        RcLoggerUtils.recordReq(logger, "post", startTime, url, headers, payload, res);
        return JSONObject.parseObject(res);
    }

    /**
     * 处理url
     *
     * @param url 原始url，如urlPrefix不为空，则会在url前补上urlPrefix
     * @return
     */
    private static String processUrl(String url) {
        if (urlPrefix != null) {
            url = urlPrefix + url;
        }
        return url;
    }

    /**
     * uri编码，默认采用UTF-8编码
     *
     * @param text 编码前的文本
     * @return
     */
    public static String encodeURIComponent(String text) {
        return encodeURIComponent(text, DEFAULT_ENCODING);
    }

    /**
     * uri编码
     *
     * @param text     编码前的文本
     * @param encoding 编码格式
     * @return
     */
    public static String encodeURIComponent(String text, String encoding) {
        try {
            return URLEncoder.encode(text, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }

    /**
     * uri解码，默认采用UTF-8解码
     *
     * @param text 解码前的文本
     * @return
     */
    public static String decodeURIComponent(String text) {
        return decodeURIComponent(text, DEFAULT_ENCODING);
    }

    /**
     * uri解码
     *
     * @param text     解码前的文本
     * @param encoding 编码格式
     * @return
     */
    public static String decodeURIComponent(String text, String encoding) {
        try {
            return URLDecoder.decode(text, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }

    /**
     * 测试main函数
     *
     * @param args
     */
    public static void main(String[] args) {
        String fileName = "data/gallery/marketing_risk.json";
        //JSONObject json = fileToJson(fileName);
        //System.out.println(json);
        //JSONObject res = UnirestUtils.post("http://10.160.92.69:8080/marketing/risk", json.toJSONString(), headerJson);
    }
}