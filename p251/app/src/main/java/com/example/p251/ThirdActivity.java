package com.example.p251;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int result = bundle.getInt("data2", 0);
        String str_result = bundle.getString("str2", "");
//        bundle를 사용하면 여러 개의 데이터를 가져올 수 있어 더 편하다
//        intent.getIntExtra("data", 0);
        Toast.makeText(this, str_result + ":" + result, Toast.LENGTH_SHORT).show();
    }

    public void ckbt(View v) {
        Intent intent = null;
        if (v.getId() == R.id.button4) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-9879-2039"));
            startActivity(intent);
        } else if (v.getId() == R.id.button5) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
            // 주소록 가져오기
            startActivity(intent);
        } else if (v.getId() == R.id.button6) {
            int check = PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if(check == PackageManager.PERMISSION_GRANTED) {
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-9878-8989"));
                startActivity(intent);
            } else{
                Toast.makeText(this, "Not Allowed", Toast.LENGTH_SHORT).show();
                // java.lang.SecurityException: Permission Denial => 사용자의 허락을 받아야 함

            }
        }
    }
}