package com.example.healthcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubeView;

    //YouTubePlayer.OnInitializedListener listener;

    Handler handler = new Handler();

    //int[] id;
    String title;
    String thumbnail;
    String video_url;
    String description;

    ExerciseAdapter exerciseAdapter;


    private VideoView vv_guide;
    private Spinner sp_selcnt;
    private Button btn_start;
    String name = "";
    String id;
    Uri uri;

    TextView tv_guide;

    ExerciseData exerciseData;

    int selCnt;
    String[] items = {"개수", "10", "20", "30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        youTubeView = (YouTubePlayerView)findViewById(R.id.vv_guide);


        exerciseData = new ExerciseData();

        tv_guide = findViewById(R.id.tv_guide);
        //Toast.makeText(getApplicationContext(), exerciseData.getId(), Toast.LENGTH_SHORT).show();


        // 메인에서 선택된 운동명 받아오고 타이틀바에 표시
        Intent intent = getIntent();
        //name = intent.getStringExtra("name");

        id = intent.getStringExtra("id");

        //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(id);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        // 동영상 연결
        //vv_guide = (VideoView) findViewById(R.id.vv_guide);
//
//        uri = Uri.parse("");
//        vv_guide.setVideoURI(uri);
//
//        vv_guide.start();
//
//        final MediaController mediaController = new MediaController(this);
//        vv_guide.setMediaController(mediaController);
//        vv_guide.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(vv_guide.isPlaying()){
//                    vv_guide.pause();
//                    return false;
//                }
//                else{
//                    vv_guide.start();
//                    return false;
//                }
//            }
//        });

        // 운동 개수 받아오기
        sp_selcnt = (Spinner) findViewById(R.id.sp_selcnt);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_selcnt.setAdapter(adapter);

        sp_selcnt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    selCnt = 0;
                }
                else{
                    selCnt = Integer.parseInt(items[position]);
                    //Toast.makeText(getApplicationContext(), selCnt, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selCnt = 0;
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                request("http://api-dev.pproject2020.xyz/exercise/" + id);
            }
        }).start();


        // 시작버튼 클릭하면 동작인식으로 화면 전환
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(GuideActivity.this, SensorActivity.class);
                if(selCnt == 0){
                    Toast.makeText(getApplicationContext(), "개수를 선택하지 않았습니다. 다시 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(GuideActivity.this, PoseDetectionTempActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("count", selCnt);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
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
        doJSONParser1(output.toString());
    }

    private void doJSONParser1(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("str123", str);
//                    exerciseAdapter = new ExerciseAdapter();
//
//                    //adapter = new RecyclerAdapter();
//                    recyclerView.setAdapter(exerciseAdapter);
                    JSONObject order = new JSONObject(str);
                    //       JSONArray index = order.getJSONArray("result");
//                    id = new int[index.length()];
//
//                    title = new String[index.length()];
//
//                    image = new String[index.length()];
                    //List<ExerciseData> dataList = new ArrayList<>();
                    //for (int i = 0; i < index.length(); i++) {
                    //JSONObject tt = index.getJSONObject(i);
                    //JSONObject tt = index.getJSONObject(0);
                    JSONObject tt = order.getJSONObject("result");
                    //id[i] = tt.getInt("id");

                    title = tt.getString("name");
                    description = tt.getString("description");
                    video_url = tt.getString("video_url");
                    thumbnail = tt.getString("thumbnail");

                    Log.d("result", id  + ", " + title + ", " + video_url + ", " + description + ", " + thumbnail);

//                        ActionBar actionBar = getActionBar();
//                        actionBar.setTitle(title);
//                        actionBar.setDisplayHomeAsUpEnabled(true);
                    tv_guide.setText(description);

//                        MediaController mc = new MediaController(GuideActivity.this);
//                        vv_guide.setMediaController(mc);
//                        Uri uri = Uri.parse(video_url);
//                        vv_guide.setVideoURI(uri);

                    YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener(){

                        //초기화 성공시
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b, String url) {
//                youTubePlayer.loadVideo(url);//url의 맨 뒷부분 ID값만 넣으면 됨
//            }

                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer.loadVideo(video_url);//url의 맨 뒷부분 ID값만 넣으면 됨

                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    };

                    youTubeView.initialize("AIzaSyBX3vpzMSbtQaw3evlZnuGrchSjngQ4KZY", listener);

                    //vv_guide.start();
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                            Uri.parse(video_url));
//                        startActivity(browserIntent);

                    //vv_guide.setVideoURI(Uri.parse("https://youtu.be/q6hBSSfokzY"));
                    //vv_guide.setVideoURI("https://youtu.be/q6hBSSfokzY");


//                        Log.d("title", id[i] + " " + member_id[i] + " " + title[i]);
//                        dataList.add(new ExerciseData(id[i], image[i], title[i], recruit_deadline[i], challenge_period[i], member_id[i]));
                    //ExerciseData data = new ExerciseData();
//                        data.setId(id[i]);
//                        data.setTitle(title[i]);
//                        data.setImage(image[i]);
                    //         exerciseAdapter.addItem(data);
                    //    }

                } catch (JSONException e) {
                    Log.d("LOG", String.valueOf(e));
                }
            }
        });
    }

}