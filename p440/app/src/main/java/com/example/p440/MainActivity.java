package com.example.p440;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Person> persons;
    LinearLayout container;

    EditText etx_name;
    EditText etx_birth;
    EditText etx_phone;
    TextView people;

    ActionBar actionBar;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
        persons = new ArrayList<>();
        etx_name = findViewById(R.id.etx_name);
        etx_birth = findViewById(R.id.etx_birth);
        etx_phone = findViewById(R.id.etx_phone);
        people = findViewById(R.id.people);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Network");
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo wifi = null;
                NetworkInfo mobile = null;
                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    cm = (ConnectivityManager) context.getSystemService(
                            Context.CONNECTIVITY_SERVICE
                    );
                    mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (mobile != null && mobile.isConnected()) {
                        actionBar.setIcon(R.drawable.wififree);
                    } else if (wifi != null && wifi.isConnected()) {
                        actionBar.setIcon(R.drawable.wifi);
                    } else {
                    }
                }actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                        ActionBar.DISPLAY_USE_LOGO);
                actionBar.show();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }//onCreate 끝

        class PersonAdapter extends BaseAdapter {

            ArrayList<Person> datas;

            public PersonAdapter(ArrayList<Person> datas) {
                this.datas = datas;
            }


            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public Object getItem(int position) {
                return datas.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(
                        R.layout.person,
                        container,
                        true
                );
                TextView tx_name = view.findViewById(R.id.tx_name);
                TextView tx_birth = view.findViewById(R.id.tx_birth);
                TextView tx_phone = view.findViewById(R.id.tx_phone);
                Person p = datas.get(position);
                tx_name.setText(p.getName());
                tx_birth.setText(p.getBirth());
                tx_phone.setText(p.getPhone());
                return view;
            }
        }

            public void setList(final ArrayList<Person> persons) {
                final PersonAdapter personAdapter = new PersonAdapter(persons);
                listView.setAdapter(personAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        final AlertDialog.Builder builder =
                                new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Hi");
                        builder.setMessage("Name : " + persons.get(position).getName());
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                persons.remove(position);
                                personAdapter.notifyDataSetChanged();
                                people.setText("회원 수 : " + persons.size() + "명");
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.show();
                    }
                });
            }


            public void getData(String name, String birth, String phone) {
                persons.add(new Person(name, birth, phone));
                setList(persons);
            }

            public void getDatas() {
                if (persons.size() == 0) {
                    persons.add(new Person("안기훈", "1994-05-26", "010-8555-5432"));
                    persons.add(new Person("고객1", "1995-06-27", "010-8555-5433"));
                    persons.add(new Person("고객2", "1996-07-28", "010-8555-5434"));
                    persons.add(new Person("고객3", "1997-08-29", "010-8555-5435"));
                    setList(persons);
                } else {

                }
            }

            public void ckbt(View v) {
                if (v.getId() == R.id.button) {
                    getDatas();
                    people.setText("회원 수 : " + persons.size() + "명");
                } else if (v.getId() == R.id.button2) {
                    String name = etx_name.getText().toString();
                    String birth = etx_birth.getText().toString();
                    String phone = etx_phone.getText().toString();
                    getData(name, birth, phone);
                    people.setText("회원 수 : " + persons.size() + "명");
                }
            }
        }