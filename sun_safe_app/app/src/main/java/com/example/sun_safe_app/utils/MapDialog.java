package com.example.sun_safe_app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.MapsFragmentBinding;
import com.example.sun_safe_app.databinding.MapsFragmentTestBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sunny on 2020/4/21.
 */
public class MapDialog extends android.app.Dialog implements View.OnClickListener{

    private EditText textView1,textView2,textView3,textView4,textView5;
    private ImageView imageView1;
    private String title,message1,message2,cancel,confirm,imageView1String;
    private OnCancelListener cancelListener;
    private OnConfirmListener confirmListener;
    private Activity parentActivity;
    private MapView mapView;

    public MapDialog(Context context, MainActivity m) {
        super(context);
        this.parentActivity = m;

    }

    public MapDialog(Context context, int themeResId) {
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

        String token = "pk.eyJ1IjoiZmFuZ3p1b2xpIiwiYSI6ImNrb2w4czMwdjBscHYydm83OHQ2aG51b2cifQ.SvrUY9vq2K-tXImD0uhWcA";
        Mapbox.getInstance(getContext(), token);
        setContentView(R.layout.maps_fragment);
        //设置宽度，固定代码
        WindowManager m=getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p=getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.8);//设置dialog的宽度为当前手机屏幕宽度*0.8
        getWindow().setAttributes(p);

        // Inflate the View for this fragment

        TextInputLayout address_textField= (TextInputLayout) findViewById(R.id.address_textField);
        address_textField.setPlaceholderText("Please input your address");

        // declare a Geocoder entity
        Geocoder gc = new Geocoder(parentActivity, Locale.ENGLISH);

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Address> addresseList = null;
                try {
                    // find the address according to user's input
                    addresseList = gc.getFromLocationName(
                            address_textField.getEditText().getText().toString(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                double lat = 0;
                double lon = 0;
                // get the latitude and longitude of the location
                if(addresseList!=null&&addresseList.size()>0){
                    Address address_temp = addresseList.get(0);
                    lat = address_temp.getLatitude();
                    lon = address_temp.getLongitude();

                }

                // create the map
                LatLng latLng= new LatLng(lat, lon);
                mapView = findViewById(R.id.mapView);
                mapView.onCreate(savedInstanceState);
                createMaps(latLng);
            }
        });

        TextView textView6 = findViewById(R.id.positiveText);

        textView6.setOnClickListener(this);


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
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dismiss();
                    }
                }, 1000);
                break;
        }
    }





    public interface OnCancelListener{
        void onCancel(MapDialog dialog);
    }

    public interface OnConfirmListener{
        void onConfirm(MapDialog dialog);
    }



    // this method create a map with the location in center
    public void createMaps(LatLng latLng){

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        CameraPosition position = new CameraPosition.Builder()
                                .target(latLng)
                                .zoom(13)
                                .build();
                        mapboxMap.setCameraPosition(position);

//                         Create a SymbolManager.
                        SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);

                        // Set non-data-driven properties.
                        symbolManager.setIconAllowOverlap(true);
                        symbolManager.setTextAllowOverlap(true);
//                        Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
//                                R.drawable.mapbox_marker_icon_default);
//                        style.addImage("defaultMarker", icon);
                        style.addImage(
                                "defaultMarker", BitmapUtils.getBitmapFromDrawable(parentActivity.getResources().getDrawable(R.drawable.mapbox_marker_icon_default)), false);

                        // Create a symbol at the specified location.
                        SymbolOptions symbolOptions = new SymbolOptions()
                                .withLatLng(new LatLng(latLng.getLatitude(), latLng.getLongitude()))
                                .withIconImage("defaultMarker")
                                .withIconSize(1.3f);

                        // Use the manager to draw the symbol.
                        symbolManager.create(symbolOptions);


//                        mapboxMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(latLng.getLatitude(), latLng.getLongitude()))
//                                .title(""));
                    }
                });
            }
        });

    }
}