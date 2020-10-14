package com.example.p331;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment1 extends Fragment {
    TextView textView;
    EditText editText;
    Button button1;
    MainActivity m;
    public Fragment1(MainActivity m) {
        this.m = m;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_1,container,false);
        textView = viewGroup.findViewById(R.id.textView);
        editText = viewGroup.findViewById(R.id.editText);
        button1 = viewGroup.findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String str = editText.getText().toString();
                textView.setText(str);
                Toast.makeText(m, "aaa", Toast.LENGTH_SHORT).show();
            }
        });
        return viewGroup;

    }

    public void setTx(String str){ textView.setText(str);
    }
    }