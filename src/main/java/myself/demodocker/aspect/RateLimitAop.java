package myself.demodocker.aspect;

import com.google.common.util.concurrent.RateLimiter;
import myself.demodocker.RateLimit;
import myself.demodocker.counter.CounterAtomic;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @className: RateLimitAop
 * @description:
 * @author: YuZhiYuan
 * @date: 2020-05-25 18:14
 **/
@Component
@Aspect
public class RateLimitAop {
    private Map<String, RateLimiter> rateHashMap = new ConcurrentHashMap<>();
    @Pointcut("@annotation(myself.demodocker.RateLimit)") // 以注解的方式定义切点
    public void serviceLimit(){}


    @Around("serviceLimit()") // 通知
    public Object limit(ProceedingJoinPoint joinPoint) throws Throwable {
        //1.如果请求方法上存在RateLimit注解的话
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取到AOP拦截的方法
        Method method = signature.getMethod();

        if (method == null) {
            // 直接报错
            System.out.println("没有获取到签名方法");
        }
        RateLimit rateLimit = method.getDeclaredAnnotation(RateLimit.class);
        if(rateLimit == null) {
            return joinPoint.proceed();
        }
        int permitsPerSecond = rateLimit.value();
        long timeout = rateLimit.timeout();


        // 3.调用原生的RateLimiter创建令牌 保证每个请求对应都是单例的RateLimiter
        // index---RateLimiter /order --RateLimiter 使用hashMap key为 请求的url地址##
        RateLimiter rateLimiter = null;
        String methodName = method.getName();
//        rateHashMap.putIfAbsent(methodName, RateLimiter.create(permitsPerSecond));
//        rateLimiter = rateHashMap.get(methodName);
        if (rateHashMap.containsKey(methodName)) {
            // 如果在hashMap URL 能检测到RateLimiter
            rateLimiter = rateHashMap.get(methodName);
        } else {
            // 如果在hashMap URL 没有检测到RateLimiter 添加新的RateLimiter
            rateLimiter = RateLimiter.create(permitsPerSecond);
            rateHashMap.put(methodName, rateLimiter);
        }

        // 4.获取令牌桶中的令牌，如果没有有效期获取到令牌的话，则直接调用本地服务降级方法，不会进入到实际请求方法中。
        boolean tryAcquire = rateLimiter.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        System.out.println(tryAcquire);
        if(!tryAcquire) {
            // 执行降级方法 fallback();
            return "超过了最大线程数";

        }

        // 5.获取令牌桶中的令牌，如果能在有效期获取令牌到令的话，则直接进入到实际请求方法中。
        // 直接进入实际请求方法中
        return joinPoint.proceed();
    }


}