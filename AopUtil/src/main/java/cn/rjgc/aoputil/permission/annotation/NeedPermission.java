package cn.rjgc.aoputil.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedPermission {
    String[] value();//需要申请的权限列表

    int requestCode() default 0;//权限申请结果
}
