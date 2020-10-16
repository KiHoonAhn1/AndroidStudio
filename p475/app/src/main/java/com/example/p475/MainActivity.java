package com.example.p475;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView textView1,textView2;
    MyHandler1 myHandler1;
    MyHandler2 myHandler2;
    ProgressBar progressBar1,progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        myHandler1 = new MyHandler1();
        myHandler2 = new MyHandler2();

        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar1.setMax(200);
        progressBar2.setMax(5000);

            Thread t1 = new Thread(new MyThread1());
            Thread t2 = new Thread(new MyThread2());
            t1.start();
            t2.start();
    }

    class MyHandler1 extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata1",0);
            textView1.setText(data+" km/h");
            progressBar1.setProgress(data);
        }
    }
    class MyHandler2 extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata2",0);
            textView2.setText(data+" rpm");
            progressBar2.setProgress(data);
//            Handler은 MainThread의 영역이기 때문에 안에 if(data==49){button2.setEnabled(true)}로
//            버튼을 다시 활성화시킬 수 있다.

        }
    }

    class MyThread1 implements Runnable {

        @Override
        public void run() {
            Random r = new Random();
            for (int a = 0; a <= 200; a++) {
                int i = r.nextInt(150);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message1 = myHandler1.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("tdata1", i);
                message1.setData(bundle);
                myHandler1.sendMessage(message1);
            }
        }
    }
    class MyThread2 implements Runnable {

        @Override
        public void run() {
            Random r = new Random();
            for (int a = 0; a <= 200; a++) {
                int i = r.nextInt(5000);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message2 = myHandler2.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("tdata2",i);
                message2.setData(bundle);
                myHandler2.sendMessage(message2);
            }
        }
    }
}