package com.itianyi.cadres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itianyi.adapter.DetailListAdapter;
import com.itianyi.bean.Roster;
import com.itianyi.fragment.FindFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChartInfoActivity extends BaseActivity {

    TextView tv_title;

    LinearLayout ll_csrq;
    ImageView iv_csrq;
    LinearLayout ll_cjgzsj;
    ImageView iv_cjgzsj;
    LinearLayout ll_rxzjsj;
    ImageView iv_rxzjsj;
    int pxState = 0;
    int pxType = 0;

    private List<Roster> rosterList;
    private String Condition;

    DetailListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_info);

        rosterList = new ArrayList<>();
        setInfo();
        getData();

        adapter = new DetailListAdapter(this,rosterList);

        PullToRefreshListView listView = (PullToRefreshListView) findViewById(R.id.listView);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ListView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChartInfoActivity.this, DetailsActivity.class);
                Roster roster = rosterList.get(position - 1);
                intent.putExtra("com.itianyi.cadres.fragment.name", roster.getXm().replace(" ", ""));
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_csrq = (LinearLayout) findViewById(R.id.ll_csrq);
        iv_csrq = (ImageView) findViewById(R.id.iv_csrq);
        ll_csrq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPX(1);
            }
        });

        ll_cjgzsj = (LinearLayout) findViewById(R.id.ll_cjgzsj);
        iv_cjgzsj = (ImageView) findViewById(R.id.iv_cjgzsj);
        ll_cjgzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPX(2);
            }
        });

        ll_rxzjsj = (LinearLayout) findViewById(R.id.ll_rxzjsj);
        iv_rxzjsj = (ImageView) findViewById(R.id.iv_rxzjsj);
        ll_rxzjsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPX(3);
            }
        });
    }

    private void setPX(int mPxType) {
        if(pxType!= mPxType) {
            pxType = mPxType;
            pxState = 1;
        } else {
            pxState++;
            if (pxState > 2) {
                pxState = 0;
            }
        }
        iv_csrq.setImageResource(R.drawable.meixu);
        iv_cjgzsj.setImageResource(R.drawable.meixu);
        iv_rxzjsj.setImageResource(R.drawable.meixu);

        ImageView iv = null;
        if(pxType == 1) {
            iv = iv_csrq;
        } else if(pxType == 2) {
            iv = iv_cjgzsj;
        } else if(pxType == 3) {
            iv = iv_rxzjsj;
        }
        if (pxState == 0) {
            iv.setImageResource(R.drawable.meixu);
        } else if (pxState == 1) {
            iv.setImageResource(R.drawable.zhengxu);
        } else if (pxState == 2) {
            iv.setImageResource(R.drawable.daoxu);
        }
        reSorting();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        int Type = bundle.getInt("com.itianyi.cadres.Chart.Type");
        Condition = bundle.getString("com.itianyi.cadres.Chart.Condition");

        switch (Type) {
            case FindFragment.MZRequest:
                tv_title.setText(Condition);
                for (Roster roster : MainTabActivity.rosterList) {
                    if (roster.getMz().equals(Condition)) {
                        rosterList.add(roster);
                    }
                }
                break;
            case FindFragment.ZJRequest:
                tv_title.setText(Condition);
                for (Roster roster : MainTabActivity.rosterList) {
                    if (roster.getXzj().equals(Condition)) {
                        rosterList.add(roster);
                    }
                }
                break;
            case FindFragment.XLRequest:
                String[] array = bundle.getStringArray("com.itianyi.cadres.Chart.Other");
                int index = Integer.parseInt(Condition);
                tv_title.setText(array[index]);
                for (Roster roster : MainTabActivity.rosterList) {

                    String XL = roster.getXl();
                    switch (index) {
                        case 0:
                            if (XL.contains("研究生") || XL.contains("博士")) {
                                rosterList.add(roster);
                            }
                            break;
                        case 1:
                            if (XL.contains("大学")) {
                                rosterList.add(roster);
                            }
                            break;
                        case 2:
                            if (XL.contains("大专")) {
                                rosterList.add(roster);
                            }
                            break;
                        case 3:
                            if (XL.contains("高中") || XL.contains("中专")) {
                                rosterList.add(roster);
                            }
                            break;
                        case 4:
                            if (!(XL.contains("研究生") || XL.contains("博士") || XL.contains("大学") || XL.contains("大专") || XL.contains("高中") || XL.contains("中专"))) {
                                rosterList.add(roster);
                            }
                            break;
                    }

                }
                break;
            case FindFragment.NLRequest:

                array = bundle.getStringArray("com.itianyi.cadres.Chart.Other");
                index = Integer.parseInt(Condition);
                tv_title.setText(array[index]);
                for (Roster roster : MainTabActivity.rosterList) {
                    int nl = roster.getNL();
                    switch (index) {
                        case 0:
                            if (nl >= 20 && nl <= 25) {
                                rosterList.add(roster);
                            }
                            break;
                        case 1:
                            if (nl >= 26 && nl <= 30) {
                                rosterList.add(roster);
                            }
                            break;
                        case 2:
                            if (nl >= 31 && nl <= 35) {
                                rosterList.add(roster);
                            }
                            break;
                        case 3:
                            if (nl >= 36 && nl <= 39) {
                                rosterList.add(roster);
                            }
                            break;
                        case 4:
                            if (nl >= 40 && nl <= 45) {
                                rosterList.add(roster);
                            }
                            break;
                        case 5:
                            if (nl >= 46 && nl <= 49) {
                                rosterList.add(roster);
                            }
                            break;
                        case 6:
                            if (nl >= 50 && nl <= 55) {
                                rosterList.add(roster);
                            }
                            break;
                        case 7:
                            if (nl >= 56) {
                                rosterList.add(roster);
                            }
                            break;
                    }
                }
                break;
            case FindFragment.JXRequest:
                tv_title.setText(Condition);
                for (Roster roster : MainTabActivity.rosterList) {
                    if (roster.getXjx().equals(Condition)) {
                        rosterList.add(roster);
                    }
                }
                break;
            case FindFragment.ZWLBRequest:
                tv_title.setText(Condition);
                for (Roster roster : MainTabActivity.rosterList) {
                    if (roster.getZwlb().equals(Condition)) {
                        rosterList.add(roster);
                    }
                }
                break;
            case FindFragment.XBRequest:
                tv_title.setText(Condition);
                for (Roster roster : MainTabActivity.rosterList) {
                    if (roster.getXb().contains(Condition)) {
                        rosterList.add(roster);
                    }
                }
                break;
            case FindFragment.RZSJRequest:

                array = bundle.getStringArray("com.itianyi.cadres.Chart.Other");
                index = Integer.parseInt(Condition);
                tv_title.setText(array[index]);
                for (Roster roster : MainTabActivity.rosterList) {
                    int gl = roster.getZL();
                    switch (index) {
                        case 0:
                            if (gl <= 3) {
                                rosterList.add(roster);
                            }
                            break;
                        case 1:
                            if (gl >= 4 && gl <= 6) {
                                rosterList.add(roster);
                            }
                            break;
                        case 2:
                            if (gl >= 7 && gl <= 10) {
                                rosterList.add(roster);
                            }
                            break;
                        case 3:
                            if (gl >= 11) {
                                rosterList.add(roster);
                            }
                            break;
                    }
                }
                break;
        }

    }

    private void reSorting() {
        Collections.sort(rosterList, new Comparator<Roster>() {
            @Override
            public int compare(Roster lhs, Roster rhs) {
                if (pxState == 0) {
                    return ((Integer) lhs.getXh()).compareTo(rhs.getXh());
                } else if (pxState == 1) {
                    if (pxType == 1) {
                        return lhs.getCsrq().compareTo(rhs.getCsrq());
                    } else if (pxType == 2) {
                        return lhs.getCjgzsj().compareTo(rhs.getCjgzsj());
                    } else if (pxType == 3) {
                        return lhs.getXrzjsj().compareTo(rhs.getXrzjsj());
                    }
                } else if (pxState == 2) {
                    if (pxType == 1) {
                        return (rhs.getCsrq()).compareTo(lhs.getCsrq());
                    } else if (pxType == 2) {
                        return  rhs.getCjgzsj().compareTo(lhs.getCjgzsj());
                    } else if (pxType == 3) {
                        return rhs.getXrzjsj().compareTo(lhs.getXrzjsj());
                    }
                }
                return 0;
            }
        });

        adapter.setRosterList(rosterList);
        adapter.notifyDataSetInvalidated();
    }

    private void setInfo() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(Condition);
    }
}
