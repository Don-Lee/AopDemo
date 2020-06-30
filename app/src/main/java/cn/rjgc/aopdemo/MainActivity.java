package cn.rjgc.aopdemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.rjgc.aoputil.anti_shake.AntiShake;
import cn.rjgc.aoputil.permission.annotation.CancelPermission;
import cn.rjgc.aoputil.permission.annotation.DeniedPermission;
import cn.rjgc.aoputil.permission.annotation.NeedPermission;
import cn.rjgc.aoputil.permission.util.SettingUtils;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //防抖动测试
    @AntiShake
    public void antiShake(View view) {
        Toast.makeText(this, "我被点击了", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "按钮被点击了" );
    }








    //权限申请测试
    @NeedPermission(value = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, requestCode = 1)
    public void requestPermission(View view) {
        Log.e(TAG, "requestPermission: ");
    }

    @CancelPermission
    public void permissionCancel(int requestCode) {
        Log.e(TAG, "permissionCancel: " + requestCode);
    }

    @DeniedPermission
    public void permissionDenied(int requestCode) {
        showMissingPermissionDialog();
    }





    /**
     * 在Service中申请权限
     */
    public void requestPermissionByService(View view) {
        Intent intent = new Intent(this, PermissionService.class);
        startService(intent);
    }

    /**
     * 在普通类中的非静态方法中申请权限
     */
    public void requestPermissionByClass(View view) {
        PermissionClass util = new PermissionClass();
        util.requestPermission(this);
    }


    //提示框
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击'设置-‘权限’-打开所需权限'");
        builder.setNegativeButton("取消", (dialogInterface, i) -> finish());

        builder.setPositiveButton("去设置", (dialogInterface, i) -> startAppSettings());
        builder.setCancelable(false);
        builder.show();

    }

    //跳转手机设置界面
    private void startAppSettings() {
        SettingUtils.go2Setting(this);
    }
}