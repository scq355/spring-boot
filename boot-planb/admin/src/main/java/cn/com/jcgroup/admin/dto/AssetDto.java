package cn.com.jcgroup.admin.dto;

/**
 * Description:资产形成
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 上午11:06
 */
public class AssetDto {
    private String asset_type;
    private String asset_value;
    private String date;
    private String pro_code;

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getAsset_value() {
        return asset_value;
    }

    public void setAsset_value(String asset_value) {
        this.asset_value = asset_value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }
}
