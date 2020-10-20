package com.example.p675;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                LatLng latLng = new LatLng(37.402456, 126.412768);
                gMap.addMarker(
                        new MarkerOptions().position(latLng).title("공항").snippet("xxx")
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background)
                        //snippet : 상세정보, icon~~ : 그림 추가
                );
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        });
    }

    public void ck1(View v){
        LatLng latLng = new LatLng(37,125);
    }

    public void ck2(View v){
        LatLng latLng = new LatLng(38,115);
        gMap.addMarker(
                new MarkerOptions().position(latLng).title("어디").snippet("xxx")
        );
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }
}