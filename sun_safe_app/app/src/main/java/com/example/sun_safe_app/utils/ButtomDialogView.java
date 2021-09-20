package com.example.sun_safe_app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.BottomDialogBinding;
import com.example.sun_safe_app.databinding.ProfileModifyDialogBinding;
import com.example.sun_safe_app.room.entity.EventRecord;
import com.example.sun_safe_app.room.viewmodel.EventRecordViewModel;
import com.example.sun_safe_app.ui.activityPlan.ActivityFragmentDialog;
import com.example.sun_safe_app.ui.activityPlan.ActivityWeatherViewModel;
import com.example.sun_safe_app.ui.map.GoogleMapsFragmentDialog;
import com.example.sun_safe_app.ui.map.MapsFragment;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ButtomDialogView extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private Context context;
    private DateViewModel vm;
    private MainActivity parentActivity;
    private EventRecordViewModel eventRecordViewModel;

    public ButtomDialogView(Context context, boolean isCancelable, boolean isBackCancelable, Activity parentActivity) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.iscancelable = isCancelable;
        this.isBackCancelable = isBackCancelable;
        this.parentActivity = (MainActivity) parentActivity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bottom_dialog);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        setCanceledOnTouchOutside(isBackCancelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setWindowAnimations(R.style.main_menu_animStyle);
        window.setAttributes(params);

        EditText textView1= (EditText) findViewById(R.id.dateEdit);
        EditText textView2= (EditText) findViewById(R.id.startTimeEdit);
        EditText textView3= (EditText) findViewById(R.id.endTimeEdit);
        EditText textView4= (EditText) findViewById(R.id.locationEdit);
        EditText titleEdit= (EditText) findViewById(R.id.titleEdit);

//        titleEdit.addTextChangedListener(new JumpTextWatcher());
        View.OnKeyListener onKey=new View.OnKeyListener() {

            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

// TODO Auto-generated method stub

                if(keyCode == KeyEvent.KEYCODE_ENTER){
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if(imm.isActive()){

                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );
                }

                    return true;
                }
                return false;

            }

        };
        titleEdit.setOnKeyListener(onKey);


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=new DatePickerDialog(getContext(),parentActivity);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog=new TimePickerDialog(getContext(),parentActivity,true);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog=new TimePickerDialog(getContext(),parentActivity,false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });




        vm =  new ViewModelProvider((ViewModelStoreOwner) parentActivity).get(DateViewModel.class);
        vm.getText().observe((LifecycleOwner) parentActivity, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView1.setText(s);
            }
        });



        vm.getStartTime().observe((LifecycleOwner) parentActivity, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView2.setText(s);
            }
        });

        vm.getEndTime().observe((LifecycleOwner) parentActivity, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView3.setText(s);
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleMapsFragmentDialog myDialog = new GoogleMapsFragmentDialog(parentActivity);
                myDialog.show(parentActivity.getSupportFragmentManager(), null);


            }
        });


        vm.getAddressString().observe((LifecycleOwner) parentActivity, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView4.setText(s);
            }
        });

        vm.setAddressString("");
        vm.setStartTime("");
        vm.setEndTime("");



        SharedPreferences sharedPref= parentActivity.
                getSharedPreferences("Default", Context.MODE_PRIVATE);
        String latString = sharedPref.getString("preLat","0");
        String lonString = sharedPref.getString("preLon","0");
        String addressString = sharedPref.getString("preAddress","");


        String format = "yyyy-MM-dd";
        Date currentDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currentDateString = sdf.format(currentDate);

        vm.setLocationString(latString + " " + lonString);
        vm.setAddressString(addressString);
        vm.setMessage(currentDateString);


        TextView textView5= (TextView) findViewById(R.id.positiveText);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()){

                    Toast.makeText(getContext().getApplicationContext(), "Event added successfully!", Toast.LENGTH_LONG).show();

                    dismiss();
                }
                else{

                }
            }
        });
        TextView textView6= (TextView) findViewById(R.id.negativeText);
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

//    private class JumpTextWatcher implements TextWatcher {
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            String str = s.toString();
//            EditText textView1= (EditText) findViewById(R.id.dateEdit);
//            EditText titleEdit= (EditText) findViewById(R.id.titleEdit);
//            if (str.indexOf("\r") >= 0 || str.indexOf("\n") >= 0) {//发现输入回车符或换行符
//                titleEdit.setText(str.replace("\r", "").replace("\n", ""));//去掉回车符和换行符
//                textView1.requestFocus();//让editText2获取焦点
////                editText2.setSelection(editText2.getText().length());//若editText2有内容就将光标移动到文本末尾
//            }
//
//        }
//    }



    public boolean checkInput()  {
        String errorMessage = "";
        EditText textView1= (EditText) findViewById(R.id.dateEdit);
        EditText textView2= (EditText) findViewById(R.id.startTimeEdit);
        EditText textView3= (EditText) findViewById(R.id.endTimeEdit);
        EditText textView4= (EditText) findViewById(R.id.locationEdit);
        EditText textView5= (EditText) findViewById(R.id.titleEdit);
        if (textView5.getText().toString().length() == 0 || textView5.getText().toString().length() > 13){
            errorMessage += "\n*Your title can't be empty or more than 12 characters";
        }
        if (textView1.getText().toString().length() == 0){
            errorMessage += "\n*Please select a date";
        }
        else if (textView2.getText().toString().length() == 0){
            errorMessage += "\n*Please select a start time";
        }
        else if (textView3.getText().toString().length() == 0){
            errorMessage += "\n*Please select an end time";
        }
        else {

            String format = "yyyy-MM-dd HH:mm";
            Date currentDate = new Date(System.currentTimeMillis());//获取当前时间


            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String currentDateString = sdf.format(currentDate);

            Date currentDateTrans = null;

            Date startDate = null;
            String startDateString = textView1.getText().toString();
            String startTime = textView2.getText().toString();
            String completeStartDate = startDateString + " " + startTime;

            Date endDate = null;
            String endTime = textView3.getText().toString();
            String completeEndDate = startDateString + " " + endTime;


            try {
                currentDateTrans = sdf.parse(currentDateString);
                startDate = sdf.parse(completeStartDate);
                endDate= sdf.parse(completeEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (startDate != null && currentDate != null){
                if (startDate.getTime() < currentDateTrans.getTime()){
                    errorMessage += "\n*Start time must later than current time";
                }
            }

            if (endDate != null & startDate != null){
                if (endDate.getTime() - 3600000 <= startDate.getTime()){
                    errorMessage += "\n*End time must be at least one hour than start time";
                }
            }

        }




        if (textView4.getText().toString().length() == 0){
            errorMessage += "\n*Please select a location";
        }
        TextView textView6 = (TextView) findViewById(R.id.errorMessage);

        if (errorMessage.length() != 0){
            textView6.setVisibility(View.VISIBLE);
            textView6.setText(errorMessage);
            return false;
        }
        else{
            textView6.setText("");
            textView6.setVisibility(View.GONE);

            String date = textView1.getText().toString();
            String startTime = textView2.getText().toString();
            String endTime = textView3.getText().toString();
            String location = textView4.getText().toString();
            String title = textView5.getText().toString();
            String lat = vm.getLocationString().getValue().split(" ")[0];
            String lon = vm.getLocationString().getValue().split(" ")[1];
            double latDouble = Double.parseDouble(lat);
            double lonDouble = Double.parseDouble(lon);

            EventRecord aEventRecord = new EventRecord(title,date,startTime,endTime,latDouble,lonDouble,location,
                    -1,"Unknown","");
            ActivityWeatherViewModel vm =  new ViewModelProvider((ViewModelStoreOwner) parentActivity).get(ActivityWeatherViewModel.class);
            vm.setMessage(true);
            eventRecordViewModel =  ViewModelProvider.AndroidViewModelFactory.getInstance(parentActivity.getApplication()).create(EventRecordViewModel.class);
            eventRecordViewModel.insert(aEventRecord);




            return true;
        }




    }
}

