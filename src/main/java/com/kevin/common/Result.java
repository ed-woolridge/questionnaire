package com.kevin.common;

import lombok.Getter;

/**
 * 统一返回结果类
 *
 * @author woolridge
 * @date 2025/6/25
 */
@Getter
public class Result {

    public final static String SUCCESS_CODE = "200";
    public final static String SYS_ERROR_CODE = "406";
    public final static String UNAUTHORIZED_CODE = "401";

    private final String code;
    private final String message;
    private final Object data;
    private final long timestamp;

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static Result success(Object data) {
        return new Result(SUCCESS_CODE, "success", data);
    }

    public static Result success() {
        return new Result(SUCCESS_CODE, "success", null);
    }

    public static Result unauthorized(Object data) {
        return new Result(UNAUTHORIZED_CODE, "unauthorized", data);
    }

    public static Result error() {
        return new Result(SYS_ERROR_CODE, "error", null);
    }

    public static Result error(String message) {
        return new Result(SYS_ERROR_CODE, message, null);
    }

    public static Result error(String code, String message) {
        return new Result(code, message, null);
    }

}
