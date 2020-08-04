package cn.com.jcgroup.planb.constant;

/**
 * 响应编码
 * @author LiuYong on 17/5/19 下午2:19.
 */
public enum ResCodeEnum {

    //服务器响应码
    SERVER_ERROR(100,"服务器错误"),
    SERVER_SUCCESS(200,"响应成功"),
    SERVER_NOT_FOUND(404,"页面不存在"),

    //登录响应码x
    NOT_ENOUGH_AUTHORITY(300,"权限不足，无法查看"),
    SESSION_EXPIRED(301,"用户未登录或登录已失效"),

    //参数响应码
    INVALID_PARAM(500,"参数非法"),
    LOGIN_FAIL(505,"用户名或密码错误");



    private int code;

    private String msg;

    ResCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
