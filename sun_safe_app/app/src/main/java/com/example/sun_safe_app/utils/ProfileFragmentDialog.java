package com.example.sun_safe_app.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.FragmentMySkinBinding;
import com.example.sun_safe_app.databinding.ProfileModifyDialogBinding;
import com.example.sun_safe_app.ui.mySkin.MySkinViewModel;
import com.example.sun_safe_app.ui.uvi.UviFragmentLatLongModel;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileFragmentDialog extends DialogFragment {

    private ProfileModifyDialogBinding binding;
    private String TAG = "tag";
    //需要的权限数组 读/写/相机
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};


    private Uri ImageUri;
    public static final int TAKE_PHOTO = 101;
    public static final int TAKE_CAMARA = 100;
    String gender;

    //try 2

//    private ImageView mIv;
//    // 拍照回传码
//    public final static int CAMERA_REQUEST_CODE = 100;
//    // 相册选择回传吗
//    public final static int GALLERY_REQUEST_CODE = 200;
//    //裁剪回传吗
//    private static final int REQUEST_CROP = 300;
//    private File imgFile;
//    private Uri imgUri;
//    private String file_path = "";
//    private Uri mCutUri;// 图片裁剪时返回的uri




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileModifyDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();





        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
        binding.negativeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] options = getResources().getStringArray(R.array.gender);
                gender = options[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        binding.positiveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    String name = binding.nameEdit.getText().toString();
                    int height = Integer.parseInt(binding.heightEdit.getText().toString());
                    int weight = Integer.parseInt(binding.weightEdit.getText().toString());
                    SharedPreferences sharedPref= getActivity().
                            getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sharedPref.edit();


                    spEditor.putString("name", name);
                    spEditor.putString("gender", gender);
                    spEditor.putInt("height", height);
                    spEditor.putInt("weight", weight);
                    spEditor.putBoolean("ifInput", true);







                    spEditor.apply();
                    MyViewModel myViewModel =  new ViewModelProvider(getActivity()).get(MyViewModel.class);

                    Bitmap bitmap = myViewModel.getBitmapMutableLiveData().getValue();
                    ShareBitmapUtils.putBitmap(getActivity(), "photo", bitmap);


                    MySkinViewModel vm =  new ViewModelProvider(getActivity()).get(MySkinViewModel.class);
                    vm.setMessage("1");

                    dismiss();
                }

            }
        });

        //引用arrays中的选项
        String[] curs = getResources().getStringArray(R.array.gender);
        //将写好的personal_spinner引用进来，此时改变的是选中后的情况，如果这里不想修改，可引用Android默认的布局，
        //比如android.R.layout.simple_spinner_item
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item_vict, curs);
        //此处修改的部分为 点击后弹出的选择框，同上可引用自己写的布局文件，也可以使用默认布局，此处使用的是默认布局
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genderSpinner.setAdapter(adapter);

        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("ifInput",false)){
            binding.nameEdit.setText(sharedPref.getString("name",""));
            binding.weightEdit.setText(sharedPref.getInt("weight",0) + "");
            binding.heightEdit.setText(sharedPref.getInt("height",0) + "");

            if (sharedPref.getString("gender","Male").equals("Male")){
                binding.genderSpinner.setSelection(0);
            }
            else{
                binding.genderSpinner.setSelection(1);
            }

        }



//        img.setCircular(true);
//        binding.imageView1.setBackground(img);
        MyViewModel vm =  new ViewModelProvider(getActivity()).get(MyViewModel.class);


        vm.getBitmapMutableLiveData().observe(this, bitmapa ->

        {
            if (bitmapa != null) {

                RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(), bitmapa);
                img.setCircular(true);

                binding.imageView1.setBackground(img);
            }

        });















        return view;
    }

    public boolean checkInput(){
        String errorMessage = "";
        if(binding.nameEdit.getText().toString().length() == 0 || binding.nameEdit.getText().toString().length() >= 13){
            errorMessage += "\n*Your name can't be empty or more than 13 characters";
        }
        if (binding.weightEdit.getText().toString().length() == 0){
            errorMessage += "\n*Your input weight can't be empty";
        }
        else {
            if (Integer.parseInt(binding.weightEdit.getText().toString()) <= 20 || Integer.parseInt(binding.weightEdit.getText().toString()) >= 220) {
                errorMessage += "\n*Your input weight needs to be between 20 and 220 kg";
            }
        }
        if (binding.heightEdit.getText().toString().length() == 0){
            errorMessage += "\n*Your input height can't be empty";
        }
        else {
            if (Integer.parseInt(binding.heightEdit.getText().toString()) <= 50 || Integer.parseInt(binding.weightEdit.getText().toString()) >= 220) {
                errorMessage += "\n*Your input height needs to be between 50 and 220 cm";
            }
        }
        if (errorMessage.length() != 0){
            binding.errorMessage.setVisibility(View.VISIBLE);
            binding.errorMessage.setText(errorMessage);
            return false;
        }
        else{
            binding.errorMessage.setText("");
            binding.errorMessage.setVisibility(View.GONE);
            return true;
        }
    }

//    public void requestTakePhoto() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            // 检查是否有存储和拍照权限
//            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                    && getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//            ) {
//                //有权限
//                takePhoto();
//            } else {
//                //没有权限，开始申请
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
//            }
//        }else {
//            takePhoto();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == TAKE_PHOTO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toCamera();
            } else {
                Toast.makeText(getActivity(), "Failed to access camara！", Toast.LENGTH_SHORT).show();
            }
        }
    }


//    public void openGallery() {
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
//            // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
//            // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    GALLERY_REQUEST_CODE);
//
//        } else { //权限已经被授予，在这里直接写要执行的相应方法即可
//            choosePhoto();
//        }
//    }


//    private void choosePhoto() {
//        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
//        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
//        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
//        startActivityForResult(intentToPickPic, GALLERY_REQUEST_CODE);
//    }




//    // 拍照
//    private void takePhoto() {
//        // 要保存的文件名
//        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
//        String fileName = "photo_" + time;
//        // 创建一个文件夹
//        String path = Environment.getExternalStorageDirectory() + "/take_photo";
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        // 要保存的图片文件
//        imgFile = new File(file, fileName + ".jpeg");
//        // 将file转换成uri
//        // 注意7.0及以上与之前获取的uri不一样了，返回的是provider路径
//        imgUri = getUriForFile(getActivity(), imgFile);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        // 添加Uri读取权限
//        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//        // 或者
//        getActivity().grantUriPermission("com.example.sun_safe_app", imgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        // 添加图片保存位置
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
//        startActivityForResult(intent, CAMERA_REQUEST_CODE);
//
//
//    }


    // 从file中获取uri
    // 7.0及以上使用的uri是contentProvider content://com.rain.takephotodemo.FileProvider/images/photo_20180824173621.jpg
    // 6.0使用的uri为file:///storage/emulated/0/take_photo/photo_20180824171132.jpg
//    private static Uri getUriForFile(Context context, File file) {
//        if (context == null || file == null) {
//            throw new NullPointerException();
//        }
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= 24) {
//            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.sun_safe_app", file);
//        } else {
//            uri = Uri.fromFile(file);
//        }
//        return uri;
//    }




    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        此方法在视图View已经创建后返回的，但是这个view 还没有添加到父级中。
        我们在这里可以重新设定view的各个数据，但是不能修改对话框最外层的ViewGroup的布局参数。
        因为这里的view还没添加到父级中，我们需要在下面onStart生命周期里修改对话框尺寸参数
         */


    }

    @Override
    public void onStart() {
        /*
            因为View在添加后,对话框最外层的ViewGroup并不知道我们导入的View所需要的的宽度。 所以我们需要在onStart生命周期里修改对话框尺寸参数
         */
        //设置宽度，固定代码
        WindowManager m= getDialog().getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p= getDialog().getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.95);//设置dialog的宽度为当前手机屏幕宽度*0.8
        getDialog().getWindow().setAttributes(p);



        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == -1) {
                    try {
                        //将拍摄的照片显示出来


                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(ImageUri));

                        InputStream input =  getContext().getContentResolver().openInputStream(ImageUri);
                        ExifInterface ei;
                        if (Build.VERSION.SDK_INT > 23)
                            ei = new ExifInterface(input);
                        else
                            ei = new ExifInterface(ImageUri.getPath());
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        Bitmap rotatedBitmap = null;
                        switch(orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(bitmap, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(bitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(bitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = bitmap;
                        }




                        RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(),rotatedBitmap);
                        img.setCircular(true);
                        MyViewModel vm =  new ViewModelProvider(getActivity()).get(MyViewModel.class);

                        vm.setBitmap(rotatedBitmap);


                        binding.imageView1.setBackground(img);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case TAKE_CAMARA:
                if (resultCode == -1) {
                    try {
                        //将相册的照片显示出来
                        Uri uri_photo = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri_photo));
                        RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                        img.setCircular(true);
                        MyViewModel vm =  new ViewModelProvider(getActivity()).get(MyViewModel.class);


                        vm.setBitmap(bitmap);


                        binding.imageView1.setBackground(img);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                // 拍照并进行裁剪
//                case CAMERA_REQUEST_CODE:
//                    try {
//                        //将拍摄的照片显示出来
//                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imgUri));
//                        RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
//                        img.setCircular(true);
//                        MyViewModel vm =  new ViewModelProvider(getActivity()).get(MyViewModel.class);
//
//                        vm.setBitmap(bitmap);
//
//
//                        binding.imageView1.setBackground(img);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//                // 打开图库获取图片并进行裁剪
//                case GALLERY_REQUEST_CODE:
//                    break;
//            }
//        }
//    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent, TAKE_CAMARA);
        Log.i(TAG, "跳转相册成功");

    }


    //跳转相机
    private void toCamera() {
        //创建File对象，用于存储拍照后的图片
//        File outputImage = new File(getExternalCacheDir(), "outputImage.jpg");
        File outputImage = new File(getActivity().getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (outputImage.exists()) {
            outputImage.delete();
        } else {
            try {
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断SDK版本高低，ImageUri方法不同
        if (Build.VERSION.SDK_INT >= 24) {
            ImageUri = FileProvider.getUriForFile(getActivity(), "com.example.sun_safe_app", outputImage);
        } else {
            ImageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
        startActivityForResult(intent, TAKE_PHOTO);


    }




    public int verifyPermissions(Activity activity, java.lang.String permission) {
        int Permission = ActivityCompat.checkSelfPermission(activity, permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "已经同意权限");
            return 1;
        } else {
            Log.i(TAG, "没有同意权限");
            return 0;
        }
    }

    //    public void requestTakePhoto() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            // 检查是否有存储和拍照权限
//            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                    && getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//            ) {
//                //有权限
//                takePhoto();
//            } else {
//                //没有权限，开始申请
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
//            }
//        }else {
//            takePhoto();
//        }
//    }

    private void showListDialog() {
        SelectPhotoDialog dialog=new SelectPhotoDialog(getContext());


        dialog.setConfirm("OK", new SelectPhotoDialog.OnConfirmListener() {
            @Override
            public void onConfirm(SelectPhotoDialog dialog) {
                toPicture();

            }
        });

        dialog.setCancel("Cancel", new SelectPhotoDialog.OnCancelListener() {
            @Override
            public void onCancel(SelectPhotoDialog dialog) {
                //检查是否已经获得相机的权限
                if (verifyPermissions(getActivity(), PERMISSIONS_STORAGE[2]) == 0) {
                    Log.i(TAG, "提示是否要授权");
//                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 3);
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, TAKE_PHOTO);


                } else {
                    //已经有权限
                    toCamera();  //打开相机
                }
//                requestTakePhoto();

            }

        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();




    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }




}