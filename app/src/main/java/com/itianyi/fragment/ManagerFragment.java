package com.itianyi.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itianyi.app.CadresApplication;
import com.itianyi.cadres.ImportActivity;
import com.itianyi.cadres.MainTabActivity;
import com.itianyi.cadres.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerFragment extends Fragment {

    private TextView tv_changeTime;
    private int clickButton;

    public static ManagerFragment newInstance() {
        ManagerFragment fragment = new ManagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manager, container, false);
        LinearLayout ll_import = (LinearLayout) rootView.findViewById(R.id.ll_import);
        ll_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImportActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        LinearLayout ll_changeTime = (LinearLayout) rootView.findViewById(R.id.ll_changeTime);
        ll_changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadresApplication cadresApplication = (CadresApplication) getActivity().getApplication();
                // 设置当前日期
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), DateSet, calendar
                        .get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clickButton = DialogInterface.BUTTON_NEGATIVE;
                    }
                });
                datePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "默认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clickButton = DialogInterface.BUTTON_NEUTRAL;

                    }
                });
                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clickButton = DialogInterface.BUTTON_POSITIVE;

                    }
                });
                datePickerDialog.show();

            }
        });
        LinearLayout ll_exit = (LinearLayout) rootView.findViewById(R.id.ll_exit);
        ll_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        tv_changeTime = (TextView) rootView.findViewById(R.id.tv_changeTime);
        CadresApplication cadresApplication = (CadresApplication) getActivity().getApplication();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date refrence_time = cadresApplication.getReference_time();
        if(refrence_time==null) {
            tv_changeTime.setText("修改参考时间 当前时间：默认");
        } else {
            String dstr = sdf.format(refrence_time);
            tv_changeTime.setText("修改参考时间 当前时间：" + dstr);
        }
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            ((MainTabActivity) getActivity()).onDateImport();
        }
    }

    DatePickerDialog.OnDateSetListener DateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            CadresApplication cadresApplication = (CadresApplication) getActivity().getApplication();
            if(clickButton == DialogInterface.BUTTON_NEGATIVE) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
                String dstr = year + "-" + monthOfYear + "-" + dayOfMonth;
                Date date = null;
                try {
                    date = sdf.parse(dstr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                cadresApplication.setReference_time(date);

                tv_changeTime.setText("修改参考时间 当前时间：" + dstr);

                ((MainTabActivity)getActivity()).onDateImport();
            } else if(clickButton == DialogInterface.BUTTON_NEUTRAL) {
                cadresApplication.setReference_time(null);

                tv_changeTime.setText("修改参考时间 当前时间：默认");

                ((MainTabActivity)getActivity()).onDateImport();
            }
            //修改参考时间 当前时间：默认
        }
    };
}
