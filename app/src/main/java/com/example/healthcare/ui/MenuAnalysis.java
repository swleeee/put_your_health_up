package com.example.healthcare.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healthcare.CalendarActivity;
import com.example.healthcare.DayAdapter;
import com.example.healthcare.ExpandableListAdapter;
import com.example.healthcare.MenuAdapter;
import com.example.healthcare.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuAnalysis extends Fragment {
    private static final String TAG = "temp";
    //    private ArrayList<ArrayList<>>
    Handler handler = new Handler();
    ImageButton imagebutton;
    int[] foodIdx;
    String[] food_list;
    String[] cal;
    ExpandableListAdapter expandableListAdapter;
    DayAdapter dataAdapter;
    MenuAdapter menuDataAdapter;
    Context context = getContext();
    RecyclerView recyclerview;
    String currentDate;
    TextView tv, text_cal, text_dan, text_vi, text_ji, text_tan;
    List<ExpandableListAdapter.Item> data = new ArrayList<>();
    public MenuAnalysis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_analysis, container, false);
        text_cal=rootView.findViewById(R.id.text_cal);
        text_dan=rootView.findViewById(R.id.text_dan);
        text_vi=rootView.findViewById(R.id.text_vi);
        text_ji=rootView.findViewById(R.id.text_ji);
        text_tan=rootView.findViewById(R.id.text_tan);

        //Log.i("temp", "temp");
        imagebutton = (ImageButton)rootView.findViewById(R.id.img_calender);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("calender", "calender");
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sf = getActivity().getSharedPreferences("jwt", MODE_PRIVATE);
        //data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "아침", ""));
        //data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "점심", ""));
        //data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "저녁", ""));
        final String shared = sf.getString("jwt", "0");

        Log.i("쉐어드프리퍼런스", shared + "  " );
        recyclerview = rootView.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        tv = rootView.findViewById(R.id.txt_today);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cal.getTime());
        tv.setText(date);


        Intent intent = getActivity().getIntent();

        try {
            currentDate = intent.getStringExtra("day");
        } catch (Exception e) {
            Log.e(TAG, "get error");
        }
        if (currentDate != null) {
            tv = rootView.findViewById(R.id.txt_today);
            tv.setText(currentDate);
        }
        String copy = tv.getText().toString();
        Log.i("DAY", copy);

        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api.pproject2020.xyz/diet", shared, copy);
            }
        }).start();

//        recyclerview.setAdapter(new com.example.healthcare.ExpandableListAdapter(data));




        return rootView;
    }

    private void request(String urlStr, String str, String tv) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr + "?date=" + tv);
            Log.i("URL!", "URL은 " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("x-access-token", str);

                conn.setDoInput(true);
                Log.i("GET!", "토큰은 " + str + " " + "날짜는 " + tv);
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
        Log.i("output", output.toString());
    }
    private void doJSONParser1(final String str) {
        Log.i("doJSONParser1", "doJSONParser1");
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("str123", str);
                    data = new ArrayList<>();
                    data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "아침", ""));
                    JSONObject order = new JSONObject(str);
                    int code = order.getInt("code");
                    String message = order.getString("message");
                    Log.i("btest", code + " " + message + " ");
                    JSONObject object = order.getJSONObject("result");
                    Log.i("btest2", String.valueOf(object));


                    String total = object.getString("totalNutrient");
                    JSONObject totalobject = new JSONObject(total);
                    Log.i("total", " " + totalobject);
                    String calory = totalobject.getString("cal");
                    if(totalobject.getString("cal").equals("null")){
                        text_cal.setText("0 kcal");
                        text_tan.setText("0 g");
                        text_ji.setText("0 g");
                        text_dan.setText("0 g");
                        text_vi.setText("0 g");
                    }
                    else{
                        text_cal.setText(calory + "kcal");
                        String carb = totalobject.getString("carb");
                        text_tan.setText(carb + "g");
                        String fat = totalobject.getString("fat");
                        text_ji.setText(fat + "g");
                        String protein = totalobject.getString("protein");
                        text_dan.setText(protein + "g");
                        String vitamin = totalobject.getString("vitamin");
                        text_vi.setText(vitamin + "g");
                    }

                    String food = object.getString("BreakfastResult");
                    JSONArray jsonArray = new JSONArray(food);
                    Log.i("bfood", food + "  " + jsonArray.length());
                    foodIdx = new int[jsonArray.length()];
                    food_list = new String[jsonArray.length()];
                    cal = new String[jsonArray.length()];
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("breakfast", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("breakfast", String.valueOf(jsonArray.length()));
                    editor.commit();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.i("for", "되나?");
                        JSONObject subJsonObject = jsonArray.getJSONObject(i);
                        Log.i("bsubJsonObject", String.valueOf(subJsonObject));
                        foodIdx[i] = subJsonObject.getInt("foodIdx");
                        food_list[i] = subJsonObject.getString("food_list");
                        cal[i] = subJsonObject.getString("cal");
                        Log.i("아침밥", foodIdx[i] + " ," + food_list[i] + ", " + cal[i]);
                        if(cal[i]==null){
                            cal[i]="0";
                        }
                        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, food_list[i], cal[i]));
//                        recyclerview.setAdapter(new com.example.healthcare.ExpandableListAdapter(data));
                    }
                    data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "점심", ""));
                    String lunchfood = object.getString("LunchResult");
                    JSONArray lunchjsonArray = new JSONArray(lunchfood);
                    Log.i("lunchfood", lunchfood + "  " + lunchjsonArray.length());
                    foodIdx = new int[lunchjsonArray.length()];
                    food_list = new String[lunchjsonArray.length()];
                    cal = new String[lunchjsonArray.length()];
                    SharedPreferences lunchsharedPreferences = getActivity().getSharedPreferences("lunch", MODE_PRIVATE);
                    SharedPreferences.Editor luncheditor = lunchsharedPreferences.edit();
                    editor.putString("lunch", String.valueOf(lunchjsonArray.length()));
                    editor.commit();
                    for (int i = 0; i < lunchjsonArray.length(); i++) {
                        Log.i("for", "되나?");
                        JSONObject subJsonObject = lunchjsonArray.getJSONObject(i);
                        Log.i("lunchsubJsonObject", String.valueOf(subJsonObject));
                        foodIdx[i] = subJsonObject.getInt("foodIdx");
                        food_list[i] = subJsonObject.getString("food_list");
                        cal[i] = subJsonObject.getString("cal");
                        Log.i("lunch밥", foodIdx[i] + " ," + food_list[i] + ", " + cal[i]);
                        if(cal[i]==null){
                            cal[i]="0";
                        }
                        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, food_list[i], cal[i]));
//                        recyclerview.setAdapter(new com.example.healthcare.ExpandableListAdapter(data));
                    }
                    data.add(new com.example.healthcare.ExpandableListAdapter.Item(com.example.healthcare.ExpandableListAdapter.HEADER, "저녁", ""));
                    String Dinnerfood = object.getString("DinnerResult");
                    JSONArray DinnerjsonArray = new JSONArray(Dinnerfood);
                    Log.i("bDinnerfood", Dinnerfood + "  " + DinnerjsonArray.length());
                    foodIdx = new int[DinnerjsonArray.length()];
                    food_list = new String[DinnerjsonArray.length()];
                    cal = new String[DinnerjsonArray.length()];
                    for (int i = 0; i < DinnerjsonArray.length(); i++) {
                        Log.i("for", "되나?");
                        JSONObject subJsonObject = DinnerjsonArray.getJSONObject(i);
                        Log.i("bsubJsonObject", String.valueOf(subJsonObject));
                        foodIdx[i] = subJsonObject.getInt("foodIdx");
                        food_list[i] = subJsonObject.getString("food_list");
                        cal[i] = subJsonObject.getString("cal");
                        Log.i("아침밥", foodIdx[i] + " ," + food_list[i] + ", " + cal[i]);
                        if(cal[i]==null){
                            cal[i]="0";
                        }
                        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, food_list[i], cal[i]));
//                        recyclerview.setAdapter(new com.example.healthcare.ExpandableListAdapter(data));
                    }
                    Log.i("data" , String.valueOf(data));
                    recyclerview.setAdapter(new com.example.healthcare.ExpandableListAdapter(data));
                } catch (JSONException e) {
                    Log.d("b안된다", String.valueOf(e));
                }
            }

        });
    }

    private void setAdapter(List<ExpandableListAdapter.Item> data) {
        recyclerview.setAdapter(new com.example.healthcare.ExpandableListAdapter(data));
    }




}