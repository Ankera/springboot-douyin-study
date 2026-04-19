package com.zimu.test;

public enum TestEnum {
    SUCCESS(200, true, "操作成功！");

    TestEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    // 响应业务状态
    private Integer status;
    // 调用是否成功
    private Boolean success;
    // 响应消息，可以为成功或者失败的消息
    private String msg;

    public Integer getStatus() { return status; }
    public Boolean getSuccess() { return success; }
    public String getMsg() { return msg; }
}
