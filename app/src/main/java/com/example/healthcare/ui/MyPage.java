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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.ChallengeAdapter;
import com.example.healthcare.ChallengeData;
import com.example.healthcare.DayAdapter;
import com.example.healthcare.DayData;
import com.example.healthcare.DoneExerciseActivity;
import com.example.healthcare.EnteringChallengeAdapter;
import com.example.healthcare.EnteringChallengeData;
import com.example.healthcare.LoginActivity;
import com.example.healthcare.MainActivity;
import com.example.healthcare.MenuAdapter;
import com.example.healthcare.ModifyInformationActivity;
import com.example.healthcare.PopUpUnregisterActivity;
import com.example.healthcare.R;
import com.example.healthcare.RecyclerViewDecoration;
import com.example.healthcare.TempActivity;


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

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPage extends Fragment {
    Handler handler = new Handler();
    Handler mhandler = new Handler();
    EnteringChallengeAdapter enteringChallengeAdapter;
    RecyclerView recyclerView;
    String jwt;
    TextView name;
    TextView money;
    public MyPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_my_page, container, false);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_entering_challenge);
        LinearLayoutManager horizonalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizonalLayoutManager);
        enteringChallengeAdapter = new EnteringChallengeAdapter();


        name = rootView.findViewById(R.id.mypage_username);
        money = rootView.findViewById(R.id.amount);
        //adapter = new RecyclerAdapter();
        recyclerView.setAdapter(enteringChallengeAdapter);

        RecyclerViewDecoration spaceDecoration = new RecyclerViewDecoration(20);
        recyclerView.addItemDecoration(spaceDecoration);
        SharedPreferences sf = this.getActivity().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        jwt = sf.getString("jwt", "0");
//        getData();

        Button button = rootView.findViewById(R.id.btn_done_exercise);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DoneExerciseActivity.class);
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api-dev.pproject2020.xyz/challenge-ongoing");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                request1("http://api-dev.pproject2020.xyz/mypage-user");
            }
        }).start();

        Button btn_unregister = rootView.findViewById(R.id.btn_unregister);
        btn_unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PopUpUnregisterActivity.class);
                intent.putExtra("data", "Popup");
                startActivityForResult(intent, 1);
            }
        });

        Button btn_logout = rootView.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                SharedPreferences preferences = getContext().getSharedPreferences("jwt",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(intent);
            }
        });
        return rootView;
    }

//    public void mOnPupupClick(View v){
//        Intent intent = new Intent(this.getContext(), PopUpUnregisterActivity.class);
//        intent.putExtra("data", "Popup");
//        startActivityForResult(intent, 1);
//
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode==1){
//            if(resultCode==RESULT_OK){
//                //데이터 받기
//                String result = data.getStringExtra("result");
//                txtResult.setText(result);
//            }
//        }
//    }

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
                    Log.d("str", str);
//                    enteringChallengeAdapter = new EnteringChallengeAdapter();
//                    recyclerView.setAdapter(enteringChallengeAdapter);
                    JSONObject order = new JSONObject(str);
                    JSONArray index = order.getJSONArray("result");

                    for (int i = 0; i < index.length(); i++) {
                        JSONObject tt = index.getJSONObject(i);
                        EnteringChallengeData data = new EnteringChallengeData();
                        data.setId(tt.getInt("challenge_id"));
                        data.setTitle(tt.getString("goal"));
                        data.setImage(tt.getString("image_url"));
                        data.setIs_success(tt.getInt("is_success"));
                        data.setKinds(tt.getString("kinds"));
                        enteringChallengeAdapter.addItem(data);
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
        doJSONParser2(output.toString());
    }

    private void doJSONParser2(final String str) {
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("str", str);
//                    enteringChallengeAdapter = new EnteringChallengeAdapter();
                    JSONObject order = new JSONObject(str);
                    JSONObject index = order.getJSONObject("result");
                    name.setText(index.getString("nick_name") + " 님");
                    money.setText(index.getString("money") + " 포인트");
                    recyclerView.setAdapter(enteringChallengeAdapter);
                } catch (JSONException e) {
                    Log.d("LOG", String.valueOf(e));
                }
            }
        });
    }


//    private void getData(){
//        List<String> listTitle = Arrays.asList("근력향상 챌린지", "한마음 운동 챌린지", "너와 나 챌린지", "우리 모두 챌린지", "챌린챌린챌린지", "챌린지2");
//        List<Integer> listImage = Arrays.asList(R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img);
//
//        for(int i = 0; i < 6; i++){
//            EnteringChallengeData data = new EnteringChallengeData();
//            data.setTitle(listTitle.get(i));
//            data.setImage(listImage.get(i));
//            enteringChallengeAdapter.addItem(data);
//        }
//    }
}