package com.example.sun_safe_app.ui.uvi;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
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

import com.example.sun_safe_app.R;
import com.example.sun_safe_app.retrofit.RetrofitClient;
import com.example.sun_safe_app.retrofit.RetrofitInterface;
import com.example.sun_safe_app.retrofit.WeatherResponse;
import com.example.sun_safe_app.utils.AppUtil;
import com.squareup.okhttp.*;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UviFragment extends Fragment {

    private UviFragmentModel UviFragmentModel;
    private RetrofitInterface retrofitInterface;
    private FragmentUviBinding binding;

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
        int preUvi = sharedPref.getInt("preUvi",0);
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





        return view;
    }

    public void updateWeather(String lat, String longi){
        // get the weather message using retrofit
        retrofitInterface = RetrofitClient.getRetrofitService();
        Call<WeatherResponse> callAsync =
                retrofitInterface.weatherSearch(lat,
                        longi,"metric","",
                        "06c51bfd1f1dd5479b28cd21ff8534dd");
        //makes an async request & invokes callback methods when the response returns
        callAsync.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call,
                                   Response<WeatherResponse> response) {
                Log.d("Weather Response ",response.body().toString());
                if (response.isSuccessful()) {
                    // if success call, change the weather message on home screen
                    WeatherResponse weatherResponse = response.body();
                    String result= response.body().toString();
//                    binding.locationText.setText("Current uvi: " + weatherResponse.current.uvi
//                            + "\n"
//                        + "Current temp: " + weatherResponse.current.temp
//                                    + "\n"
////                    );
                    int uvi = Math.round(weatherResponse.current.uvi);
                    int temp = Math.round(weatherResponse.current.temp);
                    binding.uvdataText.setText(uvi + "");
                    binding.tempText.setText(temp + "");
                    binding.weatherText.setText(weatherResponse.current.weather.get(0).main);
                    changeColor(uvi);

                    SharedPreferences sharedPref= getActivity().
                            getSharedPreferences("Default", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sharedPref.edit();
                    spEditor.putInt("preTemp", temp);
                    spEditor.putInt("preUvi", uvi);
                    spEditor.putString("preWeather", weatherResponse.current.weather.get(0).main);
                    spEditor.putInt("preWeatherCode", weatherResponse.current.weather.get(0).id);
                    spEditor.apply();

                   binding.animationView.setAnimation(AppUtil.getWeatherAnimation(weatherResponse.current.weather.get(0).id));

                    binding.animationView.playAnimation();

//                    binding.weatherText.setText(weatherResponse.current.weather.main);

                }
                else {
                    Log.e("Error ","Response failed");
                }
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT);

            }

        });


    }

    public void changeColor(int uvData){
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
    }

    @Override
    public void onPause() {

        super.onPause();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

    }







}