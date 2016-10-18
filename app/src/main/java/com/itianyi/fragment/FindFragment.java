package com.itianyi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.itianyi.bean.Roster;
import com.itianyi.cadres.JXChartActivity;
import com.itianyi.cadres.MZChartActivity;
import com.itianyi.cadres.MainTabActivity;
import com.itianyi.cadres.NLChartActivity;
import com.itianyi.cadres.R;
import com.itianyi.cadres.RZSJChartActivity;
import com.itianyi.cadres.XBChartActivity;
import com.itianyi.cadres.XLChartActivity;
import com.itianyi.cadres.ZJChartActivity;
import com.itianyi.cadres.ZWLBChartActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class FindFragment extends Fragment {

    private HorizontalBarChart mChart_mz;
    private BarChart mChart_zj;
    private HorizontalBarChart mChart_xl;
    private BarChart mChart_nl;
    private BarChart mChart_jx;
    private PieChart mChart_zwlb;
    private PieChart mChart_xb;
    private BarChart mChart_rzsj;
    private LinearLayout ll_mz;
    private LinearLayout ll_zj;
    private LinearLayout ll_xl;
    private LinearLayout ll_nl;
    private LinearLayout ll_jx;
    private LinearLayout ll_zwlb;
    private LinearLayout ll_xb;
    private LinearLayout ll_rzsj;
    private MainTabActivity activity;

    private Typeface tf;

    protected String[] mMZ = new String[] {
            "汉族", "满族", "回族", "壮族", "其他"
    };
    protected String[] mZJ = new String[] {
            "科员级", "副科级", "正科级", "副处级", "正处级", "副厅级", "正厅级"
    };
    protected String[] mXL = new String[] {
            "研究生及以上","大本","大专","高中","初中及以下"
    };
    protected String[] mNL = new String[] {
            "20-25","26-30","31-35","36-39","40-45","46-49","50-55","56及以上"
    };
    protected String[] mJX = new String[]{
            "副总警监", "一级警监", "二级警监", "三级警监", "一级警督", "二级警督", "三级警督",
            "专业技术一级警监", "专业技术二级警监", "专业技术三级警监", "专业技术一级警督", "专业技术二级警督", "专业技术三级警督"
    };
    protected String[] mZWLB = new String[]{
            "领导职务", "非领导职务"
    };
    protected String[] mXB = new String[]{
            "男", "女"
    };
    protected String[] mRZSJ = new String[] {
            "3年及以下","4-6年","7-10年","11年及以上"
    };
    public final static int MZRequest = 1;
    public final static int ZJRequest = 2;
    public final static int XLRequest = 3;
    public final static int NLRequest = 4;
    public final static int JXRequest = 5;
    public final static int ZWLBRequest = 6;
    public final static int XBRequest = 7;
    public final static int RZSJRequest = 8;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FindFragment.
     */
    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainTabActivity) activity;

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_find, container, false);
        setMZChart(rootView);
        setZJChart(rootView);
        setXLChart(rootView);
        setNLChart(rootView);
        setJXChart(rootView);
        setZWLBChart(rootView);
        setXBChart(rootView);
        setRZSJChart(rootView);

        ll_mz = (LinearLayout) rootView.findViewById(R.id.ll_mz);
        ll_zj = (LinearLayout) rootView.findViewById(R.id.ll_zj);
        ll_xl = (LinearLayout) rootView.findViewById(R.id.ll_xl);
        ll_nl = (LinearLayout) rootView.findViewById(R.id.ll_nl);
        ll_jx = (LinearLayout) rootView.findViewById(R.id.ll_jx);
        ll_zwlb = (LinearLayout) rootView.findViewById(R.id.ll_zwlb);
        ll_xb = (LinearLayout) rootView.findViewById(R.id.ll_xb);
        ll_rzsj = (LinearLayout) rootView.findViewById(R.id.ll_rzsj);
        if(MainTabActivity.rosterList!=null) {
            try {
                setData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    private void setMZChart(View rootView) {
        mChart_mz = (HorizontalBarChart) rootView.findViewById(R.id.chart_mz);

        mChart_mz.setDrawBarShadow(false);
        mChart_mz.setDrawValueAboveBar(true);

        mChart_mz.setTouchEnabled(false);
        mChart_mz.setDescription("");

        // if more than 100 entries are displayed in the chart, no values will be
        // drawn
        mChart_mz.setMaxVisibleValueCount(100);

        // scaling can now only be done on x- and y-axis separately
        mChart_mz.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart_mz.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);
        tf = Typeface.createFromAsset(this.activity.getAssets(), "OpenSans-Regular.ttf");

        ValueFormatter custom = new MyValueFormatter();

        XAxis xAxis = mChart_mz.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);


        YAxis leftAxis = mChart_mz.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_mz.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setLabelCount(8);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        Legend l = mChart_mz.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");
        List<BarEntry> yVals1 = new ArrayList<>();
        yVals1.add(new BarEntry(0,0));
        BarDataSet dataSet = new BarDataSet(yVals1, "");
        BarData data = new BarData(xVals, dataSet);
        mChart_mz.setData(data);
        mChart_mz.invalidate();
    }
    private void setZJChart(View rootView) {
        mChart_zj = (BarChart) rootView.findViewById(R.id.chart_zj);
        mChart_zj.setDrawBarShadow(false);
        mChart_zj.setDrawValueAboveBar(true);

        mChart_zj.setFocusable(false);
        mChart_zj.setClickable(false);
        mChart_zj.setTouchEnabled(false);
        mChart_zj.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart_zj.setMaxVisibleValueCount(100);

        // scaling can now only be done on x- and y-axis separately
        mChart_zj.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart_zj.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        tf = Typeface.createFromAsset(this.activity.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart_zj.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ValueFormatter custom = new MyValueFormatter();

        YAxis leftAxis = mChart_zj.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_zj.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setLabelCount(8);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        Legend l = mChart_zj.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");
        List<BarEntry> yVals1 = new ArrayList<>();
        yVals1.add(new BarEntry(0,0));
        BarDataSet dataSet = new BarDataSet(yVals1, "");
        BarData data = new BarData(xVals, dataSet);
        mChart_zj.setData(data);
        mChart_zj.invalidate();
    }
    private void setXLChart(View rootView) {
        mChart_xl = (HorizontalBarChart) rootView.findViewById(R.id.chart_xl);

        mChart_xl.setDrawBarShadow(false);
        mChart_xl.setDrawValueAboveBar(true);

        mChart_xl.setTouchEnabled(false);
        mChart_xl.setDescription("");

        // if more than 100 entries are displayed in the chart, no values will be
        // drawn
        mChart_xl.setMaxVisibleValueCount(100);

        // scaling can now only be done on x- and y-axis separately
        mChart_xl.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart_xl.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);
        tf = Typeface.createFromAsset(this.activity.getAssets(), "OpenSans-Regular.ttf");

        ValueFormatter custom = new MyValueFormatter();

        XAxis xAxis = mChart_xl.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);


        YAxis leftAxis = mChart_xl.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_xl.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setLabelCount(8);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        Legend l = mChart_xl.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");
        List<BarEntry> yVals1 = new ArrayList<>();
        yVals1.add(new BarEntry(0,0));
        BarDataSet dataSet = new BarDataSet(yVals1, "");
        BarData data = new BarData(xVals, dataSet);
        mChart_xl.setData(data);
        mChart_xl.invalidate();
    }
    private void setNLChart(View rootView) {
        mChart_nl = (BarChart) rootView.findViewById(R.id.chart_nl);

        mChart_nl.setDrawBarShadow(false);
        mChart_nl.setDrawValueAboveBar(true);
        mChart_nl.setTouchEnabled(false);
        mChart_nl.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart_nl.setMaxVisibleValueCount(100);

        // scaling can now only be done on x- and y-axis separately
        mChart_nl.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart_nl.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        tf = Typeface.createFromAsset(this.activity.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart_nl.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ValueFormatter custom = new MyValueFormatter();

        YAxis leftAxis = mChart_nl.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_nl.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setLabelCount(8);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);


        Legend l = mChart_nl.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");
        List<BarEntry> yVals1 = new ArrayList<>();
        yVals1.add(new BarEntry(0,0));
        BarDataSet dataSet = new BarDataSet(yVals1, "");
        BarData data = new BarData(xVals, dataSet);
        mChart_nl.setData(data);
        mChart_nl.invalidate();
    }
    private void setJXChart(View rootView) {
        mChart_jx = (BarChart) rootView.findViewById(R.id.chart_jx);

        mChart_jx.setDrawBarShadow(false);
        mChart_jx.setDrawValueAboveBar(true);
        mChart_jx.setTouchEnabled(false);
        mChart_jx.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart_jx.setMaxVisibleValueCount(100);

        // scaling can now only be done on x- and y-axis separately
        mChart_jx.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart_jx.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        tf = Typeface.createFromAsset(this.activity.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart_jx.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ValueFormatter custom = new MyValueFormatter();

        YAxis leftAxis = mChart_jx.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_jx.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setLabelCount(8);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);


        Legend l = mChart_jx.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");
        List<BarEntry> yVals1 = new ArrayList<>();
        yVals1.add(new BarEntry(0,0));
        BarDataSet dataSet = new BarDataSet(yVals1, "");
        BarData data = new BarData(xVals, dataSet);
        mChart_jx.setData(data);
        mChart_jx.invalidate();
    }
    private void setZWLBChart(View rootView) {
        mChart_zwlb = (PieChart) rootView.findViewById(R.id.chart_zwlb);

        mChart_zwlb.setTouchEnabled(false);
        mChart_zwlb.setDescription("");

        tf = Typeface.createFromAsset(this.activity.getAssets(), "OpenSans-Regular.ttf");

        Legend l = mChart_zwlb.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");
        List<Entry> yVals1 = new ArrayList<>();
        yVals1.add(new Entry(0,0));
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        PieData data = new PieData(xVals, dataSet);
        mChart_zwlb.setData(data);
        mChart_zwlb.invalidate();
    }
    private void setXBChart(View rootView) {
        mChart_xb = (PieChart) rootView.findViewById(R.id.chart_xb);

        mChart_xb.setTouchEnabled(false);
        mChart_xb.setDescription("");

        tf = Typeface.createFromAsset(this.activity.getAssets(), "OpenSans-Regular.ttf");

        Legend l = mChart_xb.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");
        List<Entry> yVals1 = new ArrayList<>();
        yVals1.add(new Entry(0,0));
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        PieData data = new PieData(xVals, dataSet);
        mChart_xb.setData(data);
        mChart_xb.invalidate();
    }

    private void setRZSJChart(View rootView) {
        mChart_rzsj = (BarChart) rootView.findViewById(R.id.chart_rzsj);
        mChart_rzsj.setDrawBarShadow(false);
        mChart_rzsj.setDrawValueAboveBar(true);

        mChart_rzsj.setFocusable(false);
        mChart_rzsj.setClickable(false);
        mChart_rzsj.setTouchEnabled(false);
        mChart_rzsj.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart_rzsj.setMaxVisibleValueCount(100);

        // scaling can now only be done on x- and y-axis separately
        mChart_rzsj.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart_rzsj.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        tf = Typeface.createFromAsset(this.activity.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart_rzsj.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ValueFormatter custom = new MyValueFormatter();

        YAxis leftAxis = mChart_rzsj.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_rzsj.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setLabelCount(8);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        Legend l = mChart_rzsj.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");
        List<BarEntry> yVals1 = new ArrayList<>();
        yVals1.add(new BarEntry(0,0));
        BarDataSet dataSet = new BarDataSet(yVals1, "");
        BarData data = new BarData(xVals, dataSet);
        mChart_rzsj.setData(data);
        mChart_rzsj.invalidate();
    }

    public void setData() throws ParseException {
        int[] MZCount = new int[mMZ.length];
        int[] ZJCount = new int[mZJ.length];
        int[] XLCount = new int[mXL.length];
        int[] NLCount = new int[mNL.length];
        int[] JXCount = new int[mJX.length];
        int[] ZWLBCount = new int[mZWLB.length];
        int[] XBCount = new int[mXB.length];
        int[] RZSJCount = new int[mRZSJ.length];
        List<Roster> rosterList;
        rosterList = MainTabActivity.rosterList;
        for(int i=0;i<rosterList.size();i++) {
            Roster roster = rosterList.get(i);
            for (int j = 0; j < mMZ.length; j++) {
                if (j == mMZ.length - 1 || roster.getMz().equals(mMZ[j])) {
                    MZCount[j]++;
                    break;
                }
            }
            for (int j = 0; j < mZJ.length; j++) {
                if (roster.getXzj().contains(mZJ[j])) {
                    ZJCount[j]++;
                    break;
                }
            }
            String XL = roster.getXl();
            if (XL.contains("研究生") || XL.contains("博士")) {
                XLCount[0]++;
            } else if (XL.contains("大学")) {
                XLCount[1]++;
            } else if (XL.contains("大专")) {
                XLCount[2]++;
            } else if (XL.contains("高中") || XL.contains("中专")) {
                XLCount[3]++;
            } else {
                XLCount[4]++;
            }
            int nl = roster.getNL();
            if (nl >= 20 && nl <= 25) {
                NLCount[0]++;
            } else if(nl >= 26 && nl <= 30) {
                NLCount[1]++;
            } else if(nl >=31 && nl <=35) {
                NLCount[2]++;
            } else if(nl >=36 && nl <=39) {
                NLCount[3]++;
            } else if(nl >=40 && nl <=45) {
                NLCount[4]++;
            } else if(nl >=46 && nl <=49) {
                NLCount[5]++;
            } else if(nl >=50 && nl <=55) {
                NLCount[6]++;
            } else if(nl >=56) {
                NLCount[7]++;
            }

            for (int j = 0; j < mJX.length; j++) {
                if (roster.getXjx().equals(mJX[j])) {
                    JXCount[j]++;
                    break;
                }
            }
            for (int j = 0; j < mZWLB.length; j++) {
                if (roster.getZwlb().equals(mZWLB[j])) {
                    ZWLBCount[j]++;
                    break;
                }
            }
            for (int j = 0; j < mXB.length; j++) {
                if (roster.getXb().trim().equals(mXB[j])) {
                    XBCount[j]++;
                    break;
                }
            }
            int rzsj = roster.getZL();
            if (rzsj <= 3) {
                RZSJCount[0]++;
            } else if(rzsj >= 4 && rzsj <= 6) {
                RZSJCount[1]++;
            } else if(rzsj >=7 && rzsj <=10) {
                RZSJCount[2]++;
            } else if(rzsj >=11) {
                RZSJCount[3]++;
            }
        }

        setMZData(MZCount);
        setZJData(ZJCount);
        setXLData(XLCount);
        setNLData(NLCount);
        setJXData(JXCount);
        setZWLBData(ZWLBCount);
        setXBData(XBCount);
        setRZSJData(RZSJCount);
    }
    private void setMZData(final int[] valList) {

        ll_mz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MZChartActivity.class);
                intent.putExtra("com.itianyi.cadres.mz",valList);
                startActivity(intent);
            }
        });
        int count = mMZ.length;
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < count; i++)
            xVals.add(mMZ[i]);

        int max = 0;
        for(int i = 0;i<valList.length;i++) {
            if(max<valList[i]){
                max = valList[i];
            }
        }
        if(max > 8) {
            max = 8;
        }
        YAxis leftAxis = mChart_mz.getAxisLeft();
        leftAxis.setLabelCount(max);

        YAxis rightAxis = mChart_mz.getAxisRight();
        rightAxis.setLabelCount(max);

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(valList[i], yVals1.size()));
//            if(valList[i]!=0)
//                yVals1.add(new Entry(valList[i], yVals1.size()));
//            else
//                xVals.remove(yVals1.size());
        }
        if(yVals1.isEmpty()) {
            xVals.add("");
            yVals1.add(new BarEntry(0, 0));
        }
        BarDataSet dataSet = new BarDataSet(yVals1, "民族");
        dataSet.setBarSpacePercent(35f);
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        BarData data = new BarData(xVals, dataSet);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);

        mChart_mz.setData(data);

        // undo all highlights
        mChart_mz.highlightValues(null);

        mChart_mz.animateY(2500);
    }
    private void setZJData(final int[] valList) {
        ll_zj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ZJChartActivity.class);
                intent.putExtra("com.itianyi.cadres.zj",valList);
                startActivity(intent);
            }
        });
        ArrayList<String> xVals = new ArrayList<>();
        int count = mZJ.length;
        for (int i = 0; i < count; i++) {
            xVals.add(mZJ[i]);
        }

        int max = 0;
        for(int i = 0;i<valList.length;i++) {
            if(max<valList[i]){
                max = valList[i];
            }
        }
        if(max > 8) {
            max = 8;
        }
        YAxis leftAxis = mChart_zj.getAxisLeft();
        leftAxis.setLabelCount(max);

        YAxis rightAxis = mChart_zj.getAxisRight();
        rightAxis.setLabelCount(max);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(valList[i], i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "职级");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTypeface(tf);

        mChart_zj.setData(data);
        mChart_zj.animateY(2500);
    }
    private void setXLData(final int[] valList) {
        ll_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, XLChartActivity.class);
                intent.putExtra("com.itianyi.cadres.xl",valList);
                startActivity(intent);
            }
        });
        ArrayList<String> xVals = new ArrayList<>();
        int count = mXL.length;
        for (int i = 0; i < count; i++) {
            xVals.add(mXL[i]);
        }

        int max = 0;
        for(int i = 0;i<valList.length;i++) {
            if(max<valList[i]){
                max = valList[i];
            }
        }
        if(max > 8) {
            max = 8;
        }
        YAxis leftAxis = mChart_xl.getAxisLeft();
        leftAxis.setLabelCount(max);

        YAxis rightAxis = mChart_xl.getAxisRight();
        rightAxis.setLabelCount(max);

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(valList[i], i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "学历");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTypeface(tf);

        mChart_xl.setData(data);
        mChart_xl.animateY(2500);
    }

    private void setNLData(final int[] valList) {
        ll_nl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NLChartActivity.class);
                intent.putExtra("com.itianyi.cadres.nl",valList);
                startActivity(intent);
            }
        });
        ArrayList<String> xVals = new ArrayList<>();
        int count = mNL.length;
        for (int i = 0; i < count; i++) {
            xVals.add(mNL[i]);
        }

        int max = 0;
        for(int i = 0;i<valList.length;i++) {
            if(max<valList[i]){
                max = valList[i];
            }
        }
        if(max > 8) {
            max = 8;
        }
        YAxis leftAxis = mChart_nl.getAxisLeft();
        leftAxis.setLabelCount(max);

        YAxis rightAxis = mChart_nl.getAxisRight();
        rightAxis.setLabelCount(max);

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(valList[i], i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "年龄段");
        set1.setBarSpacePercent(35f);
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        set1.setColors(colors);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTypeface(tf);

        mChart_nl.setData(data);
        mChart_nl.animateY(2500);
    }

    private void setJXData(final int[] valList) {
        ll_jx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, JXChartActivity.class);
                intent.putExtra("com.itianyi.cadres.jx",valList);
                startActivity(intent);
            }
        });
        ArrayList<String> xVals = new ArrayList<>();
        int count = mJX.length;
        for (int i = 0; i < count; i++) {
            xVals.add(mJX[i]);
        }

        int max = 0;
        for(int i = 0;i<valList.length;i++) {
            if(max<valList[i]){
                max = valList[i];
            }
        }
        if(max > 8) {
            max = 8;
        }
        YAxis leftAxis = mChart_jx.getAxisLeft();
        leftAxis.setLabelCount(max);

        YAxis rightAxis = mChart_jx.getAxisRight();
        rightAxis.setLabelCount(max);

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(valList[i], i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "警衔");
        set1.setBarSpacePercent(35f);
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        set1.setColors(colors);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTypeface(tf);

        mChart_jx.setData(data);
        mChart_jx.animateY(2500);
    }
    private void setZWLBData(final int[] valList) {
        ll_zwlb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ZWLBChartActivity.class);
                intent.putExtra("com.itianyi.cadres.zwlb",valList);
                startActivity(intent);
            }
        });
        ArrayList<String> xVals = new ArrayList<>();
        int count = mZWLB.length;
        for (int i = 0; i < count; i++) {
            xVals.add(mZWLB[i]);
        }

        int max = 0;
        for(int i = 0;i<valList.length;i++) {
            if(max<valList[i]){
                max = valList[i];
            }
        }

        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(valList[i], i));
        }

        PieDataSet set1 = new PieDataSet(yVals1, "职务类别");
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTypeface(tf);

        mChart_zwlb.setData(data);
        mChart_zwlb.animateY(2500);
    }
    private void setXBData(final int[] valList) {
        ll_xb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, XBChartActivity.class);
                intent.putExtra("com.itianyi.cadres.xb",valList);
                startActivity(intent);
            }
        });
        ArrayList<String> xVals = new ArrayList<>();
        int count = mXB.length;
        for (int i = 0; i < count; i++) {
            xVals.add(mXB[i]);
        }

        int max = 0;
        for(int i = 0;i<valList.length;i++) {
            if(max<valList[i]){
                max = valList[i];
            }
        }

        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(valList[i], i));
        }

        PieDataSet set1 = new PieDataSet(yVals1, "性别");
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTypeface(tf);

        mChart_xb.setData(data);
        mChart_xb.animateY(2500);
    }

    private void setRZSJData(final int[] valList) {
        ll_rzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RZSJChartActivity.class);
                intent.putExtra("com.itianyi.cadres.rzsj",valList);
                startActivity(intent);
            }
        });
        ArrayList<String> xVals = new ArrayList<>();
        int count = mRZSJ.length;
        for (int i = 0; i < count; i++) {
            xVals.add(mRZSJ[i]);
        }

        int max = 0;
        for(int i = 0;i<valList.length;i++) {
            if(max<valList[i]){
                max = valList[i];
            }
        }
        if(max > 8) {
            max = 8;
        }
        YAxis leftAxis = mChart_rzsj.getAxisLeft();
        leftAxis.setLabelCount(max);

        YAxis rightAxis = mChart_rzsj.getAxisRight();
        rightAxis.setLabelCount(max);

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(valList[i], i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "任现职级时间");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTypeface(tf);

        mChart_rzsj.setData(data);
        mChart_rzsj.animateY(2500);
    }

    public static class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,##0");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value);
        }

    }
}

