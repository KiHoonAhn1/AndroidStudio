package com.example.p287;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment3 extends Fragment {
    public Fragment3(){}
    TextView textView;
    LocationManager locationManager;

    SupportMapFragment supportMapFragment;
    GoogleMap gmap;
    Fragment map;
    MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_3, container, false);
//        getSupportActionBar().hide();
        String [] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
//                ,Manifest.permission.CALL_PHONE 이런 식으로 더 추가할 수 있다
        };
        ActivityCompat.requestPermissions(R.layout.activity_main, permissions,65535);
        // 여기서 permissions를 물어본다. 코드는 101
        // requestCode는 65535까지 가능하다. 2^16 16진수 쓰는듯

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
                        || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED
                ){
//            Toast.makeText(this, "Finish", Toast.LENGTH_LONG).show();
//            finish();
                    return;
                }
                gmap.setMyLocationEnabled(true);
                LatLng latLng = new LatLng(37.402456,126.412768);
                gmap.addMarker(
                        new MarkerOptions().position(latLng).
                                title("공항").snippet("xxx")
                );
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        });
        textView = viewGroup.findViewById(R.id.textView);




        MyLocation myLocation = new MyLocation();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, // GPS+NETWORK=PASSIVE / GPS는 app를 켜면 알아서 나옴
                // PASSIVE_PROVIDER로 설정하고 버튼 누르면 바로 받아와진다(NETWORK 사용하기 때문에 빠른 것 같다)
                1, //1초 기다림
                0, //조금만 움직여도 받아온다
                myLocation
        );
        startMyLocation();


        return viewGroup;


    }
    private void startMyLocation() {
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Finish", Toast.LENGTH_SHORT).show();
            finish();
        } // SecurityException을 없애기 위해
        Location location = null;
        location = locationManager.getLastKnownLocation(
//                LocationManager.PASSIVE_PROVIDER
                LocationManager.GPS_PROVIDER
        );
        if(location != null){
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            textView.setText(lat+" "+lon);
        }
    }

    class MyLocation implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            textView.setText(lat+" "+lon);
            LatLng latLng = new LatLng(lat,lon);
//            gmap.addMarker(
//                    new MarkerOptions().position(latLng).title("My point").snippet("xxx")
//            );
            // marker는 맛집 등 특정 지점에 찍을 때 사용한다
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }

    @SuppressLint("MissingPermission") // 체크 안하겠다!
    @Override
    protected void onPause() {
        super.onPause();
        if(gmap != null){
            gmap.setMyLocationEnabled(false);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        if (gmap != null) {
            gmap.setMyLocationEnabled(true);
        }}}

//    }}