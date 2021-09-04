package com.example.sun_safe_app.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;

import java.io.FileNotFoundException;

/**
 * Created by Sunny on 2020/4/21.
 */
public class ProfileModifyDialog extends android.app.Dialog implements View.OnClickListener{

    private TextView textView1,textView2,textView3,textView4,textView5;
    private ImageView imageView1;
    private String title,message1,message2,cancel,confirm,imageView1String;
    private OnCancelListener cancelListener;
    private OnConfirmListener confirmListener;


    public ProfileModifyDialog(Context context) {
        super(context);
    }

    public ProfileModifyDialog(Context context, int themeResId) {
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
        setContentView(R.layout.profile_modify_dialog);
        //设置宽度，固定代码
        WindowManager m=getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p=getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.8);//设置dialog的宽度为当前手机屏幕宽度*0.8
        getWindow().setAttributes(p);


        textView3= (TextView) findViewById(R.id.negativeText);
        textView4= (TextView) findViewById(R.id.positiveText);
        imageView1  = (ImageView) findViewById(R.id.imageView1);


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

        imageView1.setOnClickListener(this);
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
            case R.id.imageView1:
                if(confirmListener!=null){
                    MainActivity activity = (MainActivity) getOwnerActivity();
                }
                break;
        }


    }
    private Uri ImageUri;
    public static final int TAKE_PHOTO = 101;
    public static final int TAKE_CAMARA = 100;
    //跳转相册
    public void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        getOwnerActivity().startActivityForResult(intent, TAKE_CAMARA);

    }






    public interface OnCancelListener{
        void onCancel(ProfileModifyDialog dialog);
    }

    public interface OnConfirmListener{
        void onConfirm(ProfileModifyDialog dialog);
    }
}