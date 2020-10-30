package com.example.ws;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1  extends Fragment {

    TextView textView;
    EditText editText;
    ImageView imageView;
//    View1Manager manager;
    String str;
    WebView webView;
    public Fragment1(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
        webView = v.findViewById(R.id.webView);  // webView 를 가져온다.
        webView.setWebViewClient(new WebViewClient());  // 안드로이드에서 제공해주는 기본적인 브라우저의 기능

            WebSettings webSettings = webView.getSettings();  // setting
            webSettings.setJavaScriptEnabled(true);  // JavaScript 허용
        webView.loadUrl("https://www.cgv.co.kr");
        return v;
        }

    public void setTx(String str) {
    }


//    public static interface View1Manager{
//        public void changeTx(String str);
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof View1Manager){
//            manager = (View1Manager)context;
//        }
//    }

}