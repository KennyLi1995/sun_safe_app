package com.example.sun_safe_app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sun_safe_app.databinding.ActivityMainBinding;
import com.example.sun_safe_app.retrofit.RetrofitClient;
import com.example.sun_safe_app.retrofit.RetrofitInterface;
import com.example.sun_safe_app.retrofit.WeatherResponse;
import com.example.sun_safe_app.ui.mySkin.MySkinFragment;
import com.example.sun_safe_app.ui.uvi.UviFragment;
import com.example.sun_safe_app.ui.uvi.UviFragmentLatLongModel;
import com.example.sun_safe_app.ui.uvi.UviFragmentModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private ActivityMainBinding binding;
    protected LocationManager locationManager;
    private String currentLocation;
    private String current_locality;


    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};
    private static final int OPEN_SET_REQUEST_CODE = 100;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_uvi, R.id.navigation_my_skin, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
//
        NavigationUI.setupWithNavController(binding.navView, navController);
        //Sets up a Toolbar for use with a NavController.
//        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController,
//                appBarConfiguration);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_toolbar));

        initPermissions();


        Intent intent=getIntent();
        int bigMake=intent.getIntExtra("bigMake",0);
        if (bigMake == 1) {
            intent.putExtra("bigMake",0);
            MySkinFragment aFragment=new MySkinFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment,aFragment).commit();
            getSupportActionBar().setTitle("My Skin");


        }





        int prev_nav_item = 0;




//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, this);


    }

    @Override
    public void onLocationChanged(Location location) {

        List<Address> addresses;
        Geocoder gcd = new Geocoder(getBaseContext(),
                Locale.getDefault());
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String locality = addresses.get(0).getLocality();
                String subLocality = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String trueAddress = locality + ", " + state + "，" +country;
                UviFragmentModel vm = new
                        ViewModelProvider(this).get(UviFragmentModel.class);
                vm.setMessage(trueAddress);
                UviFragmentLatLongModel vmLatLong = new
                        ViewModelProvider(this).get(UviFragmentLatLongModel.class);
                float lat = (float) location.getLatitude();
                float longitude = (float) location.getLongitude();
                String laLong = lat + " " +longitude;
                vmLatLong.setMessage(laLong);

                vm.setMessage(trueAddress);
                if (subLocality != null) {

                    currentLocation = locality + "," + subLocality;
                } else {

                    currentLocation = locality;
                }
                current_locality = locality;
            }


        } catch (Exception e) {
            e.printStackTrace();
//                Toast.makeText(this, Constants.ToastConstatnts.ERROR_FETCHING_LOCATION, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void selectBottomMenu(final int position) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
            MenuItem item = menu.getItem(position);
            item.setChecked(true);

    }

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_container,
                nextFragment);
        fragmentTransaction.commit();
    }


    //调用此方法判断是否拥有权限
    private void initPermissions() {


        final int OPEN_SET_REQUEST_CODE = 100;
        if (lacksPermission(permissions)) {//判断是否拥有权限
            //请求权限，第二参数权限String数据，第三个参数是请求码便于在onRequestPermissionsResult 方法中根据code进行判断
            ActivityCompat.requestPermissions(this, permissions, OPEN_SET_REQUEST_CODE);
        } else {
            //拥有权限执行操作
        }
    }

    public boolean lacksPermission(String[] permissions) {
        for (String permission : permissions) {
            //判断是否缺少权限，true=缺少权限
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){//响应Code
            case OPEN_SET_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"Searching for your current location...",Toast.LENGTH_LONG).show();
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, this);
                            return;
                        }
                    }
                    //拥有权限执行操作
                } else {
                    Toast.makeText(this,"Don't have access to location!",Toast.LENGTH_LONG).show();

                }
                break;
        }
    }






}