package com.example.healthcare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MenuAdd extends AppCompatActivity {
    private static final String TAG = "RR";
    Handler handler = new Handler();
    RecyclerView recyclerView;
    ImageButton btn_add;
    ArrayList<MenuAddData> label = new ArrayList<>();
    ArrayList<String> qq = new ArrayList<>();
    int position;
    String Time; int p;
    int count = 0; String gram;
    ArrayList<String> list_Gram = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        for(int q = 0; q<10;q++){
            list_Gram.add(String.valueOf(100));
        }
        SharedPreferences sf = getSharedPreferences("jwt", MODE_PRIVATE);
        final String str = sf.getString("jwt", "0");
        SharedPreferences breakfast = getSharedPreferences("breakfast", MODE_PRIVATE);
        String breakfast_num = breakfast.getString("breakfast", "0");
        int break_Number = Integer.parseInt(breakfast_num);
        SharedPreferences lunch = getSharedPreferences("lunch", MODE_PRIVATE);
        String lunch_num = breakfast.getString("lunch", "0");
        int lunch_Number = Integer.parseInt(lunch_num);
//        SharedPreferences sf2 = getSharedPreferences("position", MODE_PRIVATE);
//        final String shared2 = sf2.getString("position", "0");
        Log.i("넘버쉐어드프리퍼런스", lunch_num + "  " + breakfast_num + "  ");

        Log.i("쉐어드프리퍼런스", str);
        Intent intent = getIntent();
        qq = intent.getStringArrayListExtra("label");
        String[] Food = {"바나나", "사과", "우유", "배", "귤", "밥", "물", "라면", "고구마", "수박", "파인애플", "샐러드", "계란"};
        for (int i = 0; i < qq.size(); i++) {
            String foodname = qq.get(i);
            for (int j = 0; j < Food.length; j++) {
                Log.i("foodname123", foodname + " " + Food[j]);
                if (foodname.equals(Food[j])) {
                    MenuAddData item = new MenuAddData();
                    item.setFoodname(foodname);
                    label.add(item);
                    Log.i("확인", "item " + foodname);
                } else {
                    Log.i("for문해도", "안나옴");
                }
            }
        }
        position = intent.getIntExtra("position", 0);
        Log.i("position", position + " " + break_Number + "  " + lunch_Number);
        if (position == 0) {
            Time = "B";
        } else if (position == break_Number+1) {
            Time = "L";
        } else if (position == 2+break_Number+lunch_Number) {
            Time = "D";
        }
        Log.i(TAG, "onCreate: " + position + Time + " " + qq + " " + label);
        recyclerView = findViewById(R.id.recyclerView);
        btn_add = findViewById(R.id.btn_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int k = 0; k < label.size(); k++) {
            Log.i("LABEL = ", " " + label.get(k));
        }
        MenuAddAdapter addAdapter = new MenuAddAdapter(label);
        recyclerView.setAdapter(addAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request("http://api.pproject2020.xyz/diet", Time, str);
                    }
                }).start();
            }
        });
    }

    private void request(String urlStr, String Time, String str) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr); //in the real code, there is an ip and a port
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("x-access-token", str);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            JSONArray jsonArray = new JSONArray();


            for (int i = 0; i < label.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("foodName", label.get(i).getFoodname());
                jsonObject.put("gram", list_Gram.get(i));
                jsonObject.put("type", Time);
                Log.i("jsonArray에 넣는 값", "은" + list_Gram.get(i) + " " + label.get(i).getFoodname());
                jsonArray.put(jsonObject);
            }
            for(int a=0; a<count;a++){
                Log.i("gram", "은" + list_Gram.get(a));
            }
            Log.i("배열", String.valueOf(jsonArray));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            pw.write(jsonArray.toString());
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

            conn.disconnect();
        } catch (Exception ex) {
            System.out.println("예외 발생함! : " + ex.toString());
        }
        doJSONParser1(output.toString());
    }

    void doJSONParser1(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject order = new JSONObject(str);
                    int code = order.getInt("code");
                    String message = order.getString("message");
                    Log.i("test", code + " " + message + " ");
                    if (code == 200) {
                        Log.i("식당추가", "됨");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("LOG", String.valueOf(e) + "안된다");
                }
            }
        });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            count = intent.getIntExtra("i", 0);
            gram= intent.getStringExtra("position");
            add(gram, count);
        }
    };

    private void add(String gram, int count) {
        list_Gram.set(count, gram);
        Log.i("받아온 그램과 어레이리스트", count +" " + gram + " " +list_Gram.get(0));
    }
}
