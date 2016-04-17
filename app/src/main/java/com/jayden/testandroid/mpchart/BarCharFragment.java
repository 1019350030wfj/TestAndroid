package com.jayden.testandroid.mpchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jayden.testandroid.R;

import java.util.ArrayList;

/**
 * Created by Jayden on 2016/4/16.
 * Email : 1570713698@qq.com
 */
public class BarCharFragment extends Fragment {

    private BarChart barChart;
    public ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    public BarDataSet dataset;
    public ArrayList<String> labels = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar_chart,container,false);
        barChart= (BarChart) view.findViewById(R.id.bar_chart);
        initEntriesData();
        initLableData();
        show();
        return view;
    }

    /**
     *  =========================== 定义Y的标签： ======================================
     * 也就是我们Y值，上面知道X有6个值了，然后需要对应的Y值，
     * 我们需要创建BarEntry(y,x)对象，这里面的y对应y的值，然后x对应我么的下标
     */
    public void initEntriesData() {
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
    }

    /**
     *  =========================== 我们定义X的标签 ========================================
     */
    public void initLableData(){
        labels.add("一月");
        labels.add("二月");
        labels.add("三月");
        labels.add("四月");
        labels.add("五月");
        labels.add("六月");
    }


    public void show(){
        dataset= new BarDataSet(entries,"");
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        BarData data=new BarData(labels,dataset);
        data.setValueFormatter(new PercentFormatter());
        barChart.setData(data);
        barChart.setDrawValueAboveBar(false);
//        barChart.animateXY(5000,5000);
//        barChart.animateX(5000);
        barChart.animateY(3000);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        YAxis yAxis = barChart.getAxisRight();
        yAxis.setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0);
        leftAxis.setLabelCount(7,true);

        LimitLine ll = new LimitLine(14f, "Critical Blood Pressure");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(4f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(12f);
// .. and more styling options

        leftAxis.addLimitLine(ll);

//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        // 饼图颜色
//        colors.add(Color.rgb(205, 205, 205));
//        colors.add(Color.rgb(114, 188, 223));
//        colors.add(Color.rgb(255, 123, 124));
//        colors.add(Color.rgb(57, 135, 200));
//        barChart.setColors(colors);
        barChart.setDrawHighlightArrow(true);
        barChart.setDescription("hello MPandroidChart");
    }
}
