package cn.com.jcgroup.service.enums;

/**
 * 资金流类别
 * @author LiuYong on 17/6/9 上午11:07.
 */
public enum CashFlowTypeEnum {
    
    CASH_IN("1","资金流入"),
    CASH_OUT("2","资金流出"),
    COST_SHEET("3","预算"),
    CASH_OVER("4","现金余额");
    
    private String type;
    private String info;

    CashFlowTypeEnum(String type,String info){
        this.type = type;
        this.info = info;
    }

    public static CashFlowTypeEnum convertToEnum(String type) {
        for (CashFlowTypeEnum cashFlowTypeEnum : values()) {
            if (cashFlowTypeEnum.getType().equalsIgnoreCase(type)) {
                return cashFlowTypeEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
