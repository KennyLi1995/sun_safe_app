package com.example.sun_safe_app.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sun_safe_app.R;

/**
 * Created by Sunny on 2020/4/21.
 */
public class SunScreenResultDialog extends android.app.Dialog implements View.OnClickListener{

    private TextView textView1,textView2,textView3,textView4,textView5;
    private ImageView imageView1,imageView2;
    private boolean ifWater = false;
    private String title,message1,message2,cancel,confirm;
    private int imageViewInt;
    private OnCancelListener cancelListener;
    private OnConfirmListener confirmListener;


    public SunScreenResultDialog(Context context) {
        super(context);
    }

    public SunScreenResultDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageView2(boolean ifWater) {
        this.ifWater = ifWater;
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

    public void setImageView1(int imageViewStringInput) {
        this.imageViewInt = imageViewStringInput;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sunscreen_result_dialog);
        //设置宽度，固定代码
        WindowManager m=getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p=getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.9);//设置dialog的宽度为当前手机屏幕宽度*0.8
        getWindow().setAttributes(p);

        textView1= (TextView) findViewById(R.id.title);
        textView4= (TextView) findViewById(R.id.positiveText);
        textView5= (TextView) findViewById(R.id.applyAmount);
        textView3= (TextView) findViewById(R.id.sunscreenRecommendation);

        imageView1 = (ImageView) findViewById(R.id.spf);
        imageView2 = (ImageView) findViewById(R.id.waterproof);

        if(!TextUtils.isEmpty(title)){//不为空
            textView1.setText(title);
        }

        if(!TextUtils.isEmpty(message2)){//不为空
            textView5.setText(message2);
        }

        if(!TextUtils.isEmpty(message2)){//不为空
            textView3.setText(message1);
        }


        if(!TextUtils.isEmpty(confirm)){//不为空
            textView4.setText(confirm);
        }

        if(imageViewInt == 15 ){//不为空
            imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.spf15_100x113));
        }
        else if (imageViewInt == 30){
            imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.spf30_100x113));

        }
        else if (imageViewInt == 50){
            imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.spf50_100x113));
        }

        if (ifWater){
            imageView2.setVisibility(View.VISIBLE);
        }
        else{
            imageView2.setVisibility(View.GONE);

        }


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
                dismiss();
                break;
        }
    }



    public interface OnCancelListener{
        void onCancel(SunScreenResultDialog dialog);
    }

    public interface OnConfirmListener{
        void onConfirm(SunScreenResultDialog dialog);
    }
}