package com.example.sun_safe_app.ui.mySkin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
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
import com.example.sun_safe_app.utils.MyViewModel;
import com.example.sun_safe_app.utils.ProfileFragmentDialog;
import com.example.sun_safe_app.utils.ProfileModifyDialog;
import com.example.sun_safe_app.utils.ShareBitmapUtils;
import com.example.sun_safe_app.utils.SkinTypeDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MySkinFragment extends Fragment {

    private FragmentMySkinBinding binding;
    int currentCount = 0;


    private Button cameraBt;
    private Button photoBt;
    private ImageView camereIv;
    private ImageView photoIv;
    private String TAG = "tag";
    //需要的权限数组 读/写/相机
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};


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
//        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
//        int height = (int) navView.getMeasuredHeight();
//        LinearLayout layout = view.findViewById(R.id.mainLayout);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        lp.setMargins(0, 0, 0, height);
//        layout.setLayoutParams(lp);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        binding.informationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ProfileFragmentDialog myDialog = new ProfileFragmentDialog();
                myDialog.show(getActivity().getSupportFragmentManager(),"myDialog");







            }
        });

        MySkinViewModel vm =  new ViewModelProvider(getActivity()).get(MySkinViewModel.class);
        vm.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                updateUI();
            }
        });

        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.informationCard.callOnClick();
            }
        });









        return view;
    }


    private Uri ImageUri;
    public static final int TAKE_PHOTO = 101;
    public static final int TAKE_CAMARA = 100;

//    private void initView() {
//        cameraBt = binding.cameraBt;
//        photoBt = binding.photoBt;
//        camereIv = binding.camereIv;
//        photoIv = binding.photoIv;
//
//        cameraBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //检查是否已经获得相机的权限
//                if (verifyPermissions(getActivity(), PERMISSIONS_STORAGE[2]) == 0) {
//                    Log.i(TAG, "提示是否要授权");
//                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 3);
//                } else {
//                    //已经有权限
//                    toCamera();  //打开相机
//                }
//            }
//        });
//        photoBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toPicture();
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }








    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).selectBottomMenu(3); //change value depending on your bottom menu position
        updateUI();
    }

    public void updateUI(){
        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("ifInput",false)){
            binding.cross.setVisibility(View.GONE);


            binding.nameText.setText(sharedPref.getString("name",""));
            binding.weightText.setText(sharedPref.getInt("weight", 0) + " KG");
            binding.heightText.setText(sharedPref.getInt("height", 0) + " CM");
//            binding.genderText.setText(sharedPref.getString("gender","Male"));

            Bitmap bitmap = ShareBitmapUtils.getBitmap(getActivity(), "photo",null);
            if(bitmap != null){
                RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                img.setCircular(true);

                binding.imageView1.setBackground(img);
            }




            binding.nameText.setVisibility(View.VISIBLE);
//            binding.gender.setVisibility(View.VISIBLE);
//            binding.genderText.setVisibility(View.VISIBLE);
            binding.Height.setVisibility(View.VISIBLE);
            binding.heightText.setVisibility(View.VISIBLE);
            binding.Weight.setVisibility(View.VISIBLE);
            binding.weightText.setVisibility(View.VISIBLE);
            binding.imageView1.setVisibility(View.VISIBLE);
            binding.changeAagin.setVisibility(View.VISIBLE);

//            binding.nameEdit.setText(sharedPref.getString("name",""));
//            binding.weightEdit.setText(sharedPref.getInt("weight",0) + "");
//            binding.heightEdit.setText(sharedPref.getInt("height",0) + "");
//
//            if (sharedPref.getString("gender","Male").equals("Male")){
//                binding.genderSpinner.setSelection(0);
//            }
//            else{
//                binding.genderSpinner.setSelection(1);
//            }

        }
    }








    public void setSkinType(int skinType){
        if (skinType != 0) {
//            binding.infoText.setText("Skin type " + getValue(skinType));
            binding.skinTypeCard.setVisibility(View.VISIBLE);
            binding.skinTypeExplainCard.setVisibility(View.VISIBLE);
            binding.attemptText.setText("Update");
            if (skinType == 1){
                binding.skinTypeNumber.setText("1");
                binding.skinTypeExplainText.setText("Extremely sensitive skin, always burns, never tans");

            }
            if (skinType == 2){
                binding.skinTypeNumber.setText("2");
                binding.skinTypeExplainText.setText("Very sensitive skin, burns easily, tans minimally");

            }
            if (skinType == 3){
                binding.skinTypeNumber.setText("3");
                binding.skinTypeExplainText.setText("Sensitive skin, sometimes burns, slowly tans to light brown");

            }
            if (skinType == 4){
                binding.skinTypeNumber.setText("4");
                binding.skinTypeExplainText.setText("Mildly sensitive, burns minimally, always tans to moderate\n" +
                        "brown");

            }
            if (skinType == 5){
                binding.skinTypeNumber.setText("5");
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
