package com.itianyi.cadres;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.itianyi.fragment.FindFragment;

import java.util.ArrayList;


public class NLChartActivity extends BaseActivity {
    BarChart mChart_nl;
    Typeface tf;
    protected String[] mNL = new String[] {
            "20-25","26-30","31-35","36-39","40-45","46-49","50-55","56及以上"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nlchart);
        setNLChart();
        Intent intent = getIntent();
        int[] values = intent.getIntArrayExtra("com.itianyi.cadres.nl");
        setNLData(values);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setNLChart() {
        mChart_nl = (BarChart) findViewById(R.id.chart_nl);

        mChart_nl.setDrawBarShadow(false);
        mChart_nl.setDrawValueAboveBar(true);

        mChart_nl.setDescription("");

        mChart_nl.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent intent = new Intent(NLChartActivity.this, ChartInfoActivity.class);
                intent.putExtra("com.itianyi.cadres.Chart.Type", FindFragment.NLRequest);
                intent.putExtra("com.itianyi.cadres.Chart.Condition", e.getXIndex()+"");
                intent.putExtra("com.itianyi.cadres.Chart.Other", mNL);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mChart_nl.setMaxVisibleValueCount(100);

        mChart_nl.setPinchZoom(false);

        mChart_nl.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart_nl.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ValueFormatter custom = new FindFragment.MyValueFormatter();

        YAxis leftAxis = mChart_nl.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextSize(16f);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_nl.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setTextSize(16f);
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

    }
    private void setNLData(int[] valList) {

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
        data.setValueFormatter(new FindFragment.MyValueFormatter());
        data.setValueTextSize(16f);
        data.setValueTypeface(tf);

        mChart_nl.setData(data);
        mChart_nl.animateY(2500);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nlchart, menu);
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
