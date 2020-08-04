package cn.com.jcgroup.service.enums;

/**
 * 资产组成
 *
 * @author LiuYong on 17/6/10 下午4:39.
 */
public enum AssetCompositionEnum {

    EQUITY_ASSET("1", "股权资产"),
    LAND_ASSET("2", "土地资产"),
    CONSTRUCTION_ASSET("3", "在建工程"),
    FIXED_ASSET("4", "固定资产"),
    FINANCE_ASSET("5", "金融资产"),
    OTHER_ASSET("999", "其它");

    private String type;
    private String info;

    AssetCompositionEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public static AssetCompositionEnum convertToEnum(String type) {
        for (AssetCompositionEnum assetCompositionEnum : values()) {
            if (assetCompositionEnum.getType().equalsIgnoreCase(type)) {
                return assetCompositionEnum;
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
