package cn.com.jcgroup.service.enums;

/**
 * 出差接待
 *
 * @author LiuYong on 17/6/16 上午12:35.
 */
public enum TravelReceptionEnum {

    TRAVEL("1", "出差"),
    RECEPTION("2", "接待");

    private String type;
    private String info;

    TravelReceptionEnum(String type, String info) {
        this.info = info;
        this.type = type;
    }
}
