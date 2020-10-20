package com.example.p667;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssAntennaInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        String [] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
//                ,Manifest.permission.CALL_PHONE 이런 식으로 더 추가할 수 있다
        };
        ActivityCompat.requestPermissions(this, permissions,65535);
        // 여기서 permissions를 물어본다. 코드는 101
        // requestCode는 65535까지 가능하다. 2^16 16진수 쓰는듯
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Finish", Toast.LENGTH_LONG).show();
            finish();
        }
        MyLocation myLocation = new MyLocation();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, // GPS+NETWORK=PASSIVE / GPS는 app를 켜면 알아서 나옴
                // PASSIVE_PROVIDER로 설정하고 버튼 누르면 바로 받아와진다(NETWORK 사용하기 때문에 빠른 것 같다)
                1, //1초 기다림
                0, //조금만 움직여도 받아온다
                myLocation
        );
    }
    public void ck(View v){
        startMyLocation();
    }

    private void startMyLocation() {
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Finish", Toast.LENGTH_LONG).show();
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
        }
    }
}