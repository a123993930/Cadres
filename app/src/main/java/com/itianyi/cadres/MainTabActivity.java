package com.itianyi.cadres;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itianyi.adapter.TabViewFragmentAdapter;
import com.itianyi.app.CadresApplication;
import com.itianyi.app.SQLiteHelper;
import com.itianyi.app.mHomeKeyEventReceiver;
import com.itianyi.bean.Roster;
import com.itianyi.dialog.ExitDialog;
import com.itianyi.fragment.CollectFragment;
import com.itianyi.fragment.FindFragment;
import com.itianyi.fragment.ManagerFragment;


public class MainTabActivity extends BaseActivity implements OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    TabViewFragmentAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    /**
     * The {@link LinearLayout} 人员信息按钮.
     */
    LinearLayout ll_collect;
    /**
     * The {@link LinearLayout} 统计信息按钮.
     */
    LinearLayout ll_find;
    /**
     * The {@link LinearLayout} 系统管理按钮.
     */
    LinearLayout ll_manager;
    /**
     * The {@link List<Roster>} 数据集合.
     */
    public static List<Roster> rosterList;

    public static String units = "",department = "",rank = "",name = "",police = "",category = "";
    public static int nlBegin = 0,nlEnd = 999;

    List<Fragment> fragmentList;

    private static int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_tab);

        fragmentList = new ArrayList<>();
        fragmentList.add(CollectFragment.newInstance());
        fragmentList.add(FindFragment.newInstance());
        fragmentList.add(ManagerFragment.newInstance());
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new TabViewFragmentAdapter(this, getSupportFragmentManager(), fragmentList);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        ll_collect = (LinearLayout) findViewById(R.id.ll_collect);
        ll_collect.setOnClickListener(this);
        ll_find = (LinearLayout) findViewById(R.id.ll_find);
        ll_find.setOnClickListener(this);
        ll_manager = (LinearLayout) findViewById(R.id.ll_manager);
        ll_manager.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
                ImageView iv_baobiao = (ImageView) findViewById(R.id.iv_baobiao);
                ImageView iv_renyuan = (ImageView) findViewById(R.id.iv_renyuan);
                ImageView iv_manager = (ImageView) findViewById(R.id.iv_manager);
                TextView tv_baobiao = (TextView) findViewById(R.id.tv_baobiao);
                TextView tv_renyuan = (TextView) findViewById(R.id.tv_renyuan);
                TextView tv_manager = (TextView) findViewById(R.id.tv_manager);
                switch (position) {
                    case 0:
                        iv_renyuan.setImageResource(R.drawable.renyuan2);
                        iv_baobiao.setImageResource(R.drawable.baobiao1);
                        iv_manager.setImageResource(R.drawable.setbiao1);
                        tv_renyuan.setTextColor(Color.parseColor("#0098FE"));
                        tv_baobiao.setTextColor(Color.parseColor("#AEAEAE"));
                        tv_manager.setTextColor(Color.parseColor("#AEAEAE"));
                        break;
                    case 1:
                        iv_renyuan.setImageResource(R.drawable.renyuan1);
                        iv_baobiao.setImageResource(R.drawable.baobiao2);
                        iv_manager.setImageResource(R.drawable.setbiao1);
                        tv_renyuan.setTextColor(Color.parseColor("#AEAEAE"));
                        tv_baobiao.setTextColor(Color.parseColor("#0098FE"));
                        tv_manager.setTextColor(Color.parseColor("#AEAEAE"));

                        try {
                            ((FindFragment) fragmentList.get(1)).setData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case 2:
                        iv_renyuan.setImageResource(R.drawable.renyuan1);
                        iv_baobiao.setImageResource(R.drawable.baobiao1);
                        iv_manager.setImageResource(R.drawable.setbiao2);
                        tv_renyuan.setTextColor(Color.parseColor("#AEAEAE"));
                        tv_baobiao.setTextColor(Color.parseColor("#AEAEAE"));
                        tv_manager.setTextColor(Color.parseColor("#0098FE"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(pageIndex);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_find:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_manager:
                mViewPager.setCurrentItem(2);
        }
    }

    public List<Roster> getRosterList(String units,String department,String rank,String name,int nlBegin,int nlEnd,String police,String category,int orderState,int orderType,int index) {
        getRosterList(units,department,rank,name,nlBegin,nlEnd,police,category,orderState,orderType);

        List<Roster> result = new ArrayList<>();
        int count = 20;
        for (int i = 0; i < count && index * count + i < rosterList.size(); i++) {
            result.add(rosterList.get(index * count + i));
        }
        return result;
    }

    public List<Roster> getRosterList(String units,String department,String rank,String name,int nlBegin,int nlEnd,String police,String category, final int orderState, final int orderType) {
        if(!(units.equals(this.units) && department.equals(this.department) && rank.equals(this.rank) && name.equals(this.name) &&
                this.nlBegin == nlBegin && this.nlEnd ==nlEnd && police.equals(this.police) && category.equals(this.category))) {

            this.units = units;
            this.department = department;
            this.rank = rank;
            this.name = name;
            this.nlEnd = nlEnd;
            this.nlBegin = nlBegin;
            this.police = police;
            this.category = category;

            CadresApplication application = (CadresApplication) getApplication();
            rosterList = SQLiteHelper.getListFromRoster(application,units , department, rank, name,police,category);
            List<Roster> resultList = new ArrayList<>();
            int size = rosterList.size();
            for(int i = 0;i<size;i++) {
                Roster roster = rosterList.get(i);
                if(roster.getNL()>=nlBegin && roster.getNL()<=nlEnd) {
                    roster.setXh(resultList.size());
                    resultList.add(roster);
                }
            }

            rosterList = resultList;
        }
        Collections.sort(rosterList, new Comparator<Roster>() {
            @Override
            public int compare(Roster lhs, Roster rhs) {
                if (orderState == 0) {
                    return ((Integer) lhs.getXh()).compareTo(rhs.getXh());
                } else if (orderState == 1) {
                    if (orderType == 1) {
                        return lhs.getCsrq().compareTo(rhs.getCsrq());
                    } else if (orderType == 2) {
                        return lhs.getCjgzsj().compareTo(rhs.getCjgzsj());
                    } else if (orderType == 3) {
                        return lhs.getXrzjsj().compareTo(rhs.getXrzjsj());
                    }
                } else if (orderState == 2) {
                    if (orderType == 1) {
                        return (rhs.getCsrq()).compareTo(lhs.getCsrq());
                    } else if (orderType == 2) {
                        return  rhs.getCjgzsj().compareTo(lhs.getCjgzsj());
                    } else if (orderType == 3) {
                        return rhs.getXrzjsj().compareTo(lhs.getXrzjsj());
                    }
                }
                return 0;

            }
        });
        return rosterList;
    }

    @Override
    public void onBackPressed()
    {
        ExitDialog.Builder builder = new ExitDialog.Builder(this);
        builder.setMessage("确定要退出吗？");
        builder.setTitle("提　示");
        builder.setPositiveButton("确　定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                pageIndex=0;
                MainTabActivity.rosterList = null;
                System.exit(0);
            }
        });

        builder.setNegativeButton("取　消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }
    public void onDateImport() {
        CollectFragment collectFragment = (CollectFragment)fragmentList.get(0);
        this.units = "%";
        this.department = "%";
        this.rank = "%";
        this.name = "%";
        nlBegin = 0;
        nlEnd = 999;
        this.police = "%";
        this.category = "%";
        CadresApplication app = (CadresApplication) getApplication();
        rosterList = SQLiteHelper.getListFromRoster(app, units, department, rank, name,police,category);
        collectFragment.onPageRefresh();

    }

}
