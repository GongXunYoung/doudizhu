package com.itjob.doudizhu.http;

public class HttpResult {


    /**
     * status : 200
     * message : 操作成功
     * data : 9115e748-22c1-4e9d-a9c4-cc7de4ef7589
     */

    private int status;
    private String message;
    private String data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
