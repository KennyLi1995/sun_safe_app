package com.example.sun_safe_app.ui.uvForecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sun_safe_app.databinding.FragmentActivityDataBinding;
import com.example.sun_safe_app.databinding.FragmentUvForecastBinding;

public class uvForecast extends Fragment {
    private FragmentUvForecastBinding binding;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUvForecastBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        return view;

    }


}
