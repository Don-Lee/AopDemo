package cn.rjgc.aoputil.permission.util;

import android.content.Context;
import android.os.Build;

import cn.rjgc.aoputil.permission.support.Default;
import cn.rjgc.aoputil.permission.support.ISetting;
import cn.rjgc.aoputil.permission.support.Vivo;

public class SettingUtils {

    private static final String MANUFACTURER_HUAWEI = "Huawei";//华为
    private static final String MANUFACTURER_MEIZU = "Meizu";//魅族
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";//小米
    private static final String MANUFACTURER_SONY = "Sony";//索尼
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private static final String MANUFACTURER_LETV = "Letv";//乐视
    private static final String MANUFACTURER_ZTE = "ZTE";//中兴
    private static final String MANUFACTURER_YULONG = "YuLong";//酷派
    private static final String MANUFACTURER_LENOVO = "LENOVO";//联想

    /**
     * 跳设置界面
     *
     * @param context context
     */
    public static void go2Setting(Context context) {
        ISetting iSetting;

        switch (Build.MANUFACTURER) {
            case MANUFACTURER_VIVO:
                iSetting = new Vivo(context);
                break;
            default:
                iSetting = new Default(context);
                break;
        }
        if (iSetting.getSetting() == null) return;
        context.startActivity(iSetting.getSetting());
    }


}
