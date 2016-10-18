package com.itianyi.cadres;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.itianyi.fragment.FindFragment;

import java.util.ArrayList;
import java.util.List;


public class MZChartActivity extends BaseActivity {

    protected String[] mMZ = new String[] {
            "汉族", "满族", "回族", "壮族", "其他"
    };
    HorizontalBarChart mChart_mz;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mzchart);
        setMZChart();
        Intent intent = getIntent();
        int[] values = intent.getIntArrayExtra("com.itianyi.cadres.mz");
        setMZData(values);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setMZChart() {
        mChart_mz = (HorizontalBarChart) findViewById(R.id.chart_mz);

        mChart_mz.setDrawBarShadow(false);
        mChart_mz.setDrawValueAboveBar(true);

        mChart_mz.setTouchEnabled(true);
        mChart_mz.setDescription("");

        mChart_mz.setOnChartValueSelectedListener(new OnChartValueSelectedListener(){
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent intent = new Intent(MZChartActivity.this,ChartInfoActivity.class);
                intent.putExtra("com.itianyi.cadres.Chart.Type",FindFragment.MZRequest);
                intent.putExtra("com.itianyi.cadres.Chart.Condition", mMZ[e.getXIndex()]);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });
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
        tf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");

        ValueFormatter custom = new FindFragment.MyValueFormatter();

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
    }
    private void setMZData(int[] valList) {

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
        data.setValueFormatter(new FindFragment.MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);

        mChart_mz.setData(data);

        // undo all highlights
        mChart_mz.highlightValues(null);

        mChart_mz.animateY(2500);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mzchart, menu);
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
