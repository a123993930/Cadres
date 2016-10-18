package com.itianyi.cadres;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.itianyi.app.CadresApplication;
import com.itianyi.app.SQLiteHelper;
import com.itianyi.bean.Office;

import java.text.ParseException;


public class DetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra("com.itianyi.cadres.fragment.name");
        CadresApplication app = (CadresApplication) getApplication();
        Office office = null;
        try {
            office = SQLiteHelper.getOffice(app, name);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        if(office.getName().isEmpty()) {
            findViewById(R.id.sv_details).setVisibility(View.GONE);
            findViewById(R.id.rl_noDetails).setVisibility(View.VISIBLE);
            Toast.makeText(this,"未找到该干部的详细信息",Toast.LENGTH_SHORT).show();
            finish();
        } else {
            findViewById(R.id.sv_details).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_noDetails).setVisibility(View.GONE);

            ((EditText) findViewById(R.id.et_name)).setText(office.getName());
            ((EditText) findViewById(R.id.et_sex)).setText(office.getSex());
            String birthday = office.getBirth();
            ((EditText) findViewById(R.id.et_birthday)).setText(birthday.substring(0,4) + "." + birthday.substring(4));

            ((EditText) findViewById(R.id.et_nation)).setText(office.getMinzu());
            ((EditText) findViewById(R.id.et_origin)).setText(office.getJiguan());
            ((EditText) findViewById(R.id.et_birth)).setText(office.getBirthAddr());
            String rudangshijian = office.getRudangDate();
            if("".equals(rudangshijian)){
                ((EditText) findViewById(R.id.et_party)).setText("");
            }else{
                ((EditText) findViewById(R.id.et_party)).setText(rudangshijian.substring(0, 4) + "." + rudangshijian.substring(4));
            }
            String canjiagongzuoshijian=office.getGognzuoDate();
            ((EditText) findViewById(R.id.et_work)).setText(canjiagongzuoshijian.substring(0, 4) + "." + canjiagongzuoshijian.substring(4));
            ((EditText) findViewById(R.id.et_health)).setText(office.getJiankangStatus());

            ((EditText) findViewById(R.id.et_professional)).setText(office.getZhicheng());
            ((EditText) findViewById(R.id.et_expertise)).setText(office.getTechang());

            byte[] photoByte = office.getPhoto();
            if (photoByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
                if (bitmap != null)
                    ((ImageView) findViewById(R.id.iv_photo)).setImageBitmap(bitmap);
            }

            ((EditText) findViewById(R.id.et_qrzjy)).setText(office.getQrzjy().replace("#"," "));
            ((EditText) findViewById(R.id.et_qrzSchool)).setText(office.getQrzSchool().replace("#", " "));
            ((EditText) findViewById(R.id.et_zzjy)).setText(office.getZzjy().replace("#", " "));
            ((EditText) findViewById(R.id.et_zzScroll)).setText(office.getZzSchool().replace("#"," "));

            ((EditText) findViewById(R.id.et_xrzw)).setText(office.getXrzw());
            ((EditText) findViewById(R.id.et_jianli)).setText(office.getJianli());
            ((EditText) findViewById(R.id.et_jiangcheng)).setText(office.getJiangcheng());

            TableLayout tl_member = (TableLayout) findViewById(R.id.tl_member);
            int count = office.getMemberList().size();
            for (int i = 0; i < count; i++) {
                TableRow row = (TableRow) tl_member.getChildAt(i + 1);
                Office.Member member = office.getMemberList().get(i);
                //每一行有五列
                //第一列称谓
                ((EditText) row.getChildAt(0)).setText(member.getChengwei());
                //第二列姓名
                ((EditText) row.getChildAt(1)).setText(member.getName());
                //第三列出生日期
                String age = member.getAge();
                if(age.length()==6) {
                    age = age.substring(0,4)+"." +age.substring(4);
                }
                ((EditText) row.getChildAt(2)).setText(age);
                //第四列政治面貌
                ((EditText) row.getChildAt(3)).setText(member.getZzmm());
                //第五行工作单位
                ((EditText) row.getChildAt(4)).setText(member.getWork());

            }
        }
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
