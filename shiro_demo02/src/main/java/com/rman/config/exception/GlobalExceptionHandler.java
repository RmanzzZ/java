package com.rman.config.exception;


import com.rman.config.result.Result;
import com.rman.config.result.ResultCode;
import com.rman.config.result.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * controller抛出的异常都会被此handler处理(aop)
 * @author RmanzzZ
 * @date 2019/11/2 下午2:25
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result unauthorized(UnauthorizedException e){
        logger.error(e.getMessage());
        return ResultGenerator.genFailResult("you are not been authorized!");
    }
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Result notFound(NoHandlerFoundException e){
        logger.error(e.getMessage());
        return new Result().setCode(ResultCode.NOT_FOUND).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result logicExceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        logger.error("异常提示[{}]",e.getMessage());
        Result result = ResultGenerator.genFailResult(e.getMessage());
        return result;
    }
}
