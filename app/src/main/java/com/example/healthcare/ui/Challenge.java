package com.example.healthcare.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthcare.AddChallenge;
import com.example.healthcare.ChallengeAdapter;
import com.example.healthcare.ChallengeData;
import com.example.healthcare.EnteringChallengeAdapter;
import com.example.healthcare.EnteringChallengeData;
import com.example.healthcare.MainActivity;
import com.example.healthcare.R;
import com.example.healthcare.RecyclerViewDecoration;

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

public class Challenge extends Fragment {
    Handler handler = new Handler();
    int[] id;
    int[] member_id;
    String[] title;
    String[] image;
    String[] recruit_deadline;
    String[] challenge_period;
    ChallengeAdapter challengeAdapter;
    RecyclerView recyclerView;

    public Challenge() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenge, container, false);

        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_challenge);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api-dev.pproject2020.xyz/challenge");
            }
        }).start();

        RecyclerViewDecoration spaceDecoration = new RecyclerViewDecoration(10);
        recyclerView.addItemDecoration(spaceDecoration);

        Button btn_add_challenge = rootView.findViewById(R.id.btn_add_challenge);
        btn_add_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddChallenge.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api-dev.pproject2020.xyz/challenge");
            }
        }).start();
        Log.d("onResume", "hi");
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
                    challengeAdapter = new ChallengeAdapter();
                    recyclerView.setAdapter(challengeAdapter);
                    JSONObject order = new JSONObject(str);
                    JSONArray index = order.getJSONArray("result");
                    id = new int[index.length()];
                    member_id = new int[index.length()];
                    title = new String[index.length()];
                    recruit_deadline = new String[index.length()];
                    challenge_period = new String[index.length()];
                    image = new String[index.length()];
                    List<ChallengeData> dataList = new ArrayList<>();
                    for (int i = 0; i < index.length(); i++) {
                        JSONObject tt = index.getJSONObject(i);
                        id[i] = tt.getInt("id");
                        member_id[i] = tt.getInt("member_id");
                        title[i] = tt.getString("goal");
                        recruit_deadline[i] = tt.getString("end_date");
                        challenge_period[i] = tt.getString("period");
                        image[i] = tt.getString("image_url");
                        ChallengeData data = new ChallengeData();
                        data.setId(id[i]);
                        data.setMember_id(member_id[i]);
                        data.setTitle(title[i]);
                        data.setRecruit_deadline(recruit_deadline[i]);
                        data.setChallenge_period(challenge_period[i]);
                        data.setImage(image[i]);
                        challengeAdapter.addItem(data);
                    }
                } catch (JSONException e) {
                    Log.d("LOG", String.valueOf(e));
                }
            }
        });
    }
}