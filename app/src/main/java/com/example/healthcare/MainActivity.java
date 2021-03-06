package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.healthcare.ui.Challenge;
import com.example.healthcare.ui.MenuAnalysis;
import com.example.healthcare.ui.MyPage;
import com.example.healthcare.ui.PosturalCorrection;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    MenuAnalysis menuAnalysis;
    PosturalCorrection posturalCorrection;
    Challenge challenge;
    MyPage myPage;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.nav_foodAnalysis, MenuAnalysis.newInstance());
//        ft.commit();

//        NavigationUI.setupWithNavController(
//                bottom_navigation, findNavContoller(R.id.navigation_home)
//        );
//
//        NavigationUI.setupActionBarWithNavController(
//                this,
//                findNavController(R.id.navigation_home),
//                AppBarConfiguration(
//                        setOf(
//                                R.id.
//                        )
//                )
//        );

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);




        menuAnalysis = new MenuAnalysis();
        posturalCorrection = new PosturalCorrection();
        challenge = new Challenge();
        myPage = new MyPage();


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, menuAnalysis).commitAllowingStateLoss();

        navigation.setOnNavigationItemSelectedListener(navListener);





        //?????? ?????? ????????? ?????? ??????????????????. commit();?????? ????????? ?????????.
        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,menuAnalysis).commitAllowingStateLoss();
        //bottomnavigationview??? ???????????? ?????? ????????? ????????? ?????????????????? ????????? ??? ????????? ???????????? ???????????????.
        //bottomNavigationView.On navListenr = new BottomNavigationView.OnNavigationItemSelectedListener(){

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            //Toast.makeText(MainActivity.this, "0???", Toast.LENGTH_SHORT).show();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.nav_foodAnalysis: {
                    transaction.replace(R.id.frame_container, menuAnalysis).commit();
                 //   Toast.makeText(MainActivity.this, "1???", Toast.LENGTH_SHORT).show();
                    return true;
                }
                case R.id.nav_postureCorrection: {
                    transaction.replace(R.id.frame_container, posturalCorrection).commitAllowingStateLoss();
                //    Toast.makeText(MainActivity.this, "2???", Toast.LENGTH_SHORT).show();
                    return true;
                }
                case R.id.nav_challenge: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, challenge).commitAllowingStateLoss();
                   // Toast.makeText(MainActivity.this, "3???", Toast.LENGTH_SHORT).show();
                    return true;
                }

                case R.id.nav_mypage: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myPage).commitAllowingStateLoss();
                   // Toast.makeText(MainActivity.this, "4???", Toast.LENGTH_SHORT).show();
                    return true;
                }
                default:
                    return false;


            }
        }
    };
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}
