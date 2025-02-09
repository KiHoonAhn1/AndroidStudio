package com.example.p428;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Person> persons;
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);

                LayoutInflater layoutInflater = getLayoutInflater();
                View dview = layoutInflater.inflate(R.layout.dialog,
                        (ViewGroup) findViewById(R.id.dlayout));
                ImageView dimg = dview.findViewById(R.id.imageView2);
                dimg.setImageResource(persons.get(position).getImg());
                builder.setView(dview);

                builder.setTitle("Hi");
                builder.setMessage("Name : "+persons.get(position).getName());
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
    class PersonAdapter extends BaseAdapter {

        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas){
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
            ImageView im = view.findViewById(R.id.imageView);
            TextView tx_id = view.findViewById(R.id.tx_id);
            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_age = view.findViewById(R.id.tx_age);
            Person p = datas.get(position);
            im.setImageResource(p.getImg());
            tx_id.setText(p.getId());
            tx_name.setText(p.getName());
            tx_age.setText(p.getAge()+"");
            return view;
        }
    }


    public void setList(ArrayList<Person> persons){
        PersonAdapter personAdapter = new PersonAdapter(persons);
        listView.setAdapter(personAdapter);
    }


    public void getData() {
    persons = new ArrayList<>();
    persons.add(new Person(R.drawable.p1, "id001", "일말숙",21));
    persons.add(new Person(R.drawable.p2, "id002", "이말숙",22));
    persons.add(new Person(R.drawable.p3, "id003", "삼말숙",23));
    persons.add(new Person(R.drawable.p4, "id004", "사말숙",24));
    persons.add(new Person(R.drawable.p5, "id005", "오말숙",25));
    persons.add(new Person(R.drawable.p6, "id006", "육말숙",26));
    persons.add(new Person(R.drawable.p7, "id007", "칠말숙",27));
    persons.add(new Person(R.drawable.p8, "id008", "팔말숙",28));
    persons.add(new Person(R.drawable.p9, "id009", "구말숙",29));
    setList(persons);
    }

    public void ckbt(View v){
        getData();
    }

    }