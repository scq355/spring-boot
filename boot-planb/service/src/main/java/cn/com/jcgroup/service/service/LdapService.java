package cn.com.jcgroup.service.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * Ldap service
 *
 * @author LiuYong
 */
@Service
public class LdapService {

    private static final Logger LOG = LoggerFactory.getLogger(LdapService.class);

    @Value("${ldap.domain}")
    private final String ldapDomain = null;
    @Value("${ldap.url}")
    private final String ldapUrl = null;
    @Value("${ldap.validate.off}")
    private boolean ldapValidateOff;

    public boolean ldap(String mail, String pwd) {
        if (StringUtils.isEmpty(mail) || StringUtils.isEmpty(pwd)) {
            LOG.error("用户名或密码不能为空");
            return false;
        }
        if(ldapValidateOff){
            return true;
        }
        Hashtable<String, String> hashEnv = new Hashtable<>();
        mail = mail + "@" + ldapDomain;
        hashEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        hashEnv.put(Context.SECURITY_PRINCIPAL, mail);
        hashEnv.put(Context.SECURITY_CREDENTIALS, pwd);
        hashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        hashEnv.put(Context.PROVIDER_URL, ldapUrl);
        hashEnv.put(Context.SECURITY_PROTOCOL, "ssl");
        try {
            LdapContext ctx = new InitialLdapContext(hashEnv, null);
            ctx.close();
            return true;
        } catch (NamingException e) {
            LOG.error("登录失败", e);
        }
        return false;
    }
}
