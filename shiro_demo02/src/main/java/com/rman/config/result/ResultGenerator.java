package com.rman.config.result;

/**
 * @author chenziming
 * @date 2019/11/2 下午2:09
 */
public class ResultGenerator {
    private final static String DEFAULT_SUCCESS_MESSAGE="SUCCESS";

    public static Result genSuccessResult(){
        return new Result().setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(String message){
        return new Result().setCode(ResultCode.SUCCESS)
                .setMessage(message);
    }

    public static Result genAcceptResult(String massage) {
        return new Result()
                .setCode(ResultCode.ACCEPT)
                .setMessage(massage);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }
    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
}
