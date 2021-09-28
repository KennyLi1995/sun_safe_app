package com.example.sun_safe_app.ui.activityPlan;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentActivityDataBinding;
import com.example.sun_safe_app.databinding.FragmentActivityDialogBinding;
import com.example.sun_safe_app.databinding.FragmentProtectionBinding;
import com.example.sun_safe_app.databinding.MapsFragmentTestBinding;
import com.example.sun_safe_app.room.entity.EventRecord;
import com.example.sun_safe_app.room.viewmodel.EventRecordViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityDataFragment extends Fragment {
    private FragmentActivityDataBinding binding;
    private int uid;
    private BarChart barChart;
    private YAxis leftAxis;             
    private YAxis rightAxis;            
    private XAxis xAxis;                
    private Legend legend;              
    private LimitLine limitLine;        


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentActivityDataBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        uid = bundle.getInt("uid");
        String title = bundle.getString("title");
        binding.activity.setText(title);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRecordViewModel eventRecordViewModel =  ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(EventRecordViewModel.class);
                eventRecordViewModel.deleteOne(uid);
                getActivity().onBackPressed();
            }
        });

        barChart = binding.barChart1;
        initBarChart(barChart);
        showBarChart("", getResources().getColor(R.color.black));

        String date = bundle.getString("date");
        String startTime = bundle.getString("startTime");
        String endTime = bundle.getString("endTime");
        String address = bundle.getString("address");
        String dayLeft = bundle.getString("dayLeft");
        float highestUV = bundle.getFloat("highestUV");
        String highestUVHour = bundle.getString("highestUVHour");
        binding.doing.setText(dayLeft);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        Date dateDate = null;
        try {
             dateDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sd2 =   new SimpleDateFormat( "EEEE,dd MMMM" );
        String s = sd2.format(dateDate);
        binding.date.setText(s);
        binding.time.setText(startTime + " - " +endTime);
        binding.location.setText(address);

        if (highestUV == -1){
            binding.highestUV.setText("Highest UV: Unknown");
            binding.rightSideLayout.setBackgroundColor(0xFF4B4B4B);
            binding.advice.setVisibility(View.GONE);
        }
        else             if (highestUV < 2.5){
            binding.highestUV.setText("Highest UV: " + highestUV  + " (Low)");
            binding.rightSideLayout.setBackgroundColor(0xFF4CAF50);
            binding.advice.setText("● Wear sunglasses on bright days\n● Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure");
        }
        else  if (highestUV < 5.5){
            binding.highestUV.setText("Highest UV: " + highestUV  + " (Medium)");
            binding.rightSideLayout.setBackgroundColor(0xFFEDB200);
            binding.advice.setText("● Stay in shade near midday when the sun is strongest" +
                    "\n● If outdoors, wear protective clothing, a wide-brimmed hat, and UV-blocking sunglasses" +
                    "\n● Generously apply sunscreen every 2 hours, even on cloudy days, and after swimming or sweating" +
                    "\n● Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure");

        }
        else  if (highestUV < 7.5){
            binding.highestUV.setText("Highest UV: " + highestUV  + " (High)");
            binding.rightSideLayout.setBackgroundColor(0xFFE86C00);
            binding.advice.setText("● Reduce time in the sun" +
                    "\n● If outdoors, seek shade and wear protective clothing, a wide-brimmed hat, and UV-blocking sunglasses" +
                    "\n● Generously apply sunscreen every 2 hours, even on cloudy days, and after swimming or sweating" +
                    "\n● Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure");

        }
        else  if (highestUV < 10.5){
            binding.highestUV.setText("Highest UV: " + highestUV  + " (Very High)");
            binding.rightSideLayout.setBackgroundColor(0xFFFD3324);
            binding.advice.setText("● Minimize sun exposure time" +
                    "\n● If outdoors, seek shade and wear protective clothing, a wide-brimmed hat, and UV-blocking sunglasses" +
                    "\n● Generously apply sunscreen every 2 hours, even on cloudy days, and after swimming or sweating" +
                    "\n● Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure");

        }
        else {
            binding.highestUV.setText("Highest UV: " + highestUV  + " (Extremely High)");
            binding.rightSideLayout.setBackgroundColor(0xFF9C27B0);
            binding.advice.setText("● Try to avoid sun exposure " +
                    "\n● If outdoors, seek shade and wear protective clothing, a wide-brimmed hat, and UV-blocking sunglasses" +
                    "\n● Generously apply sunscreen every 2 hours, even on cloudy days, and after swimming or sweating" +
                    "\n● Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure");

        }

        if ( !highestUVHour.equals(""))
            binding.highestUVHour.setText("Highest UV Time: " + highestUVHour);
        else
            binding.highestUVHour.setText("Highest UV Time: Unknown");



        return view;
    }




    /**
     * init barchart table
     */
    private void initBarChart(BarChart barChart) {
        //background
        barChart.setBackgroundColor(Color.TRANSPARENT);
        //not show the drawgrid background
        barChart.setDrawGridBackground(false);
        //shadow
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        //show borders
        barChart.setDrawBorders(true);
        //set animation
        barChart.animateY(1000, Easing.Linear);
        barChart.animateX(1000, Easing.Linear);


        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        leftAxis = barChart.getAxisLeft();
        rightAxis = barChart.getAxisRight();
        //ensure y start with 0
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
//        yAxis.setAxisMinValue(0);


        legend = barChart.getLegend();
        legend.setEnabled(false);
//        legend.setForm(Legend.LegendForm.LINE);
//        legend.setTextSize(11f);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
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
        xAxis.setDrawGridLines(false);
        rightAxis.enableGridDashedLine(10f, 10f, 0f);


    }


    /**
     * 
     *
     * @param barDataSet 
     * @param color      
     */
    private void initBarDataSet(BarDataSet barDataSet, int[] color) {
        barDataSet.setColors(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        barDataSet.setDrawValues(true);
        barDataSet.setValueTextSize(12f);
//        barDataSet.setValueTextColor(color);
    }



    public void showBarChart(String name, int color) {
//        MainActivity parentActivity = (MainActivity) getActivity();
//        HashMap<Long, String> thisMap = parentActivity.map.get(uid);
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("WeatherData", Context.MODE_PRIVATE);
        String weatherCombinationTotal = sharedPref.getString(uid + "","");
        String[] hoursData = weatherCombinationTotal.split(",");
        if (hoursData[0].equals("")){
            binding.barChart1.setVisibility(View.INVISIBLE);
            return;

        }
        ArrayList<BarEntry> entries = new ArrayList<>();
        String[] hoursName = new String[hoursData.length];
        int[] hoursColor = new int[hoursData.length];

        int i = 0;

        for (String aHoursData : hoursData) {
            /**
             * 此处还可传入Drawable对象 BarEntry(float x, float y, Drawable icon)
             * 即可设置柱状图顶部的 icon展示
             */
            String[] hoursWeatherData = aHoursData.split("/");
            long date = Long.parseLong(hoursWeatherData[0]);
            String format = "HH:mm";
            Date currentDate = new Date(date * 1000);//get current datetime
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String currentDateString = sdf.format(currentDate);

            float uvi = Float.parseFloat(hoursWeatherData[1]);
            BarEntry barEntry = new BarEntry(i, uvi);
            hoursName[i] = currentDateString;
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
        xAxis.setLabelRotationAngle(-60);

//        final String[] hoursName = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; // Your List / array with String Values For X-axis Labels
        xAxis.setValueFormatter(new IndexAxisValueFormatter(hoursName));

        BarDataSet barDataSet = new BarDataSet(entries, name);

        initBarDataSet(barDataSet, hoursColor);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new DefaultValueFormatter(1));

//        BarData data = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(data);

    }










}
