package com.example.saadiqbal.tutorsideapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DayTimeBooking extends AppCompatActivity implements View.OnClickListener {

    Button mon,tue,wed,thur,fri,sat,sun;
    Button t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_time_booking);
        mon = (Button) findViewById(R.id.monday);
        tue = (Button) findViewById(R.id.tuesday);
        wed = (Button) findViewById(R.id.wednesday);
        thur = (Button) findViewById(R.id.thursday);
        fri = (Button) findViewById(R.id.friday);
        sat = (Button) findViewById(R.id.saturday);
        sun = (Button) findViewById(R.id.sunday);
        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thur.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);
        sun.setOnClickListener(this);
        t1 = (Button)findViewById(R.id.slot1);
        t2 = (Button)findViewById(R.id.slot2);
        t3 = (Button)findViewById(R.id.slot3);
        t4 = (Button)findViewById(R.id.slot4);
        t5 = (Button)findViewById(R.id.slot5);
        t6 = (Button)findViewById(R.id.slot6);
        t7 = (Button)findViewById(R.id.slot7);
        t8 = (Button)findViewById(R.id.slot8);
        t9 = (Button)findViewById(R.id.slot9);
        t10 = (Button)findViewById(R.id.slot10);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
        t7.setOnClickListener(this);
        t8.setOnClickListener(this);
        t9.setOnClickListener(this);
        t10.setOnClickListener(this);
    }

    private void changeBackgroundColor(View view) {
        Button b = (Button) view;
        int colorId = R.color.white;
        if (view.getTag() != null)
            colorId = (int) b.getTag();
        if (colorId == R.color.themeappblue) {
            b.setTag(R.color.white);
            b.setBackgroundColor(getResources().getColor(R.color.white));
            b.setTextColor(getResources().getColor(R.color.black));
        } else {
            b.setTag(R.color.themeappblue);
            b.setBackgroundColor(getResources().getColor(R.color.themeappblue));
            b.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.monday:
                changeBackgroundColor(view);
                break;
            case R.id.tuesday:
                changeBackgroundColor(view);
                break;
            case R.id.wednesday:
                changeBackgroundColor(view);
                break;
            case R.id.thursday:
                changeBackgroundColor(view);
                break;
            case R.id.friday:
                changeBackgroundColor(view);
                break;
            case R.id.saturday:
                changeBackgroundColor(view);
                break;
            case R.id.sunday:
                changeBackgroundColor(view);
                break;
            case R.id.slot1:
                changeBackgroundColor(view);
                break;
            case R.id.slot2:
                changeBackgroundColor(view);
                break;
            case R.id.slot3:
                changeBackgroundColor(view);
                break;
            case R.id.slot4:
                changeBackgroundColor(view);
                break;
            case R.id.slot5:
                changeBackgroundColor(view);
                break;
            case R.id.slot6:
                changeBackgroundColor(view);
                break;
            case R.id.slot7:
                changeBackgroundColor(view);
                break;
            case R.id.slot8:
                changeBackgroundColor(view);
                break;
            case R.id.slot9:
                changeBackgroundColor(view);
                break;
            case R.id.slot10:
                changeBackgroundColor(view);
                break;
        }
    }
}
