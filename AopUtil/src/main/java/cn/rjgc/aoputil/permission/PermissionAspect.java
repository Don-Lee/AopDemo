package cn.rjgc.aoputil.permission;

import android.content.Context;

import androidx.fragment.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import cn.rjgc.aoputil.permission.annotation.CancelPermission;
import cn.rjgc.aoputil.permission.annotation.DeniedPermission;
import cn.rjgc.aoputil.permission.annotation.NeedPermission;
import cn.rjgc.aoputil.permission.util.PermissionUtils;
import cn.rjgc.aoputil.permission.util.Utils;

/**
 * 权限申请切面类
 */
@Aspect
public class PermissionAspect {
    /**
     * 根据pointcut语法寻找符合要求的jpoint
     */
    //MethodSignature语法规则 @Annotation java修饰符 返回值类型 类名.方法名(参数)
    //  "&&"后面是注解中传递的参数,参数的名称可以随便定义,但是后续有使用到此参数的地方都必须使用此处定义的变量名称
    @Pointcut("execution(@cn.rjgc.aoputil.permission.annotation.NeedPermission * *(..)) && @annotation(needPermission)")
    public void findJPoint(NeedPermission needPermission) {

    }

    /**
     * 在符合@Around中要求的连接点方法运行时运行此通知
     * @param joinPoint 使用@Around注解必须带着此参数
     *
     * @Around 表示antiShake()方法执行的时机
     */
    //    @Around("findJPoint(needPermission)")   //方式一
    //方式二
    @Around("execution(@cn.rjgc.aoputil.permission.annotation.NeedPermission * *(..)) && @annotation(needPermission)")
    public void requestPermission(final ProceedingJoinPoint joinPoint, NeedPermission needPermission) {

        final Object pointThis = joinPoint.getThis();//获取当前执行者的对象，即连接点所在的类对象
        if (pointThis == null) {
            return;
        }

        Context context;
        if (pointThis instanceof Context) {
            context = (Context) pointThis;
        } else if (pointThis instanceof Fragment) {
            context = ((Fragment) pointThis).getActivity();
        } else { //此代码块可以支持在非Activity和Fragment中申请权限
           //获取切入点方法上的参数列表
            Object[] objects = joinPoint.getArgs();
            if (objects.length > 0) {
                //非静态方法且第一个参数为context
                if (objects[0] instanceof Context) {
                    context = (Context) objects[0];
                } else {
                    //没有传入context 默认使用application
                    context = Utils.getApp();
                }
            } else {
                context = Utils.getApp();
            }
        }


        RequestPermissionActivity.startRequestPermissionActivity(context, needPermission.value(),
                needPermission.requestCode(), new IPermission() {
                    @Override
                    public void onPermissionGranted() {//权限申请成功
                        try {
                            joinPoint.proceed();//执行目标类中JPoint的代码
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onPermissionDenied(int requestCode) {
                        //通过反射调用客户端拒绝权限时应执行的方法
                        PermissionUtils.invokeMethod(pointThis, DeniedPermission.class, requestCode);
                    }

                    @Override
                    public void onPermissionCancel(int requestCode) {
                        //通过反射调用客户端取消权限时应执行的方法
                        PermissionUtils.invokeMethod(pointThis, CancelPermission.class, requestCode);
                    }
                });
    }
}
