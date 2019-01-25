package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.upyun.FormUploader;
import cn.com.jcgroup.service.upyun.Params;
import cn.com.jcgroup.service.upyun.Result;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传图片
 * @author LiuYong on 17/6/15 下午8:03.
 */
@Service
public class UploadImageService {
    
    private static final Logger LOG = LoggerFactory.getLogger(UploadImageService.class);

    private static final String BUCKET_NAME = "planb";
    private static final String OPERATOR_NAME = "planbweb";
    private static final String OPERATOR_PWD = "planbweb";
    private static final String APIDOMAIN = "http://v0.api.upyun.com";
    
    /**
     * 图片上传
     * @author LiuYong  
     */
    public String uploadImage(String path,byte[] bytes){
        String url = "";
        if(path != null && bytes.length > 0){
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put(Params.SAVE_KEY, path);
            FormUploader uploader = new FormUploader(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD, APIDOMAIN);
            try{
                Result result = uploader.upload(paramsMap, bytes);
                if (result.isSucceed()) {
                    JSONObject msgJson = JSONObject.parseObject(result.getMsg());
                    LOG.debug("msgJson={}",msgJson.toJSONString());
                    url = msgJson.getString("url");
                }
            }catch (Exception e){
                LOG.error("上传图片异常",e);
            }
        }
        return url;
    } 
    
}
