package cn.com.jcgroup.planb.rest;

import cn.com.jcgroup.planb.common.ApiResult;
import cn.com.jcgroup.planb.constant.PlanbIdentifier;
import cn.com.jcgroup.planb.constant.ResCodeEnum;
import cn.com.jcgroup.planb.dto.LoginDto;
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
        if(loginTypeEnum == LoginTypeEnum.WEB || loginTypeEnum == LoginTypeEnum.BOTH){
            //校验用户名和密码是否正确,调用oa接口进行校验
            boolean result = ldapService.ldap(username, loginDto.getPwd());
            if (result) {
                Map<String, String> map = new HashMap<>();
                map.put("name", user.getString("realName"));
                //注册session
                session.setAttribute(PlanbIdentifier.SESSION_USER, user);
                return new ApiResult(map);
            }
            return new ApiResult(ResCodeEnum.LOGIN_FAIL, null);
        }else{
            LOG.info("[用户登录]用户类别不允许登录前台系统,username={}",username);
            return new ApiResult(ResCodeEnum.LOGIN_FAIL, "用户不存在");
        }
        
    }

    @RequestMapping("/logout")
    public ApiResult logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute(PlanbIdentifier.SESSION_USER);
        }
        return new ApiResult(null);
    }


}
