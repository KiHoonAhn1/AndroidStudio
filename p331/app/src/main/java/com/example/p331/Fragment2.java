package com.example.p331;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Fragment2 extends Fragment {
    Button button2;
    MainActivity m;

    public Fragment2(MainActivity m) {
        this.m = m;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
     ViewGroup viewGroup = null;
     viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_2,container,false);
     button2 = viewGroup.findViewById(R.id.button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(m, "bbb", Toast.LENGTH_SHORT).show();
            }
        });

    return viewGroup;
    }
    }