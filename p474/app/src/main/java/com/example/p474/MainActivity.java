package com.example.p474;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar1,progressBar2;
    TextView textView1,textView2;
    MyHandler1 myHandler1;
    MyHandler2 myHandler2;
    Button button1,button2,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar1.setMax(50);
        progressBar2.setMax(50);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        myHandler1 = new MyHandler1();
        myHandler2 = new MyHandler2();
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
    }
    public void ckbt(View v)
//            throws InterruptedException
            {
        if(v.getId() == R.id.button1){
//            for(int i=0;i<=50;i++){
//                progressBar1.setProgress(i);
//                Thread.sleep(100);
//            }
            MyThread t = new MyThread();
            t.start();
            button1.setEnabled(false);
//            Thread의 흐름은 script 언어와 다르다.  t.start()가 실행되는 순간 밑에 있는 button.setEnabled(false);가 실행된다
//            Thread t = new Thread(new MyThread2());
//            t.start();
        }else if(v.getId() == R.id.button2){
            Thread t = new Thread(new MyThread2());
            t.start();
            button2.setEnabled(false);
        }else if(v.getId() == R.id.button3){
            progress();
        }
    }
    public void progress(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("progress");
        dialog.setMessage("5 seconds");
        final Handler handler = new Handler();
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Downloading ...");
                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 5000);
            }
        });
        dialog.show();
    }

//    Thread t = new Thread() {
    class MyThread extends  Thread{
        @Override
        public void run() {
            for (int i = 0; i <= 50; i++) {
                progressBar1.setProgress(i);
                textView1.setText(i + "");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button1.setEnabled(true);
                }
            });

//            MainThread에서 만들어진 버튼을 변경할 때 subThread에서는 Main의 widget를 변경할 수 없다.
//            따라서 runOnUiThread(new Runnable ... ) 안에 넣어줘야 한다.

        }


    };

    class MyHandler1 extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView1.setText("Handler1:" +data);
            progressBar1.setProgress(data);
        }
    }
    class MyHandler2 extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView2.setText("Handler2:" +data);
            progressBar2.setProgress(data);
            if(data == 49){
                button2.setEnabled(true);
            }
//            Handler은 MainThread의 영역이기 때문에 안에 if(data==49){button2.setEnabled(true)}로
//            버튼을 다시 활성화시킬 수 있다.

        }
    }

    class MyThread2 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <= 50; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message1 = myHandler1.obtainMessage();
                Message message2 = myHandler2.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("tdata",i);
                message1.setData(bundle);
                message2.setData(bundle);
//                myHandler1.sendMessage(message1);
                myHandler2.sendMessage(message2);
            }
        }
    }
}