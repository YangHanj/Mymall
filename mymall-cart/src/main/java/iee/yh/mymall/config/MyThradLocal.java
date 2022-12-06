package iee.yh.mymall.config;

/**
 * @author yanghan
 * @date 2022/12/4
 */
public class MyThradLocal {
    private static volatile ThreadLocal<Object> threadLocal;
    private MyThradLocal(){

    }
    public static ThreadLocal<Object> getInstance(){
        if (threadLocal == null){
            synchronized (MyThradLocal.class){
                if (threadLocal == null){
                    threadLocal = new ThreadLocal<>();
                }
            }
        }
        return threadLocal;
    }
}
