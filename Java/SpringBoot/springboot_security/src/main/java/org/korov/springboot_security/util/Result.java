package org.korov.springboot_security.util;

import lombok.Data;
import lombok.experimental.Accessors;
import org.korov.springboot_security.emun.ResultCode;

@Data
@Accessors(chain = true)
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 携带数据
     */
    private T data;

    /**
     * 时间戳
     */
    private long timeStamp = System.currentTimeMillis();

    /**
     * 权鉴
     */
    private String tokenKey;

    private String token;

    /**
     * 成 功 操 作
     */
    public static <T> Result<T> success() {
        return success("");
    }

    /**
     * 成 功 操 作 , 携 带 数 据
     */
    public static <T> Result<T> success(T data) {
        return success(ResultCode.SUCCESS.message(), data);
    }

    /**
     * 成 功 操 作, 携 带 消 息
     */
    public static <T> Result<T> success(String message) {
        return success(message, null);
    }

    /**
     * 成 功 操 作, 携 带 消 息 和 携 带 数 据
     */
    public static <T> Result<T> success(String message, T data) {
        return success(ResultCode.SUCCESS.code(), message, data);
    }

    /**
     * 成 功 操 作, 携 带 自 定 义 状 态 码 和 消 息
     */
    public static <T> Result<T> success(ResultCode resultCode) {
        return success(resultCode.code(), resultCode.message());
    }

    /**
     * 成 功 操 作, 携 带 自 定 义 状 态 码 和 消 息
     */
    public static <T> Result<T> success(ResultCode resultCode, String tokenKey, String token) {
        Result<T> result = success(resultCode.code(), resultCode.message());
        result.setTokenKey(tokenKey);
        result.setToken(token);
        return result;
    }

    public static <T> Result<T> success(int code, String message) {
        return success(code, message, null);
    }

    /**
     * 成 功 操 作, 携 带 自 定义 状 态 码, 消 息 和 数 据
     */
    public static <T> Result<T> success(int code, String message, T data) {
        return result(code, message, data);
    }

    /**
     * 失 败 操 作, 默 认 数 据
     */
    public static <T> Result<T> failure() {
        return failure(ResultCode.FAILURE.message());
    }

    /**
     * 失 败 操 作, 携 带 自 定 义 消 息
     */
    public static <T> Result<T> failure(String message) {
        return failure(message, null);
    }

    /**
     * 失 败 操 作 , 携 带 自 定 义 消 息 , 状 态 码
     */
    public static <T> Result<T> failure(ResultCode resultCode) {
        return failure(resultCode.code(), resultCode.message());
    }

    /**
     * 失 败 操 作, 携 带 自 定 义 消 息 和 数 据
     */
    public static <T> Result<T> failure(String message, T data) {
        return failure(ResultCode.FAILURE.code(), message, data);
    }

    /**
     * 失 败 操 作, 携 带 自 定 义 状 态 码 和 自 定 义 消 息
     */
    public static <T> Result<T> failure(int code, String message) {
        return failure(code, message, null);
    }

    /**
     * 失 败 操 作, 携 带 自 定 义 状 态 码 , 消 息 和 数 据
     */
    public static <T> Result<T> failure(int code, String message, T data) {
        return result(code, message, data);
    }

    /**
     * Boolean 返 回 操 作, 携 带 默 认 返 回 值
     */
    public static <T> Result<T> auto(boolean b) {
        return auto(b, ResultCode.SUCCESS.message(), ResultCode.FAILURE.message());
    }

    /**
     * Boolean 返 回 操 作, 携 带 自 定 义 消 息
     */
    public static <T> Result<T> auto(boolean b, String success, String failure) {
        if (b) {
            return success(success);
        } else {
            return failure(failure);
        }
    }

    /**
     * Result 构 建 方 法
     */
    public static <T> Result<T> result(int code, String message, T data) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(message);
        result.setTimeStamp(System.currentTimeMillis());
        result.setData(data);
        return result;
    }

}
