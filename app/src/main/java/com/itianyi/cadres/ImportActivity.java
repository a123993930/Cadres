package com.itianyi.cadres;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.itianyi.adapter.FileListAdapter;
import com.itianyi.app.CadresApplication;
import com.itianyi.app.DesUtil;
import com.itianyi.app.SQLiteHelper;
import com.itianyi.bean.Department;
import com.itianyi.bean.Office;
import com.itianyi.bean.Roster;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ImportActivity extends BaseActivity {

    private List<File> fileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_import);

        ListView list = (ListView) findViewById(R.id.listView);

        File file = new File(CadresApplication.mFilePath);
        File[] files = file.listFiles();
        fileList = new ArrayList();
        for(File child : files) {
            if(getExtensionName(child.getName()).equals("ros")){
                if((new File(child.getPath().substring(0,child.getPath().lastIndexOf('.'))+".des")).exists() &&
                        (new File(child.getPath().substring(0,child.getPath().lastIndexOf('.'))+".org")).exists()) {
                    fileList.add(child);
                }
            }
        }
        if(fileList.size()==0) {
            Toast.makeText(this,"未发现数据源！",Toast.LENGTH_SHORT).show();
        }
        FileListAdapter adapter = new FileListAdapter(this,fileList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final CadresApplication app = (CadresApplication) ImportActivity.this.getApplication();
                SQLiteHelper.DeleteAllData(app);

                final ProgressDialog m_pDialog = ProgressDialog.show(ImportActivity.this, "提示", "正在导入中");
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        File ros = fileList.get(position);
                        FileInputStream is = null;
                        byte[] buffer = new byte[0];
                        byte[] lrmbuffer = new byte[0];
                        byte[] deptbuffer = new byte[0];
                        try {
                            is = new FileInputStream(ros);
                            int size = is.available();
                            // Read the entire asset into a local byte buffer.
                            buffer = new byte[size];
                            is.read(buffer);
                            is.close();

                            is = new FileInputStream(new File(ros.getPath().substring(0, ros.getPath().lastIndexOf('.')) + ".des"));
                            size = is.available();
                            lrmbuffer = new byte[size];
                            is.read(lrmbuffer);
                            is.close();

                            is = new FileInputStream(new File(ros.getPath().substring(0, ros.getPath().lastIndexOf('.')) + ".org"));
                            size = is.available();
                            deptbuffer = new byte[size];
                            is.read(deptbuffer);
                            is.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                        String json = "";
                        String lrmjson = "";
                        String deptjson = "";
                        try {
                            json = new String(buffer, "UTF-8");
                            lrmjson = new String(lrmbuffer, "UTF-8");
                            deptjson = new String(deptbuffer,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String key = "gAt_8iTy";

                        try {
                            json = DesUtil.decrypt(json, key);
                            lrmjson = DesUtil.decrypt(lrmjson, key);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                            SQLiteDatabase db = app.getDateBase();
                            db.beginTransaction();  //手动设置开始事务
                            CadresApplication application = (CadresApplication) getApplication();
                            Date reference_time = application.getReference_time();
                        if(reference_time == null) {
                            reference_time = new Date();
                        }
                            try {
                                Roster.saveList(json, db,reference_time);
                                Office.saveList(lrmjson, db);
                                Department.saveList(deptjson,db);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
                            db.endTransaction(); //处理完成
                        m_pDialog.dismiss();
                        setResult(1);
                        finish();
                    }
                }).start();

                //SQLiteHelper.DeleteAllFile(appApplication.mFilePath);
            }
        });

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_import, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
