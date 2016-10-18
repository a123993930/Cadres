package com.itianyi.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.itianyi.bean.Department;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by 沫 on 2015/7/21.
 */
public class CadresApplication extends Application {

    public static String mSDCard = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
    public static String mFilePath = mSDCard + File.separator +"花名册";
    private SharedPreferences preferences;
    private SQLiteDatabase mSQLiteDatebase;
    private List<Department> mDepts;

    private Date reference_time;
    @Override
    public void onCreate() {
        super.onCreate();
        reference_time = null;
        //打开时创建文件夹
        File destDir = new File(mFilePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    public boolean isFirst() {
        SharedPreferences preferences = getPreferences();
        String userName = preferences.getString("userName", "");
        if(userName.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void setLoginInfo(String userName,String userPassword) {
        SharedPreferences preferences = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", userName.toUpperCase());
        editor.putString("userPassword", userPassword);
        editor.commit();
    }

    public int getErrorLoginNumber(String userName,String userPassword) {
        SharedPreferences preferences = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        int errorNum = preferences.getInt("errorNumber",0);
        String mName = preferences.getString("userName", "");
        String mPassword = preferences.getString("userPassword", "");
        if(mName.equals(userName)&&mPassword.equals(userPassword)) {
            editor.putInt("errorNumber", 0);
            editor.commit();
            return 0;
        }
        errorNum++;
        editor.putInt("errorNumber", errorNum);
        editor.commit();
        return errorNum;
    }



    private SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = this.getSharedPreferences("OfficeSharedPreferences",
                    Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public SQLiteDatabase getDateBase() {
        if(mSQLiteDatebase == null) {
            mSQLiteDatebase=this.openOrCreateDatabase("roster", MODE_PRIVATE, null);
        }
        return mSQLiteDatebase;
    }

    public List<Department> getDeptmentList() {
        if(mDepts==null) {
            mDepts = SQLiteHelper.getDepartmentList(this);
        }
        return mDepts;
    }

    public Date getReference_time() {
        return reference_time;
    }

    public void setReference_time(Date reference_time) {
        this.reference_time = reference_time;
    }
}
