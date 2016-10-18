package com.itianyi.cadres;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.itianyi.app.CadresApplication;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        CadresApplication app = (CadresApplication) getApplication();
        Intent intent;
        if(app.isFirst()) {
            //首次打开的时候，注册用户名密码
            intent = new Intent(this,RegisteredActivity.class);
        } else {
            //再次打开的时候，登录进入程序

            intent = new Intent(this,LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
