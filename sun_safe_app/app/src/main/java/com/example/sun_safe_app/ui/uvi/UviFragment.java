package com.example.sun_safe_app.ui.uvi;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UviFragment extends Fragment {

    private UviFragmentModel UviFragmentModel;
    private RetrofitInterface retrofitInterface;
    private FragmentUviBinding binding;

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




//        binding.animationView.setAnimation(AppUtil.getWeatherAnimation(500));
//        binding.animationView.playAnimation();

        UviFragmentModel vm =  new ViewModelProvider(getActivity()).get(UviFragmentModel.class);;
        vm.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.addressText.setText(s);
            }
        });


        // get the weather message using retrofit
        retrofitInterface = RetrofitClient.getRetrofitService();
        Call<WeatherResponse> callAsync =
                retrofitInterface.weatherSearch("-37.8136",
                        "144.9631","metric","",
                        "a10215765c5afc9587cd4e6924b695db");
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






        return view;
    }





}