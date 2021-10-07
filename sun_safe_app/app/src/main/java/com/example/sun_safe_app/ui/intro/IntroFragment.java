package com.example.sun_safe_app.ui.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sun_safe_app.databinding.ActivityIntroBinding;
import com.example.sun_safe_app.databinding.FragmentIntroBinding;

public class IntroFragment extends Fragment {
    private FragmentIntroBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentIntroBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        return view;

    }



}
