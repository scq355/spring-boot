package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.domain.PbUser;
import cn.com.jcgroup.service.repositories.PbUserRepository;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关service
 *
 * @author LiuYong on 17/5/30 下午4:47.
 */
@Service
public class UserService {

    @Autowired
    private PbUserRepository pbUserRepository;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @author LiuYong
     */
    public JSONObject queryUserInfo(String username) {
        JSONObject jsonObject = null;
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        PbUser pbUser = pbUserRepository.findUserInfoByUserName(username);
        if (pbUser != null) {
            jsonObject = new JSONObject();
            jsonObject.put("userName", pbUser.getUserName());
            jsonObject.put("company", pbUser.getCompany());
            jsonObject.put("loginType", pbUser.getLoginType());
            jsonObject.put("realName", pbUser.getRealName());
        }
        return jsonObject;
    }
}
