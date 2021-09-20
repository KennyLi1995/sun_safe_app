package com.example.sun_safe_app.ui.map;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.MapsFragmentBinding;
import com.example.sun_safe_app.databinding.MapsFragmentTestBinding;
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


public class MapsFragment extends Fragment {
    private MapView mapView;
    private MapsFragmentTestBinding binding;
    public MapsFragment(){}
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String token = getString(R.string.mapbox_access_token);
        Mapbox.getInstance(getContext(), token);
        // Inflate the View for this fragment

        binding = MapsFragmentTestBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.addressTextField.setPlaceholderText("Please input your address");

        // declare a Geocoder entity
        Geocoder gc = new Geocoder(this.getActivity(), Locale.CHINA);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Address> addresseList = null;
                try {
                    // find the address according to user's input
                    addresseList = gc.getFromLocationName(
                            binding.addressTextField.getEditText().getText().toString(), 1);
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
                mapView = view.findViewById(R.id.mapView);
                mapView.onCreate(savedInstanceState);
                createMaps(latLng);
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
