package com.example.healthcare.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.healthcare.ExerciseData;
import com.example.healthcare.ExerciseAdapter;
import com.example.healthcare.GuideActivity;
import com.example.healthcare.MainActivity;
import com.example.healthcare.R;
import com.example.healthcare.RecyclerViewDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PosturalCorrection extends Fragment {
    Handler handler = new Handler();

    int[] id;
    String[] title;
    String[] image;

    ExerciseAdapter exerciseAdapter;

    RecyclerView recyclerView;

    private CardView cv_exer1, cv_exer2, cv_exer3, cv_exer4, cv_exer5, cv_exer6;
    String name = "";

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }


    public PosturalCorrection() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        SharedPreferences sf = getActivity().getSharedPreferences("jwt", MODE_PRIVATE);
//        final String shared = sf.getString("jwt", "0");
//        Log.i("쉐어드프리퍼런스", shared);

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_postural_correction, container, false);

        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.rv_exercise);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api-dev.pproject2020.xyz/exercises");
            }
        }).start();


        exerciseAdapter = new ExerciseAdapter();

        //adapter = new RecyclerAdapter();
        recyclerView.setAdapter(exerciseAdapter);

        RecyclerViewDecoration spaceDecoration = new RecyclerViewDecoration(20);
        recyclerView.addItemDecoration(spaceDecoration);

     //   getData();



//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

//        cv_exer1 = (CardView) rootview.findViewById(R.id.cv_exer1);
//        cv_exer2 = (CardView) rootview.findViewById(R.id.cv_exer2);
//        cv_exer3 = (CardView) rootview.findViewById(R.id.cv_exer3);
//        cv_exer4 = (CardView) rootview.findViewById(R.id.cv_exer4);
//        cv_exer5 = (CardView) rootview.findViewById(R.id.cv_exer5);
//        cv_exer6 = (CardView) rootview.findViewById(R.id.cv_exer6);


        // 선택된 운동명 운동안내로 넘겨주며 화면전환
//        cv_exer1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = "exer1";
//                Intent intent = new Intent(getContext(), com.example.healthcare.GuideActivity.class);
//                intent.putExtra("name", name);
//                startActivity(intent);
//            }
//        });
//
//        cv_exer2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = "exer2";
//                Intent intent = new Intent(getContext(), com.example.healthcare.GuideActivity.class);
//                intent.putExtra("name", name);
//                startActivity(intent);
//            }
//        });
//
//        cv_exer3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = "exer3";
//                Intent intent = new Intent(getContext(), com.example.healthcare.GuideActivity.class);
//                intent.putExtra("name", name);
//                startActivity(intent);
//            }
//        });
//
//        cv_exer4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = "exer4";
//                Intent intent = new Intent(getContext(), com.example.healthcare.GuideActivity.class);
//                intent.putExtra("name", name);
//                startActivity(intent);
//            }
//        });
//
//        cv_exer5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = "exer5";
//                Intent intent = new Intent(getContext(), com.example.healthcare.GuideActivity.class);
//                intent.putExtra("name", name);
//                startActivity(intent);
//            }
//        });
//
//        cv_exer6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = "exer6";
//                Intent intent = new Intent(getContext(), com.example.healthcare.GuideActivity.class);
//                intent.putExtra("name", name);
//                startActivity(intent);
//            }
//        });
        return rootview;
    }

    public void request(String urlStr) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                int resCode = conn.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    output.append(line);
                }
                reader.close();
                conn.disconnect();
            }
        } catch (Exception ex) {
            System.out.println("예외 발생함! : " + ex.toString());
        }
        doJSONParser1(output.toString());
    }

    private void doJSONParser1(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("str123", str);
                    exerciseAdapter = new ExerciseAdapter();

                    //adapter = new RecyclerAdapter();
                    recyclerView.setAdapter(exerciseAdapter);
                    JSONObject order = new JSONObject(str);
                    JSONArray index = order.getJSONArray("result");
                    id = new int[index.length()];

                    title = new String[index.length()];

                    image = new String[index.length()];
                    List<ExerciseData> dataList = new ArrayList<>();
                    for (int i = 0; i < index.length(); i++) {
                        JSONObject tt = index.getJSONObject(i);
                        id[i] = tt.getInt("id");

                        title[i] = tt.getString("name");

                        image[i] = tt.getString("thumbnail");
                        Log.d("result", id[i]  + ", " + title[i] + ", " + image[i]);

//                        Log.d("title", id[i] + " " + member_id[i] + " " + title[i]);
//                        dataList.add(new ExerciseData(id[i], image[i], title[i], recruit_deadline[i], challenge_period[i], member_id[i]));
                        ExerciseData data = new ExerciseData();
                        data.setId(id[i]);
                        data.setTitle(title[i]);
                        data.setImage(image[i]);
                        exerciseAdapter.addItem(data);
                    }

                } catch (JSONException e) {
                    Log.d("LOG", String.valueOf(e));
                }
            }
        });
    }

    
//    private void getData(){
//        List<String> listTitle = Arrays.asList("스쿼트1", "스쿼트2", "스쿼트3", "스쿼트4", "스쿼트5", "스쿼트6");
//        List<Integer> listImage = Arrays.asList(R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img);
//
//        for(int i = 0; i < 6; i++){
//            ExerciseData data = new ExerciseData();
//            data.setTitle(listTitle.get(i));
//            data.setImage(listImage.get(i).toString());
//            exerciseAdpter.addItem(data);
//        }
//}


}

