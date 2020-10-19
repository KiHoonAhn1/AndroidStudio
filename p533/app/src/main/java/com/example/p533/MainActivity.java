package com.example.p533;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tx_id,tx_pwd;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_id = findViewById(R.id.tx_id);
        tx_pwd = findViewById(R.id.tx_pwd);
    }
    public void ckbt(View v) throws InterruptedException {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Login ...");
//            progressDialog.setCancelable(false);
        progressDialog.show();

        String id = tx_id.getText().toString();
        String pwd = tx_pwd.getText().toString();
        if(id.equals("id01") && pwd.equals("pwd01")){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Login fail");
            dialog.setMessage("login failed");
            progressDialog.dismiss();
            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            dialog.show();
        }
        }
    }