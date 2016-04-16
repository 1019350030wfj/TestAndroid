package com.jayden.testandroid.mpchart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data=new BarData(labels,dataset);
        LimitLine line=new LimitLine(10f);
        barChart.setData(data);
//        barChart.animateXY(5000,5000);
//        barChart.animateX(5000);
        barChart.animateY(3000);
        barChart.setDescription("hello MPandroidChart");
    }
}
