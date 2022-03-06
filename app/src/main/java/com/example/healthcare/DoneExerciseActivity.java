package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoneExerciseActivity extends AppCompatActivity {

    DoneExerciseAdapter doneExerciseAdapter;
    RecyclerView recyclerView;
    Handler handler = new Handler();
    TextView name;
    TextView count;
    TextView date;
    String jwt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_exercise);

        Log.i("ee", "ee");
        recyclerView = (RecyclerView) findViewById(R.id.rv_done_exercise);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences sf = this.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        jwt = sf.getString("jwt", "0");
        name = findViewById(R.id.tv_done_exercise_name);
        count = findViewById(R.id.tv_done_exercise_count);
        date = findViewById(R.id.tv_done_exercise_date);

        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api-dev.pproject2020.xyz/user-exercise-list");
            }
        }).start();
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
                conn.setRequestProperty("x-access-token", jwt);
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
                    doneExerciseAdapter = new DoneExerciseAdapter();
                    recyclerView.setAdapter(doneExerciseAdapter);
                    JSONObject order = new JSONObject(str);
                    JSONArray index = order.getJSONArray("result");
                    List<DoneExerciseData> dataList = new ArrayList<>();
                    for (int i = 0; i < index.length(); i++) {
                        JSONObject tt = index.getJSONObject(i);
                        DoneExerciseData data = new DoneExerciseData();
                        data.setTitle(tt.getString("name"));
                        data.setCount("횟수: " + tt.getString("count"));
                        data.setDate(tt.getString("created_at"));
                        doneExerciseAdapter.addItem(data);
                    }
                } catch (JSONException e) {
                    Log.d("LOG", String.valueOf(e));
                }
            }
        });
    }
}