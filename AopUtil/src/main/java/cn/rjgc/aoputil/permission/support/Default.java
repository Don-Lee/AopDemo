package cn.rjgc.aoputil.permission.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class Default implements ISetting {
    private Context mContext;

    public Default(Context context) {
        mContext = context;
    }

    @Override
    public Intent getSetting() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        return intent;
    }
}
