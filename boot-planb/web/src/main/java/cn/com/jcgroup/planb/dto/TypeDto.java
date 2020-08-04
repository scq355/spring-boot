package cn.com.jcgroup.planb.dto;

import static cn.com.jcgroup.planb.constant.PlanbIdentifier.Regex.NON_NEGATIVE_NUMBER;

import javax.validation.constraints.Pattern;

/**
 * 类别
 * @author LiuYong on 17/6/11 下午4:47.
 */
public class TypeDto {
    
    @Pattern(regexp = NON_NEGATIVE_NUMBER)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
