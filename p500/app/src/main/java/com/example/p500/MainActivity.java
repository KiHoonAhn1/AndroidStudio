package com.example.p500;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tx_id,tx_pwd;
    HttpAsync httpAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_id = findViewById(R.id.tx_id);
        tx_pwd = findViewById(R.id.tx_pwd);
    }
    public void ck(View v){
        String id = tx_id.getText().toString();
        String pwd = tx_pwd.getText().toString();
//        Toast.makeText(this, id+pwd, Toast.LENGTH_SHORT).show();
        String url = "http://192.168.0.9/android/login.jsp";
        url += "?id="+id+"&pwd="+pwd;
//        String result = HttpConnect.getString(url);
//        thread 안에서 네트워크로 보내야 한다. 이러면 반응X
        httpAsync = new HttpAsync();
        httpAsync.execute(url);
    }

    class HttpAsync extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Login ...");
//            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPostExecute(String s) {
        String result = s.trim();
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
        if(s.equals("1")){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }else if(s.equals("2")){
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

    }
