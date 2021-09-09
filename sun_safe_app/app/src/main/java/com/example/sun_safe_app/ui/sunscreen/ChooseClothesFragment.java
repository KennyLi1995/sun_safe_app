package com.example.sun_safe_app.ui.sunscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentClothBinding;
import com.example.sun_safe_app.databinding.FragmentMySkinBinding;
import com.example.sun_safe_app.utils.CommonDialog;
import com.example.sun_safe_app.utils.SunScreenResultDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ChooseClothesFragment extends Fragment {


    FragmentClothBinding binding;
    int headCount = 0;
    int maleBodyCount = 0;
    int maleLegCount = 0;
    int femaleBodyCount = 0;
    int femaleLegCount = 0;
    int feetCount = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cloth, container, false);
        binding = FragmentClothBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });


//        View app = view.findViewById(R.id.app_bar);
//        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
//        int height = (int) navView.getMeasuredHeight();
//        int top = (int )app.getHeight();
//        ScrollView layout = view.findViewById(R.id.mainLayout);
//        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 400, 0, height);
//        layout.setLayoutParams(lp);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        // Gets linearlayout
        ScrollView layout = view.findViewById(R.id.mainLayout);
        // Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = height + 200;
        params.width = width;
        layout.setLayoutParams(params);



        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);

        ArrayList<Drawable> hats = new ArrayList<>();
        hats.add(null);
        hats.add(getResources().getDrawable(R.drawable.ic_hat));

        ArrayList<Double> valHead = new ArrayList<>();
        valHead.add(0.0);
        valHead.add(0.036);

        ArrayList<Drawable> shoes = new ArrayList<>();
        shoes.add(null);
        shoes.add(getResources().getDrawable(R.drawable.shoe1_92x76));
        shoes.add(getResources().getDrawable(R.drawable.shoe2_84x78));


        ArrayList<Double> valShoe = new ArrayList<>();
        valShoe.add(0.0);
        valShoe.add(0.0);
        valShoe.add(0.035);

        binding.prevHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headCount --;
                if (headCount < 0){
                    headCount = hats.size() - 1;
                }
                binding.head.setBackground(hats.get(headCount));

            }
        });

        binding.nextHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headCount ++;
                if (headCount > hats.size() - 1){
                    headCount = 0;
                }
                binding.head.setBackground(hats.get(headCount));

            }
        });

        binding.preFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feetCount --;
                if (feetCount < 0){
                    feetCount = shoes.size() - 1;
                }
                binding.shoe.setBackground(shoes.get(feetCount));

            }
        });

        binding.nextFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feetCount ++;
                if (feetCount > shoes.size() - 1){
                    feetCount = 0;
                }
                binding.shoe.setBackground(shoes.get(feetCount));

            }
        });




//        ArrayList<Drawable> maleBody = new ArrayList<>();
//        maleBody.add(null);
//        maleBody.add(getResources().getDrawable(R.drawable.ic_male_cloth1));
//        maleBody.add(getResources().getDrawable(R.drawable.ic_male_cloth2));
//        maleBody.add(getResources().getDrawable(R.drawable.male_cloth3_99x135));


        ArrayList<Double> valMShirt = new ArrayList<>();
        valMShirt.add(0.0);
        valMShirt.add(0.404);
        valMShirt.add(0.35);
        valMShirt.add(0.266);



//        ArrayList<Drawable> maleLeg = new ArrayList<>();
//        maleLeg.add(null);
//        maleLeg.add(getResources().getDrawable(R.drawable.ic_male_trousers1));
//        maleLeg.add(getResources().getDrawable(R.drawable.ic_male_trousers2));
//        maleLeg.add(getResources().getDrawable(R.drawable.ic_male_trousers3));
//        maleLeg.add(getResources().getDrawable(R.drawable.ic_male_trousers4_78x46));

        ArrayList<Double> valMPant = new ArrayList<>();
        valMPant.add(0.0);
        valMPant.add(0.437);
        valMPant.add(0.292);
        valMPant.add(0.213);
        valMPant.add(0.104);

//        ArrayList<Drawable> femaleBody = new ArrayList<>();
//        femaleBody.add(null);
//        femaleBody.add(getResources().getDrawable(R.drawable.female_cloth1_141x161));
//        femaleBody.add(getResources().getDrawable(R.drawable.ic_female_cloth2));
//        femaleBody.add(getResources().getDrawable(R.drawable.ic_female_cloth3));
//        femaleBody.add(getResources().getDrawable(R.drawable.female_cloth4_79x173));
//        femaleBody.add(getResources().getDrawable(R.drawable.female_cloth5_81x167));

        ArrayList<Double> valFShirt = new ArrayList<>();
        valFShirt.add(0.0);
        valFShirt.add(0.404);
        valFShirt.add(0.35);
        valFShirt.add(0.266);
        valFShirt.add(0.4);
        valFShirt.add(0.134);


        ArrayList<Drawable> femaleLeg = new ArrayList<>();
        femaleLeg.add(null);
        femaleLeg.add(getResources().getDrawable(R.drawable.ic_female_dress1));
        femaleLeg.add(getResources().getDrawable(R.drawable.ic_female_dress2));
        femaleLeg.add(getResources().getDrawable(R.drawable.ic_female_dress3));

        ArrayList<Double> valFPant = new ArrayList<>();
        valFPant.add(0.0);
        valFPant.add(0.437);
        valFPant.add(0.292);
        valFPant.add(0.213);

        updateUI(view);

        binding.Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int strHeight = sharedPref.getInt("height", 0);
                int strWeight = sharedPref.getInt("weight", 0);

                double strBSA = 0.007184  * Math.pow(strHeight,0.725) * Math.pow(strWeight,0.425) * 10000;
                double valHeadNum = valHead.get(headCount);
                double valShoeNum = valShoe.get(feetCount);
                double valShirtNum;
                double valPantNum;
                if (sharedPref.getString("gender","Male").equals("Male")){
                    valShirtNum = valMShirt.get(maleBodyCount);
                    valPantNum = valMPant.get(maleLegCount);
                }
                else{
                    valShirtNum = valFShirt.get(femaleBodyCount);
                    valPantNum = valFPant.get(femaleLegCount);
                }

                double strBodyCovered = valHeadNum + valShoeNum + valShirtNum + valPantNum;
                double strAmount = Math.round((strBSA * (1 - strBodyCovered)) * 0.002);
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
                    s = "Sunscreen with SPF 50 or higher";
                }
                else if (skinType == 3 || skinType == 4) {
                    spf = 30;
                    s = "Sunscreen with SPF 30 or higher";
                }
                else if (skinType == 5 || skinType == 6) {
                    spf = 15;
                    s = "Sunscreen with SPF 15 or higher";
                }
                spEditor.putString("spfRecommendation", s);
                spEditor.putString("sunscreenAmount",
                        (int) strTeaspoon + " teaspoons (" + (int) strAmount + " ml)");
                spEditor.apply();
                dialog.setImageView1(spf);
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

         binding.genderSwitch.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SharedPreferences sharedPref= getActivity().
                         getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                 if (sharedPref.getString("gender","Male").equals("Male")){
                     SharedPreferences.Editor spEditor = sharedPref.edit();
                     spEditor.putString("gender", "Female");
                     spEditor.apply();
                     binding.genderSwitchText.setText("Women Clothes");
                     maleLegCount = 0;
                     maleBodyCount = 0;
                     changeMaleBody(view);
                     changeMaleLeg(view);
                     updateUI(view);
                 }
                 else if (sharedPref.getString("gender","Male").equals("Female")){
                     SharedPreferences.Editor spEditor = sharedPref.edit();
                     spEditor.putString("gender", "Male");
                     spEditor.apply();
                     binding.genderSwitchText.setText("Men Clothes");
                     femaleLegCount = 0;
                     femaleBodyCount = 0;
                     changeFemaleBody(view);
                     changeFeMaleLeg(view);
                     updateUI(view);
                 }
             }
         });




        return view;
    }

    public void updateUI(View view){

        ArrayList<Drawable> maleBody = new ArrayList<>();
        maleBody.add(null);
        maleBody.add(getResources().getDrawable(R.drawable.ic_male_cloth1));
        maleBody.add(getResources().getDrawable(R.drawable.ic_male_cloth2));
        maleBody.add(getResources().getDrawable(R.drawable.male_cloth3_99x135));

        ArrayList<Drawable> maleLeg = new ArrayList<>();
        maleLeg.add(null);
        maleLeg.add(getResources().getDrawable(R.drawable.ic_male_trousers1));
        maleLeg.add(getResources().getDrawable(R.drawable.ic_male_trousers2));
        maleLeg.add(getResources().getDrawable(R.drawable.ic_male_trousers3));
        maleLeg.add(getResources().getDrawable(R.drawable.ic_male_trousers4_78x46));

        ArrayList<Drawable> femaleBody = new ArrayList<>();
        femaleBody.add(null);
        femaleBody.add(getResources().getDrawable(R.drawable.female_cloth1_141x161));
        femaleBody.add(getResources().getDrawable(R.drawable.ic_female_cloth2));
        femaleBody.add(getResources().getDrawable(R.drawable.ic_female_cloth3));
        femaleBody.add(getResources().getDrawable(R.drawable.female_cloth4_79x173));
        femaleBody.add(getResources().getDrawable(R.drawable.female_cloth5_81x167));

        ArrayList<Drawable> femaleLeg = new ArrayList<>();
        femaleLeg.add(null);
        femaleLeg.add(getResources().getDrawable(R.drawable.ic_female_dress1));
        femaleLeg.add(getResources().getDrawable(R.drawable.ic_female_dress2));
        femaleLeg.add(getResources().getDrawable(R.drawable.ic_female_dress3));


        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);


        if (sharedPref.getString("gender","Male").equals("Male")) {
            binding.realModel.setBackground(getResources().getDrawable(R.drawable.ic_male_model));
            binding.realModel.setImageDrawable(null);
            binding.preBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    maleBodyCount --;
                    if (maleBodyCount < 0){
                        maleBodyCount = maleBody.size() - 1;
                    }
                    changeMaleBody(view);
                }
            });

            binding.nextBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    maleBodyCount ++;
                    if (maleBodyCount > maleBody.size() - 1){
                        maleBodyCount = 0;
                    }
                    changeMaleBody(view);
                }
            });

            binding.preLeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    maleLegCount --;
                    if (maleLegCount < 0){
                        maleLegCount = maleLeg.size() - 1;
                    }
                    changeMaleLeg(view);

                }
            });

            binding.nextLeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    maleLegCount ++;
                    if (maleLegCount > maleLeg.size() - 1){
                        maleLegCount = 0;
                    }
                    changeMaleLeg(view);
                }
            });



        }
        else{
            binding.realModel.setBackground(null);
            binding.realModel.setImageDrawable(getResources().getDrawable(R.drawable.female_model));

            binding.preBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    femaleBodyCount --;
                    if (femaleBodyCount < 0){
                        femaleBodyCount = femaleBody.size() - 1;
                    }
                    changeFemaleBody(view);

                }
            });

            binding.nextBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    femaleBodyCount ++;
                    if (femaleBodyCount > femaleBody.size() - 1){
                        femaleBodyCount = 0;
                    }
                    changeFemaleBody(view);
                }
            });

            binding.preLeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    femaleLegCount --;
                    if (femaleLegCount < 0){
                        femaleLegCount = femaleLeg.size() - 1;
                    }
                    changeFeMaleLeg(view);

                }
            });

            binding.nextLeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    femaleLegCount ++;
                    if (femaleLegCount > femaleLeg.size() - 1){
                        femaleLegCount = 0;
                    }
                    changeFeMaleLeg(view);
                }
            });

        }


    }

    public void changeMaleBody(View view) {

        if (maleBodyCount == 1) {
            binding.icMaleCloth2.setVisibility(View.GONE);
            binding.icMaleCloth3.setVisibility(View.GONE);
            binding.icMaleCloth1.setVisibility(View.VISIBLE);
        } else if (maleBodyCount == 2) {
            binding.icMaleCloth1.setVisibility(View.GONE);
            binding.icMaleCloth3.setVisibility(View.GONE);
            binding.icMaleCloth2.setVisibility(View.VISIBLE);
        } else if (maleBodyCount == 3) {
            binding.icMaleCloth1.setVisibility(View.GONE);
            binding.icMaleCloth2.setVisibility(View.GONE);
            binding.icMaleCloth3.setVisibility(View.VISIBLE);
        } else if (maleBodyCount == 0) {
            binding.icMaleCloth1.setVisibility(View.GONE);
            binding.icMaleCloth2.setVisibility(View.GONE);
            binding.icMaleCloth3.setVisibility(View.GONE);
        }
    }

        public void changeMaleLeg(View view) {

            if (maleLegCount == 1) {
                binding.icMaleTrousers1.setVisibility(View.VISIBLE);
                binding.icMaleTrousers2.setVisibility(View.GONE);
                binding.icMaleTrousers3.setVisibility(View.GONE);
                binding.icMaleTrousers4.setVisibility(View.GONE);

            }
            else if (maleLegCount == 2){
                binding.icMaleTrousers1.setVisibility(View.GONE);
                binding.icMaleTrousers2.setVisibility(View.VISIBLE);
                binding.icMaleTrousers3.setVisibility(View.GONE);
                binding.icMaleTrousers4.setVisibility(View.GONE);

            }
            else if (maleLegCount == 3){
                binding.icMaleTrousers1.setVisibility(View.GONE);
                binding.icMaleTrousers2.setVisibility(View.GONE);
                binding.icMaleTrousers3.setVisibility(View.VISIBLE);
                binding.icMaleTrousers4.setVisibility(View.GONE);

            }
            else if (maleLegCount == 4){
                binding.icMaleTrousers1.setVisibility(View.GONE);
                binding.icMaleTrousers2.setVisibility(View.GONE);
                binding.icMaleTrousers3.setVisibility(View.GONE);
                binding.icMaleTrousers4.setVisibility(View.VISIBLE);

            }
            else if (maleLegCount == 0){
                binding.icMaleTrousers1.setVisibility(View.GONE);
                binding.icMaleTrousers2.setVisibility(View.GONE);
                binding.icMaleTrousers3.setVisibility(View.GONE);
                binding.icMaleTrousers4.setVisibility(View.GONE);
            }
    }

    public void changeFemaleBody(View view) {

        if (femaleBodyCount == 1) {
            binding.icFemaleCloth1.setVisibility(View.VISIBLE);
            binding.icFemaleCloth2.setVisibility(View.GONE);
            binding.icFemaleCloth3.setVisibility(View.GONE);
            binding.icFemaleCloth4.setVisibility(View.GONE);
            binding.icFemaleCloth5.setVisibility(View.GONE);
        }
        else if (femaleBodyCount == 2){
            binding.icFemaleCloth1.setVisibility(View.GONE);
            binding.icFemaleCloth2.setVisibility(View.VISIBLE);
            binding.icFemaleCloth3.setVisibility(View.GONE);
            binding.icFemaleCloth4.setVisibility(View.GONE);
            binding.icFemaleCloth5.setVisibility(View.GONE);
        }
        else if (femaleBodyCount == 3){
            binding.icFemaleCloth1.setVisibility(View.GONE);
            binding.icFemaleCloth2.setVisibility(View.GONE);
            binding.icFemaleCloth3.setVisibility(View.VISIBLE);
            binding.icFemaleCloth4.setVisibility(View.GONE);
            binding.icFemaleCloth5.setVisibility(View.GONE);
        }
        else if (femaleBodyCount == 4){
            binding.icFemaleCloth1.setVisibility(View.GONE);
            binding.icFemaleCloth2.setVisibility(View.GONE);
            binding.icFemaleCloth3.setVisibility(View.GONE);
            binding.icFemaleCloth4.setVisibility(View.VISIBLE);
            binding.icFemaleCloth5.setVisibility(View.GONE);
        }
        else if (femaleBodyCount == 5){
            binding.icFemaleCloth1.setVisibility(View.GONE);
            binding.icFemaleCloth2.setVisibility(View.GONE);
            binding.icFemaleCloth3.setVisibility(View.GONE);
            binding.icFemaleCloth4.setVisibility(View.GONE);
            binding.icFemaleCloth5.setVisibility(View.VISIBLE);
        }

        else if (femaleBodyCount == 0){
            binding.icFemaleCloth1.setVisibility(View.GONE);
            binding.icFemaleCloth2.setVisibility(View.GONE);
            binding.icFemaleCloth3.setVisibility(View.GONE);
            binding.icFemaleCloth4.setVisibility(View.GONE);
            binding.icFemaleCloth5.setVisibility(View.GONE);



        }
    }

    public void changeFeMaleLeg(View view) {

        if (femaleLegCount == 1) {
            binding.icFemaleTrousers1.setVisibility(View.VISIBLE);
            binding.icFemaleTrousers2.setVisibility(View.GONE);
            binding.icFemaleTrousers3.setVisibility(View.GONE);



        }
        else if (femaleLegCount == 2){
            binding.icFemaleTrousers1.setVisibility(View.GONE);
            binding.icFemaleTrousers2.setVisibility(View.VISIBLE);
            binding.icFemaleTrousers3.setVisibility(View.GONE);


        }
        else if (femaleLegCount == 3){
            binding.icFemaleTrousers1.setVisibility(View.GONE);
            binding.icFemaleTrousers2.setVisibility(View.GONE);
            binding.icFemaleTrousers3.setVisibility(View.VISIBLE);


        }

        else if (femaleLegCount == 0){
            binding.icFemaleTrousers1.setVisibility(View.GONE);
            binding.icFemaleTrousers2.setVisibility(View.GONE);
            binding.icFemaleTrousers3.setVisibility(View.GONE);

        }
    }
}