package com.example.ws.ui.home;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ws.HttpConnect;
import com.example.ws.R;
import com.example.ws.SecondActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ListView listView;
    LinearLayout containers;
    ImageView movieImg;
    ArrayList<Movies> list;
    Integer[] imgs = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,
            R.drawable.img6,R.drawable.img7,R.drawable.img8,R.drawable.img9,R.drawable.img10};
    Context context;
    private HomeViewModel homeViewModel;

    public HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        context = container.getContext();
        listView = root.findViewById(R.id.listView);
        containers = root.findViewById(R.id.containers);
        movieImg = root.findViewById(R.id.movieImg);
        list = new ArrayList<>();
        getData();
        return root;
    }
    private void getData() {
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20201018";
        MovieAsync movieAsync = new MovieAsync();
        movieAsync.execute(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(list.get(position).getMovieNm());
                builder.setMessage("순위 : " + list.get(position).getRank() +
                        "위 \n개봉일 : " + list.get(position).getOpenDt() + "\n관객수 : " + list.get(position).getAudiAcc()+" 명");

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
            progressDialog = new ProgressDialog(context);
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
                    Movies movie = new Movies(rank,movieNm,openDt,audiAcc,rankInten);
//                    Log.d("[TEST]", movieImg+"");
                    list.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
            MovieAdapter movieAdapter = new MovieAdapter();
            listView.setAdapter(movieAdapter);

        }

    } //end AsyncTask

    class MovieAdapter extends BaseAdapter {

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
            View movieView = null;
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
