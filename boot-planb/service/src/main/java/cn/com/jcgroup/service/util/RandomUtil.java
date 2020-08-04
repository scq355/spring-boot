package cn.com.jcgroup.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 随机数值工具类
 *
 * @author LiuYong on 17/6/30 上午10:12.
 */
public class RandomUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RandomUtil.class);

    public static String generateRandomCode(int length, boolean ignoreCase) {
        if (length < 1) {
            LOG.error("length can not be negative number,length={}", length);
            return "";
        }
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数  
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字  
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                if (ignoreCase) {
                    temp = 65;
                }
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


    /**
     * 生成随机机构编号
     * 
     * @author LiuYong  
     */
    public static String generateAgencyCode(){
        return generateRandomCode(7,true);
    }
    
    public static void main(String[] args){
        Map<String,Integer> map = new HashMap<>();
        for(int i=0;i<100000;i++){
            map.put(generateRandomCode(7,true),i);
        }
        System.out.println(map.size());
    }
}
