package com.example.p552;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        String status = sp.getString("status","");
        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }
    public void ckbt(View v){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("status","ok");
        edit.commit();
    } // 다시 로그인 할 때 데이터 유지
    
    public void ckbt2(View v){
        SharedPreferences.Editor edit = sp.edit();
        edit.remove("status");
        edit.commit();
    } // 누르고 다시 로그인하면 켤 때 ok가 뜨지 않음
}