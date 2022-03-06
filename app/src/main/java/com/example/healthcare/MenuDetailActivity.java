package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuDetailActivity extends AppCompatActivity {
    private TextView tv_menu_detail_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
//        tv_menu_detail_name =  findViewById(R.id.tv_title);
//
//        String menu_name = getIntent().getStringExtra("menu");
//        tv_menu_detail_name.setText(menu_name);


    }
}
