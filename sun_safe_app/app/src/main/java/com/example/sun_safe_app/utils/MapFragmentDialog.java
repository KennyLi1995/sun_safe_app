package com.example.sun_safe_app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.MapsFragmentBinding;
import com.example.sun_safe_app.databinding.ProfileModifyDialogBinding;
import com.example.sun_safe_app.ui.mySkin.MySkinViewModel;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class MapFragmentDialog extends DialogFragment {

    private MapsFragmentBinding binding;
    private MainActivity parentActivity;
    private MapView mapView;

    public MapFragmentDialog(Activity parentActivity){
        this.parentActivity = (MainActivity) parentActivity;
    }


    double lat = 0;
    double lon = 0;
    String location = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String token = getString(R.string.mapbox_access_token);
        Mapbox.getInstance(getContext(), token);

        // Inflate the View for this fragment
        binding = MapsFragmentBinding.inflate(inflater,container,false);
        View view = inflater.inflate(R.layout.maps_fragment,container,false);



        mapView = binding.mapView;
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull MapboxMap mapboxMap) {
//
//                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
//                    @Override
//                    public void onStyleLoaded(@NonNull Style style) {
//
//                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
//
//
//                    }
//                });
//
//            }
//        });


        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        binding.addressTextField.setPlaceholderText("Please input your address");
        // declare a Geocoder entity
//        Geocoder gc = new Geocoder(parentActivity, Locale.ENGLISH);

//        binding.searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                List<Address> addresseList = null;
//                try {
//                    // find the address according to user's input
//                    addresseList = gc.getFromLocationName(
//                            binding.addressTextField.getEditText().getText().toString(), 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                lat = 0;
//                lon = 0;
//                // get the latitude and longitude of the location
//                if(addresseList!=null&&addresseList.size()>0){
//                    Address address_temp = addresseList.get(0);
//                    lat = address_temp.getLatitude();
//                    lon = address_temp.getLongitude();
//                    location = "";
//                    if (address_temp.getAddressLine(0) != null){
//                        location += address_temp.getAddressLine(0);
//                    }
//
//                }



                // create the map
//                LatLng latLng= new LatLng(lat, lon);
//                mapView = view.findViewById(R.id.mapView);
//                mapView.onCreate(savedInstanceState);
//                createMaps(latLng);
//            }
//        });

//        binding.negativeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        binding.positiveText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String laLong = lat + " " + lon;
//                DateViewModel vm =  new ViewModelProvider((ViewModelStoreOwner) parentActivity).get(DateViewModel.class);
//                if (!vm.getLocationString().equals(laLong)) {
//                    vm.setLocationString(laLong);
//                    vm.setAddressString(location);
//                }
//                mapView.onDestroy();
//                dismiss();
//            }
//        });
        return view;
    }

//    @Override
//    public void show(FragmentManager manager, String tag) {
//        //避免重复添加的异常 java.lang.IllegalStateException: Fragment already added
//        Fragment fragment = manager.findFragmentByTag(tag);
//        if (fragment != null) {
//            FragmentTransaction fragmentTransaction = manager.beginTransaction();
//            fragmentTransaction.remove(fragment);
//            fragmentTransaction.commitAllowingStateLoss();
//        }
//        //避免状态丢失的异常 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
//        try {
//            super.show(manager, tag);
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }






    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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
//        mapView.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        dismissAllowingStateLoss();
    }

    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
//        mapView.onStop();
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
                                "defaultMarker", BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.mapbox_marker_icon_default)), false);

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