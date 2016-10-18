package com.itianyi.cadres;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.itianyi.app.mHomeKeyEventReceiver;

/**
 * Created by 沫 on 2015/7/30.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        ActionBar actionBar= getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //注册广播
        registerReceiver(new mHomeKeyEventReceiver(), new IntentFilter(
                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }
}
