package cn.com.jcgroup.planb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目相关菜单权限校验
 *
 * @author LiuYong on 17/5/30 下午6:50.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProjectAuth {

    /**
     * 菜单编号
     */
    String menu() default "";

}
