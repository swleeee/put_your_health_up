package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.healthcare.ui.MenuAnalysis;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class CalendarActivity extends AppCompatActivity {

    ImageButton imgbutton;
    MenuAnalysis menuAnalysis;

    //    materialCalenderView.add
////    mater
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_calendar);



//        //UI 객체생성
//        imgbutton = (ImageButton) findViewById(R.id.img_calender);
//
//        //데이터 가져오기
//        Intent intent = getIntent();
//        String data = intent.getStringExtra("data");
//        imgbutton.setText(data);
//        mCalendarView = (CalendarView)view.findViewById(R.id.calendarView);
//        whenDate = (TextView) view.findViewById(R.id.whenDate);
        MaterialCalendarView materialCalendarView = findViewById(R.id.calenderView);
        materialCalendarView.setSelectedDate(CalendarDay.today());
//        materialCalendarView.addDecorator(new MySelectorDecorator(this));

        //materialCalendarView.setOnDateChangedListener();
//        materialCalendarView.setOnDateChangedListener(new CalendarView.OnDateChangeListener(){
//
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                month+= 1;
//
//            }
//        });



        Button button = findViewById(R.id.btn_choose_day);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                menuAnalysis = new MenuAnalysis();
                //getSupportFragmentManager().beginTransaction().replace(R.id.calendar, menuAnalysis).commit();

                Bundle bundle = new Bundle();
//                 String day = materialCalendarView.getCurrentDate().toString();
                String day = materialCalendarView.getSelectedDate().toString();
                Log.i("OGday", day);
                int day_length = day.length()-1;
                day = day.substring(12, day_length);
                String parts[] = day.split("-");
                Log.i("OGday", parts[0]);
//                int day2 = Integer.parseInt(parts[2]);
//                int month = Integer.parseInt(parts[1]);
//                int year = Integer.parseInt(parts[0]);
                String year = parts[0];
                int realmonth = (Integer.parseInt(parts[1])+1);
                String month = String.valueOf(realmonth);
                String day1 = parts[2];
                String all = year + "-" + realmonth + "-" + day1;

//                bundle.putString("day", day);
//                menuAnalysis.setArguments(bundle);

//                int day_length = day.length()-1;
//                Log.i("day_lengj", String.valueOf(day_length));
//
//                day = day.substring(12, day_length);
                Log.i("day", all);

                intent.putExtra("day", all);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), day, Toast.LENGTH_SHORT).show();
                //bundle.putString("day", day)
            }
        });

    }
}