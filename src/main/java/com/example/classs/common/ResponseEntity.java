package com.example.classs.common;

import lombok.Getter;

/**
 * @version v1.0
 * @ClassName ResultData
 * @Description TODO
 * @Author Q
 */
@Getter
public class ResponseEntity<T> {

    //定义当前时间戳
    private final long timestamp;

    //定义返回状态码
    private final int status;

    //定义返回消息
    private final String message;

    //定义返回数据
    private final T data;


    private ResponseEntity(int status, Builder<T> builder) {
        this.status = status;
        this.message = builder.message;
        this.data = builder.data;
        this.timestamp = System.currentTimeMillis();
    }

    private final static class Builder<T> {
        //定义返回状态码
        private int status;

        //定义返回消息
        private String message;

        //定义返回数据
        private T data;

        public Builder(int status) {
            this.status = status;
        }


        public Builder<T> message(String val) {
            this.message = val;
            return this;
        }

        public Builder<T> data(T val) {
            this.data = val;
            return this;
        }

        public ResponseEntity<T> build() {
            return new ResponseEntity<T>(status, this);
        }
    }

    public static <T> ResponseEntity<T> ok() {
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), null);
    }


    public static <T> ResponseEntity<T> ok(int status, T data) {
        return ResponseEntity.ok(status, data, null);
    }

    public static <T> ResponseEntity<T> ok(int status, T data, String message) {
        return new Builder<T>(status).data(data).message(message).build();
    }

    public static <T> ResponseEntity<T> fail() {
        return ResponseEntity.ok(ResponseCode.RC999.getCode(), null);
    }


    public static <T> ResponseEntity<T> fail(int status, T data) {
        return ResponseEntity.ok(status, data, null);
    }

    public static <T> ResponseEntity<T> fail(int status, T data, String message) {
        return new Builder<T>(status).data(data).message(message).build();
    }

}
