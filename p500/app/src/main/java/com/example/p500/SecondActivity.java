package com.example.p500;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ListView listView;
    LinearLayout container;
    ArrayList<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
        list = new ArrayList<>();
        getData();
    }

    private void getData() {
        String url = "http://192.168.0.19/android/items.jsp";
        ItemAsync itemAsync = new ItemAsync();
        itemAsync.execute(url);
    }

    class ItemAsync extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SecondActivity.this);
            progressDialog.setTitle("Get Data ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            return result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for(int i=0;i<ja.length();i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String name = jo.getString("name");
                    String id = jo.getString("id");
                    int age = jo.getInt("age");
                    String level = jo.getString("level");
                    String img = jo.getString("img");
                    Item item = new Item(id,name,age,level,img);
                    list.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ItemAdapter itemAdapter = new ItemAdapter();
            listView.setAdapter(itemAdapter);

        }
    }// end AsyncTask


    class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = null;
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView =
                inflater.inflate(R.layout.item, container,true);
            TextView tx_id = itemView.findViewById(R.id.textView);
            TextView tx_name = itemView.findViewById(R.id.textView2);
            TextView tx_age = itemView.findViewById(R.id.textView3);
            TextView tx_level = itemView.findViewById(R.id.textView4);
            tx_id.setText(list.get(position).getId());
            tx_name.setText(list.get(position).getName());
            tx_age.setText(list.get(position).getAge()+""); // int는 String으로 변경해주기
            tx_level.setText(list.get(position).getLevel());

            final ImageView imageView = itemView.findViewById(R.id.imageView);
            String img = list.get(position).getImg();
            final String url = "http://192.168.0.19/android/img/"+img;
//            GetImg t1 = new GetImg(img, url, imageView);
//            t1.start();
//
//            final ImageView imageView2 = itemView.findViewById(R.id.imageView2);
//            String level = list.get(position).getLevel();
//            String img2 = null;
//            if(level.equals("bronze")){
//                img2 = "img11.jpg";
//            }else if(level.equals("silver")){
//                img2 = "img12.jpg";
//            }else if(level.equals("gold")){
//                img2 = "img13.jpg";
//            }
//            Log.d("[TEST]",img2+" "+level);
//            final String lurl = "http://192.168.0.19/android/img/"+img2;
//            GetImg t2 = new GetImg(img2, lurl, imageView2);
//            t2.start();


            final ImageView imageView2 = itemView.findViewById(R.id.imageView2);
            String level = list.get(position).getLevel();
            String img2 = null;
            if(level.equals("bronze")){
                img2 = "img11.jpg";
            }else if(level.equals("silver")){
                img2 = "img12.jpg";
            }else if(level.equals("gold")){
                img2 = "img13.jpg";
            }
            final String lurl = "http://192.168.0.19/android/img/"+img2;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    URL httpurl = null;
                    URL httplurl = null;
                    InputStream is = null;
                    InputStream lis = null;
                    try {
                        httpurl = new URL(url);
                        httplurl = new URL(lurl);
                        is = httpurl.openStream();
                        lis = httplurl.openStream();
                        final Bitmap bm = BitmapFactory.decodeStream(is);
                        final Bitmap lbm = BitmapFactory.decodeStream(lis);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bm);
                                imageView2.setImageBitmap(lbm);

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();

            return itemView;
        }

//        class GetImg extends Thread {
//            String img;
//            String url;
//            ImageView imageView;
//            public GetImg(String img, String url, ImageView imageView){
//                this.img = img;
//                this.url = url;
//                this.imageView = imageView;
//            }
//
//            @Override
//            public void run() {
//                URL httpurl = null;
//                InputStream is = null;
//                try {
//                    httpurl = new URL(url+img); //이너클래서 안에서 함수가 바깥 변수를 사용하려면 final을 사용해햐 한다
//                    is = httpurl.openStream();
//                    final Bitmap bm = BitmapFactory.decodeStream(is);
//                    runOnUiThread(new Runnable() { //서브스레드에서 메인스레드의 이미지뷰 변경할 때 runOnUiThread사용!
//                        @Override
//                        public void run() {
//                            imageView.setImageBitmap(bm);
//
//                        }
//                    });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    } // end Adapter

}