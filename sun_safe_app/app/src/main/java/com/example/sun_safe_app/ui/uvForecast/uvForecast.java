package com.example.sun_safe_app.ui.uvForecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentActivityDataBinding;
import com.example.sun_safe_app.databinding.FragmentUvForecastBinding;
import com.example.sun_safe_app.retrofit.Daily;
import com.example.sun_safe_app.retrofit.Hourly;
import com.example.sun_safe_app.retrofit.RetrofitClient;
import com.example.sun_safe_app.retrofit.RetrofitInterface;
import com.example.sun_safe_app.retrofit.WeatherResponse;
import com.example.sun_safe_app.room.entity.EventRecord;
import com.example.sun_safe_app.utils.AppUtil;
import com.example.sun_safe_app.utils.BarChartCustomRenderer;
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
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class uvForecast extends Fragment {
    private FragmentUvForecastBinding binding;
    private RetrofitInterface retrofitInterface;
    private BarChart barChart;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    private float highestUVToday;

    private BarChart barChart2;
    private YAxis leftAxis2;             //左侧Y轴
    private YAxis rightAxis2;            //右侧Y轴
    private XAxis xAxis2;                //X轴
    private Legend legend2;              //图例
    private LimitLine limitLine2;        //限制线
    private float highestUVTomorrow;
    private float highestUVDay3;
    private float highestUVDay4;
    private float highestUVDay5;
    private float highestUVDay6;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUvForecastBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.adviceCard.setVisibility(View.INVISIBLE);
        binding.adviceCard2.setVisibility(View.INVISIBLE);
        binding.adviceCard3.setVisibility(View.INVISIBLE);
        binding.adviceCard4.setVisibility(View.INVISIBLE);
        binding.adviceCard5.setVisibility(View.INVISIBLE);
        binding.adviceCard6.setVisibility(View.INVISIBLE);

        updateWeather();

        return view;

    }

    public void updateUI(){
        binding.highestUVNumber.setText(new BigDecimal(highestUVToday).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
        if (highestUVToday == -1){
            binding.highestUV.setText("Unknown");
            binding.leftSideLayout.setBackgroundColor(0xFF4B4B4B);
            binding.highestUVNumber.setText("Unknown");
        }
        else if (highestUVToday < 2.5){
            binding.leftSideLayout.setBackgroundColor(0xFF4CAF50);
            binding.highestUV.setText("Low peak UVI");
        }
        else if (highestUVToday < 5.5){
            binding.leftSideLayout.setBackgroundColor(0xFFEDB200);
            binding.highestUV.setText("Moderate peak UVI");
        }
        else if (highestUVToday < 7.5){
            binding.leftSideLayout.setBackgroundColor(0xFFE86C00);
            binding.highestUV.setText("High peak UVI");
        }
        else if (highestUVToday < 10.5){
            binding.leftSideLayout.setBackgroundColor(0xFFFD3324);
            binding.highestUV.setText("Very High peak UVI");
        }
        else {
            binding.leftSideLayout.setBackgroundColor(0xFF9C27B0);
            binding.highestUV.setText("Extremely High peak UVI");
        }


        binding.highestUVNumber2.setText(new BigDecimal(highestUVTomorrow).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
        if (highestUVTomorrow == -1){
            binding.highestUV2.setText("Unknown");
            binding.leftSideLayout2.setBackgroundColor(0xFF4B4B4B);
            binding.highestUVNumber2.setText("Unknown");
        }
        else if (highestUVTomorrow < 2.5){
            binding.leftSideLayout2.setBackgroundColor(0xFF4CAF50);
            binding.highestUV2.setText("Low peak UVI");
        }
        else if (highestUVTomorrow < 5.5){
            binding.leftSideLayout2.setBackgroundColor(0xFFEDB200);
            binding.highestUV2.setText("Moderate peak UVI");
        }
        else if (highestUVTomorrow < 7.5){
            binding.leftSideLayout2.setBackgroundColor(0xFFE86C00);
            binding.highestUV2.setText("High peak UVI");
        }
        else if (highestUVTomorrow < 10.5){
            binding.leftSideLayout2.setBackgroundColor(0xFFFD3324);
            binding.highestUV2.setText("Very High peak UVI");
        }
        else {
            binding.leftSideLayout2.setBackgroundColor(0xFF9C27B0);
            binding.highestUV2.setText("Extremely High peak UVI");
        }
        binding.adviceCard3.setVisibility(View.VISIBLE);
        binding.highestUVNumber3.setText(new BigDecimal(highestUVDay3).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
        if (highestUVDay3 == -1){
            binding.adviceCard3.setVisibility(View.GONE);
            binding.highestUV3.setText("Unknown");
            binding.leftSideLayout3.setBackgroundColor(0xFF4B4B4B);
            binding.highestUVNumber3.setText("Unknown");
        }
        else if (highestUVDay3 < 2.5){
            binding.leftSideLayout3.setBackgroundColor(0xFF4CAF50);
            binding.highestUV3.setText("Low peak UVI");
        }
        else if (highestUVDay3 < 5.5){
            binding.leftSideLayout3.setBackgroundColor(0xFFEDB200);
            binding.highestUV3.setText("Moderate peak UVI");
        }
        else if (highestUVDay3 < 7.5){
            binding.leftSideLayout3.setBackgroundColor(0xFFE86C00);
            binding.highestUV3.setText("High peak UVI");
        }
        else if (highestUVDay3 < 10.5){
            binding.leftSideLayout3.setBackgroundColor(0xFFFD3324);
            binding.highestUV3.setText("Very High peak UVI");
        }
        else {
            binding.leftSideLayout3.setBackgroundColor(0xFF9C27B0);
            binding.highestUV3.setText("Extremely High peak UVI");
        }
        binding.adviceCard4.setVisibility(View.VISIBLE);
        binding.highestUVNumber4.setText(new BigDecimal(highestUVDay4).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
        if (highestUVDay4 == -1){
            binding.adviceCard4.setVisibility(View.GONE);
            binding.highestUV4.setText("Unknown");
            binding.leftSideLayout4.setBackgroundColor(0xFF4B4B4B);
            binding.highestUVNumber4.setText("Unknown");
        }
        else if (highestUVDay4 < 2.5){
            binding.leftSideLayout4.setBackgroundColor(0xFF4CAF50);
            binding.highestUV4.setText("Low peak UVI");
        }
        else if (highestUVDay4 < 5.5){
            binding.leftSideLayout4.setBackgroundColor(0xFFEDB200);
            binding.highestUV4.setText("Moderate peak UVI");
        }
        else if (highestUVDay4 < 7.5){
            binding.leftSideLayout4.setBackgroundColor(0xFFE86C00);
            binding.highestUV4.setText("High peak UVI");
        }
        else if (highestUVDay4 < 10.5){
            binding.leftSideLayout4.setBackgroundColor(0xFFFD3324);
            binding.highestUV4.setText("Very High peak UVI");
        }
        else {
            binding.leftSideLayout4.setBackgroundColor(0xFF9C27B0);
            binding.highestUV4.setText("Extremely High peak UVI");
        }

        binding.adviceCard5.setVisibility(View.VISIBLE);
        binding.highestUVNumber5.setText(new BigDecimal(highestUVDay5).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
        if (highestUVDay5 == -1){
            binding.highestUV5.setText("Unknown");
            binding.leftSideLayout5.setBackgroundColor(0xFF4B4B4B);
            binding.highestUVNumber5.setText("Unknown");
        }
        else if (highestUVDay5 < 2.5){
            binding.leftSideLayout5.setBackgroundColor(0xFF4CAF50);
            binding.highestUV5.setText("Low peak UVI");
        }
        else if (highestUVDay5 < 5.5){
            binding.leftSideLayout5.setBackgroundColor(0xFFEDB200);
            binding.highestUV5.setText("Moderate peak UVI");
        }
        else if (highestUVDay5 < 7.5){
            binding.leftSideLayout5.setBackgroundColor(0xFFE86C00);
            binding.highestUV5.setText("High peak UVI");
        }
        else if (highestUVDay5 < 10.5){
            binding.leftSideLayout5.setBackgroundColor(0xFFFD3324);
            binding.highestUV5.setText("Very High peak UVI");
        }
        else {
            binding.leftSideLayout5.setBackgroundColor(0xFF9C27B0);
            binding.highestUV5.setText("Extremely High peak UVI");
        }
        binding.adviceCard6.setVisibility(View.VISIBLE);
        binding.highestUVNumber6.setText(new BigDecimal(highestUVDay6).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
        if (highestUVDay6 == -1){
            binding.highestUV6.setText("Unknown");
            binding.leftSideLayout6.setBackgroundColor(0xFF4B4B4B);
            binding.highestUVNumber6.setText("Unknown");
        }
        else if (highestUVDay6 < 2.5){
            binding.leftSideLayout6.setBackgroundColor(0xFF4CAF50);
            binding.highestUV6.setText("Low peak UVI");
        }
        else if (highestUVDay6 < 5.5){
            binding.leftSideLayout6.setBackgroundColor(0xFFEDB200);
            binding.highestUV6.setText("Moderate peak UVI");
        }
        else if (highestUVDay6 < 7.5){
            binding.leftSideLayout6.setBackgroundColor(0xFFE86C00);
            binding.highestUV6.setText("High peak UVI");
        }
        else if (highestUVDay6 < 10.5){
            binding.leftSideLayout6.setBackgroundColor(0xFFFD3324);
            binding.highestUV6.setText("Very High peak UVI");
        }
        else {
            binding.leftSideLayout6.setBackgroundColor(0xFF9C27B0);
            binding.highestUV6.setText("Extremely High peak UVI");
        }



    }



    public void updateWeather(){
        // get the weather message using retrofit
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("Default", Context.MODE_PRIVATE);
        String lat = sharedPref.getString("preLat","0");
        String longi = sharedPref.getString("preLon", "0");
        retrofitInterface = RetrofitClient.getRetrofitService();
        Call<WeatherResponse> callAsync =
                retrofitInterface.weatherSearch(lat,
                        longi,"metric","",
                        "06c51bfd1f1dd5479b28cd21ff8534dd");
        //06c51bfd1f1dd5479b28cd21ff8534dd
        //makes an async request & invokes callback methods when the response returns
        callAsync.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call,
                                   Response<WeatherResponse> response) {
                try {
                    Log.d("Weather Response ", response.body().toString());
                }
                catch (Exception ex){
                    if(isAdded()) {

                        Toast.makeText(getActivity().getApplicationContext(), "Can't get the weather information!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.isSuccessful()) {
                    // if success call, change the weather message on home screen



                    WeatherResponse weatherResponse = response.body();
                    int timezone_offset = Integer.parseInt(weatherResponse.timezone_offset);
                    Calendar mCalendar = new GregorianCalendar();
                    TimeZone mTimeZone = mCalendar.getTimeZone();
                    int system_offset = mTimeZone.getRawOffset() + (mTimeZone.inDaylightTime(new Date()) ? mTimeZone.getDSTSavings() : 0);
                    system_offset /= 1000;
//                    int system_offset = mTimeZone.getRawOffset()/1000;
//                    HashMap<Long, String> map  = new HashMap<Long,String>();
                    ArrayList<Hourly> hourlys = weatherResponse.hourly;
                    ArrayList<Daily> dailies = weatherResponse.daily;

                    String weatherCombinationTotal = "";
                    String weatherCombinationTotal2 = "";

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
                    Date date = new Date(System.currentTimeMillis());
                    String today = simpleDateFormat.format(date);
                    String startTimeString = today + " " + "09:00";
                    String endTimeString = today + " " + "17:00";
                    Date startDate = null;
                    Date endDate = null;
                    String format = "yyyy-MM-dd HH:mm";
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    try {
                        startDate = sdf.parse(startTimeString);
                        endDate= sdf.parse(endTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long startTime = startDate.getTime();
                    long endTime = endDate.getTime();
                    int startTimeSecond = (int) (startTime/1000);
                    int endTimeSecond = (int) (endTime/1000);
                    int startTimeSecond2 = startTimeSecond + 60*60*24;
                    int endTimeSecond2 = endTimeSecond + 60*60*24;

                    long todaySunrise = weatherResponse.current.sunrise;
                    long todaySunset = weatherResponse.current.sunset;
                    long tomorrowSunrise = weatherResponse.daily.get(1).sunrise;
                    long tomorrowSunset = weatherResponse.daily.get(1).sunset;

                    highestUVToday = 0;
                    highestUVTomorrow = 0;
                    SimpleDateFormat sd2 =   new SimpleDateFormat( "EEEE, dd MMMM" );
                    String s = sd2.format(new Date(System.currentTimeMillis()+ 24 * 60 * 60 * 1000));
                    binding.title2.setText(s);
                    String s3 = sd2.format(new Date(System.currentTimeMillis()+ 2 * 24 * 60 * 60 * 1000));
                    binding.title3.setText(s3);
                    String s4 = sd2.format(new Date(System.currentTimeMillis()+ 3 * 24 * 60 * 60 * 1000));
                    binding.title4.setText(s4);
                    String s5 = sd2.format(new Date(System.currentTimeMillis()+ 4 * 24 * 60 * 60 * 1000));
                    binding.title5.setText(s5);
                    String s6 = sd2.format(new Date(System.currentTimeMillis()+ 5 * 24 * 60 * 60 * 1000));
                    binding.title6.setText(s6);


                    for(Hourly ahour: hourlys){
                        if (ahour.dt + (timezone_offset - system_offset)>= startTimeSecond  && ahour.dt + (timezone_offset - system_offset) <= endTimeSecond){
                            float uvi = (float) new BigDecimal(ahour.uvi).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                            String weatherCombination = ahour.dt + (timezone_offset - system_offset) + "/" + uvi + "/" +ahour.temp + "/" +ahour.weather.get(0).id + ",";
//                            map.put(ahour.dt, weatherCombination);
                            weatherCombinationTotal = weatherCombinationTotal.concat(weatherCombination);
//                            weatherCombinationTotal += weatherCombination;

                            if (uvi > highestUVToday)
                                highestUVToday = uvi;
                        }
                        else if (ahour.dt + (timezone_offset - system_offset)>= startTimeSecond2  && ahour.dt + (timezone_offset - system_offset) <= endTimeSecond2){
                            float uvi = (float) new BigDecimal(ahour.uvi).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                            String weatherCombination = ahour.dt + (timezone_offset - system_offset) + "/" + uvi + "/" +ahour.temp + "/" +ahour.weather.get(0).id + ",";
//                            map.put(ahour.dt, weatherCombination);
                            weatherCombinationTotal2 = weatherCombinationTotal2.concat(weatherCombination);
//                            weatherCombinationTotal += weatherCombination;

                            if (uvi > highestUVTomorrow)
                                highestUVTomorrow = uvi;

                        }
                    }

                     highestUVDay3 = weatherResponse.daily.get(2).uvi;
                     highestUVDay4 = weatherResponse.daily.get(3).uvi;
                     highestUVDay5 = weatherResponse.daily.get(4).uvi;
                     highestUVDay6 = weatherResponse.daily.get(5).uvi;




                    if (getActivity() != null) {
                        SharedPreferences sharedPref = getActivity().
                                getSharedPreferences("WeatherForecast", Context.MODE_PRIVATE);
                        SharedPreferences.Editor spEditor = sharedPref.edit();
                        spEditor.putString("Today", weatherCombinationTotal);
                        spEditor.putString("Tomorrow", weatherCombinationTotal2);

                        spEditor.apply();
//                    MainActivity parentActivity = (MainActivity) getActivity();
//                    parentActivity.map.put(aEventRecord.uid,map);

                    }

                    if (weatherCombinationTotal.length() != 0) {
                        binding.adviceCard.setVisibility(View.VISIBLE);
                        barChart = binding.barChart1;
                        initBarChart(barChart);
                        showBarChart("", getResources().getColor(R.color.black));
                    }
                    else{
                        binding.adviceCard.setVisibility(View.GONE);
                    }
                    binding.adviceCard2.setVisibility(View.VISIBLE);
                    barChart2 = binding.barChart2;
                    initBarChart(barChart2);
                    showBarChart2("", getResources().getColor(R.color.black));
                    updateUI();


//                    binding.weatherText.setText(weatherResponse.current.weather.main);

                }
                else {
                    Log.e("Error ","Response failed");
                    if(isAdded()) {

                        Toast.makeText(requireActivity(), "Can't get the weather information!", Toast.LENGTH_LONG).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                if(isAdded()) {
                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT);
                }

            }

        });


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
//        xAxis.setAxisMinimum(-2f);
        xAxis.setGranularity(1f);

        leftAxis = barChart.getAxisLeft();
        rightAxis = barChart.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
//        leftAxis.setSpaceBottom(50f);
//        leftAxis.setAxisMinValue(-2);
//        rightAxis.setAxisMinValue(-2);



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
        barDataSet.setValueTextColor(0xFFFFFFFF);
//        barDataSet.setValueTextColor(color);
    }



    public void showBarChart(String name, int color) {
//        MainActivity parentActivity = (MainActivity) getActivity();
//        HashMap<Long, String> thisMap = parentActivity.map.get(uid);
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("WeatherForecast", Context.MODE_PRIVATE);
        String weatherCombinationTotal = sharedPref.getString("Today","");
        String[] hoursData = weatherCombinationTotal.split(",");
        if (hoursData[0].equals("")){
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
            int weatherCode = Integer.parseInt(hoursWeatherData[3]);
            String format = "HH:mm";
            Date currentDate = new Date(date * 1000);//获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String currentDateString = sdf.format(currentDate);
            float uvi = Float.parseFloat(hoursWeatherData[1]);
            Drawable drawable = getResources().getDrawable(getWeatherIcon(weatherCode));
            BarEntry barEntry = new BarEntry(i, uvi +10,drawable);
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

        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, name);

        initBarDataSet(barDataSet, hoursColor);

//        // 添加多个BarDataSet时
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new DefaultValueFormatter(1));

//        BarData data = new BarData(barDataSet);

        //**add renderer**
        barChart.setDrawValueAboveBar(false);
        BarChartCustomRenderer barChartCustomRenderer = new BarChartCustomRenderer(barChart, barChart.getAnimator(), barChart.getViewPortHandler());
        barChart.setRenderer(barChartCustomRenderer);

        barChart.setFitBars(true);
        barChart.setData(data);

    }

    public void showBarChart2(String name, int color) {
//        MainActivity parentActivity = (MainActivity) getActivity();
//        HashMap<Long, String> thisMap = parentActivity.map.get(uid);
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("WeatherForecast", Context.MODE_PRIVATE);
        String weatherCombinationTotal = sharedPref.getString("Tomorrow","");
        String[] hoursData = weatherCombinationTotal.split(",");
        if (hoursData[0].equals("")){
            binding.barChart2.setVisibility(View.INVISIBLE);
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
            int weatherCode = Integer.parseInt(hoursWeatherData[3]);
            String format = "HH:mm";
            Date currentDate = new Date(date * 1000);//获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String currentDateString = sdf.format(currentDate);
            float uvi = Float.parseFloat(hoursWeatherData[1]);
            Drawable drawable = getResources().getDrawable(getWeatherIcon(weatherCode));
            BarEntry barEntry = new BarEntry(i, uvi +10,drawable);
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

        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, name);

        initBarDataSet(barDataSet, hoursColor);

//        // 添加多个BarDataSet时
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new DefaultValueFormatter(1));

//        BarData data = new BarData(barDataSet);

        //**add renderer**
        barChart2.setDrawValueAboveBar(false);
        BarChartCustomRenderer barChartCustomRenderer = new BarChartCustomRenderer(barChart2, barChart2.getAnimator(), barChart2.getViewPortHandler());
        barChart2.setRenderer(barChartCustomRenderer);
        barChart2.setFitBars(true);
        barChart2.setData(data);
    }


    public int getWeatherIcon(int weatherCode) {
        if (weatherCode / 100 == 2) {
            return R.drawable.ic_thunder;
        } else if (weatherCode / 100 == 3) {
            return R.drawable.ic_rainy_1;
        } else if (weatherCode / 100 == 5) {
            return R.drawable.ic_rainy_6;
        } else if (weatherCode / 100 == 6) {
            return R.drawable.ic_snowy_4;
        } else if (weatherCode / 100 == 7) {
            return R.drawable.ic_cloudy;
        } else if (weatherCode == 800) {
            return R.drawable.ic_day;
        } else if (weatherCode == 801) {
            return R.drawable.ic_cloudy_day_1;
        } else if (weatherCode == 803) {
            return R.drawable.ic_cloudy_day_3;
        } else if (weatherCode == 802) {
            return R.drawable.ic_cloudy_day_2;
        }else if (weatherCode == 804) {
            return R.drawable.ic_cloudy;
        }
        return R.raw.unknown;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onPause() {

        super.onPause();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }


}
