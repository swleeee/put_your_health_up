package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class PopUpUnregisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_pop_up_unregister);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

    }
    public void onClickDialog(View v){
        switch(v.getId()){
            case R.id.btn_cancel_unregister:
                this.finish();
                break;
            case R.id.btn_confirm_unregister:
//                this.finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                break;
        }
    }
}
