package com.wowjoy.boot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        /**
         * 开启登录功能
         * 1./login 请求来到登录页
         * 2.重定向到/login?error表示登录失败
         * 3.默认post方式的/login代表处理登录
         * 4.一旦定制loginPage，loginPage的post请求就是登录
         */
        http.formLogin()
                .usernameParameter("user")
                .passwordParameter("pwd")
                .loginPage("/userlogin");

        /**
         * 开启自动配置注销功能
         * 1.访问/logout 表示注销用户，清空session
         * 2.注销成功会返回/login?logout 页面
         */
        http.logout().logoutSuccessUrl("/"); // 注销成功跳转到首页

        /**
         * 开启记住我，登录成功将cookie保存到浏览器，注销会删除cookie
         */
        http.rememberMe().rememberMeParameter("remember");
    }

    /**
     * 指定密码的加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        auth.inMemoryAuthentication()
                .withUser("scq").password(passwordEncoder().encode("scq355")).roles("VIP1", "VIP2")
                .and()
                .withUser("admin").password(passwordEncoder().encode("scq355")).roles("VIP3")
                .and()
                .withUser("guest").password(passwordEncoder().encode("scq355")).roles("VIP1");
    }
}
