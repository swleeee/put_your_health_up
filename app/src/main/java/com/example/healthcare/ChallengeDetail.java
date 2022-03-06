package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ChallengeDetail extends AppCompatActivity {
    Handler handler = new Handler();
    TextView goal;
    TextView start;
    TextView end;
    TextView period;
    TextView amount;
    TextView organizer;
    TextView description;
    ImageView url;
    Button button;
    String jwt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);
        Intent intent = getIntent();
        Log.d("test22", Objects.requireNonNull(intent.getStringExtra("id")));
        goal = findViewById(R.id.textView11);
        start = findViewById(R.id.textView17);
        end = findViewById(R.id.textView18);
        period = findViewById(R.id.textView19);
        amount = findViewById(R.id.textView20);
        organizer = findViewById(R.id.textView21);
        url = findViewById(R.id.imageView3);
        button = findViewById(R.id.button3);
        description = findViewById(R.id.textView23);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request1("http://api-dev.pproject2020.xyz/challenge-participation?challenge_id="+intent.getStringExtra("id"));
                    }
                }).start();
            }
        });
        SharedPreferences sf = this.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        jwt = sf.getString("jwt", "0");
        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api-dev.pproject2020.xyz/challenge/" + intent.getStringExtra("id"));
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
                conn.setRequestProperty("x-access-token",jwt);
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
                    Log.d("str", str);
                    JSONObject order = new JSONObject(str);
                    JSONObject result = order.getJSONObject("result");
                    result.getInt("id");
                    goal.setText(result.getString("goal"));
                    start.setText(result.getString("start_date"));
                    end.setText(result.getString("end_date"));
                    period.setText(result.getString("period"));
                    amount.setText(result.getString("amount"));
                    organizer.setText(result.getString("nick_name"));
                    Glide.with(getApplicationContext()).load(result.getString("image_url")).into(url);
                    if(result.getInt("is_participation") == 1){
                        button.setEnabled(false);
                    }
                    else{
                        button.setEnabled(true);
                    }
                    JSONArray goal = result.getJSONArray("goal_detail");
                    String a = "";
                    for (int i = 0; i < goal.length(); i++) {
                        JSONObject tt = goal.getJSONObject(i);
                        a += tt.getString("goal") + "\n";
                        description.setText(a);


                    }
                } catch (JSONException e) {
                    Log.d("LOG", String.valueOf(e));
                }
            }
        });
    }

    public void request1(String urlStr) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr); //in the real code, there is an ip and a port
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("x-access-token", jwt);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            JSONObject jsonParam = new JSONObject();

            Log.d("json", String.valueOf(jsonParam));

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            pw.write(jsonParam.toString());
            pw.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = null;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                output.append(line);
            }
            pw.close();
            Log.d("output", output.toString());
            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            conn.disconnect();
        } catch (Exception ex) {
            System.out.println("예외 발생함! : " + ex.toString());
        }
        doJSONParser2(output.toString());
    }
    void doJSONParser2(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject order = new JSONObject(str);
                    String code = order.getString("code");
                    String message = order.getString("message");

                    if (code.equals("200")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("LOG", e.toString());
                }
            }
        });
    }
}