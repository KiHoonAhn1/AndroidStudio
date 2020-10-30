package com.example.p712;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    TextView tx;
    Intent intent;
    NotificationManager manager;
    PendingIntent fullScreenPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }//end onCreate

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
    } // MyFService의 intent가 보낸걸 얘가 받는다
