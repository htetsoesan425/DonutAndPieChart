package com.example.myapplication.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DecimalRemover;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    PieChart mChart;
    Button button;

    private final int[] yValues = {100, 40};
    private final String[] xValues = {"Men", "Women"};

    public static final int[] MY_COLORS = {
            Color.rgb(20, 122, 214),
            Color.rgb(235, 101, 101)
    };
    private Button btnScrollBarChart, btnHorizontalBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChart = findViewById(R.id.piechart);

        button = findViewById(R.id.btnPieChart);
        btnScrollBarChart = findViewById(R.id.btnScrollBarChart);
        btnHorizontalBarChart = findViewById(R.id.btnHorizontalBarChart);

        mChart.getDescription().setEnabled(false);

        mChart.setRotationEnabled(true);

        int tempInt = 0;
        for (int yValue : yValues) {
            tempInt += yValue;
        }

        mChart.setCenterText(tempInt + "\n" + getString(R.string.lbl_employees));
        mChart.setCenterTextSize(12);
        mChart.setCenterTextColor(getColor(R.color.purple_500));

        // setting sample Data for Pie Chart
        setDataForPieChart();

        setUpClickListener();
    }

    public void setDataForPieChart() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < yValues.length; i++)
            pieEntries.add(new PieEntry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<>();

        Collections.addAll(xVals, xValues);

        // create pieDataSet
        PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
        pieDataSet.setSliceSpace(0f);
        pieDataSet.setSelectionShift(3);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<>();

        // add custom colors
        for (int c : MY_COLORS)
            colors.add(c);

        // set color to data set
        pieDataSet.setColors(colors);

        ArrayList<String> temp = new ArrayList<>();

        String legendValue = "";
        for (int i = 0; i < xVals.size(); i++) {
            for (int j = 0; j < pieEntries.size(); j++) {
                legendValue = xValues[i] + "    " + yValues[i];
            }
            temp.add(legendValue);
        }

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(pieDataSet);

        data.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
        data.setValueTextSize(10);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate pie chart
        mChart.animateXY(1400, 1400);

        mChart.setUsePercentValues(true);

        // for not drawing the x-values
        //mChart.setDrawSliceText(false);
        mChart.setDrawEntryLabels(true);

        Legend mChartLegend = mChart.getLegend();
        mChartLegend.setFormSize(12);
        mChartLegend.setTextSize(14f);
        mChartLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        mChartLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        // set what type of form/shape should be used
        mChartLegend.setForm(Legend.LegendForm.CIRCLE);
        mChartLegend.setYOffset(0f);

    }


    private void setUpClickListener() {
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(MainActivity.this, xValues[(int) e.getX()]
                        + " is " + e.getY() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, PieChartAndRingDetail.class);
            startActivity(intent);
        });

        btnScrollBarChart.setOnClickListener(view -> {
            Intent intent = new Intent(this, ScrollingHorizontalBarChartActivity.class);
            startActivity(intent);
        });

        btnHorizontalBarChart.setOnClickListener(view -> {
            Intent intent = new Intent(this, HorizontalBarChartActivity.class);
            startActivity(intent);
        });
    }
}