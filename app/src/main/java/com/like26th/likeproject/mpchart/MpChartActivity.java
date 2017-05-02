package com.like26th.likeproject.mpchart;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.like26th.likeproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/22.
 */
public class MpChartActivity extends Activity {
    @BindView(R.id.chart)
    BarChart barChart;
    @BindArray(R.array.main_item)
    String[] datas;

    private BarData barData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpchart);
        ButterKnife.bind(this);

        barData = getBarData();
        showBarChart();
    }

    private void showBarChart() {
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框

        barChart.setDescription(null);// 数据描述

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
//        barChart.setNoDataTextDescription("You need to provide data for the chart.");

        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        barChart.setTouchEnabled(true); // 设置是否可以触摸

        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放

        barChart.setPinchZoom(false);//

//      barChart.setBackgroundColor();// 设置背景

        barChart.setDrawBarShadow(true);

        Legend mLegend = barChart.getLegend(); // 设置比例图标示

        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setAxisMaximum(100);
        axisLeft.setAxisMinimum(0);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new LargeValueFormatter());
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                Log.d("MpChartActivity", "value:" + value);
//                axis.setAxisMinimum(0);
//                axis.setAxisMaximum(9);
//                return String.valueOf(value);
//            }
//        });

        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色

//      X轴设定
//      XAxis xAxis = barChart.getXAxis();
//      xAxis.setPosition(XAxisPosition.BOTTOM);

        barChart.animateX(2500); // 立即执行的动画,x轴

//        barChart
        barChart.setData(barData); // 设置数据
        barChart.invalidate();
    }

    private BarData getBarData() {
//        List<String> entries = new ArrayList<String>();
//        for (int i = 0; i < datas.length; i++) {
//            entries.add(datas[i]);
//        }

        //entry 代表柱状图数据的x， y 值，不是坐标轴显示的值
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 9; i++) {
            float value = (float) (50+i);
            entries.add(new BarEntry(i, value));
        }

        // entries 的数据集
        BarDataSet barDataSet = new BarDataSet(entries, "测试饼状图");

//        barDataSet.setColor(Color.rgb(114, 188, 223));

        return new BarData(barDataSet);
    }
}
