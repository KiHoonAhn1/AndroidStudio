package com.example.day02ws;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LinearLayout container;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        progressBar = findViewById(R.id.progressBar);
    }

    public void bt(View v) {
        ProgressDialog progressDialog;
        if (v.getId() == R.id.button) {
            container.removeAllViews();
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.login, container, true);
        } else if (v.getId() == R.id.button6) {
            int pdata = progressBar.getProgress();
            progressBar.setProgress(pdata - 1);

        } else if (v.getId() == R.id.button3) {
            container.removeAllViews();
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.register, container, true);
        } else if (v.getId() == R.id.button2) {
            container.removeAllViews();
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.mypage, container, true);
        }
        if (v.getId() == R.id.button6) {
            finish();
        }
        if (v.getId() == R.id.button5) {
            int pdata = progressBar.getProgress();
                progressBar.setProgress(pdata + 1);
            }
    }
}