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

import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentClothBinding;
import com.example.sun_safe_app.databinding.FragmentMySkinBinding;
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

        ArrayList<Drawable> shoes = new ArrayList<>();
        shoes.add(null);
        shoes.add(getResources().getDrawable(R.drawable.shoe1_92x76));
        shoes.add(getResources().getDrawable(R.drawable.shoe2_84x78));

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






        if (sharedPref.getString("gender","Male").equals("Male")) {
            binding.realModel.setBackground(getResources().getDrawable(R.drawable.ic_male_model));
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




        return view;
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