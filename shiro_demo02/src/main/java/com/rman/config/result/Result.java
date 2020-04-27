package com.rman.config.result;

/**
 * @author chenziming
 * @date 2019/11/2 下午2:02
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.core;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
