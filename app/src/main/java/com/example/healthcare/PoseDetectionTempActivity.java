package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.ui.PosturalCorrection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

//enum Exercise_URL{
//    SQUAT, LUNGE, STANDING_PULL_DOWN
//}
public class PoseDetectionTempActivity extends AppCompatActivity {

    private WebView webView;
    private WebView webView2;
    private TextView tv_temp;
    private TextView tv_total_count;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private String TAG = "js";
    private String TAG2 = "count";
    String id;
    Integer count;
    Intent intent;
    String exercise_url;
    String jwt;
    //private long backBtnTime = 0;
    //private String url = webView.loadUrl("file:///android_asset/index.html");

//    @Override
//    public void onBackPressed(){
//        webView = (WebView)findViewById(R.id.webView);
////        long curTime = System.currentTimeMillis();
////        long gapTime = curTime - backBtnTime;
////        if (webView.canGoBack()) {
////            webView.goBack();
////        } else if (0 <= gapTime && 2000 >= gapTime) {
////            super.onBackPressed();
////        } else {
////            backBtnTime = curTime;
////            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
////        }
//
//        if(webView.canGoBack()) {
//            webView.goBack();
//
//        }else{
//            super.onBackPressed();
//        }
//    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pose_detection_temp);

        intent = getIntent();
        id = intent.getStringExtra("id");
        count = intent.getIntExtra("count", 0);
        SharedPreferences sf = this.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        jwt = sf.getString("jwt", "0");
        //Toast.makeText(getApplicationContext(), count, Toast.LENGTH_SHORT).show();

        switch(id){
            case "1":
                exercise_url = "https://admiring-hoover-6d910d.netlify.app/";
                break;
            case "2":
                exercise_url = "https://jovial-turing-2f0718.netlify.app/";
                break;
            case "3":
                exercise_url = "https://unruffled-panini-750e92.netlify.app";
                break;

            default:
                break;
        }

        tv_temp = (TextView)findViewById(R.id.tv_temp);
        tv_total_count = (TextView)findViewById(R.id.tv_total_count);
        tv_total_count.setText(count.toString());

        //String url= "https://admiring-hoover-6d910d.netlify.app/";  // 스쿼트
        //    String url= "https://jovial-turing-2f0718.netlify.app/"; // 런지
        // https://unruffled-panini-750e92.netlify.app // 스탠딩 풀 다운

//        TextWatcher textWatcher = ;
//        tv_temp.addTextChangedListener(textWatcher);
//        tv_temp.onScreenStateChanged();

        //String url= "file:///android_asset/pose_detection/index.html";

//        WebView view= (WebView) this.findViewById(R.id.webView);

//        final WebViewClient client = new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                //return false;
//                return true;
//            }
//
//        };

//        WebSettings settings = view.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setAllowFileAccessFromFileURLs(true);
//        settings.setSaveFormData(false);
//        settings.setSupportMultipleWindows(true);
//        settings.setMediaPlaybackRequiresUserGesture(false);
//        settings.setDomStorageEnabled(true);
//        view.setWebViewClient(client);
//        view.setWebChromeClient(new WebChromeClient());
//        view.loadUrl(url);

        //view.loadData(url, "text/html", "UTF-8");

        webView = (WebView)findViewById(R.id.webView);

        // webView2 = (WebView)findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.addJavascriptInterface(new AndroidBridge(), "android");
        webView.addJavascriptInterface(new AndroidBridge(), "android");
        //webView2.getSettings().setJavaScriptEnabled(true);

        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        //webView.loadUrl("file:///android_asset/pose_detection/index.html");
        //webView.loadUrl(url);
        webView.loadUrl(exercise_url);


        //webView2.loadUrl("https://www.naver.com");
        //webView.loadUrl("https://teachablemachine.withgoogle.com/models/km2YuSGSj/");

        //webView.loadUrl("http://naver.com");
        //https://admiring-hoover-6d910d.netlify.app/
        //webView2.setWebChromeClient(new WebChromeClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onPermissionRequest(PermissionRequest request){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    request.grant(request.getResources());
                }
            }
        });
        //webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new WebViewClientClass());
        //webView2.setWebViewClient(new WebViewClientClass());


//        Log.d(TAG2, tv_temp.getText().toString());
//        Log.d(TAG2, tv_temp.toString());
//        if(tv_temp.getText().toString().equals(3)){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        }


    }
    private class AndroidBridge {
        @JavascriptInterface
        public void callAndroid(final String arg) { // must be final
            handler.post(new Runnable() {
                public void run() {
                    Log.d(TAG, "callAndroid(" + arg + ")");
                    tv_temp.setText(arg);
                    //if(arg.equals("3")){
                    if(arg.equals(count.toString())){
                        Toast.makeText(getApplicationContext(), "성공하셨습니다!", Toast.LENGTH_SHORT).show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                request("http://api-dev.pproject2020.xyz/exercise-count");
                            }
                        }).start();

                        Intent intent = new Intent(PoseDetectionTempActivity.this, MainActivity.class);
//                        //webView.onPause();
//                        webView.removeAllViews();
//                        webView.destroy();
                        startActivity(intent);

                    }
                }
            });
        }
    }

//    public class JavaScirptInterface{
//        Context mContext;
//        JavaScirptInterface(Context c) {
//            mContext =c;
//        }
//        public void webview_finish(){
//            finish();
//        }
//    }

    public void request(String urlStr) {
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
            jsonParam.put("exercise_id", id);
            jsonParam.put("count", count);

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private class WebViewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
            //return super.shouldOverrideUrlLoading(view, url);
        }
    }


}

