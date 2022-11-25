package iee.yh.Mymall.product.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import iee.yh.Mymall.product.annotation.MyCacheAnno;
import iee.yh.Mymall.product.entity.CategoryEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yanghan
 * @date 2022/11/24
 */
@Aspect
@Component
public class ReadCache {

    private String NAME;
    private Long TTL;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Resource
    RedissonClient redissonClient;

    @Around(value = "PointCut()")
    public List<CategoryEntity> ReadCache(ProceedingJoinPoint jp) throws NoSuchMethodException {
        getInfo(jp);
        // 查询缓存
        String catalogJSON = redisTemplate.opsForValue().get(NAME);
        if (StringUtils.isEmpty(catalogJSON)){
            List<CategoryEntity> categoryEntities = null;
            // 加锁 防止缓存击穿
            RLock lock = redissonClient.getLock("product-lock");
            lock.lock();
            try {
                catalogJSON = redisTemplate.opsForValue().get(NAME);
                // Double Check
                if (StringUtils.isEmpty(catalogJSON)){
                    // 反射查库
                    categoryEntities = (List<CategoryEntity>)jp.proceed();
                    // 放入缓存
                    catalogJSON = JSON.toJSONString(categoryEntities);
                    redisTemplate.opsForValue().set(NAME,catalogJSON,TTL, TimeUnit.SECONDS);
                    return categoryEntities;
                }
            }catch (Throwable throwable) {
                throwable.printStackTrace();
            }finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread())
                lock.unlock();
            }
        }
        // 反序列化（JSON --> List）
        List<CategoryEntity> res = JSON.parseObject(catalogJSON, new TypeReference<List<CategoryEntity>>(){});
        return res;
    }

    private void getInfo(ProceedingJoinPoint jp) throws NoSuchMethodException {
        Class<?> targetCls= jp.getTarget().getClass();
        //获取方法签名(通过此签名获取目标方法信息)
        MethodSignature ms=(MethodSignature) jp.getSignature();
        //获取目标方法上的注解指定的操作名称
        Method targetMethod=
                targetCls.getDeclaredMethod(
                        ms.getName(),
                        ms.getParameterTypes());
        MyCacheAnno annotation = targetMethod.getAnnotation(MyCacheAnno.class);
        NAME = annotation.name();
        TTL = annotation.ttl();
    }

    @Pointcut("@annotation(iee.yh.Mymall.product.annotation.MyCacheAnno)")
    private void PointCut(){}
}
