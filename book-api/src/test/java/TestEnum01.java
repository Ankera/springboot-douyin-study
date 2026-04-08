public enum TestEnum01 {
    MOBILE_ERROR(504, false, "短信发送失败，请稍后重试！"),
    SMS_NEED_WAIT_ERROR(505, false, "短信发送太快啦~请稍后再试！");

    private final int status;
    private final boolean success;
    private final String msg;

    // 构造方法（必须是 private）
    TestEnum01(int status, boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    // 需要手动添加 getter 方法
    public int getStatus() { return status; }
    public boolean isSuccess() { return success; }
    public String getMsg() { return msg; }


    public static void main(String[] args) {
        String msg1 = TestEnum01.MOBILE_ERROR.msg;
    }

}
