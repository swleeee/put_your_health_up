package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    Handler handler = new Handler();
    EditText id;
    EditText password;
    EditText nickName;
    EditText age;
    EditText height;
    EditText weight;

    public static final int REQUEST_CODE_MENU = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageButton imagebutton = findViewById(R.id.btn_back);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sf = getSharedPreferences("jwt", MODE_PRIVATE);
        final String str = sf.getString("jwt", "0");
        if (str.length() > 10){
            Toast.makeText(getApplicationContext(), "자동로그인 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE_MENU);
        }
        id = findViewById(R.id.et_id);
        password = findViewById(R.id.et_pass);
        nickName = findViewById(R.id.et_nickname);
        age = findViewById(R.id.et_age);
        height = findViewById(R.id.et_stature);
        weight = findViewById(R.id.et_weight);

        Button button = findViewById(R.id.btn_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request("http://api-dev.pproject2020.xyz/user");
                    }
                }).start();

//                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
            }
        });

    }

    public void request(String urlStr) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr); //in the real code, there is an ip and a port
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
//            conn.setRequestProperty("x-access-token",jwt);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", id.getText());
            jsonParam.put("pw", password.getText());
            jsonParam.put("nickName", nickName.getText());
            jsonParam.put("age", age.getText());
            jsonParam.put("height", height.getText());
            jsonParam.put("weight", weight.getText());
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

            conn.disconnect();
        } catch (Exception ex) {
            System.out.println("예외 발생함! : " + ex.toString());
        }
        doJSONParser1(output.toString());
    }

    void doJSONParser1(final String str){
        handler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject order = new JSONObject(str);
                    int code = order.getInt("code");
                    String message = order.getString("message");
//                    boolean isSuccess = order.getBoolean("isSuccess");
                    Log.d("test", code + " " + message + " ");
                    if(code == 100){
                        JSONObject tt = order.getJSONObject("result");
                        Log.d("jwt", tt.getString("jwt"));
                        String jwt = tt.getString("jwt");
                        SharedPreferences sf = getSharedPreferences("jwt", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();
                        editor.putString("jwt", jwt);
                        editor.apply();
                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e){ Log.d("LOG", String.valueOf(e));}
            }
        });
    }
}
