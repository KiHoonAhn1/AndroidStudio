package com.example.ws;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    ListView listView;
    LinearLayout containers;
    ImageView movieImg;
    ArrayList<Movie> list;
    Integer[] imgs = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,
            R.drawable.img6,R.drawable.img7,R.drawable.img8,R.drawable.img9,R.drawable.img10};
//    Context context;
    public Fragment2(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_2, container, false);
//        context = container.getContext();
        listView = viewGroup.findViewById(R.id.listView);
        containers = viewGroup.findViewById(R.id.containers`);
        movieImg = viewGroup.findViewById(R.id.movieImg);
        list = new ArrayList<>();
        getData();

        return viewGroup;
    }// end onCreate

    private void getData() {
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20201018";
        MovieAsync movieAsync = new MovieAsync();
        movieAsync.execute(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // this 대신 getActivity !!
                builder.setTitle(list.get(position).getMovieNm());
                builder.setMessage("순위 : " + list.get(position).getRank() +
                        "위 \n개봉일 : " + list.get(position).getOpenDt() + "\n관객수 : " + list.get(position).getAudiAcc() + " 명");
                Log.d("log", list.get(position).getMovieNm());
                builder.setIcon(imgs[Integer.parseInt(list.get(position).getRank())]);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                    }
                });

                builder.show();
            }
        });
    }
    class MovieAsync extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
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
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            JSONArray ja = null;
            JSONObject jo = null;
            progressDialog.dismiss();

            try {
                jo = new JSONObject(s);
                String str = jo.getString("boxOfficeResult");
                jo = new JSONObject(str);
                str = jo.getString("dailyBoxOfficeList");
                Log.d("[TEST]",str);
                ja = new JSONArray(str);
                for(int i=0;i<ja.length();i++){
                    JSONObject jo2 = ja.getJSONObject(i);
                    String rank = jo2.getString("rank");
                    String movieNm = jo2.getString("movieNm");
                    String openDt = jo2.getString("openDt");
                    String audiAcc = jo2.getString("audiAcc");
                    String rankInten = jo2.getString("rankInten");
                    Movie movie = new Movie(rank,movieNm,openDt,audiAcc,rankInten);
                    Log.d("[TEST]", movieNm+"");
                    list.add(movie);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("[TEST]", "movieAdapter before!");
            MovieAdapter movieAdapter = new MovieAdapter();
            listView.setAdapter(movieAdapter);
            Log.d("[TEST]", "movieAdapter after!");
        }

    } //end AsyncTask

    class MovieAdapter extends BaseAdapter {
    private Context context;
        @Override
        public int getCount() {
            Log.d("[TEST]", "getCount!");
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            Log.d("[TEST]", "getItem!");
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            Log.d("[TEST]", "getItemId");
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View movieView = null;
            Log.d("[TEST]", "getView!");
            LayoutInflater inflater =
                    (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            movieView =
                    inflater.inflate(R.layout.movie, containers,true);
            TextView tx_rank = movieView.findViewById(R.id.tx_rank);
            TextView tx_name = movieView.findViewById(R.id.tx_name);
            TextView tx_open = movieView.findViewById(R.id.tx_open);
            TextView tx_audi = movieView.findViewById(R.id.tx_audi);

            ImageView img1 = movieView.findViewById(R.id.movieImg);
            ImageView img2 = movieView.findViewById(R.id.img_updown);
            TextView tx_rankInten = movieView.findViewById(R.id.tx_rankInten);

            Log.d("[TEST]",list.get(position).getAudiAcc());
            tx_rank.setText(list.get(position).getRank()+"위");
            tx_name.setText(list.get(position).getMovieNm());
            tx_open.setText("개봉일 : "+list.get(position).getOpenDt()); // int는 String으로 변경해주기
            tx_audi.setText("관객 수 : "+list.get(position).getAudiAcc()+" 명");

            String i = list.get(position).getRank();
            img1.setImageResource(imgs[Integer.parseInt(i)-1]);
            if(Integer.parseInt(list.get(position).getRankInten()) < 0){
                tx_rankInten.setText(list.get(position).getRankInten());
                img2.setImageResource(R.drawable.down);
            }else if(Integer.parseInt(list.get(position).getRankInten()) == 0){
                img2.setImageResource(R.drawable.non);
                tx_rankInten.setText("");
            }else if(Integer.parseInt(list.get(position).getRankInten()) > 0){
                tx_rankInten.setText(list.get(position).getRankInten());
                img2.setImageResource(R.drawable.up);
            }
            return movieView;
        }
    } //end Adapter

}
