package cn.com.jcgroup.service.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司枚举
 *
 * @author LiuYong on 17/5/30 下午9:22.
 */
public enum CompanyEnum {

    ALL("0", "全部"),
    FIRST("1", "一局"),
    SECOND("2", "二局"),
    THIRD("3", "三局"),
    FOURTH("4", "四局"),
    FIFTH("5", "五局"),
    SIX("6", "太悦"),
    SEVEN("7", "之星"),
    EIGHT("8", "综合");


    private String code;
    private String name;

    CompanyEnum(String code, String name) {
        this.code = code;
        this.name = name;

    }

    public static CompanyEnum convertToEnum(String code) {
        for (CompanyEnum companyEnum : values()) {
            if (companyEnum.getCode().equalsIgnoreCase(code)) {
                return companyEnum;
            }
        }
        return null;
    }
    
    public static List<String> covertToList(String code){
        CompanyEnum companyEnum = convertToEnum(code);
        if(companyEnum == null){
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        if(companyEnum == CompanyEnum.ALL){
            for (CompanyEnum temp : values()) {
                if (temp == CompanyEnum.ALL) {
                    continue;
                }
                list.add(temp.getCode());
            }
        }else{
            list.add(companyEnum.getCode());
        }
        return list;
    }
    
    /**
     * 返回索引
     * @author LiuYong  
     */
    public static int index(CompanyEnum companyEnum){
        int index = -1;
        if (companyEnum == CompanyEnum.FIRST) {
            index = 0;
        } else if (companyEnum == CompanyEnum.SECOND) {
            index = 1;
        } else if (companyEnum == CompanyEnum.THIRD) {
            index = 2;
        } else if (companyEnum == CompanyEnum.FOURTH) {
            index = 3;
        } else if (companyEnum == CompanyEnum.FIFTH) {
            index = 4;
        } else if(companyEnum == CompanyEnum.SIX){
            index = 5;
        } else if(companyEnum == CompanyEnum.SEVEN){
            index = 6;
        } else if(companyEnum == CompanyEnum.EIGHT){
            index = 7;
        }
        return index;
    }
    
    public static List<String> getCompanyName(String code){
        CompanyEnum companyEnum = convertToEnum(code);
        if(companyEnum == null){
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        if(companyEnum == CompanyEnum.ALL){
            for (CompanyEnum temp : values()) {
                if (temp == CompanyEnum.ALL) {
                    continue;
                }
                list.add(temp.getName());
            }
        }else{
            list.add(companyEnum.getName());
        }
        return list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
