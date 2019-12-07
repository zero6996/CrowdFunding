package com.zero.entity;

/**
 * @Author: zero
 * @Description: 封装统一的Ajax响应结果，统一了整个项目中所有Ajax请求的响应格式
 * Date: Create in 2019/11/16 8:56
 * Modified By:
 */

public class ResultEntity<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";

    private String result;
    private String message;
    private T data;

    /**
     * 方便返回成功结果(不携带查询结果情况)
     * @return
     */
    public static ResultEntity<String> successWithoutData() {
        return new ResultEntity<String>(SUCCESS,NO_MESSAGE,NO_DATA);
    }

    /**
     * 便返回成功结果(携带查询结果情况)
     * @param data
     * @param <E>
     * @return
     */
    public static<E> ResultEntity<E> successWithoutData(E data){
        return new ResultEntity<E>(SUCCESS,NO_MESSAGE,data);
    }

    /**
     * 方便返回失败结果
     * @param message
     * @param data
     * @param <E>
     * @return
     */
    public static <E>ResultEntity<E> failed(E data,String message) {
        return new ResultEntity<E>(FAILED,message,data);
    }

    public ResultEntity() { }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
