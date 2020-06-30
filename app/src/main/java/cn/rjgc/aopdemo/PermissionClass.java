package cn.rjgc.aopdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import cn.rjgc.aoputil.permission.annotation.CancelPermission;
import cn.rjgc.aoputil.permission.annotation.DeniedPermission;
import cn.rjgc.aoputil.permission.annotation.NeedPermission;
import cn.rjgc.aoputil.permission.util.SettingUtils;

public class PermissionClass {
    private static final String TAG = "PermissionClass";
    private Context mContext;
    //权限申请测试
    @NeedPermission(value = Manifest.permission.CAMERA, requestCode = 1)
    public void requestPermission(Context context) {
        mContext = context;
        Log.e(TAG, "requestPermission: ");
    }

    @CancelPermission
    public void permissionCancel(int requestCode) {
        Log.e(TAG, "permissionCancel: " + requestCode);
    }

    @DeniedPermission
    public void permissionDenied(int requestCode) {
        Log.e(TAG, "permissionDenied: " + requestCode);
    }

}
