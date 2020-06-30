package cn.rjgc.aoputil.permission.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PermissionUtils {
    private static final int PERMISSION_REQUESTCODE = 0;
    //判断是否所有的权限都已授权
    public static boolean checkPermissions(Context context, String... permissions) {

        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(context, perm) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    //判断权限申请结果，全部授权返回true,否者返回false
    public static boolean verifyPermissions(int[] grantResults) {
        boolean res = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                res = false;
            }
        }
        return res;
    }

    //判断是否需要给用户提示
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过反射调用指定方法
     * @param object
     * @param annotationClass
     * @param requestCode
     */
    public static void invokeMethod(Object object, Class annotationClass, int requestCode) {
        //获取切面上下文的类型
        Class<?> clazz = object.getClass();
        //获取类型中的方法
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length < 1) {
            return;
        }
        for (Method method : methods) {
            //判断该方法是否有annotationClass注解
            boolean isHasAnnotation = method.isAnnotationPresent(annotationClass);
            if (isHasAnnotation) {
                //判断是否有且近由一个int参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException("参数数量不匹配，应当仅有一个参数");
                }
                method.setAccessible(true);//更改参数的访问权限为public
                try {
                    method.invoke(object, requestCode);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
