package cn.rjgc.aopdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.rjgc.aoputil.annotation.AntiShake;


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
        Log.e(TAG, "按钮被点击了" );
    }
}