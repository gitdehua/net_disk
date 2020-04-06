package com.bytescat.netdisk.pojo;

public class Result<T> {
    public int code;
    public String msg;
    public T data;

    public enum ResStatus {
        OK(0, "OK"),
        ERROR(1, "ERROR"),
        SERVER_ERROR(2, "SERVER_ERROR"),
        FILE_NOT_FOUND(201, "FILE_NOT_FOUND") // 系统找不到指定的路径
        ;

        private int code;
        private String msg;

        private ResStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public ResStatus setMsg(String msg) {
            this.msg = msg;
            return this;
        }
    }

    public Result(ResStatus status) {
        this.code = status.code;
        this.msg = status.msg;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}
