package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import cn.com.jcgroup.service.service.UploadImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author LiuYong on 17/6/15 下午7:37.
 */
@RestController
public class UploadImageController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UploadImageController.class);
    
    @Autowired
    private UploadImageService uploadImageService;
    
    @RequestMapping(path = "/upload")
    public ApiResult uploadBanner(@RequestParam("file") MultipartFile file){
        String url = "";
        if (!file.isEmpty()) {
            try{
                String path = "/project/{year}-{mon}-{day}/{random}{.suffix}";
                url = uploadImageService.uploadImage(path,file.getBytes());
            }catch (Exception e){
                LOG.error("[UploadImageController]上传图片失败",e);
            }
        }
        return new ApiResult(url);
    }
}
