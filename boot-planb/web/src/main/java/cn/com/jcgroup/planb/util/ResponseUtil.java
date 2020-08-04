package cn.com.jcgroup.planb.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * response application/json
 *
 * @author LiuYong on 17/5/26 下午8:16.
 */
public class ResponseUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 以JSON格式输出
     *
     * @author LiuYong
     */
    public static void responseWithJson(HttpServletResponse response, Object responseObject) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String str = JSONObject.toJSONString(responseObject, SerializerFeature.WriteMapNullValue);
            LOG.debug("[ResponseUtil json 输出] 返回消息为:{}", str);
            out.append(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
