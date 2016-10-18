package com.itianyi.cadres;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itianyi.app.CadresApplication;


public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_password;
    private EditText et_confirm;
    private Button btn_setLoginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registered);

        //初始化视图
        init();
    }

    private void init() {

        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm = (EditText) findViewById(R.id.et_confirm);

        btn_setLoginInfo = (Button) findViewById(R.id.btn_setLoginInfo);
        btn_setLoginInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        if(name.isEmpty()) {
            Toast.makeText(this,"请设置登录名！", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = et_password.getText().toString();
        if(password.isEmpty()) {
            Toast.makeText(this,"请设置密码！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 3) {
            Toast.makeText(this,"密码长度不能小于3位！", Toast.LENGTH_SHORT).show();
            return;
        }
//        boolean isNum = false;
//        boolean isChar = false;
//        boolean isOther = false;
//        int typeNum = 0;
//        for(int i = 0;i<password.length();i++) {
//            char c = password.charAt(i);
//            if(Character.isDigit(c)) {
//                if(!isNum)
//                    typeNum++;
//                isNum = true;
//            } else if(Character.isLetter(c)) {
//                if(!isChar)
//                    typeNum++;
//                isChar = true;
//            } else {
//                if(!isOther)
//                    typeNum++;
//                isOther = true;
//            }
//        }
//        if(typeNum < 2) {
//            Toast.makeText(this,"密码中应包含数字，字母或其他符号！", Toast.LENGTH_SHORT).show();
//            return;
//        }
        String confirm = et_confirm.getText().toString();
        if(!password.equals(confirm)) {
            Toast.makeText(this,"两次密码输入不一致！", Toast.LENGTH_SHORT).show();
            return;
        }

        CadresApplication appApplication = (CadresApplication) getApplication();
        appApplication.setLoginInfo(name,password);
        Intent intent = new Intent(this,MainTabActivity.class);
        startActivity(intent);
        finish();
    }
}
