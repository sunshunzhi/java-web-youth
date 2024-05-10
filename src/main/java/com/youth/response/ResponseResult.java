package com.youth.response;

import com.youth.common.IErrorCode;
import com.youth.enums.ResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 通用返回结果封装类
 */
@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    @Schema(description = "状态码")
    private long code;
    /**
     * 提示信息
     */
    @Schema(description = "提示信息")
    private String message;
    /**
     * 数据封装
     */
    private T data;

    public ResponseResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<T>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> ResponseResult<T> failed(IErrorCode errorCode) {
        return new ResponseResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> ResponseResult<T> failed(IErrorCode errorCode, String message) {
        return new ResponseResult<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ResponseResult<T> failed(String message) {
        return new ResponseResult<T>(ResultEnum.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResponseResult<T> failed() {
        return failed(ResultEnum.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResponseResult<T> validateFailed() {
        return failed(ResultEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> ResponseResult<T> validateFailed(String message) {
        return new ResponseResult<T>(ResultEnum.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResponseResult<T> unauthorized(T data) {
        return new ResponseResult<T>(ResultEnum.UNAUTHORIZED.getCode(), ResultEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResponseResult<T> forbidden(T data) {
        return new ResponseResult<T>(ResultEnum.FORBIDDEN.getCode(), ResultEnum.FORBIDDEN.getMessage(), data);
    }
}
