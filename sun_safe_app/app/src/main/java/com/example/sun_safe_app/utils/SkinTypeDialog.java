package com.example.sun_safe_app.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import com.example.sun_safe_app.R;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sunny on 2020/4/21.
 */
public class SkinTypeDialog extends android.app.Dialog implements View.OnClickListener{

    private TextView textView1,textView2,textView3,textView4,textView5;
    private ImageView imageView1;
    private String title,message1,message2,cancel,confirm,imageView1String;
    private OnCancelListener cancelListener;
    private OnConfirmListener confirmListener;


    public SkinTypeDialog(Context context) {
        super(context);
    }

    public SkinTypeDialog(Context context, int themeResId) {
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
        setContentView(R.layout.skin_tpye_dialog);
        //设置宽度，固定代码
        WindowManager m=getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p=getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.8);//设置dialog的宽度为当前手机屏幕宽度*0.8
        getWindow().setAttributes(p);

        textView1= (TextView) findViewById(R.id.title);
        textView2= (TextView) findViewById(R.id.message1);
        textView3= (TextView) findViewById(R.id.negativeText);
        textView4= (TextView) findViewById(R.id.positiveText);
        textView5= (TextView) findViewById(R.id.message2);
        imageView1  = (ImageView) findViewById(R.id.imageView1);
        if(!TextUtils.isEmpty(title)){//不为空
            textView1.setText(title);
        }
        if(!TextUtils.isEmpty(message1)){//不为空
            textView2.setText(message1);
            textView2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            textView2.getPaint().setAntiAlias(true);//抗锯齿
        }
        if(!TextUtils.isEmpty(message2)){//不为空
            textView5.setText(message2);
        }

        if(!TextUtils.isEmpty(cancel)){//不为空
            textView3.setText(cancel);
        }
        if(!TextUtils.isEmpty(confirm)){//不为空
            textView4.setText(confirm);
        }

        if(!TextUtils.isEmpty(imageView1String)){//不为空
            if (imageView1String.equals("1"))
                imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.skin_type_one));
        }
        if(!TextUtils.isEmpty(imageView1String)){//不为空
            if (imageView1String.equals("2"))
                imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.skin_type_two));
        }
        if(!TextUtils.isEmpty(imageView1String)){//不为空
            if (imageView1String.equals("3"))
                imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.skin_type_three));
        }
        if(!TextUtils.isEmpty(imageView1String)){//不为空
            if (imageView1String.equals("4"))
                imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.skin_type_four));
        }
        if(!TextUtils.isEmpty(imageView1String)){//不为空
            if (imageView1String.equals("5"))
                imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.skin_type_five));
        }
        if(!TextUtils.isEmpty(imageView1String)){//不为空
            if (imageView1String.equals("6"))
                imageView1.setBackground(getContext().getResources().getDrawable(R.drawable.skin_type_six));
        }

        textView3.setOnClickListener(this);
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
        void onCancel(SkinTypeDialog dialog);
    }

    public interface OnConfirmListener{
        void onConfirm(SkinTypeDialog dialog);
    }
}