package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TempActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<com.example.healthcare.ExpandableListAdapter.Item> data = new ArrayList<>();

        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "아침", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Apple", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Orange", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Banana", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "점심", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Audi", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Aston Martin", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "BMW", "365kcal"));
        data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Cadillac", "365kcal"));



        com.example.healthcare.ExpandableListAdapter.Item places = new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "저녁", "365kcal");
        places.invisibleChildren = new ArrayList<>();

        places.invisibleChildren.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Kerala", "365kcal"));
        places.invisibleChildren.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Tamil Nadu", "365kcal"));
        places.invisibleChildren.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Karnataka", "365kcal"));
        places.invisibleChildren.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.CHILD, "Maharashtra", "365kcal"));

        data.add(places);

        recyclerview.setAdapter(new com.example.healthcare.ExpandableListAdapter(data));

//        itemView.findViewById(R.id.btn_add_menu);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = getAdapterPosition();
//                if(pos != RecyclerView.NO_POSITION){
//                    Intent intent = new Intent(v.getContext(), GetImageActivity.class);
//                    Toast.makeText(v.getContext(), String.valueOf(pos), Toast.LENGTH_LONG).show();
////                        Intent intent = new Intent(v.getContext(), CameraActivity.class);
//                    v.getContext().startActivity(intent);
//
//                }
//
//            }
//        });




    }
}
