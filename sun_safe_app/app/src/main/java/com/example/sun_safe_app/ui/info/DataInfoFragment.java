package com.example.sun_safe_app.ui.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentDataInfoBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.sevenheaven.segmentcontrol.SegmentControl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataInfoFragment extends Fragment{
    private FragmentDataInfoBinding binding;
    private ArrayList<PieEntry> entries;
    String currentCity = "Sydney";
    String currentSeason = "All year";
    Map<String, Double> map = new HashMap<String, Double>();
    double[] monthlyData = new double[12];



    private BarChart barChart;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
//
//    public int[] PIE_COLORS = {
//            Color.rgb(167, 251, 9), Color.rgb(253, 231, 33), Color.rgb(255, 152, 0),
//            Color.rgb(253, 51, 36), Color.rgb(156, 39, 176)
//    };

        public ArrayList<Integer> PIE_COLORS = new ArrayList<Integer>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDataInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        createDataMap();
//        updatePieChart(map);

        binding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String info=adapterView.getItemAtPosition(i).toString();
                currentCity = info;
                createDataMap();
                createMonthlyData();
                updatePieChart(map);
                barChart = binding.barChart1;
                initBarChart(barChart);
                showBarChart("", getResources().getColor(R.color.black));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.spinnerSeason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String info=adapterView.getItemAtPosition(i).toString();
                currentSeason = info;
                createDataMap();
                updatePieChart(map);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.barChartLayOut.setVisibility(View.GONE);
        binding.pieChartLayOut.setVisibility(View.VISIBLE);

        binding.segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                if (index == 0){
                    binding.barChartLayOut.setVisibility(View.GONE);
                    binding.pieChartLayOut.setVisibility(View.VISIBLE);
                }
                else if (index == 1){
                    binding.barChartLayOut.setVisibility(View.VISIBLE);
                    binding.pieChartLayOut.setVisibility(View.GONE);
                    barChart = binding.barChart1;
                    initBarChart(barChart);
                    showBarChart("", getResources().getColor(R.color.black));
                }
            }
        });




        return view;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void createMonthlyData(){
        if (currentCity.equals("Sydney")){
            monthlyData[0] = 10.997;
            monthlyData[1] = 10.595;
            monthlyData[2] = 8.117;
            monthlyData[3] = 5.389;
            monthlyData[4] = 3.358;
            monthlyData[5] = 2.340;
            monthlyData[6] = 2.592;
            monthlyData[7] = 3.654;
            monthlyData[8] = 5.577;
            monthlyData[9] = 8.052;
            monthlyData[10] = 9.952;
            monthlyData[11] = 11.254;


        }
        else if (currentCity.equals("Melbourne")){
            monthlyData[0] = 10.673;
            monthlyData[1] = 9.481;
            monthlyData[2] = 7.362;
            monthlyData[3] = 4.591;
            monthlyData[4] = 2.481;
            monthlyData[5] = 1.722;
            monthlyData[6] = 1.930;
            monthlyData[7] = 2.917;
            monthlyData[8] = 4.715;
            monthlyData[9] = 6.632;
            monthlyData[10] = 8.906;
            monthlyData[11] = 10.975;

        }
        else if (currentCity.equals("Brisbane")){
            monthlyData[0] = 12.881;
            monthlyData[1] = 11.740;
            monthlyData[2] = 9.831;
            monthlyData[3] = 7.815;
            monthlyData[4] = 4.820;
            monthlyData[5] = 3.742;
            monthlyData[6] = 3.956;
            monthlyData[7] = 5.285;
            monthlyData[8] = 7.286;
            monthlyData[9] = 8.873;
            monthlyData[10] = 10.825;
            monthlyData[11] = 11.902;

        }
        else if (currentCity.equals("Gold Coast")){
            monthlyData[0] = 12.169;
            monthlyData[1] = 11.481;
            monthlyData[2] = 10.204;
            monthlyData[3] = 7.510;
            monthlyData[4] = 5.070;
            monthlyData[5] = 3.984;
            monthlyData[6] = 4.190;
            monthlyData[7] = 5.494;
            monthlyData[8] = 7.728;
            monthlyData[9] = 9.513;
            monthlyData[10] = 10.979;
            monthlyData[11] = 11.098;
        }
        else if (currentCity.equals("Perth")){
            monthlyData[0] = 12.017;
            monthlyData[1] = 10.935;
            monthlyData[2] = 8.726;
            monthlyData[3] = 6.043;
            monthlyData[4] = 3.612;
            monthlyData[5] = 2.747;
            monthlyData[6] = 2.963;
            monthlyData[7] = 4.278;
            monthlyData[8] = 6.192;
            monthlyData[9] = 8.317;
            monthlyData[10] = 10.485;
            monthlyData[11] = 12.021;

        }

    }

    public void createDataMap(){
        map.clear();
        PIE_COLORS.clear();
        if (currentCity.equals("Sydney")){
            if (currentSeason.equals("All year")){
                map.put("Low",9.95);
                map.put("Medium",35.04);
                map.put("High",12.86);
                map.put("Very high",19.89);
                map.put("Extremely high",22.26);
            }
            if (currentSeason.equals("Spring")){
                map.put("Low",1.83);
                map.put("Medium",15.02);
                map.put("High",29.30);
                map.put("Very high",38.46);
                map.put("Extremely high",15.38);
            }
            if (currentSeason.equals("Summer")){
                map.put("Low",1.48);
                map.put("Medium",4.42);
                map.put("High",4.06);
                map.put("Very high",17.34);
                map.put("Extremely high",72.69);
            }
            if (currentSeason.equals("Autumn")){
                map.put("Low",3.62);
                map.put("Medium",52.54);
                map.put("High",18.12);
                map.put("Very high",23.91);
                map.put("Extremely high",1.82);
            }
            if (currentSeason.equals("Winter")){
                map.put("Low",32.61);
                map.put("Medium",67.39);
//                map.put("High",29.30);
//                map.put("Very high",38.46);
//                map.put("Extremely high",15.38);
            }

        }
        else if (currentCity.equals("Melbourne")){
            if (currentSeason.equals("All year")){
                map.put("Low",23.63);
                map.put("Medium",27.46);
                map.put("High",12.04);
                map.put("Very high",21.35);
                map.put("Extremely high",15.51);
            }
            if (currentSeason.equals("Spring")){
                map.put("Low",2.93);
                map.put("Medium",32.23);
                map.put("High",26.74);
                map.put("Very high",31.14);
                map.put("Extremely high",6.96);
            }
            if (currentSeason.equals("Summer")){
//                map.put("Low",0.0);
                map.put("Medium",4.06);
                map.put("High",1.85);
                map.put("Very high",38.38);
                map.put("Extremely high",55.72);
            }
            if (currentSeason.equals("Autumn")){
                map.put("Low",18.84);
                map.put("Medium",45.29);
                map.put("High",19.57);
                map.put("Very high",16.30);
//                map.put("Extremely high",1.82);
            }
            if (currentSeason.equals("Winter")){
                map.put("Low",72.10);
                map.put("Medium",27.90);
//                map.put("High",29.30);
//                map.put("Very high",38.46);
//                map.put("Extremely high",15.38);
            }

        }
        else if (currentCity.equals("Brisbane")){
            if (currentSeason.equals("All year")){
                map.put("Low",0.55);
                map.put("Medium",31.32);
                map.put("High",14.89);
                map.put("Very high",22.47);
                map.put("Extremely high",30.78);
            }
            if (currentSeason.equals("Spring")){
//                map.put("Low",0.0);
                map.put("Medium",4.40);
                map.put("High",19.05);
                map.put("Very high",52.38);
                map.put("Extremely high",24.18);
            }
            if (currentSeason.equals("Summer")){
//                map.put("Low",0.0);
                map.put("Medium",3.32);
                map.put("High",2.58);
                map.put("Very high",8.49);
                map.put("Extremely high",85.61);
            }
            if (currentSeason.equals("Autumn")){
                map.put("Low",0.73);
                map.put("Medium",29.09);
                map.put("High",26.91);
                map.put("Very high",29.09);
                map.put("Extremely high",14.18);
            }
            if (currentSeason.equals("Winter")){
                map.put("Low",1.45);
                map.put("Medium",10.87);
                map.put("High",87.68);
//                map.put("Very high",38.46);
//                map.put("Extremely high",15.38);
            }

        }
        else if (currentCity.equals("Gold Coast")){
            if (currentSeason.equals("All year")){
                map.put("Low",1.09);
                map.put("Medium",27.09);
                map.put("High",17.10);
                map.put("Very high",23.53);
                map.put("Extremely high",31.19);
            }
            if (currentSeason.equals("Spring")){
//                map.put("Low",0.0);
                map.put("Medium",1.10);
                map.put("High",16.48);
                map.put("Very high",51.65);
                map.put("Extremely high",30.77);
            }
            if (currentSeason.equals("Summer")){
                map.put("Low",0.55);
                map.put("Medium",3.31);
                map.put("High",4.97);
                map.put("Very high",13.26);
                map.put("Extremely high",77.90);
            }
            if (currentSeason.equals("Autumn")){
                map.put("Low",1.09);
                map.put("Medium",23.37);
                map.put("High",29.35);
                map.put("Very high",29.35);
                map.put("Extremely high",16.85);
            }
            if (currentSeason.equals("Winter")){
                map.put("Low",2.72);
                map.put("Medium",79.89);
                map.put("High",17.39);
//                map.put("Very high",38.46);
//                map.put("Extremely high",15.38);
            }



        }
        else if (currentCity.equals("Perth")){
            if (currentSeason.equals("All year")){
                map.put("Low",2.83);
                map.put("Medium",35.77);
                map.put("High",14.42);
                map.put("Very high",19.16);
                map.put("Extremely high",27.83);
            }
            if (currentSeason.equals("Spring")){
//                map.put("Low",0.0);
                map.put("Medium",9.16);
                map.put("High",30.04);
                map.put("Very high",42.86);
                map.put("Extremely high",17.95);
            }
            if (currentSeason.equals("Summer")){
                map.put("Low",0.37);
                map.put("Medium",0.37);
                map.put("High",0.74);
                map.put("Very high",6.64);
                map.put("Extremely high",91.88);
            }
            if (currentSeason.equals("Autumn")){
                map.put("Low",1.09);
                map.put("Medium",43.48);
                map.put("High",25.72);
                map.put("Very high",27.17);
                map.put("Extremely high",2.54);
            }
            if (currentSeason.equals("Winter")){
                map.put("Low",9.78);
                map.put("Medium",89.13);
                map.put("High",1.09);
//                map.put("Very high",38.46);
//                map.put("Extremely high",15.38);
            }



        }


        if (map.get("Low")!= null){
            PIE_COLORS.add(Color.rgb(167, 251, 9));
        }
        if (map.get("Medium")!= null){
            PIE_COLORS.add(Color.rgb(253, 231, 33));
        }
        if (map.get("High")!= null){
            PIE_COLORS.add(Color.rgb(255, 152, 0));
        }
        if (map.get("Very high")!= null){
            PIE_COLORS.add(Color.rgb(253, 51, 36));
        }
        if (map.get("Extremely high")!= null){
            PIE_COLORS.add(Color.rgb(156, 39, 176));
        }


    }
    /**
     * 初始化BarChart图表
     */
    private void initBarChart(BarChart barChart) {
        /***图表设置***/
        //背景颜色
        barChart.setBackgroundColor(Color.TRANSPARENT);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        //显示边框
        barChart.setDrawBorders(true);
        //设置动画效果
        barChart.animateY(1000, Easing.Linear);
        barChart.animateX(1000, Easing.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        leftAxis = barChart.getAxisLeft();
        rightAxis = barChart.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
//        yAxis.setAxisMinValue(0);


        /***折线图例 标签 设置***/
        legend = barChart.getLegend();
        legend.setEnabled(false);
//        legend.setForm(Legend.LegendForm.LINE);
//        legend.setTextSize(11f);
//        //显示位置
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        //是否绘制在图表里面
//        legend.setDrawInside(false);
        barChart.setDrawBorders(false);

        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);
        xAxis.setDrawAxisLine(false);
        leftAxis.setDrawAxisLine(false);
        rightAxis.setDrawAxisLine(false);
        leftAxis.setEnabled(false);
        rightAxis.setEnabled(false);
        //不显示X轴网格线
        xAxis.setDrawGridLines(false);
//右侧Y轴网格线设置为虚线
        rightAxis.enableGridDashedLine(10f, 10f, 0f);


    }

    /**
     * 柱状图始化设置 一个BarDataSet 代表一列柱状图
     *
     * @param barDataSet 柱状图
     * @param color      柱状图颜色
     */
    private void initBarDataSet(BarDataSet barDataSet, int[] color) {
        barDataSet.setColors(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(true);
        barDataSet.setValueTextSize(12f);
//        barDataSet.setValueTextColor(color);
    }



    public void showBarChart(String name, int color) {
//        MainActivity parentActivity = (MainActivity) getActivity();
//        HashMap<Long, String> thisMap = parentActivity.map.get(uid);


        ArrayList<BarEntry> entries = new ArrayList<>();
        String[] hoursName = new String[12];
        int[] hoursColor = new int[12];

        int i = 0;

        for (double aHoursData : monthlyData) {
            /**
             * 此处还可传入Drawable对象 BarEntry(float x, float y, Drawable icon)
             * 即可设置柱状图顶部的 icon展示
             */
            double uvi = aHoursData;
            BarEntry barEntry = new BarEntry(i, (float) uvi);
            hoursName[i] = i + 1 + "";
            entries.add(barEntry);
            if (uvi < 2.5){
                hoursColor[i] = 0xFF4CAF50;
//                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFF00E05A);
            }
            else  if (uvi < 5.5){
//                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFFFCDD66);
                hoursColor[i] = 0xFFEDB200;

            }
            else  if (uvi < 7.5){
//                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFFFBA909);
                hoursColor[i] = 0xFFE86C00;

            }
            else  if (uvi < 10.5){
//                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFFFD3324);
                hoursColor[i] = 0xFFFD3324;

            }
            else {
//                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFF9C27B0);
                hoursColor[i] = 0xFF9C27B0;

            }
            i ++;
        }


        xAxis.setLabelCount(hoursName.length);
//        xAxis.setLabelRotationAngle(-60);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(hoursName));

        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, name);

        initBarDataSet(barDataSet, hoursColor);

//        // 添加多个BarDataSet时
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new DefaultValueFormatter(1));

//        BarData data = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(data);

    }

    public void updatePieChart(Map<String, Double> map){
        entries = new ArrayList<PieEntry>();
        entries.clear();
        // create a map to store the pain location number data
//        for (Map.Entry<String,Double> entry : map.entrySet()) {
//            String name = entry.getKey();
//            double value = entry.getValue();
//            name = name;
//            entries.add(new PieEntry((float) value, name));
//        }
        if (map.get("Low")!= null){ ;
            double value = map.get("Low");
            String name = "Low";
            entries.add(new PieEntry((float) value, name));
        }
        if (map.get("Medium")!= null){ ;
            double value = map.get("Medium");
            String name = "Medium";
            entries.add(new PieEntry((float) value, name));
        }
        if (map.get("High")!= null){ ;
            double value = map.get("High");
            String name = "High";
            entries.add(new PieEntry((float) value, name));
        }
        if (map.get("Very high")!= null){ ;
            double value = map.get("Very high");
            String name = "Very high";
            entries.add(new PieEntry((float) value, name));
        }
        if (map.get("Extremely high")!= null){ ;
            double value = map.get("Extremely high");
            String name = "Extremely high";
            entries.add(new PieEntry((float) value, name));
        }
            binding.pieChart.setUsePercentValues(true); // Set whether to display data entities (percentage, true: the following attributes are meaningful)
        binding.pieChart.getDescription().setEnabled(false);// Set the description of the pieChart chart
//        binding.pieChart.setExtraOffsets(10, 10, 10, 10);// Top, bottom, left, and right margins of pie chart


        binding.pieChart.setDragDecelerationFrictionCoef(0.95f);//Set pieChart chart rotation resistance friction coefficient [0,1]

//              binding.pieChart.setCenterTextTypeface(mTfLight);//Set the text font style of all data entities (percentages) in the DataSet
//                binding.pieChart.setCenterText("Pain location");// Set the content of the circular text inside PieChart
        binding.pieChart.setCenterTextSize(16);
        binding.pieChart.setDrawHoleEnabled(false);//Whether to display the inner ring of PieChart (true: the following attributes are meaningful)
        binding.pieChart.setHoleColor(Color.WHITE);//Set the color of the inner circle of PieChart
        binding.pieChart.setTransparentCircleColor(Color.WHITE);//Set the filling color between the inner transparent circle and the inner circle of PieChart (31f-28f)
        binding.pieChart.setTransparentCircleAlpha(0);//Set the distance between the inner transparent circle and the inner circle of PieChart (31f-28f) Transparency [0~255] The smaller the value, the more transparent
        binding.pieChart.setHoleRadius(0f);//Set the radius of the inner circle of PieChart (here 0f is set, that is, the inner circle is not required)
        binding.pieChart.setTransparentCircleRadius(31f);//Set the radius of the transparent circle inside PieChart (here set 31.0f)

        binding.pieChart.setDrawCenterText(false);//Whether to draw the center text inside PieChart (true: the following attributes are meaningful)

        binding.pieChart.setRotationAngle(0);//Set the starting angle of the pieChart chart

        binding.pieChart.setRotationEnabled(true);//Set whether the pieChart chart can be rotated manually
        binding.pieChart.setHighlightPerTapEnabled(true);//Set the piecahrt chart to click on the item highlighting is available

        binding.pieChart.animateY(1400, Easing.EaseInOutQuad);// Set the pieChart chart to display the animation effect
//              binding.pieChart.spin(2000, 0, 360);//旋转

        binding.pieChart.setEntryLabelColor(Color.BLACK);       //Set pieChart chart text font color



        // Get the pieCahrt chart column (the position of the chart column, whether it is displayed horizontally or vertically)
        Legend l = binding.pieChart.getLegend();
        l.setForm(Legend.LegendForm.SQUARE);// line
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);// top
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//Right (following TOP is the upper right corner, set the upper left corner and the lower left corner according to your needs...)
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f); //Set the distance between the legend entities along the X axis (setOrientation = HORIZONTAL is valid)
        l.setYEntrySpace(0f); //Set the distance between the legend entities along the Y axis (setOrientation = VERTICAL is valid)
        l.setYOffset(0f);//Set the Y axis offset of the proportional block


//              binding.pieChart.setEntryLabelTypeface(mTfRegular);//Set pieChart chart text font style
        binding.pieChart.setEntryLabelTextSize(12f);//Set pieChart chart text font size
        PieDataSet dataSet = new PieDataSet(entries, "UV level");//Upper right corner, arranged in order
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(0f);//Set the gap between the pie-shaped items
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(20f);//Set the distance that the pie-shaped Item changes when it is selected (when it is 0f, the selected item will not bounce)


        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart1Length(0.6f);//Set description cable length
        //Set the location of the data
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart2Length(0.6f);//description
        //Set the color of the two connecting lines
        dataSet.setValueLineColor(Color.BLACK);

//                ArrayList<Integer> colors = new ArrayList<Integer>();
//
//                for (int c : ColorTemplate.VORDIPLOM_COLORS) {
//                    colors.add(c);
//                }
//
//                for (int c : ColorTemplate.JOYFUL_COLORS) {
//                    colors.add(c);
//                }
//
//                for (int c : ColorTemplate.COLORFUL_COLORS) {
//                    colors.add(c);
//                }
//
//                for (int c : ColorTemplate.LIBERTY_COLORS) {
//                    colors.add(c);
//                }
//
//                for (int c : ColorTemplate.PASTEL_COLORS) {
//                    colors.add(c);
//                }
//
//                colors.add(ColorTemplate.getHoloBlue());
//
//                dataSet.setColors(colors);
        dataSet.setColors(PIE_COLORS);


        PieData data = new PieData(dataSet);//Set the percentage in the pie chart (eg: 20.8%)
        data.setDrawValues(true);            //Set whether to display data entities (percentage, true: the following attributes are meaningful)
        data.setValueTextColor(Color.BLUE);  //设置所有DataSet内数据实体（百分比）的文本颜色
        data.setValueTextSize(11f);          //Set the text color of all data entities (percentages) in the DataSet
//        data.setValueTypeface(mTfLight);     //Set the text font style of all data entities (percentages) in the DataSet
        data.setValueFormatter(new PercentFormatter()); //Set the text font format of all data entities (percentages) in the DataSet


        binding.pieChart.setData(data);// Add data to the chart

        binding.pieChart.highlightValues(null);//Set highlight
        binding.pieChart.setDrawEntryLabels(false);// Set whether pieChart only displays the percentage on the pie chart without displaying text
        binding.pieChart.invalidate();//The chart is redrawn to show the set attributes and data



        binding.pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // e.getX() get the data
                PieEntry pieEntry = (PieEntry) e;
                Toast.makeText(requireActivity(), "Proportion: " + pieEntry.getValue() + "\nLevel: " + pieEntry.getLabel().split(" ")[0], Toast.LENGTH_SHORT).show();
//                Toast.makeText(requireActivity(), "Can't get the weather information!", Toast.LENGTH_SHORT).show();

            }



            @Override
            public void onNothingSelected() {

            }
        });
    }

}
