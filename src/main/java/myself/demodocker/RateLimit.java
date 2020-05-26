package myself.demodocker;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 一秒钟的令牌数
     * @return
     */
    int value() default 10;

    /**
     * 等待时间 毫秒
     * @return
     */
    long timeout() default 0;
}
