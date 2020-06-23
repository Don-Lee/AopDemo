package cn.rjgc.aoputil;


import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * 防抖动切面类
 * 拦截1s内多次点击同一view所触发的重复事件
 */
@Aspect
public class AntiShakeAspect {
    private Long lastClickTime = 0L;
    private final Long FILTER_TIMEM = 1000L;
    /**
     * 根据pointcut语法寻找符合要求的jpoint
     */
    //MethodSignature语法规则 @Annotation java修饰符 返回值类型 类名.方法名(参数)
    @Pointcut("execution(@cn.rjgc.aoputil.annotation.AntiShake * *(..))")
    public void findJPoint() {

    }

    /**
     * 在符合findJPoint()要求的连接点方法运行时运行此通知
     * @param joinPoint 使用@Around注解必须带着此参数
     */
    @Around("findJPoint()")
    public void antiShake(ProceedingJoinPoint joinPoint) {
        if (System.currentTimeMillis() - lastClickTime >= FILTER_TIMEM) {
            lastClickTime = System.currentTimeMillis();
            try {
                joinPoint.proceed();//执行Join Point方法的代码
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }/* else {
            Log.e("AntiShakeAspect","重复点击,已过滤");
        }*/
    }
}
