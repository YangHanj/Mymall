package iee.yh;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yanghan
 * @date 2022/4/7
 */
public class Demo2 {
    @Test
    public void JDKProxyDemo(){
        Target target = new Target();
        ClassLoader classLoader = Target.class.getClassLoader();
        Class<?>[] interfaces = Target.class.getInterfaces();
        Advice advice = new Advice();
        TargetInterface o = (TargetInterface) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                advice.printMSG();
                method.invoke(target, objects);
                return null;
            }
        });
        o.printMSG();
    }
    @Test
    public void CGLIBDemo(){
        Enhancer enhancer = new Enhancer();
        Target target = new Target();
        Advice advice = new Advice();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                advice.printMSG();
                method.invoke(target,objects);
                return null;
            }
        });
        Target o = (Target) enhancer.create();
        o.printMSG();
    }
}
interface TargetInterface{
    void printMSG();
}
class Target implements TargetInterface{

    @Override
    public void printMSG() {
        System.out.println("Hello World!");
    }
}
class Advice{
    public void printMSG(){
        System.out.println("Hello!");
    }

}
