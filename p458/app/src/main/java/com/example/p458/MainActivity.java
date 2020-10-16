package com.example.p458;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = findViewById(R.id.webView);
        webview.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    public void ckbt(View v){
        if(v.getId() == R.id.button){
            webview.loadUrl("https://www.naver.com");
        }else if(v.getId() == R.id.button2){
            webview.loadUrl("https://www.daum.net");
        }else if(v.getId() == R.id.button3){
            webview.loadUrl("http://192.168.0.9/android");
        }
    }
}