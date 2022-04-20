package iee.yh.common.constant;

/**
 * @author yanghan
 * @date 2022/4/18
 */
public class WareConstant {
    public enum PurchaseStatus{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        RECEIVE(2,"已领取"),
        FINISH(3,"已完成"),
        HASERROR(4,"有异常");

        private int code;
        private String msg;

        PurchaseStatus(int code,String msg){
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
    public enum PurchaseDetailStatus{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        BUYING(2,"正在采购"),
        FINISH(3,"已完成"),
        FAILURE(4,"采购失败");

        private int code;
        private String msg;

        PurchaseDetailStatus(int code,String msg){
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
}
