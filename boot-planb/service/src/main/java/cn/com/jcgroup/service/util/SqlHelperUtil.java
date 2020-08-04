package cn.com.jcgroup.service.util;

import cn.com.jcgroup.service.domain.PbPrivateFund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * sql拼接工具类
 *
 * @author LiuYong on 17/6/20 下午3:34.
 */
public class SqlHelperUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SqlHelperUtil.class);

    /**
     * sql拼接工具类，跳过ID字段，跳过空字断，跳过值为－1的整形数据
     *
     * @param removeParam     不需要跟新的参数
     * @param targetClass     目标类
     * @param paramName       参数别名
     * @param filterParamName 过滤参数名称
     * @author LiuYong
     */
    public static String convertToSqlWithSpel(Class<?> targetClass, String paramName, String[] filterParamName, String[] removeParam) {
        if (targetClass == null) {
            return null;
        }
        Field[] fields = targetClass.getDeclaredFields();
        int length = fields.length;
        String targetName = targetClass.getSimpleName();
        String sql = "update " + targetName + "  pb  set ";
        for (int i = 0; i < length; i++) {
            try {
                Field field = fields[i];
                field.setAccessible(true);
                String fieldName = field.getName();
                //去除字断id
                if (fieldName.equalsIgnoreCase("id")) {
                    continue;
                }
                //去除不需要更新的元素
                if(skipColumn(removeParam,fieldName)){
                    continue;
                }
                //去除where字断
                if(skipColumn(filterParamName,fieldName)){
                    continue;
                }
                sql += "pb." + fieldName + "=:#{#" + paramName + "." + fieldName + "}";
                if (i != length - 1) {
                    sql += ",";
                }
            } catch (Exception e) {
                LOG.error("[sql工具类]反射取值失败", e);
            }
        }
        int dotIndex = sql.lastIndexOf(",");
        if (dotIndex != -1) {
            //去除最后一个逗号
            sql = sql.substring(0, dotIndex);
        }
        //拼接where语句
        if(filterParamName != null && filterParamName.length>0){
            sql += " where 1=1 ";
            for(String key:filterParamName){
                sql += "  and pb." + key + "=:#{#" + paramName + "." + key + "}";
            }
        }
        return sql;
    }


    public static boolean skipColumn(String[] filterParamName,String fieldName){
        boolean flag = false;
        if(filterParamName != null && filterParamName.length>0){
            for (String str:filterParamName) {
                if (fieldName.equalsIgnoreCase(str)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * sql拼接工具类，跳过ID字段，跳过空字断，跳过值为－1的整形数据
     *
     * @param target          更新对象值
     * @param targetClass     目标类
     * @param filterParamName 过滤参数名称
     * @author LiuYong
     */
    public static String convertToSqlWithPlaceHolder(Object target, Class<?> targetClass, String[] filterParamName) {
        if (target == null) {
            return null;
        }
        if (target.getClass().isAssignableFrom(targetClass)) {
            Field[] fields = target.getClass().getDeclaredFields();
            int length = fields.length;
            String targetName = targetClass.getSimpleName();
            String sql = "update " + targetName + "  pb  set ";
            for (int i = 0; i < length; i++) {
                try {
                    Field field = fields[i];
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    //去除字断id
                    if (fieldName.equalsIgnoreCase("id")) {
                        continue;
                    }
                    //去除为空的元素
                    Object value = field.get(target);
                    if (value == null) {
                        continue;
                    }
                    if (value instanceof Long) {
                        //去除－1
                        if ((Long) value == -1) {
                            continue;
                        }
                    }
                    if (value instanceof Integer) {
                        //去除－1
                        if ((Integer) value == -1) {
                            continue;
                        }
                    }
                    sql += "pb." + fieldName + "=:" + fieldName;
                    if (i != length - 1) {
                        sql += ",";
                    }
                } catch (Exception e) {
                    LOG.error("[sql工具类]反射取值失败", e);
                }
            }
            int dotIndex = sql.lastIndexOf(",");
            if (dotIndex != -1) {
                //去除最后一个逗号
                sql = sql.substring(0, dotIndex);
            }
            //拼接where语句
            int filterLength = filterParamName.length;
            if (length > 0) {
                sql += " where ";
                for (int j = 0; j < filterLength; j++) {
                    sql += "pb." + filterParamName[j] + "=:" + filterParamName[j];
                    if (j != filterLength - 1) {
                        sql += " and ";
                    }
                }
            }
            return sql;
        } else {
            LOG.error("[sql工具类]源对象类型与所传类型不匹配");
            return null;
        }
    }

    public static void main(String[] args) {
        //应付台账
//        String[] removeAccountPayfor = new String[] {"subProCode"};
//        String[] where = new String[]{ "id" };
//        String sql = convertToSqlWithSpel(PbBillPayFor.class,"pbBillPayFor", where, removeAccountPayfor);
//        System.out.printf(sql);
        //私募基金
        String[] removePrivateFund = new String[] {"id", "createTime", "currentTotalMoney"};
        String[] where = new String[] { "fundCode" };
        String sql = convertToSqlWithSpel(PbPrivateFund.class, "pbPrivateFund", where, removePrivateFund);
        System.out.printf(sql);
    }
}
