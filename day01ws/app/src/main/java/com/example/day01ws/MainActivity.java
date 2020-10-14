package com.example.day01ws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button button;
    Text te;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        te = findViewById(R.id.te);
    }
    public void clickBt(View view){

        button.setText(R.string.app_name);
        Log.d("[TEST]","----------");
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }
}