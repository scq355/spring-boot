package cn.com.jcgroup.service.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * post请求发送
 *
 * @author LiuYong on 17/6/1 下午3:35.
 */
public class HttpClientUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);


    /**
     * post请求传输json参数
     *
     * @param url       url地址
     * @param jsonParam 参数
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = buidConfig();
        httpPost.setConfig(requestConfig);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(),
                        "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            //请求发送成功，并得到响应
            int code = result.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                String str;
                try {
                    //读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    //把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    LOG.error("post请求结果转换异常:" + url, e);
                }
            } else {
                LOG.error("post请求提交失败:url={},code={}", url, code);
            }
        } catch (IOException e) {
            LOG.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }


    /**
     * 发送get请求
     *
     * @param url 路径
     */
    public static JSONObject httpGet(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = buidConfig();
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            //请求发送成功，并得到响应
            if (code == HttpStatus.SC_OK) {
                //读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                //把json字符串转换成json对
                jsonResult = JSONObject.parseObject(strResult);
            } else {
                LOG.error("get请求提交失败:url={},code={}", url, code);
            }
        } catch (IOException e) {
            LOG.error("get请求提交失败:" + url, e);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }

    private static RequestConfig buidConfig() {
        return RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
    }
}
