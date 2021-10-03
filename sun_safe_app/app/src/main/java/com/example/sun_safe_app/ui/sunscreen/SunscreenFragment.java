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
import com.example.sun_safe_app.utils.SunScreenResultDialog;

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
        SharedPreferences sharedPref2= getActivity().
                getSharedPreferences("Default", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("ifInput",false) || sharedPref2.getInt("skinType",0) == 0) {


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
                            activity.onClickItem(4);
                        }
                    });
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();


                }
            });

            binding.swimmingActivity.setOnClickListener(new View.OnClickListener() {
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
                            activity.onClickItem(3);
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
            binding.swimmingActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int strHeight = sharedPref.getInt("height", 0);
                    int strWeight = sharedPref.getInt("weight", 0);

                    double strBSA = 0.007184  * Math.pow(strHeight,0.725) * Math.pow(strWeight,0.425) * 10000;
//                    double valHeadNum = valHead.get(headCount);
//                    double valShoeNum = valShoe.get(feetCount);
//                    double valShirtNum;
//                    double valPantNum;
//                    if (sharedPref.getString("gender","Male").equals("Male")){
//                        valShirtNum = valMShirt.get(maleBodyCount);
//                        valPantNum = valMPant.get(maleLegCount);
//                    }
//                    else{
//                        valShirtNum = valFShirt.get(femaleBodyCount);
//                        valPantNum = valFPant.get(femaleLegCount);
//                    }
//                    double strBodyCovered = valHeadNum + valShoeNum + valShirtNum + valPantNum;
                    double strAmount = Math.round((strBSA) * 0.002);
                    double strTeaspoon = (strAmount/5)-(strAmount-(strAmount%5))/5;
                    if(strTeaspoon==0) {
                        strTeaspoon = strAmount/5;
                    }
                    else if(strTeaspoon>0.5) {
                        strTeaspoon = ((strAmount-(strAmount%5))/5) + 1;
                    }
                    else {
                        strTeaspoon = ((strAmount-(strAmount%5))/5) + 0.5;
                    }


                    SunScreenResultDialog dialog = new SunScreenResultDialog(getContext());
                    dialog.setMessage2((int) strTeaspoon + " teaspoons (" + (int) strAmount + " ml)");
                    int spf = 15;
                    SharedPreferences sharedPref2= getActivity().
                            getSharedPreferences("Default", Context.MODE_PRIVATE);
                    SharedPreferences sharedPref3= getActivity().
                            getSharedPreferences("Sunscreen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sharedPref3.edit();

                    int skinType = sharedPref2.getInt("skinType",0);
                    String s = "";
                    if (skinType == 1 || skinType == 2 || skinType == 0) {
                        spf = 50;
                        s = "Waterproof sunscreen with SPF 50 or higher";
                    }
                    else if (skinType == 3 || skinType == 4) {
                        spf = 30;
                        s = "Waterproof sunscreen with SPF 30 or higher";
                    }
                    else if (skinType == 5 || skinType == 6) {
                        spf = 15;
                        s = "Waterproof sunscreen with SPF 15 or higher";
                    }
                    spEditor.putString("spfRecommendation", s);
                    spEditor.putString("sunscreenAmount",
                            (int) strTeaspoon + " teaspoons (" + (int) strAmount + " ml)");
                    spEditor.apply();
                    dialog.setImageView1(spf);
                    dialog.setImageView2(true);
                    dialog.setMessage1(s);
                    dialog.setConfirm("OK", new SunScreenResultDialog.OnConfirmListener() {
                        @Override
                        public void onConfirm(SunScreenResultDialog dialog) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.onClickItem(2);
                        }
                    });
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();



                }
            });
        }

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });

        return view;
    }
}