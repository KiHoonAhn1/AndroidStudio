package com.example.tcpip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_list = findViewById(R.id.tx_list);
        tx_msg = findViewById(R.id.tx_msg);
        et_ip = findViewById(R.id.et_ip);
        et_msg = findViewById(R.id.et_msg);
        port = 5555;
        address = "192.168.0.9";
        id = "[JEAN]";
        new Thread(con).start(); // 밑에 connect를 thread로 실행
    }

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