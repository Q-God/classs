package com.example.classs.advise;

import com.example.classs.common.ResponseEntity;
import com.example.classs.exception.UnauthorizedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import static com.example.classs.common.ResponseCode.PROGRAMMEREXCEPTION;
import static com.example.classs.common.ResponseCode.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity MethodArgumentNotValidExceptionHandler(UnauthorizedException e) {
        String message = e.getMessage();
        //下边ResultCodeEnum.PARAMS_BS_ERROR.getCode()就是你自己自定义的返回code码
        return ResponseEntity.fail(UNAUTHORIZED.getCode(), message, UNAUTHORIZED.getMessage());
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        //下边ResultCodeEnum.PARAMS_BS_ERROR.getCode()就是你自己自定义的返回code码
        return ResponseEntity.fail(PROGRAMMEREXCEPTION.getCode(), message, PROGRAMMEREXCEPTION.getMessage());
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity BindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return ResponseEntity.fail(PROGRAMMEREXCEPTION.getCode(), message, PROGRAMMEREXCEPTION.getMessage());
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return ResponseEntity.fail(PROGRAMMEREXCEPTION.getCode(), message, PROGRAMMEREXCEPTION.getMessage());
    }

}
