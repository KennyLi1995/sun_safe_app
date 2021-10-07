package com.example.sun_safe_app.ui.activityPlan;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.adapter.RecyclerViewAdapter;
import com.example.sun_safe_app.databinding.FragmentActivityBinding;
import com.example.sun_safe_app.databinding.FragmentMySkinBinding;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.retrofit.Hourly;
import com.example.sun_safe_app.retrofit.RetrofitClient;
import com.example.sun_safe_app.retrofit.RetrofitInterface;
import com.example.sun_safe_app.retrofit.Weather;
import com.example.sun_safe_app.retrofit.WeatherResponse;
import com.example.sun_safe_app.room.entity.EventRecord;
import com.example.sun_safe_app.room.viewmodel.EventRecordViewModel;
import com.example.sun_safe_app.utils.ButtomDialogView;
import com.example.sun_safe_app.utils.DateViewModel;
import com.example.sun_safe_app.utils.MapDialog;
import com.example.sun_safe_app.utils.MapFragmentDialog;
import com.example.sun_safe_app.utils.SkinTypeDialog;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPlanFragment extends Fragment {

    private FragmentActivityBinding binding;
    private RecyclerViewAdapter adapter;
    private EventRecordViewModel eventRecordViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private RetrofitInterface retrofitInterface;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentActivityBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        SharedPreferences sharedPref2= getActivity().
                getSharedPreferences("Default", Context.MODE_PRIVATE);

        if (sharedPref2.getInt("skinType",0) == 0) {
            binding.notificationLayout.setVisibility(View.VISIBLE);
            binding.addButton.setVisibility(View.GONE);
            binding.mainLayout.setVisibility(View.GONE);
        }
        else{
            binding.notificationLayout.setVisibility(View.GONE);
            binding.addButton.setVisibility(View.VISIBLE);
            binding.mainLayout.setVisibility(View.VISIBLE);
            updateViewHolder();
        }
        binding.todo.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.navigation_my_skin, null));
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtomDialogView dialog=new ButtomDialogView(getContext(),true,true,getActivity());
                dialog.show();
            }
        });

        ActivityWeatherViewModel vm =  new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(ActivityWeatherViewModel.class);
        vm.getText().observe((LifecycleOwner) getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean s) {
                updateCheck = s;
            }
        });


        return view;
    }

    boolean updateCheck = true;


    public void updateViewHolder(){
        List<EventRecord> pd = new LinkedList<>();
        adapter = new RecyclerViewAdapter(pd, (MainActivity) getActivity());
        reOrderEventList(pd);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });
        binding.recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        eventRecordViewModel =  ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(EventRecordViewModel.class);
        eventRecordViewModel.getAllEventRecords().observe(getViewLifecycleOwner(), new Observer<List<EventRecord>>() {
            @Override
            public void onChanged(@Nullable final List<EventRecord> painRecords) {
                // adjust the recycle view

                reOrderEventList(painRecords);
                if (updateCheck) {
                    for (EventRecord aEventRecord : painRecords) {
                        String completeEndDate = aEventRecord.date + " " + aEventRecord.end_time;
                        Date endDate = null;
                        String format = "yyyy-MM-dd HH:mm";
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        try {
                            endDate= sdf.parse(completeEndDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (endDate.getTime() > System.currentTimeMillis()){
                            updateWeather(aEventRecord);
                        }
                    }
                    updateCheck = false;
                }


                adapter = new RecyclerViewAdapter(painRecords, (MainActivity) getActivity());
//                binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL) {
//                    @Override
//                    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                        // Do not draw the divider
//                    }
//                });
                binding.recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(getContext());
                binding.recyclerView.setLayoutManager(layoutManager);

            }
        });



    }

    public void reOrderEventList(List<EventRecord> pd){
        for (int i = pd.size(); i >= 0; i --){
            for (int j = 0; j < i - 1; j ++){
                if (firstOneLater(pd.get(j),pd.get(j +1 ))){
                    EventRecord firstOne = pd.get(j);
                    pd.set(j, pd.get(j +1));
                    pd.set(j + 1 ,firstOne);
                }
            }
        }
    }

    public boolean firstOneLater(EventRecord firstOne, EventRecord secondOne){
        String completeFirstDate = firstOne.date + " " + firstOne.start_time;
        String completeSecondDate = secondOne.date + " " + secondOne.start_time;
        String format = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date firstOneDate = null;
        Date secondOneDate = null;
        try {
            firstOneDate = sdf.parse(completeFirstDate);
            secondOneDate = sdf.parse(completeSecondDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (firstOneDate.getTime() > secondOneDate.getTime()){
            return true;
        }
        else{
            return false;
        }
    }


    public void updateWeather(EventRecord aEventRecord){
        // get the weather message using retrofit
        String lat = String.valueOf(aEventRecord.lat);
        String longi = String.valueOf(aEventRecord.lon);
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
                    String startTimeString = aEventRecord.date + " " + aEventRecord.start_time;
                    String endTimeString = aEventRecord.date + " " + aEventRecord.end_time;
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


                    WeatherResponse weatherResponse = response.body();
                    int timezone_offset = Integer.parseInt(weatherResponse.timezone_offset);
                    Date systemDate = new Date(System.currentTimeMillis());

                    Calendar mCalendar = new GregorianCalendar();
                    TimeZone mTimeZone = mCalendar.getTimeZone();
                    int system_offset = mTimeZone.getRawOffset() + (mTimeZone.inDaylightTime(new Date()) ? mTimeZone.getDSTSavings() : 0);
                    system_offset /= 1000;
//                    int system_offset = mTimeZone.getRawOffset()/1000;
//                    HashMap<Long, String> map  = new HashMap<Long,String>();
                    ArrayList<Hourly> hourlys = weatherResponse.hourly;
                    String weatherCombinationTotal = "";
                    float highestUV = aEventRecord.highest_uvi;

                    String highestHour = aEventRecord.highest_uvi_hour;
                    for(Hourly ahour: hourlys){
                        if (ahour.dt + (timezone_offset - system_offset)>= startTimeSecond  && ahour.dt + (timezone_offset - system_offset) <= endTimeSecond){
                            float uvi = (float) new BigDecimal(ahour.uvi).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                            String weatherCombination = ahour.dt + (timezone_offset - system_offset) + "/" + uvi + "/" +ahour.temp + "/" +ahour.weather.get(0).main + ",";
                            if (uvi >= highestUV){
                                highestUV = uvi;
                                Date date = new Date((ahour.dt + (timezone_offset - system_offset)) * 1000);

                                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
                                highestHour = format2.format(date);
                            }
//                            map.put(ahour.dt, weatherCombination);
                            weatherCombinationTotal = weatherCombinationTotal.concat(weatherCombination);
//                            weatherCombinationTotal += weatherCombination;

                        }
                    }
                    if (getActivity() != null) {
                        SharedPreferences sharedPref = getActivity().
                                getSharedPreferences("WeatherData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor spEditor = sharedPref.edit();
                        spEditor.putString(aEventRecord.uid + "", weatherCombinationTotal);
                        spEditor.apply();

                        final String finalHighestHour = highestHour;
                        final float finalHighestUV = highestUV;

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            CompletableFuture<EventRecord> painRecordCompletableFuture =
                                    eventRecordViewModel.findByIDFuture(aEventRecord.uid);

                            painRecordCompletableFuture.thenApply(eventRecord -> {
                                if (eventRecord != null) {
                                    eventRecord.highest_uvi_hour = finalHighestHour;
                                    eventRecord.highest_uvi = finalHighestUV;
                                    eventRecordViewModel.update(eventRecord);

                                }
                                return eventRecord;
                            });
                        }

//                    MainActivity parentActivity = (MainActivity) getActivity();
//                    parentActivity.map.put(aEventRecord.uid,map);


                        float uvi = (float) new BigDecimal(weatherResponse.current.uvi).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                        int temp = Math.round(weatherResponse.current.temp);
                    }


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





}
