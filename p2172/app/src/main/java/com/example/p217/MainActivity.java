package com.example.p217;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void clickb1(View v){
        Toast t = Toast.makeText(this,"Toast1 ...", Toast.LENGTH_SHORT);
//        t.setGravity(50, 50, 100);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }
    public void clickb2(View v){

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast,
                findViewById(R.id.toast_layout));

        Toast t = new Toast(this);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setDuration(Toast.LENGTH_LONG);
        t.setView(view);
        t.show();
    }
}