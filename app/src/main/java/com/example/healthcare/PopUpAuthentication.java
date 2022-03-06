package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.healthcare.ui.MyPage;

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

public class PopUpAuthentication extends AppCompatActivity  implements CircleProgressBar.ProgressFormatter {
    Handler handler = new Handler();
    private static final String DEFAULT_PATTERN = "%d%%";
    TextView title;
    TextView progress;
    CircleProgressBar circleProgressBar;
    String jwt;
    String kinds;
    String id;
    String period;
    String successDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_authentication);
        circleProgressBar=findViewById(R.id.cpb_circlebar);
        progress = findViewById(R.id.tv_progress_sate);
        title = findViewById(R.id.tv_authentication_title);
        Intent intent = getIntent();
        kinds = intent.getStringExtra("kinds");
        id = intent.getStringExtra("id");
        SharedPreferences sf = this.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        jwt = sf.getString("jwt", "0");
        Log.d("test22", Objects.requireNonNull(intent.getStringExtra("id")) + kinds);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(kinds.equals("D")){
                    request("http://api-dev.pproject2020.xyz/challenge-ongoing/" + intent.getStringExtra("id"));
                }
                else{
                    request("http://api-dev.pproject2020.xyz/exercise-authentication/" + intent.getStringExtra("id"));
                }
//                if(kinds.equals("D")){
//                    Log.d("test", period + "    " + successDate);
//                    if(period.equals(successDate)){
//                        request1("http://api-dev.pproject2020.xyz/challenge-success");
//                    }
//                }
            }
        }).start();
    }// onCreate()..

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }

    public void onClickDialog(View v){
        switch(v.getId()){
            case R.id.btn_close:
                this.finish();
                break;
        }
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
                    JSONObject index = order.getJSONObject("result");
                    title.setText(index.getString("goal"));
                    progress.setText(index.getString("period") + "일 중" + index.getString("successDate") + "일 완료했습니다.");
                    period = index.getString("period");
                    successDate = index.getString("successDate");
                    circleProgressBar.setProgress(index.getInt("successPercent"));
                    circleProgressBar.setProgressTextSize(32);
                    if(kinds.equals("D")){
                        Log.d("test", period + "    " + successDate);
                        Log.d("hey", "여기 성공함");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(period.equals(successDate)){
                                    Log.d("hey", "여기 성공함2");
                                    request1("http://api-dev.pproject2020.xyz/challenge-success");
                                    Log.d("hey", "여기 성공함3");
                                }
                            }
                        }).start();
                    }
                    else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(period.equals(successDate)){
                                    Log.d("hey", "여기 성공함6");
                                    request1("http://api-dev.pproject2020.xyz/challenge-exercise-success");
                                    Log.d("hey", "여기 성공함7");
                                }
                            }
                        }).start();
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
            Log.d("hey", "여기 성공함4");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("x-access-token", jwt);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("chIdx", id);

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