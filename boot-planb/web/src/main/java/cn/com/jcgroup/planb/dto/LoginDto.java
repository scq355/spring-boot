package cn.com.jcgroup.planb.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author LiuYong on 17/6/16 下午1:58.
 */
public class LoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String pwd;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
