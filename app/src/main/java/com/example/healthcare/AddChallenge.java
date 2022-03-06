package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthcare.ui.Challenge;

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

public class AddChallenge extends AppCompatActivity {

    Handler handler = new Handler();
    private static final String TAG = "AddChallenge";
    String [] name;
    int spinner_count = 0;
    int [] exercise_id;
    int [] exercise_count;
    RadioButton rb_menu, rb_exercise;
    RadioGroup radioGroup;
    EditText et_input_calory;
    EditText et_challenge_goal;
    EditText et_recruit_startday;
    EditText et_recruit_due;
    EditText et_challenge_period;
    EditText et_challenge_money;

    Button button;
    String jwt;
    String whatGoal = "0";
//    ImageButton btn_add_challenge_exercise_todo;

    private Spinner sp_category;
    private ImageButton btn_add_todo, btn_challenge_exercise_delete;

    String exercise_title = "근력향상 챌린지";

    LinearLayout linearLayout;

    ChallengeExerciseTodoAdapter challengeExerciseTodoAdapter;

    RecyclerView recyclerView;

    String count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);


        SharedPreferences sf = this.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        jwt = sf.getString("jwt", "0");
        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api-dev.pproject2020.xyz/exercises");
            }
        }).start();

        button = findViewById(R.id.button);
        et_input_calory = (EditText)findViewById(R.id.et_input_calory);
        et_challenge_goal = findViewById(R.id.et_challenge_goal);
        et_challenge_money = findViewById(R.id.et_challenge_money);
        et_recruit_startday = findViewById(R.id.et_recruit_startday);
        et_recruit_due = findViewById(R.id.et_recruit_due);
        et_challenge_period = findViewById(R.id.et_challenge_period);
        rb_menu = (RadioButton)findViewById(R.id.rd_challenge_menu);
        rb_exercise = (RadioButton)findViewById(R.id.rd_challenge_exercise);
        radioGroup = (RadioGroup)findViewById(R.id.rd_challenge_category);
        sp_category = (Spinner) findViewById(R.id.sp_challenge_exercise_category);
        linearLayout = (LinearLayout)findViewById(R.id.ll_exercise_category);
        btn_add_todo = (ImageButton)findViewById(R.id.btn_add_challenge_exercise_todo);
        btn_challenge_exercise_delete = (ImageButton)findViewById(R.id.btn_challenge_exercise_delete);

        sp_category.setVisibility(View.INVISIBLE);
        btn_add_todo.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        et_input_calory.setVisibility(View.VISIBLE);

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId == 0){
//                    et_input_calory.setVisibility(View.VISIBLE);
//                }
//                else if(checkedId == 1){
//                    et_input_calory.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request1("http://api-dev.pproject2020.xyz/challenge");
                    }
                }).start();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv_challenge_exercise_todo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        challengeExerciseTodoAdapter = new ChallengeExerciseTodoAdapter(this);

        //adapter = new RecyclerAdapter();
        recyclerView.setAdapter(challengeExerciseTodoAdapter);

//        List<String> listTitle = Arrays.asList("근력향상 챌린지", "한마음 운동 챌린지", "너와 나 챌린지", "우리 모두 챌린지", "챌린챌린챌린지", "챌린지2");
//        List<String> listCount = Arrays.asList("0", "1", "2", "3", "4", "5");
        //ChallengeExerciseTodoData temp = new ChallengeExerciseTodoData();
//        data.setCount("");
//        data.setTitle(exercise_title);
//        challengeExerciseTodoAdapter.addItem(data);

//        for(int i = 0; i < 6; i++){
//            ChallengeExerciseTodoData temp = new ChallengeExerciseTodoData();
//            temp.setTitle(listTitle.get(i));
//            temp.setCount(listCount.get(i));
//            challengeExerciseTodoAdapter.addItem(temp);
//        }

        rb_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_category.setVisibility(View.INVISIBLE);
                btn_add_todo.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                et_input_calory.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                whatGoal = "식단";
                Log.d("menu", "식단");
            }
        });
        rb_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input_calory.setVisibility(View.INVISIBLE);
                sp_category.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                btn_add_todo.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                whatGoal = "운동";
                Log.d("exercise", "운동");
//                recyclerView = (RecyclerView) findViewById(R.id.rv_challenge_exercise_todo);
//
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
//                recyclerView.setLayoutManager(linearLayoutManager);
//
//                challengeExerciseTodoAdapter = new ChallengeExerciseTodoAdapter();
//
//                //adapter = new RecyclerAdapter();
//                recyclerView.setAdapter(challengeExerciseTodoAdapter);

                //exercise_item.
                btn_add_todo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChallengeExerciseTodoData data = new ChallengeExerciseTodoData();
                        data.setCount(count);
                        data.setTitle(exercise_title);
                        challengeExerciseTodoAdapter.addItem(data);
                        exercise_id[spinner_count] = Integer.parseInt(data.getTitle().substring(0, 1));
                        Log.d("count", Arrays.toString(exercise_id));
                        spinner_count += 1;

                        //Toast.makeText(AddChallenge.this, count, Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", data.toString());
//                        Log.d("TAG", data.getCount());
//                        Log.d("TAG", data.getTitle());
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
//                if(challengeExerciseTodoAdapter.getItemCount() != 0) {
//                    btn_challenge_exercise_delete.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            challengeExerciseTodoAdapter.removeItem(challengeExerciseTodoAdapter.getPosition());
//                        }
//                    });
//                }

            }
        });

        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                count = name[position];
                exercise_title = name[position];
//                btn_add_todo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        data.setCount("");
//                        data.setTitle(count);
//                       challengeExerciseTodoAdapter.addItem(data);
//                        //Toast.makeText(AddChallenge.this, count, Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", data.toString());
//                        Log.d("TAG", data.getCount());
//                        Log.d("TAG", data.getTitle());
//                    }
//                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                count = "";
            }
        });
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
        doJSONParser(output.toString());
    }

    private void doJSONParser(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("str", str);
                    JSONObject order = new JSONObject(str);
                    JSONArray index = order.getJSONArray("result");
                    name = new String[index.length()];
                    for (int i = 0; i < index.length(); i++) {
                        JSONObject tt = index.getJSONObject(i);
                        name[i] = tt.getInt("id") + " " + tt.getString("name");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, name);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_category.setAdapter(adapter);
                    exercise_id = new int[name.length];
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
            jsonParam.put("goal", et_challenge_goal.getText());
            jsonParam.put("amount", et_challenge_money.getText());
            jsonParam.put("recruitment_start_date", et_recruit_startday.getText());
            jsonParam.put("recruitment_end_date", et_recruit_due.getText());
            jsonParam.put("period", et_challenge_period.getText());
            if(whatGoal.equals("식단")){
                jsonParam.put("cal", et_input_calory.getText());
                jsonParam.put("kinds", "D");
                jsonParam.put("image_url", "https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/3J3RIHSVDLSTTJCFNCZIEQ3XSU.jpg");
            }
            else if(whatGoal.equals("운동")){
                jsonParam.put("image_url", "https://t1.daumcdn.net/cfile/blog/99B8F64E5C6E4D6610");
                jsonParam.put("exercise_id", Arrays.toString(exercise_id).substring(1, Arrays.toString(exercise_id).length() - 1));
                jsonParam.put("count", Arrays.toString(challengeExerciseTodoAdapter.getEx_count()).substring(1, Arrays.toString(challengeExerciseTodoAdapter.getEx_count()).length() - 1));
                jsonParam.put("kinds", "E");
//                jsonParam.put("exercise_id", exercise_id);
//                jsonParam.put("count", challengeExerciseTodoAdapter.getEx_count());
                Log.d("hmm", Arrays.toString(exercise_id) + " " + Arrays.toString(challengeExerciseTodoAdapter.getEx_count()));
                Log.d("hmm1", exercise_id + " " + challengeExerciseTodoAdapter.getEx_count());
            }
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
        doJSONParser1(output.toString());
    }
    void doJSONParser1(final String str) {
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