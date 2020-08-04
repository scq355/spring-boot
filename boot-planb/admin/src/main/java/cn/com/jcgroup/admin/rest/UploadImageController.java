package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.service.service.UploadImageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author LiuYong on 17/6/15 下午7:37.
 */
@RestController
public class UploadImageController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadImageController.class);

    @Autowired
    private UploadImageService uploadImageService;
    
    @RequestMapping(path = "/uploadBanner")
    public ApiResult uploadBanner(@RequestBody  MultipartFile file){
        String url = "";
        if (!file.isEmpty()) {
            try{
                url = uploadImageService.uploadImage(AdminIdentifier.Image.PROJECT_BANNER_PATH,file.getBytes());
                if(StringUtils.isNotBlank(url)){
                    //TODO 存储路径
                }else{
                    return new ApiResult(null,"上传失败");
                }
            }catch (Exception e){
                LOG.error("[UploadImageController]上传图片失败",e);
            }
        }
        return new ApiResult(url);
    }
}
