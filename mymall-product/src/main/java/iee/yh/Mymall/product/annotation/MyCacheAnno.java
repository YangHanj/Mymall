package iee.yh.Mymall.product.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author yanghan
 * @date 2022/11/24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyCacheAnno {

    @AliasFor("name")
    String value() default "";

    // 缓存的key名称
    @AliasFor("value")
    String name() default "";

    //过期时间
    long ttl() default -1l;
}
