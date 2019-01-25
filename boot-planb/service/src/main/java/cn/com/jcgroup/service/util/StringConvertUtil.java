package cn.com.jcgroup.service.util;

/**
 * 字符转换工具类
 * @author LiuYong on 17/6/19 上午9:30.
 */
public class StringConvertUtil {
    
    /**
     * 转换为string
     * @author LiuYong  
     */
    public static String convertToString(Object o){
        if(o == null){
            return null;
        }else{
            return String.valueOf(o);
        }   
    }
}
