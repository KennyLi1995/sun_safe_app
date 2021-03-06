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
    //????????????????????? ???/???/??????
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};


    private Uri ImageUri;
    public static final int TAKE_PHOTO = 101;
    public static final int TAKE_CAMARA = 100;
    String gender;

    //try 2

//    private ImageView mIv;
//    // ???????????????
//    public final static int CAMERA_REQUEST_CODE = 100;
//    // ?????????????????????
//    public final static int GALLERY_REQUEST_CODE = 200;
//    //???????????????
//    private static final int REQUEST_CROP = 300;
//    private File imgFile;
//    private Uri imgUri;
//    private String file_path = "";
//    private Uri mCutUri;// ????????????????????????uri




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
//        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int pos, long id) {
//
//                String[] options = getResources().getStringArray(R.array.gender);
//                gender = options[pos];
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Another interface callback
//            }
//        });
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

        //??????arrays????????????
        String[] curs = getResources().getStringArray(R.array.gender);
        //????????????personal_spinner??????????????????????????????????????????????????????????????????????????????????????????Android??????????????????
        //??????android.R.layout.simple_spinner_item
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item_vict, curs);
        //???????????????????????? ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.genderSpinner.setAdapter(adapter);

        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("ifInput",false)){
            binding.nameEdit.setText(sharedPref.getString("name",""));
            binding.weightEdit.setText(sharedPref.getInt("weight",0) + "");
            binding.heightEdit.setText(sharedPref.getInt("height",0) + "");

//            if (sharedPref.getString("gender","Male").equals("Male")){
//                binding.genderSpinner.setSelection(0);
//            }
//            else if (sharedPref.getString("gender","Male").equals("Female")){
//                binding.genderSpinner.setSelection(1);
//            }
//            else{
//                binding.genderSpinner.setSelection(2);
//            }

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
            if (Integer.parseInt(binding.heightEdit.getText().toString()) <= 50 || Integer.parseInt(binding.heightEdit.getText().toString()) >= 220) {
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
//            // ????????????????????????????????????
//            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                    && getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//            ) {
//                //?????????
//                takePhoto();
//            } else {
//                //???????????????????????????
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
                Toast.makeText(getActivity(), "Failed to access camara???", Toast.LENGTH_SHORT).show();
            }
        }
    }


//    public void openGallery() {
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {   //???????????????????????????????????????????????????????????????
//            // ??????????????????????????????????????????????????????????????????????????? ??????????????????????????????
//            // ??????????????????????????????????????????????????????????????????onRequestPermissionsResult????????????
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    GALLERY_REQUEST_CODE);
//
//        } else { //????????????????????????????????????????????????????????????????????????
//            choosePhoto();
//        }
//    }


//    private void choosePhoto() {
//        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
//        // ?????????????????????????????????????????????????????????????????????"image/jpeg ??? image/png????????????" ?????????????????? "image/*"
//        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
//        startActivityForResult(intentToPickPic, GALLERY_REQUEST_CODE);
//    }




//    // ??????
//    private void takePhoto() {
//        // ?????????????????????
//        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
//        String fileName = "photo_" + time;
//        // ?????????????????????
//        String path = Environment.getExternalStorageDirectory() + "/take_photo";
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        // ????????????????????????
//        imgFile = new File(file, fileName + ".jpeg");
//        // ???file?????????uri
//        // ??????7.0???????????????????????????uri???????????????????????????provider??????
//        imgUri = getUriForFile(getActivity(), imgFile);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        // ??????Uri????????????
//        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//        // ??????
//        getActivity().grantUriPermission("com.example.sun_safe_app", imgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        // ????????????????????????
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
//        startActivityForResult(intent, CAMERA_REQUEST_CODE);
//
//
//    }


    // ???file?????????uri
    // 7.0??????????????????uri???contentProvider content://com.rain.takephotodemo.FileProvider/images/photo_20180824173621.jpg
    // 6.0?????????uri???file:///storage/emulated/0/take_photo/photo_20180824171132.jpg
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
        ??????????????????View???????????????????????????????????????view ??????????????????????????????
        ?????????????????????????????????view?????????????????????????????????????????????????????????ViewGroup??????????????????
        ???????????????view????????????????????????????????????????????????onStart??????????????????????????????????????????
         */


    }

    @Override
    public void onStart() {
        /*
            ??????View????????????,?????????????????????ViewGroup???????????????????????????View???????????????????????? ?????????????????????onStart??????????????????????????????????????????
         */
        //???????????????????????????
        WindowManager m= getDialog().getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p= getDialog().getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.95);//??????dialog????????????????????????????????????*0.8
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
                        //??????????????????????????????


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
                        //??????????????????????????????
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
//                // ?????????????????????
//                case CAMERA_REQUEST_CODE:
//                    try {
//                        //??????????????????????????????
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
//                // ???????????????????????????????????????
//                case GALLERY_REQUEST_CODE:
//                    break;
//            }
//        }
//    }

    //????????????
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //????????? ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent, TAKE_CAMARA);
        Log.i(TAG, "??????????????????");

    }


    //????????????
    private void toCamera() {
        //??????File???????????????????????????????????????
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
        //??????SDK???????????????ImageUri????????????
        if (Build.VERSION.SDK_INT >= 24) {
            ImageUri = FileProvider.getUriForFile(getActivity(), "com.example.sun_safe_app", outputImage);
        } else {
            ImageUri = Uri.fromFile(outputImage);
        }

        //??????????????????
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
        startActivityForResult(intent, TAKE_PHOTO);


    }




    public int verifyPermissions(Activity activity, java.lang.String permission) {
        int Permission = ActivityCompat.checkSelfPermission(activity, permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "??????????????????");
            return 1;
        } else {
            Log.i(TAG, "??????????????????");
            return 0;
        }
    }

    //    public void requestTakePhoto() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            // ????????????????????????????????????
//            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                    && getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//            ) {
//                //?????????
//                takePhoto();
//            } else {
//                //???????????????????????????
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
                //???????????????????????????????????????
                if (verifyPermissions(getActivity(), PERMISSIONS_STORAGE[2]) == 0) {
                    Log.i(TAG, "?????????????????????");
//                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 3);
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, TAKE_PHOTO);


                } else {
                    //???????????????
                    toCamera();  //????????????
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