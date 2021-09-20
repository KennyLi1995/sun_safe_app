package com.example.sun_safe_app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.sun_safe_app.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sunny on 2020/4/21.
 */
public class TimePickerDialog extends android.app.Dialog implements View.OnClickListener{

    private TextView textView1,textView2,textView3,textView4,textView5;
    private ImageView imageView1;
    private String title,message1,message2,cancel,confirm,imageView1String;
    private OnCancelListener cancelListener;
    private OnConfirmListener confirmListener;
    private Activity parentActivity;
    private boolean ifStartDate;

    int hour;
    int minutes;

    public TimePickerDialog(Context context, Activity parentActivity,boolean ifStartDate) {
        super(context);
        this.parentActivity = parentActivity;
        this.ifStartDate = ifStartDate;
    }

    public TimePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage1(String message) {
        this.message1 = message;
    }
    public void setMessage2(String message) {
        this.message2 = message;
    }

    public void setCancel(String cancel,OnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener=listener;
    }

    public void setConfirm(String confirm,OnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener=listener;
    }

    public void setImageView1(String imageViewStringInput) {
        this.imageView1String = imageViewStringInput;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);
        //设置宽度，固定代码
        WindowManager m=getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p=getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.8);//设置dialog的宽度为当前手机屏幕宽度*0.8
        getWindow().setAttributes(p);



        textView4= (TextView) findViewById(R.id.positiveText);
        textView4.setOnClickListener(this);



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.negativeText:
                if(cancelListener!=null){
                    cancelListener.onCancel(this);
                }
                dismiss();
                break;
            case R.id.positiveText:
                if(confirmListener!=null){
                    confirmListener.onConfirm(this);
                }
                TimePicker timePicker=(TimePicker)findViewById(R.id.timepicker);
                hour = timePicker.getHour();
                minutes= timePicker.getMinute();

                DateViewModel vm =  new ViewModelProvider((ViewModelStoreOwner) parentActivity).get(DateViewModel.class);
                String minuteString = "";
                if (minutes <= 9){
                    minuteString = "0" + minutes;
                }
                else{
                    minuteString = "" + minutes;
                }
                String hourString = "";
                if (hour <= 9){
                    hourString = "0" + hour;
                }
                else{
                    hourString = "" + hour;
                }
                if (ifStartDate)
                    vm.setStartTime(hourString + ":" + minuteString);
                else{
                    vm.setEndTime(hourString + ":" + minuteString);
                }
                dismiss();
                break;
        }
    }



    public interface OnCancelListener{
        void onCancel(TimePickerDialog dialog);
    }

    public interface OnConfirmListener{
        void onConfirm(TimePickerDialog dialog);
    }
}