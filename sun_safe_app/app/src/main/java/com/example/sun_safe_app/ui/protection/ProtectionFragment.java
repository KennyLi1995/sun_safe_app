package com.example.sun_safe_app.ui.protection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentProtectionBinding;
import com.example.sun_safe_app.databinding.FragmentSunscreenBinding;
import com.example.sun_safe_app.utils.CommonDialog;

public class ProtectionFragment extends Fragment {
    private FragmentProtectionBinding binding;
    int spf = 1;
    int safeTime = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProtectionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        SharedPreferences sharedPref3= getActivity().
                getSharedPreferences("Sunscreen", Context.MODE_PRIVATE);
        String s = sharedPref3.getString("sunscreenAmount","");
        if(!s.equals("")){
            binding.calculatorHint.setVisibility(View.GONE);
            binding.sunScreenAmount.setVisibility(View.VISIBLE);
            binding.sunScreenTypeHint.setVisibility(View.VISIBLE);
            binding.sunScreenAmount.setText(s);
            String s2 = sharedPref3.getString("spfRecommendation","");
            binding.sunScreenTypeHint.setText("of " +s2);
            binding.starttText.setText("Retry");

        }

        binding.start.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.navigation_sunscreen, null));
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        SharedPreferences sharedPref2= getActivity().
                getSharedPreferences("Default", Context.MODE_PRIVATE);
        if (sharedPref2.getInt("skinType",0) == 0) {
            binding.informationCard.setVisibility(View.GONE);
            binding.adviceCard.setVisibility(View.GONE);
            binding.sunsafeteCard.setVisibility(View.GONE);
            binding.trackingCard.setVisibility(View.GONE);
            binding.goToText.setVisibility(View.VISIBLE);
            binding.todo.setVisibility(View.VISIBLE);

        }
        else{
            binding.informationCard.setVisibility(View.VISIBLE);
            binding.adviceCard.setVisibility(View.VISIBLE);
            binding.sunsafeteCard.setVisibility(View.VISIBLE);
            binding.trackingCard.setVisibility(View.VISIBLE);
            binding.goToText.setVisibility(View.GONE);
            binding.todo.setVisibility(View.GONE);
            updateSafeTime();
        }
        binding.starttText2.setText(sharedPref2.getString("track","Begin Tracking"));
        binding.todo.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.navigation_my_skin, null));


        binding.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spf = 1;
                    updateSafeTime();
                }
                else{

                }
            }
        });
        binding.radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spf = 15;
                    updateSafeTime();
                }
                else{

                }
            }
        });
        binding.radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spf = 30;
                    updateSafeTime();
                }
                else{

                }
            }
        });
        binding.radioButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spf = 50;
                    updateSafeTime();
                }
                else{

                }
            }
        });
        int chosenSpf = sharedPref2.getInt("chosenSpf",0);
        if (chosenSpf == 0){
            binding.radioButton.setChecked(true);
        }
        else if (chosenSpf == 1){
            binding.radioButton2.setChecked(true);
        }
        else if (chosenSpf == 2){
            binding.radioButton3.setChecked(true);
        }
        else if (chosenSpf == 3){
            binding.radioButton4.setChecked(true);
        }
        updateAdvice();
        binding.checkBox1.setChecked(sharedPref2.getBoolean("checkBox1",true));
        binding.checkBox2.setChecked(sharedPref2.getBoolean("checkBox2",true));
        binding.timeEdit2.setText(sharedPref2.getInt("inputmins",120) + "");
        binding.startTracking.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if (sharedPref2.getString("track","Begin Tracking").equals("Begin Tracking")){
                boolean ifChecked1 = binding.checkBox1.isChecked();
                boolean ifChecked2 = binding.checkBox2.isChecked();
                boolean check = true;
                    String errorMessage = "";

                if (!ifChecked1 && !ifChecked2){
                    check = false;
                    errorMessage += "\n*You must select at least one option to be tracked";
                }

                if (ifChecked1){
                    int sunscreenmin = Integer.parseInt(binding.timeEdit2.getText().toString());
                    if (sunscreenmin < 10 || sunscreenmin > 480){
                        check = false;
                        errorMessage += "\n*The time interval of sunscreen noticification must between 10 mins and 480 mins";
                    }
                }


                if (check) {
                    binding.errorMessage.setVisibility(View.GONE);

                    int n = 0;
                    if (binding.radioButton.isChecked())
                        n = 0;
                    else if (binding.radioButton2.isChecked())
                        n = 1;
                    else if (binding.radioButton3.isChecked())
                        n = 2;
                    else if (binding.radioButton4.isChecked())
                        n = 3;
                    SharedPreferences.Editor spEditor = sharedPref2.edit();
                    spEditor.putInt("chosenSpf", n);
                    spEditor.putBoolean("checkBox1",ifChecked1);
                    spEditor.putBoolean("checkBox2",ifChecked2);
                    spEditor.putInt("inputmins",Integer.parseInt(binding.timeEdit2.getText().toString()));
                    spEditor.apply();


                    if (ifChecked1) {
                        MainActivity parentActivity = (MainActivity) getActivity();
                        int sunscreenmin = 120;
                        try {
                            sunscreenmin = Integer.parseInt(binding.timeEdit2.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            sunscreenmin = 120;
                        }
                        parentActivity.setAlarmForSunscreen(true, sunscreenmin);

                    }
                    if (ifChecked2) {
                        if (safeTime > 0) {
                            MainActivity parentActivity = (MainActivity) getActivity();
                            parentActivity.setAlarmForSunSafeTime(true, safeTime);
                        }

                    }

                    binding.starttText2.setText("Stop tracking");
                    spEditor.putLong("startTrackTime",System.currentTimeMillis());
                    spEditor.putString("track","Stop tracking");
                    spEditor.apply();
                }
                else{
                    binding.errorMessage.setVisibility(View.VISIBLE);
                    binding.errorMessage.setText(errorMessage);
                }
                }
                else {
                    binding.starttText2.setText("Begin Tracking");
                    SharedPreferences.Editor spEditor = sharedPref2.edit();
                    spEditor.putString("track","Begin Tracking");
                    boolean ifChecked1 = binding.checkBox1.isChecked();
                    boolean ifChecked2 = binding.checkBox2.isChecked();
                    spEditor.putBoolean("checkBox1",ifChecked1);
                    spEditor.putBoolean("checkBox2",ifChecked2);
                    spEditor.putInt("inputmins",Integer.parseInt(binding.timeEdit2.getText().toString()));
                    spEditor.apply();
                    MainActivity parentActivity = (MainActivity) getActivity();
                    parentActivity.setAlarmForSunscreen(false, 0);
                    parentActivity.setAlarmForSunSafeTime(false, 0);

                }
            }
        });

        return view;
    }




    public void updateSafeTime(){
        SharedPreferences sharedPref2= getActivity().
                getSharedPreferences("Default", Context.MODE_PRIVATE);
        int skinType = sharedPref2.getInt("skinType",0);
        float preUvi = sharedPref2.getFloat("preUvi",0);
        int safeMin = calculateSunSafeExporsureTime(spf, skinType, preUvi);

        if (safeMin <= 480) {
            binding.nonsafe.setVisibility(View.VISIBLE);
            binding.safe.setVisibility(View.GONE);
            int hour = safeMin/60;
            int min = safeMin - hour * 60;
            binding.Hour.setText(hour + "");
            binding.Min.setText(min + "");
            binding.sunsafeteCard.setCardBackgroundColor(0xFFFFEBEB);
            safeTime = safeMin;
        }
        else {
            binding.safe.setVisibility(View.VISIBLE);
            binding.nonsafe.setVisibility(View.GONE);
            safeTime = -1;
            binding.sunsafeteCard.setCardBackgroundColor(0xFFF0FFD3);
        }
    }

    public void updateAdvice(){
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        float preUvi = sharedPref.getFloat("preUvi",0);

        if (preUvi <= 2){
            binding.protection.setText("No protection required");
            binding.adviceText.setText("You can safely stay outside");
            binding.adviceCard.setCardBackgroundColor(0xFFF0FFD3);
        }
        else if (preUvi <= 7){
            binding.protection.setText("Protection required");
            binding.adviceText.setText("●Seek shade during midday hours! \n●Slip on a shirt, slop on sunscreen and slap on a hat!");
            binding.adviceCard.setCardBackgroundColor(0xFFFFF5E6);
        }
        else {
            binding.protection.setText("Extra protection");
            binding.adviceText.setText("●Avoid being outside during midday hours! \n●Make sure you seek shade! Shirt, sunscreen and hat are a must!");
            binding.adviceCard.setCardBackgroundColor(0xFFFFEBEB);
        }

    }

    public int calculateSunSafeExporsureTime(int spf,int skinType, double uv){
        double safeMin = 0;
            if (skinType == 1)
                safeMin = spf * (200 * 2.5) / (3 * uv);
            else if (skinType == 2)
                safeMin = spf * (200 * 3) / (3 * uv);
            else if (skinType == 3)
                safeMin = spf * (200 * 4) / (3 * uv);
            else if (skinType == 4)
                safeMin = spf * (200 * 5) / (3 * uv);
            else if (skinType == 5)
                safeMin = spf * (200 * 8) / (3 * uv);
            else if (skinType == 6)
                safeMin = spf * (200 * 15) / (3 * uv);
        int safeMinInt = (int) safeMin;
        return safeMinInt;
    }
}
