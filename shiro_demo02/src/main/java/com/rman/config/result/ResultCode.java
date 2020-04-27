package com.rman.config.result;

/**
 * @author chenziming
 * @date 2019/11/2 下午2:00
 */
public enum ResultCode {
    //成功
    SUCCESS(200),
    //请求成功且服务器已创建了新的资源。
    BUILT(201),
    //服务器已接受了请求，但尚未对其进行处理。
    ACCEPT(202),
    //失败
    FAIL(400),
    //未认证（签名错误）
    UNAUTHORIZED(401),
    //接口不存在
    NOT_FOUND(404),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    public int core;
    ResultCode(int core){
        this.core=core;
    }
}
