package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import cn.com.jcgroup.admin.dto.LoginDto;
import cn.com.jcgroup.service.enums.LoginTypeEnum;
import cn.com.jcgroup.service.service.LdapService;
import cn.com.jcgroup.service.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录登出
 *
 * @author LiuYong on 17/5/25 下午7:31.
 */
@RestController
@Validated
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private LdapService ldapService;

    @RequestMapping("/login")
    public ApiResult login(@RequestBody LoginDto loginDto, HttpSession session) {
        //校验用户名是否可以登录planb系统
        String username = loginDto.getEmail();
        JSONObject user = userService.queryUserInfo(username);
        if (user == null) {
            LOG.info("用户名不存在，username={}", username);
            return new ApiResult(ResCodeEnum.LOGIN_FAIL, null);
        }
        String loginType = user.getString("loginType");
        //校验用户帐号是否允许登录后台
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.convertToEnum(loginType);
        if(loginTypeEnum == null){
            LOG.error("[用户登录]用户帐号类别异常，username={},type={}", username,loginType);
            return new ApiResult(ResCodeEnum.LOGIN_FAIL, "用户帐号异常");
        }
        if(loginTypeEnum == LoginTypeEnum.FORBID){
            LOG.error("[用户登录]用户帐号被冻结，username={}", username);
            return new ApiResult(ResCodeEnum.LOGIN_FAIL, "用户帐号已冻结");
        }
        if(loginTypeEnum == LoginTypeEnum.BACKEND || loginTypeEnum == LoginTypeEnum.BOTH){
            //校验用户名和密码是否正确,调用oa接口进行校验
            boolean result = ldapService.ldap(username, loginDto.getPwd());
            if (result) {
                Map<String, Object> map = new HashMap<String,Object>();
                map.put("name", user.getString("realName"));
                map.put("role", new String[]{"admin"});
                map.put("token", "admin");
                map.put("introduction", "我是超级管理员");
                map.put("avatar", "https://wdl.wallstreetcn.com/48a3e1e0-ea2c-4a4e-9928-247645e3428b");
                map.put("uid", "001");
                //注册session
                session.setAttribute(AdminIdentifier.SESSION_USER, user);
                return new ApiResult(map);
            }
            return new ApiResult(ResCodeEnum.LOGIN_FAIL, null);
        }else{
            LOG.info("[用户登录]用户类别不允许登录后台系统,username={}",username);
            return new ApiResult(ResCodeEnum.LOGIN_FAIL, "用户不存在");
        }
    }

    @RequestMapping("/logout")
    public ApiResult logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute(AdminIdentifier.SESSION_USER);
        }
        return new ApiResult(null);
    }
    
    /**
     * 此接口仅用于前端框架连调
     * 
     * @author LiuYong  
     */
    @RequestMapping("/user/info")
    public ApiResult userInfo(@RequestBody Map<String,Object> paramMap) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("name", "超级管理员");
        map.put("role", new String[]{"admin"});
        map.put("token", "admin");
        map.put("introduction", "我是超级管理员");
        map.put("avatar", "https://wdl.wallstreetcn.com/48a3e1e0-ea2c-4a4e-9928-247645e3428b");
        map.put("uid", "001");
        return new ApiResult(map);
    }
}