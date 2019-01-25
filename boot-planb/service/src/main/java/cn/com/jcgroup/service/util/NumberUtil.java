package cn.com.jcgroup.service.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 数值转换
 * @author LiuYong on 17/6/9 下午1:52.
 */
public class NumberUtil {

    //亿单位 以分为单位
    public static final long UNIT_HUNDRED_MILLION = 100000000L*100;
    //万单位 以分为单位
    private static final long UNIT_TEN_THOUSAND = 10000 * 100;
    //元单位 以分为单位
    private static final long UNIT_MONEY = 100;
    
    /**
     * 转换为亿为单位
     * @author LiuYong  
     */
    public static String unitHundredMillion(Long money){
        if(money == null){
            return "0";
        }
        double temp = MathUtil.div(money,UNIT_HUNDRED_MILLION);
        return MathUtil.format(temp);
    }

    /**
     * 转换为万为单位
     * @author LiuYong
     */
    public static String unitTenThousand(Long money){
        if(money == null){
            return "0";
        }
        double temp = MathUtil.div(money,UNIT_TEN_THOUSAND);
        return MathUtil.format(temp);
    }

    /**
     * 转换为元为单位
     * @author LiuYong
     */
    public static String unitMoney(Long money){
        if(money == null){
            return "0";
        }
        double temp = MathUtil.div(money,UNIT_MONEY);
        return MathUtil.format(temp);
    }
    
    /**
     * 将金额单位为元的字符串转换为分为单位的金额
     * @author LiuYong  
     */
    public static long convertToPoint(String money){
        if(StringUtils.isBlank(money)){
            return 0L;
        }
        try{
            return Long.valueOf(money) * 100;
        }catch (Exception e){
            return 0L;
        }
    }
    /**
     * 将金额单位为unit元的字符串转换为分为单位的金额
     * @author LiuYong
     */
    public static long convertToPointWithUnit(String money,long unit){
        if(StringUtils.isBlank(money)){
            return 0L;
        }
        try{
            return (long)MathUtil.mul(Double.valueOf(money),unit*100);
        }catch (Exception e){
            return 0L;
        }
    }
    
}
