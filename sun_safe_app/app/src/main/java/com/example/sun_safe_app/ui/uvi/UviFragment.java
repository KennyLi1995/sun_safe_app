package com.example.sun_safe_app.ui.uvi;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.databinding.FragmentUviBinding;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.sun_safe_app.R;
import com.example.sun_safe_app.retrofit.RetrofitClient;
import com.example.sun_safe_app.retrofit.RetrofitInterface;
import com.example.sun_safe_app.retrofit.WeatherResponse;
import com.example.sun_safe_app.utils.AppUtil;
import com.squareup.okhttp.*;


import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UviFragment extends Fragment {

    private UviFragmentModel UviFragmentModel;
    private RetrofitInterface retrofitInterface;
    private FragmentUviBinding binding;

    Handler handler = new Handler();
    // declare a new thread to get the current time, update 1 second
    Runnable updateThread = new Runnable() {

        public void run() {
            handler.postDelayed(updateThread, 30000);
            Log.e("test","oneTime");
            if (getActivity() != null) {
                Log.e("test","pass");
                updateAdvice();
            }
        }

    };

    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        UviFragmentModel =
//                new ViewModelProvider(this).get(com.example.sun_safe_app.ui.uvi.UviFragmentModel.class);
//        View root = inflater.inflate(R.layout.fragment_uvi, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        UviFragmentModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        binding = FragmentUviBinding.inflate(inflater, container, false);
        View view = binding.getRoot();




        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("Default", Context.MODE_PRIVATE);
        int preTemp = sharedPref.getInt("preTemp",0);
        float preUvi = (float) sharedPref.getFloat("preUvi", (float) 0.0);
        String preWeather = sharedPref.getString("preWeather"," ");
        int preWeatherCode = sharedPref.getInt("preWeatherCode",0);

        binding.uvdataText.setText(preUvi + "");
        binding.tempText.setText(preTemp + "");
        binding.weatherText.setText(preWeather);
        changeColor(preUvi);
        binding.animationView.setAnimation(AppUtil.getWeatherAnimation(preWeatherCode));
        binding.animationView.playAnimation();



//        binding.animationView.setAnimation(AppUtil.getWeatherAnimation(500));
//        binding.animationView.playAnimation();

        UviFragmentModel vm =  new ViewModelProvider(getActivity()).get(UviFragmentModel.class);;
        vm.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.addressText.setText(s);
            }
        });

        UviFragmentLatLongModel vmlatlong =  new ViewModelProvider(getActivity()).get(UviFragmentLatLongModel.class);;
        vmlatlong.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s.length() != 0 ){
                    String[] ss = s.split(" ");
                    String lat = ss[0];
                    String longi = ss[1];
                    updateWeather(lat,longi);

                }
            }
        });


//        // get the weather message using retrofit
//        retrofitInterface = RetrofitClient.getRetrofitService();
//        Call<WeatherResponse> callAsync =
//                retrofitInterface.weatherSearch("-37.8136",
//                        "144.9631","metric","",
//                        "06c51bfd1f1dd5479b28cd21ff8534dd");
//        //makes an async request & invokes callback methods when the response returns
//        callAsync.enqueue(new Callback<WeatherResponse>() {
//            @Override
//            public void onResponse(Call<WeatherResponse> call,
//                                   Response<WeatherResponse> response) {
//                Log.d("Weather Response ",response.body().toString());
//                if (response.isSuccessful()) {
//                    // if success call, change the weather message on home screen
//                    WeatherResponse weatherResponse = response.body();
//                    String result= response.body().toString();
////                    binding.locationText.setText("Current uvi: " + weatherResponse.current.uvi
////                            + "\n"
////                        + "Current temp: " + weatherResponse.current.temp
////                                    + "\n"
//////                    );
//                    int uvi = Math.round(weatherResponse.current.uvi);
//                    int temp = Math.round(weatherResponse.current.temp);
//                    binding.uvdataText.setText(uvi + "");
//                    binding.tempText.setText(temp + "");
//                    binding.weatherText.setText(weatherResponse.current.weather.get(0).main);
//                    changeColor(uvi);
//
//                    binding.animationView.setAnimation(AppUtil.getWeatherAnimation(weatherResponse.current.weather.get(0).id));
//                    binding.animationView.playAnimation();
//
////                    binding.weatherText.setText(weatherResponse.current.weather.main);
//
//                }
//                else {
//                    Log.e("Error ","Response failed");
//                }
//            }
//            @Override
//            public void onFailure(Call<WeatherResponse> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT);
//
//            }
//
//        });

//                            int uvi = 3;
//                    int temp = 12;
//                    binding.uvdataText.setText(uvi + "");
//                    binding.tempText.setText(temp + "");
//                    binding.weatherText.setText("clouds");
//                    changeColor(uvi);

//                    binding.animationView.setAnimation(AppUtil.getWeatherAnimation(800));
//                    binding.animationView.playAnimation();
//
//                  binding.weatherText.setText("clouds");


        binding.insiderCircle.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.navigation_protection, null));
        handler.post(updateThread);



        updateAdvice();
        return view;
    }

    public void updateWeather(String lat, String longi){
        // get the weather message using retrofit
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

                        Toast.makeText(requireActivity(), "Can't get the weather information!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.isSuccessful()) {
                    // if success call, change the weather message on home screen
                    WeatherResponse weatherResponse = response.body();
                    String result= response.body().toString();
//                    binding.locationText.setText("Current uvi: " + weatherResponse.current.uvi
//                            + "\n"
//                        + "Current temp: " + weatherResponse.current.temp
//                                    + "\n"
////                    );
//                  int uvi = Math.round(weatherResponse.current.uvi);
                    float uvi = (float) new BigDecimal(weatherResponse.current.uvi).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                    int temp = Math.round(weatherResponse.current.temp);
                    binding.uvdataText.setText(uvi + "");
                    binding.tempText.setText(temp + "");
                    binding.weatherText.setText(weatherResponse.current.weather.get(0).main);
                    changeColor(uvi);

                    SharedPreferences sharedPref= getActivity().
                            getSharedPreferences("Default", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sharedPref.edit();
                    spEditor.putInt("preTemp", temp);
                    spEditor.putFloat("preUvi", uvi);
                    spEditor.putString("preWeather", weatherResponse.current.weather.get(0).main);
                    spEditor.putInt("preWeatherCode", weatherResponse.current.weather.get(0).id);
                    spEditor.apply();

                    updateAdvice();
                   binding.animationView.setAnimation(AppUtil.getWeatherAnimation(weatherResponse.current.weather.get(0).id));

                    binding.animationView.playAnimation();

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


    public void updateAdvice(){
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        float preUvi = sharedPref.getFloat("preUvi",0);

        SharedPreferences sharedPref2= getActivity().
                getSharedPreferences("Default", Context.MODE_PRIVATE);
        if (sharedPref2.getString("track","Begin Tracking").equals("Begin Tracking")){
            binding.trackingLayout.setVisibility(View.INVISIBLE);

        }
        else{
            binding.trackingLayout.setVisibility(View.VISIBLE);
            Long startTime = sharedPref2.getLong("startTrackTime",System.currentTimeMillis());
            Long passTiME = System.currentTimeMillis() - startTime;
            int min = (int) (passTiME/60000);
            int tranHour = min/60;
            int tranMin = min - tranHour * 60;
            binding.Hour.setText(tranHour + "");
            binding.Min.setText(tranMin + "");
        }

        if (preUvi <= 2){
            binding.infoText.setText("No protection\nrequired");
//            binding.adviceText.setText("You can safely stay outside");
            binding.insiderCircle.setCardBackgroundColor(0xFF38C022);
            binding.cvImgActivity.setCardBackgroundColor(0xFF30AE25);

        }
        else if (preUvi <= 7){
            binding.infoText.setText("Protection\nrequired");
//            binding.adviceText.setText("●Seek shade during midday hours! \n●Slip on a shirt, slop on sunscreen and slap on a hat!");
            binding.insiderCircle.setCardBackgroundColor(0xFFFFC107);
            binding.cvImgActivity.setCardBackgroundColor(0xFFFCDD66);


        }
        else {
            binding.infoText.setText("Extra\nprotection");
//            binding.adviceText.setText("●Avoid being outside during midday hours! \n●Make sure you seek shade! Shirt, sunscreen and hat are a must!");
            binding.insiderCircle.setCardBackgroundColor(0xFFD909FB);
            binding.cvImgActivity.setCardBackgroundColor(0xFFF477FF);
        }

    }



    public void changeColor(float uvData){
        if (uvData <= 2){
            binding.uvLevelText.setTextColor(getResources().getColor(R.color.colorLowUV));
            binding.uvdataText.setTextColor(getResources().getColor(R.color.colorLowUV));
            binding.uvLevelText.setText("Low");
        }
        else if (uvData <= 5){
            binding.uvLevelText.setTextColor(getResources().getColor(R.color.colorMediumUV));
            binding.uvdataText.setTextColor(getResources().getColor(R.color.colorMediumUV));
            binding.uvLevelText.setText("Medium");
        }
        else if (uvData <= 7){
            binding.uvLevelText.setTextColor(getResources().getColor(R.color.colorHighUV));
            binding.uvdataText.setTextColor(getResources().getColor(R.color.colorHighUV));
            binding.uvLevelText.setText("High");
        }
        else if (uvData <= 10){
            binding.uvLevelText.setTextColor(getResources().getColor(R.color.colorVeryHighUV));
            binding.uvdataText.setTextColor(getResources().getColor(R.color.colorVeryHighUV));
            binding.uvLevelText.setText("Very High");
        }
        else{
            binding.uvLevelText.setTextColor(getResources().getColor(R.color.colorExtremelyHighUV));
            binding.uvdataText.setTextColor(getResources().getColor(R.color.colorExtremelyHighUV));
            binding.uvLevelText.setText("Extremly High");
        }


    }

    @Override
    public void onResume() {
        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((MainActivity) getActivity()).selectBottomMenu(0); //change value depending on your bottom menu position
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        final Calendar mCalendar= Calendar.getInstance();
        long time=System.currentTimeMillis();
        mCalendar.setTimeInMillis(time);
        int mHour=mCalendar.get(Calendar.HOUR_OF_DAY);
        if (mHour >= 6 && mHour < 12){
            binding.hiMessage.setText("Good morning, " + sharedPref.getString("name", "new user"));

        }
        else if(mHour >= 12 && mHour < 18){
            binding.hiMessage.setText("Good afternoon, " + sharedPref.getString("name", "new user"));

        }
        else if(mHour >= 18 && mHour < 21){
            binding.hiMessage.setText("Good evening, " + sharedPref.getString("name", "new user"));

        }
        else if(mHour >= 21 || mHour < 6){
            binding.hiMessage.setText("Good night, " + sharedPref.getString("name", "new user"));

        }

    }

    @Override
    public void onPause() {

        super.onPause();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        handler.removeCallbacks(updateThread);


    }







}