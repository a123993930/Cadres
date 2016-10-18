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
import com.itianyi.fragment.FindFragment;

import java.util.ArrayList;


public class ZWLBChartActivity extends BaseActivity {
    PieChart mChart_zwlb;
    Typeface tf;
    protected String[] mZWLB = new String[]{
            "领导职务", "非领导职务"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zwlbchart);
        setZWLBChart();
        Intent intent = getIntent();
        int[] values = intent.getIntArrayExtra("com.itianyi.cadres.zwlb");
        setZWLBData(values);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setZWLBChart() {
        mChart_zwlb = (PieChart) findViewById(R.id.chart_zwlb);

        mChart_zwlb.setDescription("");

        mChart_zwlb.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent intent = new Intent(ZWLBChartActivity.this, ChartInfoActivity.class);
                intent.putExtra("com.itianyi.cadres.Chart.Type", FindFragment.ZWLBRequest);
                intent.putExtra("com.itianyi.cadres.Chart.Condition", mZWLB[e.getXIndex()]);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");


        Legend l = mChart_zwlb.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);

    }
    private void setZWLBData(int[] valList) {

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
        if(max > 8) {
            max = 8;
        }

        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(valList[i], i));
        }

        PieDataSet set1 = new PieDataSet(yVals1, "年龄段");
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        set1.setColors(colors);


        PieData data = new PieData(xVals, set1);
        data.setValueFormatter(new FindFragment.MyValueFormatter());
        data.setValueTextSize(16f);
        data.setValueTypeface(tf);

        mChart_zwlb.setData(data);
        mChart_zwlb.animateY(2500);
    }
}
