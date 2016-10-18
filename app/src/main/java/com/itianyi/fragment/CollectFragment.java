package com.itianyi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itianyi.adapter.DetailListAdapter;
import com.itianyi.app.CadresApplication;
import com.itianyi.app.SQLiteHelper;
import com.itianyi.bean.Department;
import com.itianyi.bean.Roster;
import com.itianyi.cadres.DetailsActivity;
import com.itianyi.cadres.ImportActivity;
import com.itianyi.cadres.MainTabActivity;
import com.itianyi.cadres.R;
import com.itianyi.cadres.SelectDeptActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 沫 on 2015/7/21.
 */
public class CollectFragment extends Fragment {
    LinearLayout ll_dept;
    EditText et_unit;
    EditText et_department;
    Spinner s_rank;
    EditText et_name;
    TextView tv_noRecord;
    EditText et_nlBegin;
    EditText et_nlEnd;
    Spinner s_police;
    Spinner s_category;

    LinearLayout ll_csrq;
    ImageView iv_csrq;
    LinearLayout ll_cjgzsj;
    ImageView iv_cjgzsj;
    LinearLayout ll_rxzjsj;
    ImageView iv_rxzjsj;
    int pxState = 0;
    int pxType = 0;

    PullToRefreshListView listView;
    List<Roster> rosterList;
    int pageIndex = 0;
    DetailListAdapter adapter;
    String units="%", department="%",rank="%",name="%",police = "%",category = "%";
    int nlBegin = 0,nlEnd = 999;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CollectFragment newInstance() {
        CollectFragment fragment = new CollectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        rosterList = new ArrayList<>();
        adapter = new DetailListAdapter(getActivity(), rosterList);

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_collect, container, false);

        final MainTabActivity activity = (MainTabActivity) getActivity();

        ll_dept = (LinearLayout) rootView.findViewById(R.id.ll_dept);
        ll_dept.setOnClickListener(new View.OnClickListener() {//点击时打开新页面选择部门
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectDeptActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        et_unit = (EditText) rootView.findViewById(R.id.et_unit);
        this.units = MainTabActivity.units;
        if(!units.isEmpty() && units!="%")
            et_unit.setText(MainTabActivity.units);
        et_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectDeptActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        et_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                units = s.toString();
                if (units.equals("全部")) {
                    units = "%";
                }
                onRefresh();
            }
        });

        et_department = (EditText) rootView.findViewById(R.id.et_department);
        this.department = MainTabActivity.department;
        if(!department.isEmpty() && department!="%")
            et_department.setText(department);
        et_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectDeptActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        et_department.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                department = s.toString();
                if (department.equals("河北省公安厅")) {
                    department = "%";
                }
                onRefresh();
            }
        });
        et_name = (EditText) rootView.findViewById(R.id.et_name);
        this.name = MainTabActivity.name;
        if(!name.isEmpty() && name!="%")
            et_name.setText(this.name);
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = et_name.getText().toString();
                onRefresh();
            }
        });
        s_rank = (Spinner) rootView.findViewById(R.id.s_rank);
        final String[] ranks= getActivity().getResources().getStringArray(R.array.rank);
        this.rank = MainTabActivity.rank;
        for(int i = 0;i<ranks.length;i++) {
            if(ranks[i].equals(this.rank)) {
                s_rank.setSelection(i);
                break;
            }
        }
        s_rank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    rank = "";
                } else {
                    rank = ranks[position];
                }
                onRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv_noRecord = (TextView) rootView.findViewById(R.id.tv_noRecord);

        et_nlBegin = (EditText) rootView.findViewById(R.id.et_nlBegin);
        this.nlBegin = MainTabActivity.nlBegin;
        if(nlBegin!=0)
            et_nlBegin.setText(nlBegin + "");
        et_nlBegin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nlStr = et_nlBegin.getText().toString();
                if (nlStr.isEmpty()) {
                    nlBegin = 0;
                } else {
                    nlBegin = Integer.parseInt(nlStr);
                }
                onRefresh();
            }
        });

        et_nlEnd = (EditText) rootView.findViewById(R.id.et_nlEnd);
        this.nlEnd = MainTabActivity.nlEnd;
        if(nlEnd!=999)
            et_nlEnd.setText(nlEnd + "");
        et_nlEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nlStr = et_nlEnd.getText().toString();
                if (nlStr.isEmpty()) {
                    nlEnd = 999;
                } else {
                    nlEnd = Integer.parseInt(nlStr);
                }
                onRefresh();
            }
        });

        s_police = (Spinner) rootView.findViewById(R.id.s_police);
        final String[] polices= getActivity().getResources().getStringArray(R.array.police_rank);
        this.police = MainTabActivity.police;
        for(int i = 0;i<polices.length;i++) {
            if(polices[i].equals(this.police)) {

                s_police.setSelection(i);
                break;
            }
        }
        s_police.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    police="";
                } else {
                    police = polices[position];
                }
                onRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s_category = (Spinner) rootView.findViewById(R.id.s_category);
        final String[] categorys= getActivity().getResources().getStringArray(R.array.category);
        this.category = MainTabActivity.category;
        for(int i = 0;i<categorys.length;i++) {
            if(categorys[i].equals(this.category)) {
                s_category.setSelection(i);
                break;
            }
        }
        s_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    category = "";
                } else {
                    category = categorys[position];
                }
                onRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll_csrq = (LinearLayout) rootView.findViewById(R.id.ll_csrq);
        iv_csrq = (ImageView) rootView.findViewById(R.id.iv_csrq);
        ll_csrq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPX(1);
            }
        });

        ll_cjgzsj = (LinearLayout) rootView.findViewById(R.id.ll_cjgzsj);
        iv_cjgzsj = (ImageView) rootView.findViewById(R.id.iv_cjgzsj);
        ll_cjgzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPX(2);
            }
        });

        ll_rxzjsj = (LinearLayout) rootView.findViewById(R.id.ll_rxzjsj);
        iv_rxzjsj = (ImageView) rootView.findViewById(R.id.iv_rxzjsj);
        ll_rxzjsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPX(3);
            }
        });


        listView = (PullToRefreshListView) rootView.findViewById(R.id.listView);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ListView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
                pageIndex++;
                rosterList.addAll(activity.getRosterList(units, department, rank, name, nlBegin, nlEnd, police, category, pxState, pxType, pageIndex));
                adapter.setRosterList(rosterList);
                adapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                Roster roster = rosterList.get(position - 1);
                intent.putExtra("com.itianyi.cadres.fragment.name", roster.getXm().replace(" ", ""));
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
        return rootView;
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
        onRefresh();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == -1) {
                Bundle bundle = data.getExtras();
                String unitStr = bundle.getString("com.itianyi.cadres.selectdept.unit");
                String departmentStr = bundle.getString("com.itianyi.cadres.selectdept.department");

                et_unit.setText(unitStr);
                et_department.setText(departmentStr);
            }
    }

    private void onRefresh() {
        MainTabActivity activity = (MainTabActivity) getActivity();
        pageIndex = 0;
        if(units.isEmpty()) {
            units="%";
        }
        if(department.isEmpty()) {
            department="%";
        }
        if(rank.isEmpty()) {
            rank="%";
        }
        if(name.isEmpty()) {
            name="%";
        }
        if(police.isEmpty()) {
            police = "%";
        }
        if(category.isEmpty()) {
            category = "%";
        }
        rosterList = activity.getRosterList(units,department, rank, name,nlBegin,nlEnd, police,category,pxState,pxType,pageIndex);
        if(rosterList.size()==0) {
            listView.setVisibility(View.GONE);
            tv_noRecord.setVisibility(View.VISIBLE);
        } else {
            adapter.setRosterList(rosterList);
            adapter.notifyDataSetInvalidated();

            listView.setVisibility(View.VISIBLE);
            tv_noRecord.setVisibility(View.GONE);
        }
    }

    public void onPageRefresh () {
        department="%";
        et_unit.setText("");
        et_department.setText("");
        rank="%";
        s_rank.setSelection(0);
        name="%";
        et_name.setText("");
        police = "%";
        s_police.setSelection(0);
        category = "%";
        s_category.setSelection(0);
        nlBegin = 0;
        et_nlBegin.setText("");
        nlEnd = 999;
        et_nlEnd.setText("");
    }

    public void onOtherCondition(int request,String name) {
        switch (request) {
            case FindFragment.MZRequest:
                List<Roster> mList = new ArrayList<>();
                for(Roster roster :MainTabActivity.rosterList) {
                    if(roster.getMz().equals(name)) {
                        mList.add(roster);
                    }
                }
                adapter.setRosterList(mList);
                adapter.notifyDataSetInvalidated();
                break;
        }
    }
}


