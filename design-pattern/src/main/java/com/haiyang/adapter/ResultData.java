package com.haiyang.adapter;

/**
 * @ClassName: ResultData
 * @Description:  结果
 * @Author Administrator
 * @CreateDate 2019/4/6 15:05
 * @Version 1.0
 */
public class ResultData {
    private int code;
    private String message;
    private Object data;

    public ResultData(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }



}
