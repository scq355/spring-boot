package cn.com.jcgroup.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Description: 激励情况
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 下午5:07
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EncourageDto {

    private int id;
    private boolean money_flag;
    private String pre_date;
    private String pre_percent;
    private String real_money;
    private String real_percent;
    private boolean real_status;
    private String stage;
    private String pro_code;
    private String level;
    private String encourage_money;

    private int page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMoney_flag() {
        return money_flag;
    }

    public void setMoney_flag(boolean money_flag) {
        this.money_flag = money_flag;
    }

    public String getPre_date() {
        return pre_date;
    }

    public void setPre_date(String pre_date) {
        this.pre_date = pre_date;
    }

    public String getPre_percent() {
        return pre_percent;
    }

    public void setPre_percent(String pre_percent) {
        this.pre_percent = pre_percent;
    }

    public String getReal_money() {
        return real_money;
    }

    public void setReal_money(String real_money) {
        this.real_money = real_money;
    }

    public String getReal_percent() {
        return real_percent;
    }

    public void setReal_percent(String real_percent) {
        this.real_percent = real_percent;
    }

    public boolean isReal_status() {
        return real_status;
    }

    public void setReal_status(boolean real_status) {
        this.real_status = real_status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEncourage_money() {
        return encourage_money;
    }

    public void setEncourage_money(String encourage_money) {
        this.encourage_money = encourage_money;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
