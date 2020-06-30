package cn.rjgc.aoputil.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.rjgc.aoputil.R;
import cn.rjgc.aoputil.permission.util.PermissionUtils;

/**
 * 请求权限的Activity
 * 使用Activity进行权限申请是为了支持在Activity和Fragment以外的地方进行权限申请，比如Service等
 */
public class RequestPermissionActivity extends AppCompatActivity {

    private static IPermission mIPermission;
    private static final String PERMISSSION = "permission";
    private static final String REQUEST_CODE = "request_code";
    private String[] mPermissions;
    private int mRequestCode;

    public List<String> needRequestPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPermissions = bundle.getStringArray(PERMISSSION);
            mRequestCode = bundle.getInt(REQUEST_CODE);
        }

        if (mPermissions == null || mPermissions.length < 1) {
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
            finish();
            return;
        }

        requestPermission();
    }


    public static void startRequestPermissionActivity(Context context, String[] permissions,
                                                      int requestCode, IPermission iPermission) {
        mIPermission = iPermission;
        Intent intent = new Intent(context, RequestPermissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PERMISSSION, permissions);
        bundle.putInt(REQUEST_CODE, requestCode);
        intent.putExtras(bundle);

        context.startActivity(intent);

        if (context instanceof Activity) {
            //去掉activity的跳转动画，必须要加，否者在高版本上会有闪烁现象
            //参数不能直接设置为0，因为在某些厂商的手机上高版本上设置为0也会闪烁
            ((Activity) context).overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
        }
    }

    private void requestPermission() {
        if (PermissionUtils.checkPermissions(this, mPermissions)) {//所有权限均已授权
            mIPermission.onPermissionGranted();
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
            finish();
        } else {//申请权限
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, mPermissions, mRequestCode);
            } else {
                //解决6.0版本中已授权权限重复申请的问题，例：一起申请2个权限，如果其中一个被拒绝了，
                // 那么再次申请时另外一个已允许的权限依然会再次申请
                List<String> needRequestPermissionList = findDeniedPermissions(this, mPermissions);
                if (needRequestPermissionList.size() > 0) {
                    ActivityCompat.requestPermissions(this,needRequestPermissionList.toArray(
                            new String [0]), mRequestCode);
                }
            }

        }
    }
    //获取权限集中需要申请权限的列表
    private  List<String> findDeniedPermissions(Context context, String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(context, perm) !=
                    PackageManager.PERMISSION_GRANTED) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }
    //权限申请结果的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtils.verifyPermissions(grantResults)) {//授权成功
            mIPermission.onPermissionGranted();
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {//取消权限
                mIPermission.onPermissionCancel(requestCode);
            } else {//拒绝权限
                mIPermission.onPermissionDenied(requestCode);
            }
        }

        //去掉activity的跳转动画，必须要加，否者在高版本上会有闪烁现象
        //参数不能直接设置为0，因为在某些厂商的手机上高版本上设置为0也会闪烁
        overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
        finish();
    }
}