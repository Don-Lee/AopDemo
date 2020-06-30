package cn.rjgc.aopdemo;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import cn.rjgc.aoputil.permission.annotation.CancelPermission;
import cn.rjgc.aoputil.permission.annotation.DeniedPermission;
import cn.rjgc.aoputil.permission.annotation.NeedPermission;
import cn.rjgc.aoputil.permission.util.SettingUtils;

public class PermissionService extends Service {
    private static final String TAG = "PermissionService";
    public PermissionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestPermission();
        return super.onStartCommand(intent, flags, startId);
    }

    //权限申请测试
    @NeedPermission(value = Manifest.permission.CAMERA, requestCode = 1)
    public void requestPermission() {
        Toast.makeText(this, "requestPermission: 权限请求成功" , Toast.LENGTH_SHORT).show();
        Log.e(TAG, "requestPermission: ");
    }

    @CancelPermission
    public void permissionCancel(int requestCode) {
        Toast.makeText(this, "permissionCancel: " + requestCode, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "permissionCancel: " + requestCode);
    }

    @DeniedPermission
    public void permissionDenied(int requestCode) {
        Toast.makeText(this, "permissionDenied: " + requestCode, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "permissionDenied: " + requestCode);
    }
}
