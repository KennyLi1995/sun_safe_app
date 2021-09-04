package com.example.sun_safe_app.ui.sunscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentQuizBinding;
import com.example.sun_safe_app.databinding.FragmentSunscreenBinding;
import com.example.sun_safe_app.utils.CommonDialog;
import com.example.sun_safe_app.utils.SkinTypeDialog;

public class SunscreenFragment extends Fragment {

    private SunscreenViewModel sunscreenViewModel;
    private FragmentSunscreenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sunscreen, container, false);
        binding = FragmentSunscreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("ifInput",false)) {


            binding.walkingActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    CommonDialog dialog = new CommonDialog(getContext());
                    dialog.setTitle("Not create user profile");
                    dialog.setMessage2("To calculate your sunscreen amount, You need to create your user profile first");
                    dialog.setCancel("Not now", new CommonDialog.OnCancelListener() {
                        @Override
                        public void onCancel(CommonDialog dialog) {
                        }
                    });
                    dialog.setConfirm("Do it now", new CommonDialog.OnConfirmListener() {
                        @Override
                        public void onConfirm(CommonDialog dialog) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.onClickItem(2);
                        }
                    });
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();


                }
            });

        }
        else{
            binding.walkingActivity.setOnClickListener(
                    Navigation.createNavigateOnClickListener(R.id.chooseClothesFragment, null)
            );
        }

        return view;
    }
}