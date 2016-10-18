package com.itianyi.cadres;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.itianyi.fragment.FindFragment;

import java.util.ArrayList;


public class RZSJChartActivity extends BaseActivity {

    private BarChart mChart_rzsj;
    private Typeface tf;
    protected String[] mRZSJ = new String[] {
            "3年及以下","4-6年","7-10年","11年及以上"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rzsjchart);
        setRZSJChart();
        Intent intent = getIntent();
        int[] values = intent.getIntArrayExtra("com.itianyi.cadres.rzsj");
        setRZSJData(values);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setRZSJChart() {
        mChart_rzsj = (BarChart) findViewById(R.id.chart_rzsj);
        mChart_rzsj.setFocusable(false);
        mChart_rzsj.setDrawBarShadow(false);
        mChart_rzsj.setDrawValueAboveBar(true);

        mChart_rzsj.setDescription("");

        mChart_rzsj.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent intent = new Intent(RZSJChartActivity.this, ChartInfoActivity.class);
                intent.putExtra("com.itianyi.cadres.Chart.Type", FindFragment.RZSJRequest);
                intent.putExtra("com.itianyi.cadres.Chart.Condition", e.getXIndex()+"");
                intent.putExtra("com.itianyi.cadres.Chart.Other", mRZSJ);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });

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

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart_rzsj.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ValueFormatter custom = new FindFragment.MyValueFormatter();

        YAxis leftAxis = mChart_rzsj.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextSize(16f);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_rzsj.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setTextSize(16f);
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

    }
    private void setRZSJData(int[] valList) {
        ArrayList<String> xVals = new ArrayList<>();
        int count = valList.length;
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

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(valList[i], i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "任职级时间");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(new FindFragment.MyValueFormatter());
        data.setValueTextSize(16f);
        data.setValueTypeface(tf);

        mChart_rzsj.setData(data);
        mChart_rzsj.animateY(2500);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zjchart, menu);
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
