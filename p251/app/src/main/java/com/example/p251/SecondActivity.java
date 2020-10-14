package com.example.p251;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int result = bundle.getInt("data", 0);
        String str_result = bundle.getString("str", "");
//        bundle를 사용하면 여러 개의 데이터를 가져올 수 있어 더 편하다
//        intent.getIntExtra("data", 0);
        Toast.makeText(this, str_result + ":"+result, Toast.LENGTH_SHORT).show();
    }
}