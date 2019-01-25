package cn.com.jcgroup.service.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * @author LiuYong on 17/6/7 上午10:31.
 */
public class DateUtil {
    
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public static String formatDate(String format,Date date){
        String result = "";
        try{
            if(date == null){
                return result;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(date);
        }catch (Exception e){
           LOG.error("[日期工具类]日期格式化异常,format={}",format);
        }
        return result;
    }
    
    public static Date covertToDate(String format, String dateStr) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateStr);
    }

    /**
     * 根据月份获取第一天,从1开始
     * 
     * @author LiuYong  
     */
    public static Date getFirstDay(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    
    /**
     * 构建一个返回当年12月份，格式为  format 的列表
     * @author LiuYong  
     */
    public static List<Object> buildCurrentYearMonth(String format){
        List<Object> list = new ArrayList<>(12);
        for(int i=1;i<=12;i++){
            list.add(formatDate(format,getFirstDay(i)));
        }
        return list;
    } 
    /**
     * 获取月份
     * @author LiuYong  
     */
    public static int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }
    
    
    public static Date parseDate(String date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date result = null;       
        try{
            result = sdf.parse(date);
        }catch (Exception e){
            LOG.error("[日期工具类]转换异常,date={},format={}",date,format);
        }      
        return result;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate){
        if(smdate == null || bdate == null){
            LOG.error("[日期工具类]前后日期不能为空");
            return 0;
        }
        long between_days;
        try{
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        }catch (Exception e){
            LOG.error("[日期工具类]日期转换异常",e);
            return 0;
        }
        return (int)between_days;
    }

    /**
     * 计算年龄
     * @author LiuYong
     */
    public static Integer getAge(Date birthDay){
        if(birthDay == null){
            return null;
        }   
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        //获取出生年
        calendar.setTime(birthDay);
        int birth = calendar.get(Calendar.YEAR);
        return year - birth + 1;
    }

    /**
     * 计算工作年限
     *
     * @author LiuYong
     */
    public static Integer getWorkYears(Date workDay) {
        if(workDay == null){
            return null;
        }
        Date current = new Date();
        long year = (current.getTime()-workDay.getTime())/ (1000*3600L*24*365);
        return (int)year;
    }
    
    /**
     * 获取最后一天
     * @author LiuYong  
     */
    public static Date getLastDay(Date time){
        if(time == null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.add(Calendar.MONTH,1);
        cal.add(Calendar.DATE,-1);
        return cal.getTime();
    }

    /**
     * 日期格式转换（eg:2017.01 ---> 日期）
     */
    public static Date parseDate(String formatDateStr) {
        String year = StringUtils.substringBefore(formatDateStr, ".");
        String month = StringUtils.substringAfter(formatDateStr,".");
        String formatDate = year + "-" + month + "-01 00:00:00";
        String format = "yyyy-MM-dd HH:mm:ss";
        Date resDate = DateUtil.parseDate(formatDate, format);
        return resDate;
    }

    /**
     * 日期格式转换（eg:2017.01.01 ---> 日期）
     */
    public static Date parseDateDay(String formatDateStr) {
        String[] time = formatDateStr.split("\\.");
        String year = time[0];
        String month = time[1];
        String day = time[2];
        String formatDay = year + "-" + month + "-" + day + " 00:00:00";
        String format = "yyyy-MM-dd HH:mm:ss";
        Date resDate = DateUtil.parseDate(formatDay, format);
        return resDate;
    }
    
    public static void main(String[] args) throws  Exception{
        System.out.println(getWorkYears(DateUtil.covertToDate("yyyy-MM-ddd","2016-09-08")));
        System.out.println(getWorkYears(DateUtil.covertToDate("yyyy-MM-ddd","2016-04-08")));
    }
}
