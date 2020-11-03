package com.example.ws1;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tx_id,tx_pwd,tx_quiz;
    HttpAsync httpAsync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_id = findViewById(R.id.tx_id);
        tx_pwd = findViewById(R.id.tx_pwd);
        tx_quiz = findViewById(R.id.tx_quiz);
    }

    public void ck_login(View v){
        String id = tx_id.getText().toString();
        String pwd = tx_pwd.getText().toString();
        String quiz = tx_quiz.getText().toString();
//        Log.d("[TEST]",id+" "+pwd+" "+quiz);
//        Toast.makeText(this, id+pwd+quiz, Toast.LENGTH_SHORT).show();
        String url = "http://192.168.35.179/android/wsLogin.jsp";
        url += "?id="+id+"&pwd="+pwd+"&quiz="+quiz;
//        String result = HttpConnect.getString(url);
//        thread 안에서 네트워크로 보내야 한다. 이러면 반응X
        httpAsync = new HttpAsync();
        httpAsync.execute(url);
    }
    class HttpAsync extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(com.example.ws1.MainActivity.this);
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
//            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            if(s.equals("1")){
                Intent intent = new Intent(com.example.ws1.MainActivity.this, SecondActivity.class); // 이 부분 Second로 바꿔야 함
                startActivity(intent);
            }else if(s.equals("2")){
                AlertDialog.Builder dialog = new AlertDialog.Builder(com.example.ws1.MainActivity.this);
                dialog.setTitle("Login fail");
                dialog.setMessage("아이디/비밀번호를 확인해주세요");
                progressDialog.dismiss();
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
            }else if(s.equals("3")){
                AlertDialog.Builder dialog = new AlertDialog.Builder(com.example.ws1.MainActivity.this);
                dialog.setTitle("QUIZ fail");
                dialog.setMessage("정답이 숫자보다 높습니다");
                dialog.setIcon(R.drawable.up);
                progressDialog.dismiss();
                dialog.setPositiveButton("재도전", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
            }else if(s.equals("4")){
                AlertDialog.Builder dialog = new AlertDialog.Builder(com.example.ws1.MainActivity.this);
                dialog.setTitle("QUIZ fail");
                dialog.setMessage("정답이 숫자보다 낮습니다");
                dialog.setIcon(R.drawable.down);
                progressDialog.dismiss();
                dialog.setPositiveButton("재도전", new DialogInterface.OnClickListener() {
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