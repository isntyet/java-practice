package com.isntyet.java.practice.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class CommonResponse<T> {
    private Result result;
    private String message;
    private String errorCode;
    private T data;

    public static <T> CommonResponse<T> ok() {
        return ok("", null);
    }

    public static <T> CommonResponse<T> ok(String message) {
        return ok(message, null);
    }

    public static <T> CommonResponse<T> ok(T data) {
        return ok("", data);
    }

    public static <T> CommonResponse<T> ok(String message, T data) {
        return new CommonResponse<>(Result.SUCCESS, message, null, data);
    }

    public static <T> CommonResponse<T> fail(String message, String errorCode, T data) {
        return new CommonResponse<>(Result.FAIL, message, errorCode, data);
    }

    public enum Result {
        SUCCESS, FAIL
    }
}
