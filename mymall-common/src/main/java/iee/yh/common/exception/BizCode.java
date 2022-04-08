package iee.yh.common.exception;

/**
 * @author yanghan
 * @date 2022/4/8
 */
public enum BizCode {
    //参数校验
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    //系统未知异常
    UNKNOW_EXCEPTION(10000,"系统未知异常");

    private int code;
    private String msg;

    BizCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
