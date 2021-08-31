package com.example.sun_safe_app.ui.mySkin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentMySkinBinding;
import com.example.sun_safe_app.databinding.FragmentUviBinding;
import com.example.sun_safe_app.ui.uvi.UviFragmentModel;
import com.example.sun_safe_app.utils.AppUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MySkinFragment extends Fragment {

    private FragmentMySkinBinding binding;
    int currentCount = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMySkinBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.attempt.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.navigation_quiz, null)
        );




        SharedPreferences sharedPref= requireActivity().
                getSharedPreferences("Default", Context.MODE_PRIVATE);
        int skinType = sharedPref.getInt("skinType",0);
        setSkinType(skinType);


//
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        int height = (int) navView.getMeasuredHeight();
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)binding.mainLayout.getLayoutParams();
//        params.setMargins(0, 0, 0, height); //substitute parameters for left, top, right, bottom
//        binding.mainLayout.setLayoutParams(params);




        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).selectBottomMenu(1); //change value depending on your bottom menu position
    }

//    //单选对话框
//    public void click1(View view) {
//
//        AlertDialog dialog;
//        dialog = new AlertDialog.Builder(getContext())
//                .setTitle("Q1: Your eye color is ")  //设置标题
//                .setIcon(R.mipmap.ic_launcher) //设置图标
//                .setSingleChoiceItems(new String[]{"Light blue, light gray or light green",
//                                "Blue, gray or green",
//                                "Hazel or light brown",
//                                "Dark brown",
//                                "Brownish black"}, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        }
//                )
//                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lw = ((AlertDialog)dialog).getListView();
//                        currentCount += lw.getCheckedItemPosition();
//                        click2(view);
//                    }
//                })   //添加“确定”按钮
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentCount = 0;
//                    }
//                })   //添加“取消”按钮
//                .create();  //创建对话框
//
//        dialog.show();  //显示对话框
//
//    }
//
//    //单选对话框
//    public void click2(View view) {
//
//        AlertDialog dialog;
//        dialog = new AlertDialog.Builder(getContext())
//                .setTitle("Q2: Your natural hair color is ")  //设置标题
//                .setIcon(R.mipmap.ic_launcher) //设置图标
//                .setSingleChoiceItems(new String[]{"Red or light blonde",
//                                "Blonde",
//                                "Dark blonde or light brown",
//                                "Dark brown",
//                                "Black"}, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                )
//                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lw = ((AlertDialog)dialog).getListView();
//                        currentCount += lw.getCheckedItemPosition();
//                        click3(view);
//                    }
//                })   //添加“确定”按钮
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentCount = 0;
//                    }
//                })   //添加“取消”按钮
//                .create();  //创建对话框
//
//        dialog.show();  //显示对话框
//
//    }
//
//    //单选对话框
//    public void click3(View view) {
//
//        AlertDialog dialog;
//        dialog = new AlertDialog.Builder(getContext())
//                .setTitle("Q3: Your natural skin color (before sun exposure) is")  //设置标题
//                .setIcon(R.mipmap.ic_launcher) //设置图标
//                .setSingleChoiceItems(new String[]{"Ivory white",
//                                "Fair or pale",
//                                "Fair to beige, with golden undertone",
//                                "Olive or light brown",
//                                "Dark brown or black"}, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                )
//                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lw = ((AlertDialog)dialog).getListView();
//                        currentCount += lw.getCheckedItemPosition();
//                        click4(view);
//                    }
//                })   //添加“确定”按钮
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentCount = 0;
//                    }
//                })   //添加“取消”按钮
//                .create();  //创建对话框
//
//        dialog.show();  //显示对话框
//
//    }
//
//    //单选对话框
//    public void click4(View view) {
//
//        AlertDialog dialog;
//        dialog = new AlertDialog.Builder(getContext())
//                .setTitle("Q4: How many freckles are on unexposed areas of your skin?")  //设置标题
//                .setIcon(R.mipmap.ic_launcher) //设置图标
//                .setSingleChoiceItems(new String[]{"Many",
//                                "Several",
//                                "A few",
//                                "Very few",
//                                "None"}, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                )
//                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lw = ((AlertDialog)dialog).getListView();
//                        currentCount += lw.getCheckedItemPosition();
//                        click5(view);
//                    }
//                })   //添加“确定”按钮
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentCount = 0;
//                    }
//                })   //添加“取消”按钮
//                .create();  //创建对话框
//
//        dialog.show();  //显示对话框
//
//    }
//    //单选对话框
//    public void click5(View view) {
//
//        AlertDialog dialog;
//        dialog = new AlertDialog.Builder(getContext())
//                .setTitle("Q5: How does your skin respond to the sun?")  //设置标题
//                .setIcon(R.mipmap.ic_launcher) //设置图标
//                .setSingleChoiceItems(new String[]{"Always burns, blisters and peels",
//                                "Often burns, blisters and peels",
//                                "Burns moderately",
//                                "Burns rarely, if at all",
//                                "Never burns"}, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                )
//                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lw = ((AlertDialog)dialog).getListView();
//                        currentCount += lw.getCheckedItemPosition();
//                        click6(view);
//                    }
//                })   //添加“确定”按钮
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentCount = 0;
//                    }
//                })   //添加“取消”按钮
//                .create();  //创建对话框
//
//        dialog.show();  //显示对话框
//
//    }
//    //单选对话框
//    public void click6(View view) {
//
//        AlertDialog dialog;
//        dialog = new AlertDialog.Builder(getContext())
//                .setTitle("Q6: Does your skin tan?")  //设置标题
//                .setIcon(R.mipmap.ic_launcher) //设置图标
//                .setSingleChoiceItems(new String[]{"Never — I always burn",
//                                "Seldom",
//                                "Sometimes",
//                                "Often",
//                                "Always"}, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                )
//                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lw = ((AlertDialog)dialog).getListView();
//                        currentCount += lw.getCheckedItemPosition();
//                        click7(view);
//                    }
//                })   //添加“确定”按钮
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentCount = 0;
//                    }
//                })   //添加“取消”按钮
//                .create();  //创建对话框
//
//        dialog.show();  //显示对话框
//
//    }
//    //单选对话框
//    public void click7(View view) {
//
//        AlertDialog dialog;
//        dialog = new AlertDialog.Builder(getContext())
//                .setTitle("Q7: How deeply do you tan?")  //设置标题
//                .setIcon(R.mipmap.ic_launcher) //设置图标
//                .setSingleChoiceItems(new String[]{"Not at all or very little",
//                                "Lightly",
//                                "Moderately",
//                                "Deeply",
//                                "My skin is naturally dark"}, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                )
//                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lw = ((AlertDialog)dialog).getListView();
//                        currentCount += lw.getCheckedItemPosition();
//                        click8(view);
//                    }
//                })   //添加“确定”按钮
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentCount = 0;
//                    }
//                })   //添加“取消”按钮
//                .create();  //创建对话框
//
//        dialog.show();  //显示对话框
//
//    }
//    //单选对话框
//    public void click8(View view) {
//
//        AlertDialog dialog;
//        dialog = new AlertDialog.Builder(getContext())
//                .setTitle("Q8: How sensitive is your face to the sun?")  //设置标题
//                .setIcon(R.mipmap.ic_launcher) //设置图标
//                .setSingleChoiceItems(new String[]{"Very sensitive",
//                                "Sensitive",
//                                "Normal",
//                                "Resistant",
//                                "Very resistant/Never had a problem"}, 0,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }
//                )
//                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ListView lw = ((AlertDialog)dialog).getListView();
//                        currentCount += lw.getCheckedItemPosition();
//                        changeSkinType();
//                    }
//                })   //添加“确定”按钮
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        currentCount = 0;
//                    }
//                })   //添加“取消”按钮
//                .create();  //创建对话框
//
//        dialog.show();  //显示对话框
//
//    }
//
//    public void changeSkinType(){
//        int skinType = 0;
//        if (currentCount <= 6){
//            skinType = 1;
//        }
//        else if (currentCount <= 13){
//            skinType = 2;
//        }
//        else if (currentCount <= 20){
//            skinType = 3;
//        }
//        else if (currentCount <= 27){
//            skinType = 4;
//        }
//        else if (currentCount <= 34){
//            skinType = 5;
//        }
//        else{
//            skinType = 6;
//        }
//
//        // store it in SharedPrefernece
//        SharedPreferences sharedPref= requireActivity().
//                getSharedPreferences("Default", Context.MODE_PRIVATE);
//        SharedPreferences.Editor spEditor = sharedPref.edit();
//        spEditor.putInt("skinType", skinType);
//        spEditor.apply();
//        setSkinType(skinType);
//
//    }
    public void setSkinType(int skinType){
        if (skinType != 0) {
//            binding.infoText.setText("Skin type " + getValue(skinType));
            binding.skinTypeCard.setVisibility(View.VISIBLE);
            binding.skinTypeExplainCard.setVisibility(View.VISIBLE);
            binding.attemptText.setText("Re-attempt");
            if (skinType == 1){
                binding.skinTypeNumber.setText("I");
                binding.skinTypeExplainText.setText("Extremely sensitive skin, always burns, never tans");

            }
            if (skinType == 2){
                binding.skinTypeNumber.setText("II");
                binding.skinTypeExplainText.setText("Very sensitive skin, burns easily, tans minimally");

            }
            if (skinType == 3){
                binding.skinTypeNumber.setText("III");
                binding.skinTypeExplainText.setText("Sensitive skin, sometimes burns, slowly tans to light brown");

            }
            if (skinType == 4){
                binding.skinTypeNumber.setText("IV");
                binding.skinTypeExplainText.setText("Mildly sensitive, burns minimally, always tans to moderate\n" +
                        "brown");

            }
            if (skinType == 5){
                binding.skinTypeNumber.setText("V");
                binding.skinTypeExplainText.setText("Resistant skin, rarely burns, tans well");

            }
            if (skinType == 6){
                binding.skinTypeNumber.setText("VI");
                binding.skinTypeExplainText.setText("Very resistant skin, never burns, deeply pigmented");

            }
        }
        else{
            binding.skinTypeCard.setVisibility(View.GONE);
            binding.skinTypeExplainCard.setVisibility(View.GONE);
        }

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





}
