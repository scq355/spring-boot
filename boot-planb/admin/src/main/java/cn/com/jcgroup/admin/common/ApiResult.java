package cn.com.jcgroup.admin.common;

import cn.com.jcgroup.admin.constant.ResCodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 接口返回对象
 *
 * @author LiuYong
 */
public class ApiResult implements Serializable {

    private int code;

    private String msg;

    private Object data;

    public ApiResult() {
    }

    /**
     * response error
     *
     * @author LiuYong
     */
    public ApiResult(ResCodeEnum errorCode, String errorMsg) {
        if (errorCode == null) {
            errorCode = ResCodeEnum.SERVER_ERROR;
        }
        this.code = errorCode.getCode();
        if (!StringUtils.isEmpty(errorMsg)) {
            this.msg = errorMsg;
        } else {
            this.msg = errorCode.getMsg();
        }
    }

    /**
     * response correct result
     *
     * @author LiuYong
     */
    public ApiResult(Object data) {
        this.code = ResCodeEnum.SERVER_SUCCESS.getCode();
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
