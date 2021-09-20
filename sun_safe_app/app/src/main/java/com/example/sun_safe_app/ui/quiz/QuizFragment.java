package com.example.sun_safe_app.ui.quiz;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentMySkinBinding;
import com.example.sun_safe_app.databinding.FragmentQuizBinding;
import com.example.sun_safe_app.databinding.FragmentUviBinding;
import com.example.sun_safe_app.ui.mySkin.MySkinFragment;
import com.example.sun_safe_app.ui.uvi.UviFragmentModel;
import com.example.sun_safe_app.utils.CommonDialog;
import com.example.sun_safe_app.utils.SkinTypeDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;
    private int currentPage;
    private ArrayList<String > questions;
    private ArrayList<ArrayList<String>> answers;
    private HashMap<Integer,Integer> points;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        navView.setVisibility(View.GONE);
        currentPage = 0;
        points = new HashMap<Integer,Integer>();
        createQA();
        binding.image1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                    currentPage = 0;
                    upDateUI();
            }

        });
        binding.image2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance

                    currentPage = 1;
                    upDateUI();


            }

        });
        binding.image3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance


                    currentPage = 2;
                    upDateUI();



            }

        });
        binding.image4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance


                    currentPage = 3;
                    upDateUI();

            }

        });

        binding.prev.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    currentPage -= 1;
                    upDateUI();
                    button.setSelected(!button.isSelected());


                } else {
                    //Handle de-select state change
                }

            }

        });
        binding.nextButton.bringToFront();
        binding.prev.bringToFront();

        binding.LongtBar.setProgress(25);
        binding.shortBar.setProgress(100);

        binding.nextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    currentPage += 1;
                    upDateUI();
                    button.setSelected(!button.isSelected());


                } else {
                    //Handle de-select state change
                }

            }

        });


        binding.buttonA.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonB.setSelected(false);
                    binding.buttonC.setSelected(false);
                    binding.buttonD.setSelected(false);
                    binding.buttonE.setSelected(false);
                    points.put(2*currentPage,0);


                } else {
                    points.remove(2*currentPage);
                }
                updateNumber();

            }

        });
        binding.buttonB.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonA.setSelected(false);
                    binding.buttonC.setSelected(false);
                    binding.buttonD.setSelected(false);
                    binding.buttonE.setSelected(false);
                    points.put(2*currentPage,1);



                } else {
                    //Handle de-select state change
                    points.remove(2*currentPage);
                }
                updateNumber();


            }

        });
        binding.buttonC.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonB.setSelected(false);
                    binding.buttonA.setSelected(false);
                    binding.buttonD.setSelected(false);
                    binding.buttonE.setSelected(false);
                    points.put(2*currentPage,2);



                } else {
                    //Handle de-select state change
                    points.remove(2*currentPage);

                }
                updateNumber();


            }

        });
        binding.buttonD.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonB.setSelected(false);
                    binding.buttonC.setSelected(false);
                    binding.buttonA.setSelected(false);
                    binding.buttonE.setSelected(false);
                    points.put(2*currentPage,3);



                } else {
                    points.remove(2*currentPage);

                }
                updateNumber();


            }

        });
        binding.buttonE.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonB.setSelected(false);
                    binding.buttonC.setSelected(false);
                    binding.buttonD.setSelected(false);
                    binding.buttonA.setSelected(false);
                    points.put(2*currentPage,4);



                } else {
                    points.remove(2*currentPage);
                }
                updateNumber();


            }

        });
        binding.buttonA2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonB2.setSelected(false);
                    binding.buttonC2.setSelected(false);
                    binding.buttonD2.setSelected(false);
                    binding.buttonE2.setSelected(false);
                    points.put(2*currentPage + 1,0);



                } else {
                    points.remove(2*currentPage + 1);

                }
                updateNumber();


            }

        });
        binding.buttonB2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonA2.setSelected(false);
                    binding.buttonC2.setSelected(false);
                    binding.buttonD2.setSelected(false);
                    binding.buttonE2.setSelected(false);
                    points.put(2*currentPage + 1,1);



                } else {
                    points.remove(2*currentPage + 1);
                }
                updateNumber();


            }

        });
        binding.buttonC2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonA2.setSelected(false);
                    binding.buttonB2.setSelected(false);
                    binding.buttonD2.setSelected(false);
                    binding.buttonE2.setSelected(false);
                    points.put(2*currentPage + 1,2);



                } else {
                    points.remove(2*currentPage + 1);
                }
                updateNumber();


            }

        });
        binding.buttonD2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonA2.setSelected(false);
                    binding.buttonC2.setSelected(false);
                    binding.buttonB2.setSelected(false);
                    binding.buttonE2.setSelected(false);
                    points.put(2*currentPage + 1,3);



                } else {
                    points.remove(2*currentPage + 1);
                }
                updateNumber();


            }

        });
        binding.buttonE2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View button) {
                //Set the button's appearance
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    binding.buttonA2.setSelected(false);
                    binding.buttonC2.setSelected(false);
                    binding.buttonD2.setSelected(false);
                    binding.buttonB2.setSelected(false);
                    points.put(2*currentPage + 1,4);



                } else {
                    points.remove(2*currentPage + 1);
                }
                updateNumber();


            }


        });

        binding.optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonA.callOnClick();
            }
        });
        binding.optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonB.callOnClick();
            }
        });
        binding.optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonC.callOnClick();
            }
        });
        binding.optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonD.callOnClick();
            }
        });
        binding.optionE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonE.callOnClick();
            }
        });

        binding.optionA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonA2.callOnClick();
            }
        });
        binding.optionB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonB2.callOnClick();
            }
        });
        binding.optionC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonC2.callOnClick();
            }
        });
        binding.optionD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonD2.callOnClick();
            }
        });
        binding.optionE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonE2.callOnClick();
            }
        });




        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int totalPoints = 0;
                for(Map.Entry<Integer, Integer> entry:  points.entrySet()){
                    totalPoints += entry.getValue();


                }
                int skinType = changeSkinType(totalPoints);
                SkinTypeDialog dialog=new SkinTypeDialog(getContext());
                dialog.setTitle("Result");
                dialog.setMessage1("");
                dialog.setCancel("Back", new SkinTypeDialog.OnCancelListener() {
                    @Override
                    public void onCancel(SkinTypeDialog dialog) {
                    }
                });
                dialog.setConfirm("Done", new SkinTypeDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(SkinTypeDialog dialog) {
                        SharedPreferences sharedPref= getActivity().
                        getSharedPreferences("Default", Context.MODE_PRIVATE);
                        SharedPreferences.Editor spEditor = sharedPref.edit();
                        spEditor.putInt("skinType", skinType);
                        spEditor.apply();
                        onDestroyView();
                        getActivity().onBackPressed();
                    }
                });
                if (skinType == 1){
                    dialog.setImageView1("1");
                    dialog.setMessage1("Skin Type I");
                    dialog.setMessage2("Skin Photo Type I tans little or not at all, burns easily and severely, and then peels. \n" +
                            "Individuals with Skin Photo Type I do not have the ability to create natural protection from ultraviolet exposure, and are particularly susceptible to burning and damage from UV radiation.");

                }

                if (skinType == 2){
                    dialog.setImageView1("2");
                    dialog.setMessage1("Skin Type II");
                    dialog.setMessage2("Skin Photo Type II usually burns easily and severely (painful burn), and tans minimally and lightly.Individuals with Skin Photo Type II are not recommended to use a tanning unit.");

                }
                if (skinType == 3){
                    dialog.setImageView1("3");
                    dialog.setMessage1("Skin Type III");
                    dialog.setMessage2("Skin Photo Type III burns moderately, and usually develops an average tan.");

                }
                if (skinType == 4){
                    dialog.setImageView1("4");
                    dialog.setMessage1("Skin Type IV");
                    dialog.setMessage2("Skin Type IV burns minimally, tans easily with each exposure and exhibits immediate pigment darkening.");

                }
                if (skinType == 5){
                    dialog.setImageView1("5");
                    dialog.setMessage1("Skin Type V");
                    dialog.setMessage2("Skin Type V rarely burns, tans easily and substantially, and always exhibits immediate pigment darkening.");

                }
                if (skinType == 6){
                    dialog.setImageView1("6");
                    dialog.setMessage1("Skin Type VI");
                    dialog.setMessage2("Skin Type VI, tans easily, never burns and exhibits immediate pigment darkening.");

                }

                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                    }
                });


        binding.tooltipq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog(getContext());
                dialog.setTitle("Example");
                dialog.setMessage2("Q1");
                dialog.setImageView1("1");
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });
        binding.tooltipq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog(getContext());
                dialog.setTitle("Example");
                dialog.setMessage2("Q2");
                dialog.setImageView1("2");
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });








        return view;
    }

    private void replaceFragment(Fragment nextFragment) {

    }

        public int  changeSkinType(int currentCount ) {
            int skinType = 0;
            if (currentCount <= 6) {
                skinType = 1;
            } else if (currentCount <= 13) {
                skinType = 2;
            } else if (currentCount <= 20) {
                skinType = 3;
            } else if (currentCount <= 27) {
                skinType = 4;
            } else if (currentCount <= 34) {
                skinType = 5;
            } else {
                skinType = 6;
            }
            return skinType;

        }

    private String getValue(int skinType){
        switch(skinType){
            case 1:return "Ⅰ";
            case 2:return "Ⅱ";
            case 3:return "Ⅲ";
            case 4:return "Ⅳ";
            case 5:return "Ⅴ";
            case 6:return "Ⅵ";
            default: return "";

        }
    }

    public void createQA(){
        questions = new ArrayList<String>();
        questions.add("Your eye color is:");
        questions.add("Your natural hair color is:");
        questions.add("Your natural skin color is:");
        questions.add("How many freckles do you have on unexposed areas of your skin?");
        questions.add("How does your skin respond to the sun?");
        questions.add("Do you turn brown after several hours of sun exposure?");
        questions.add("How deeply do you tan?");
        questions.add("How sensitive is your face to the sun?");
        answers = new ArrayList<ArrayList<String>>();



        ArrayList<String> q1 = new ArrayList<String>() {{
            add("Light blue, light gray or light green");
            add("Blue, gray or green");
            add("Hazel or light brown");
            add("Dark brown");
            add("Brownish black");
        }};
        ArrayList<String> q2 = new ArrayList<String>() {{
            add("Red or light blonde");
            add("Blonde");
            add("Dark blonde or light brown");
            add("Dark brown");
            add("Black");
        }};
        ArrayList<String> q3 = new ArrayList<String>() {{
            add("lvory white");
            add("Fair or pale");
            add("Fair to beige, with golden undertone");
            add("Olive or light brown");
            add("Dark brown or black");
        }};
        ArrayList<String> q4 = new ArrayList<String>() {{
            add("Many");
            add("Several");
            add("A few ");
            add("Very few");
            add("None");
        }};
        answers.add(q1);
        answers.add(q2);
        answers.add(q3);
        answers.add(q4);
        ArrayList<String> q5 = new ArrayList<String>() {{
            add("Always burns, blisters and peels");
            add("Often burns, blisters and peels");
            add("Burns moderately");
            add("Burns rarely, if at all");
            add("Never burns");
        }};
        ArrayList<String> q6 = new ArrayList<String>() {{
            add("Never");
            add("Seldom");
            add("Sometimes");
            add("Often");
            add("Always");
        }};
        ArrayList<String> q7 = new ArrayList<String>() {{
            add("Not at all or very little");
            add("Lightly");
            add("Moderately");
            add("Deeply");
            add("My skin is naturally dark");
        }};
        ArrayList<String> q8 = new ArrayList<String>() {{
            add("Very sensitive");
            add("Sensitive");
            add("Normal");
            add("Resistant");
            add("Very resistant/Never had a problem");
        }};
        answers.add(q5);
        answers.add(q6);
        answers.add(q7);
        answers.add(q8);


    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;
        BottomNavigationView navView =
                getActivity().findViewById(R.id.nav_view);
        navView.setVisibility(View.VISIBLE);
    }
    public void upDateUI(){
         if(currentPage == 0){
             binding.prev.setVisibility(View.GONE);
             binding.nextButton.setVisibility(View.VISIBLE);

         }
         else if(currentPage == 3){
            binding.prev.setVisibility(View.VISIBLE);
            binding.nextButton.setVisibility(View.GONE);
         }
         else{
             binding.prev.setVisibility(View.VISIBLE);
             binding.nextButton.setVisibility(View.VISIBLE);
         }
        binding.buttonA.setSelected(false);
        binding.buttonB.setSelected(false);
        binding.buttonC.setSelected(false);
        binding.buttonD.setSelected(false);
        binding.buttonE.setSelected(false);
        binding.buttonA2.setSelected(false);
        binding.buttonB2.setSelected(false);
        binding.buttonC2.setSelected(false);
        binding.buttonD2.setSelected(false);
        binding.buttonE2.setSelected(false);

        if (currentPage == 0){
            binding.image2.setSelected(false);
            binding.image3.setSelected(false);
            binding.image4.setSelected(false);
            binding.LongtBar.setProgress(25);
            binding.tooltipq1.setVisibility(View.VISIBLE);
            binding.tooltipq2.setVisibility(View.VISIBLE);
            binding.tooltipq1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonDialog dialog = new CommonDialog(getContext());
                    dialog.setTitle("Example");
                    dialog.setMessage2("Q1");
                    dialog.setImageView1("1");
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                }
            });
            binding.tooltipq2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonDialog dialog = new CommonDialog(getContext());
                    dialog.setTitle("Example");
                    dialog.setMessage2("Q2");
                    dialog.setImageView1("2");
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                }
            });

        }
        if (currentPage == 1){
            binding.image2.setSelected(true);
            binding.image3.setSelected(false);
            binding.image4.setSelected(false);
            binding.LongtBar.setProgress(50);
            binding.tooltipq1.setVisibility(View.VISIBLE);
            binding.tooltipq2.setVisibility(View.VISIBLE);
            binding.tooltipq1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonDialog dialog = new CommonDialog(getContext());
                    dialog.setTitle("Example");
                    dialog.setMessage2("Q3");
                    dialog.setImageView1("3");
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                }
            });
            binding.tooltipq2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonDialog dialog = new CommonDialog(getContext());
                    dialog.setTitle("Example");
                    dialog.setMessage2("Q4");
                    dialog.setImageView1("4");
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                }
            });

        }
        if (currentPage == 2){
            binding.image2.setSelected(true);
            binding.image3.setSelected(true);
            binding.image4.setSelected(false);
            binding.LongtBar.setProgress(75);
            binding.tooltipq1.setVisibility(View.GONE);
            binding.tooltipq2.setVisibility(View.GONE);

        }
        if (currentPage == 3){
            binding.image2.setSelected(true);
            binding.image3.setSelected(true);
            binding.image4.setSelected(true);
            binding.LongtBar.setProgress(100);
            binding.tooltipq1.setVisibility(View.GONE);
            binding.tooltipq2.setVisibility(View.GONE);
        }




            if (points.get(0) != null && points.get(1) != null){
                binding.image1.setSelected(true);
            }
            else{
                binding.image1.setSelected(false);
            }
        if (points.get(2) != null && points.get(3) != null){
            binding.image2.setSelected(true);
        }
        else{
            binding.image2.setSelected(false);
        }
        if (points.get(4) != null && points.get(5) != null){
            binding.image3.setSelected(true);
        }
        else{
            binding.image3.setSelected(false);
        }
        if (points.get(6) != null && points.get(7) != null){
            binding.image4.setSelected(true);
        }
        else{
            binding.image4.setSelected(false);
        }


        if (points.get(2* currentPage) != null){
            if (points.get(2* currentPage) == 0){
                binding.buttonA.setSelected(true);
            }
            if (points.get(2* currentPage) == 1){
                binding.buttonB.setSelected(true);
            }
            if (points.get(2* currentPage) == 2){
                binding.buttonC.setSelected(true);
            }
            if (points.get(2* currentPage) == 3){
                binding.buttonD.setSelected(true);
            }
            if (points.get(2* currentPage) == 4){
                binding.buttonE.setSelected(true);
            }

        }

        if (points.get(2* currentPage +1) != null){
            if (points.get(2* currentPage +1) == 0){
                binding.buttonA2.setSelected(true);
            }
            if (points.get(2* currentPage +1) == 1){
                binding.buttonB2.setSelected(true);
            }
            if (points.get(2* currentPage +1) == 2){
                binding.buttonC2.setSelected(true);
            }
            if (points.get(2* currentPage +1) == 3){
                binding.buttonD2.setSelected(true);
            }
            if (points.get(2* currentPage +1) == 4){
                binding.buttonE2.setSelected(true);
            }

        }



        binding.question.setText(questions.get(2 * currentPage));
         binding.question2.setText(questions.get(2 * currentPage + 1));

         binding.optionA.setText(answers.get(2 * currentPage).get(0));
        binding.optionB.setText(answers.get(2 * currentPage).get(1));
        binding.optionC.setText(answers.get(2 * currentPage).get(2));
        binding.optionD.setText(answers.get(2 * currentPage).get(3));
        binding.optionE.setText(answers.get(2 * currentPage).get(4));

        binding.optionA2.setText(answers.get(2 * currentPage + 1).get(0));
        binding.optionB2.setText(answers.get(2 * currentPage +1).get(1));
        binding.optionC2.setText(answers.get(2 * currentPage+1).get(2));
        binding.optionD2.setText(answers.get(2 * currentPage+1).get(3));
        binding.optionE2.setText(answers.get(2 * currentPage+1).get(4));


    }

    public void updateNumber(){
        if (points.get(0) != null && points.get(1) != null){
            binding.image1.setSelected(true);
        }
        else{
            binding.image1.setSelected(false);
        }
        if (points.get(2) != null && points.get(3) != null){
            binding.image2.setSelected(true);
        }
        else{
            binding.image2.setSelected(false);
        }
        if (points.get(4) != null && points.get(5) != null){
            binding.image3.setSelected(true);
        }
        else{
            binding.image3.setSelected(false);
        }
        if (points.get(6) != null && points.get(7) != null){
            binding.image4.setSelected(true);
        }
        else{
            binding.image4.setSelected(false);
        }

        if (points.size() == 8){
            binding.submit.setVisibility(View.INVISIBLE);

        }

        if(points.size() == 0){
            binding.recordHint.setText("0% completed");
            binding.recordBar.setProgress(0);
        }
        else if(points.size() == 1){
            binding.recordHint.setText("12.5% completed");
            binding.recordBar.setProgress(13);
        }
        else if(points.size() == 2){
            binding.recordHint.setText("25% completed");
            binding.recordBar.setProgress(25);
        }
        else if(points.size() == 3){
            binding.recordHint.setText("37.5% completed");
            binding.recordBar.setProgress(38);
        }
        else if(points.size() == 4){
            binding.recordHint.setText("50% completed");
            binding.recordBar.setProgress(50);
        }
        else if(points.size() == 5){
            binding.recordHint.setText("62.5% completed");
            binding.recordBar.setProgress(63);
        }
        else if(points.size() == 6){
            binding.recordHint.setText("75% completed");
            binding.recordBar.setProgress(75);
        }
        else if(points.size() == 7){
            binding.recordHint.setText("87.5% completed");
            binding.recordBar.setProgress(88);
        }
        else if(points.size() == 8){
            binding.recordHint.setText("100% completed");
            binding.recordBar.setProgress(100);
            binding.recordBar.setVisibility(View.INVISIBLE);
            binding.recordHint.setVisibility(View.INVISIBLE);
            binding.submit.setVisibility(View.VISIBLE);

        }




    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onPause() {

        super.onPause();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }






}