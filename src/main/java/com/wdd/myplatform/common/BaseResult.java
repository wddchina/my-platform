package com.wdd.myplatform.common;

import com.wdd.myplatform.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wdd
 */
@Data
public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    private Integer code = ResultEnum.ERROR.getCode();

    /**
     * 错误信息
     */
    private String msg = null;

    /**
     * 返回结果实体
     */
    private T data = null;

    private static final String SUCCESS_MSG = "success";

    private static final String ERROR_MSG = "error";

    public BaseResult() {
    }

    private BaseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResult<T> error() {
        return new BaseResult<>(ResultEnum.ERROR.getCode(), ERROR_MSG, null);
    }

    public static <T> BaseResult<T> error(String msg) {
        return new BaseResult<>(ResultEnum.ERROR.getCode(), msg, null);
    }

    public static <T> BaseResult<T> error(ResultEnum resultEnum) {
        return new BaseResult<>(resultEnum.getCode(), resultEnum.getDesc(), null);
    }

    public static <T> BaseResult<T> error(Integer code) {
        return new BaseResult<>(code, ERROR_MSG, null);
    }

    public static <T> BaseResult<T> error(int code, String msg) {
        return new BaseResult<>(code, msg, null);
    }
    public static <T> BaseResult<T> error(int code, String msg,T data) {
        return new BaseResult<>(code, msg, data);
    }
    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<>(ResultEnum.SUCCESS.getCode(), SUCCESS_MSG, data);
    }

    public static <T> BaseResult<T> success(int code, String msg) {
        return new BaseResult<>(code, msg, null);
    }
}
