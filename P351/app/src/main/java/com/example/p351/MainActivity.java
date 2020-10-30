package com.example.p351;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Intent intent = new Intent();

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = new Fragment1(this);
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout, fragment1).commit();

        actionBar = getSupportActionBar();
//        actionBar.hide();
        actionBar.setTitle("day03WS");
        actionBar.setIcon(R.drawable.icon);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
                ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.show();


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tab1) {
                    fragmentManager.beginTransaction().replace(
                            R.id.framelayout, fragment1).commit();
                    Toast.makeText(MainActivity.this, "tab1", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.tab2) {
                    fragmentManager.beginTransaction().replace(
                            R.id.framelayout, fragment2).commit();
                    Toast.makeText(MainActivity.this, "tab2", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.tab3) {
                    fragmentManager.beginTransaction().replace(
                            R.id.framelayout, fragment3).commit();
                    Toast.makeText(MainActivity.this, "tab3", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.tab4) {
//                    intent(getApplicationContext(), );
                }
                return false;
            }
        });
    }
}

