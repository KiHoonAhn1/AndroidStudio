package com.example.wsfinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

public class Fragment1  extends Fragment {

    TextView textView;
    EditText editText;
    ImageView imageView;
    String str;
    WebView webView;
    Button button4;

    public Fragment1(){}
    private WebView mWebView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup)inflater.inflate(
                R.layout.fragment_1,container,false);
//        webView = viewGroup.findViewById(R.id.webView);  // webView 를 가져온다.
//        webView.setWebViewClient(new WebViewClient());  // 안드로이드에서 제공해주는 기본적인 브라우저의 기능
//        WebSettings webSettings = webView.getSettings();  // setting
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl("https://www.cgv.co.kr");
        button4 = viewGroup.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = PermissionChecker.checkSelfPermission(getActivity().SecondActivity.this, Manifest.permission.CALL_PHONE);
                if(check == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-9090-9898"));
                    startActivity(intent);
                }else{
                    Toast.makeText(ThirdActivity.this, "DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return viewGroup;
    }

}





