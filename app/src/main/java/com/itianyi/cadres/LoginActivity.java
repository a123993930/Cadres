package com.itianyi.cadres;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itianyi.app.CadresApplication;
import com.itianyi.app.SQLiteHelper;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {

        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString().toUpperCase();
        if(name.isEmpty()) {
            Toast.makeText(this, "请填写登录名！", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = et_password.getText().toString();
        if(password.isEmpty()) {
            Toast.makeText(this,"请填写密码！", Toast.LENGTH_SHORT).show();
            return;
        }

        CadresApplication appApplication = (CadresApplication) getApplication();
        int errorLoginNumber = appApplication.getErrorLoginNumber(name,password);
        if(errorLoginNumber!=0) {
            if(errorLoginNumber > 4) {
                SQLiteHelper.DeleteAllData(appApplication);
                MainTabActivity.rosterList = null;
                Toast.makeText(this,"输入错误次数过多，已删除所有数据。", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this,"请输入正确的用户名和密码，您已输入错误 " + errorLoginNumber + " 次！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this,MainTabActivity.class);
        startActivity(intent);
        finish();
    }
}
