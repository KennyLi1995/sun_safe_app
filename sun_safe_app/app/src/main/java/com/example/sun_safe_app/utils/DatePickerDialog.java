package com.example.sun_safe_app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.sun_safe_app.R;
import com.example.sun_safe_app.ui.uvi.UviFragmentModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sunny on 2020/4/21.
 */
public class DatePickerDialog extends android.app.Dialog implements View.OnClickListener{

    private TextView textView1,textView2,textView3,textView4,textView5;
    private ImageView imageView1;
    private String title,message1,message2,cancel,confirm,imageView1String;
    private OnCancelListener cancelListener;
    private OnConfirmListener confirmListener;
    private Activity parentActivity;

    public DatePickerDialog(Context context, Activity parentActivity) {
        super(context);
        this.parentActivity = parentActivity;

    }

    public DatePickerDialog(Context context, int themeResId) {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker);
        //设置宽度，固定代码
        WindowManager m=getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p=getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.8);//设置dialog的宽度为当前手机屏幕宽度*0.8
        getWindow().setAttributes(p);

        Calendar calendar= Calendar.getInstance(); //获取日历的实例
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);//实际月份需要加1
        int day=calendar.get(Calendar.DATE);

        DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker1);
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        Calendar todayM = Calendar.getInstance();
        c.setTime(today);


//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = null;
//        try {
//             date = sdf.parse("2021-09-27");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        c.setTime(date);


        todayM.setTime(today);
        c.add( Calendar.DATE, 7 );
        long maxDate = c.getTime().getTime();
        long minDate = todayM.getTime().getTime();

        datePicker.setMinDate(minDate);
        datePicker.setMaxDate(maxDate);
        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });

        textView4= (TextView) findViewById(R.id.positiveText);
        textView4.setOnClickListener(this);



    }

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
                DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker1);
                int monthPlusOne = datePicker.getMonth() +1;
                String month = "";
                if (monthPlusOne <= 9){
                    month = "0" + monthPlusOne;
                }
                else{
                    month = "" + monthPlusOne;
                }

                String day = "";
                if (datePicker.getDayOfMonth() <= 9){
                    day = "0" + datePicker.getDayOfMonth();
                }
                else{
                    day = "" + datePicker.getDayOfMonth();
                }

                String s = datePicker.getYear() + "-" + month + "-" + day;
                DateViewModel vm =  new ViewModelProvider((ViewModelStoreOwner) parentActivity).get(DateViewModel.class);

                vm.setMessage(s);
                dismiss();
                break;
        }
    }



    public interface OnCancelListener{
        void onCancel(DatePickerDialog dialog);
    }

    public interface OnConfirmListener{
        void onConfirm(DatePickerDialog dialog);
    }
}