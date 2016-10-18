package com.itianyi.cadres;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
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


public class XLChartActivity extends BaseActivity {

    HorizontalBarChart mChart_xl;
    Typeface tf;
    protected String[] mXL = new String[] {
            "研究生及以上","大本","大专","高中","初中及以下"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xlchart);
        setXLChart();
        Intent intent = getIntent();
        int[] values = intent.getIntArrayExtra("com.itianyi.cadres.xl");
        setXLData(values);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setXLChart() {
        mChart_xl = (HorizontalBarChart) findViewById(R.id.chart_xl);

        mChart_xl.setDrawBarShadow(false);
        mChart_xl.setDrawValueAboveBar(true);

        mChart_xl.setDescription("");

        mChart_xl.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent intent = new Intent(XLChartActivity.this, ChartInfoActivity.class);
                intent.putExtra("com.itianyi.cadres.Chart.Type", FindFragment.XLRequest);
                intent.putExtra("com.itianyi.cadres.Chart.Condition", e.getXIndex()+"");
                intent.putExtra("com.itianyi.cadres.Chart.Other", mXL);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mChart_xl.setMaxVisibleValueCount(100);

        mChart_xl.setPinchZoom(false);

        mChart_xl.setDrawGridBackground(false);
        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        ValueFormatter custom = new FindFragment.MyValueFormatter();

        XAxis xAxis = mChart_xl.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(16f);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);


        YAxis leftAxis = mChart_xl.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextSize(16f);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart_xl.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tf);
        rightAxis.setTextSize(16f);
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

    }

    private void setXLData(int[] valList) {

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
        data.setValueFormatter(new FindFragment.MyValueFormatter());
        data.setValueTextSize(16f);
        data.setValueTypeface(tf);

        mChart_xl.setData(data);
        mChart_xl.animateY(2500);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xlchart, menu);
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
