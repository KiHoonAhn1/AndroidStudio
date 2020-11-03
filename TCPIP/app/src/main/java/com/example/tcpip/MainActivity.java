package com.example.tcpip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.msg.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView tx_list,tx_msg;
    EditText et_ip, et_msg;

    int port;
    String address;
    String id;
    Socket socket;

    Sender sender;

    // FCM
    TextView tx;
    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_list = findViewById(R.id.tx_list);
        tx_msg = findViewById(R.id.tx_msg);
        et_ip = findViewById(R.id.et_ip);
        et_msg = findViewById(R.id.et_msg);
        port = 5555;
        address = "192.168.0.144";
        id = "[JEAN]";
        new Thread(con).start(); // 밑에 connect를 thread로 실행

        tx = findViewById(R.id.tx);
        FirebaseMessaging.getInstance().subscribeToTopic("car")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "FCM Complete ...";
                        if(!task.isSuccessful()){
                            msg = "FCM Fail";
                        }
                        Log.d("[TAG]:",msg);
                    }
                }); // 제목
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("notification"));

    }
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String title = intent.getStringExtra("title");
                String control = intent.getStringExtra("control");
                String data = intent.getStringExtra("data");
                tx.setText(control+" "+data);
                Toast.makeText(MainActivity.this,
                        title+" "+control+" "+data, Toast.LENGTH_LONG).show();
            }
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if(Build.VERSION.SDK_INT >= 26){ //버전 체크를 해줘야 작동하도록 한다
                vibrator.vibrate(VibrationEffect.createOneShot(1000,20));
            }else{
                vibrator.vibrate(1000);
            }
//            PendingIntent fullScreenPendingIntent;
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = null;
            if(Build.VERSION.SDK_INT >= 26){
                if(manager.getNotificationChannel("ch1") == null){
                    manager.createNotificationChannel(new NotificationChannel("ch1", "chname", NotificationManager.IMPORTANCE_HIGH));
                }
                builder = new NotificationCompat.Builder(MainActivity.this, "ch1");
            }else{
                builder = new NotificationCompat.Builder(MainActivity.this);
            }
//            Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
//            builder.setPriority(Notification.PRIORITY_MAX);
//            fullScreenPendingIntent = PendingIntent.getActivity(
//                    MainActivity.this, 101, intent, PendingIntent.FLAG_CANCEL_CURRENT
//            );
//            intent1.setClass(MainActivity.this, MainActivity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            builder.setFullScreenIntent(fullScreenPendingIntent, true);
//            builder.setAutoCancel(true);
//            builder.setWhen(System.currentTimeMillis());
//            builder.setContentIntent(fullScreenPendingIntent);

            builder.setContentTitle("Noti Test");
            builder.setContentText("Content Text");
            builder.setSmallIcon(R.drawable.img07);
            Notification noti = builder.build();
            manager.notify(1,noti);

        }
    };
    @Override
    public void onBackPressed() { // 뒤로갈 때
        super.onBackPressed();
        try{
            Msg msg = new Msg(null, id,"q");
            sender.setMsg(msg);
            new Thread(sender).start();
            if(socket != null){
                socket.close();
            }
            finish();
            onDestroy();
        }catch(Exception e){

        }
    }
    public void clickBt(View v){
        String etip = et_ip.getText().toString();
        String etmsg = et_msg.getText().toString();
        Msg msg = null;
        if(etip == null || etip.equals("")){
            msg = new Msg(id,etmsg);
        }else{
            ArrayList<String> ips = new ArrayList<>();
            ips.add(etip);
            msg = new Msg(ips,id,etmsg);
        }
        sender.setMsg(msg);
        new Thread(sender).start();
    }
    public void clickBt2(View v){

    }

    class Receiver extends Thread {
        ObjectInputStream oi;

        public Receiver(Socket socket) throws IOException {
            oi = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            while (oi != null) {
                Msg msg = null;
                try {
                    msg = (Msg) oi.readObject();
                    if (msg.getMaps() != null) {
                        HashMap<String, Msg> hm = msg.getMaps();
                        Set<String> keys = hm.keySet();
                        for (String k : keys) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String tx = tx_list.getText().toString();
                                    tx_list.setText(k+"\n"+tx);
                                }
                            });
//                            System.out.println(k);
                        }
                        continue; // 밑에 있는 System.out.println을 실행하지 않고 지나가기 위해 continue;
                    }
                    Msg finalMsg = msg;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String tx = tx_msg.getText().toString();
                            tx_msg.setText(finalMsg.getId()+ finalMsg.getMsg()+"\n"+tx);
                        }
                    });
                    System.out.println(msg.getId() + ": " + msg.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            try {
                if (oi != null) {
                    oi.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class Sender implements Runnable { // Thread 말고 implements Runnable로 !
        Socket socket;
        ObjectOutputStream oo;
        Msg msg;

        public Sender(Socket socket) throws IOException {
            this.socket = socket;
            oo = new ObjectOutputStream(socket.getOutputStream());
        }

        public void setMsg(Msg msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            if (oo != null) {
                try {
                    oo.writeObject(msg); // 이떄 exception이 나면 server가 죽어있을 가능성이 큼

                } catch (IOException e) {
//					e.printStackTrace();
                    try {
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    try {
                        Thread.sleep(2000);
                        connect();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

    }

    Runnable con = new Runnable() {
        @Override
        public void run() {
            try {
                connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void connect() throws Exception {
        try {
            socket = new Socket(address, port);
        } catch (Exception e) {
            while (true) {
                try {
                    Thread.sleep(1000);
                    socket = new Socket(address, port);
                    break;
                } catch (Exception e1) {
                    System.out.println("Retry ..");

                }
            }
        }

        System.out.println("Connected Server: " + address);
        sender = new Sender(socket);
        new Receiver(socket).start(); // server에서 정보를 받음

        getList(); // connect가 일어난 후 바로 getList를 호출
    }

    private void getList() throws InterruptedException {
        while(true){
            tx_list.setText(null); // append되지 않게 계속 지우고 받아옴
            Thread.sleep(10000);
            Msg msg = new Msg(null,"[Ki]","1");
            sender.setMsg(msg);
            new Thread(sender).start();
        }

    }
}