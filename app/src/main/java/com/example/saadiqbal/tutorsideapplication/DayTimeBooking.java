package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DayTimeBooking extends AppCompatActivity implements View.OnClickListener {

    Button mon, tue, wed, thur, fri, sat, sun;
    Button t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
    Button saveTime;

    public static final String PREFS_NAME = "preferences";
    public static final String PREF_UNAME = "Username";

    int[] dayList = new int[]{R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday, R.id.sunday};
    int[] timeList = new int[]{R.id.slot1, R.id.slot2, R.id.slot3, R.id.slot4, R.id.slot5, R.id.slot6, R.id.slot7, R.id.slot8, R.id.slot9, R.id.slot10};

    ArrayList<Button> dayarrayList = new ArrayList<>();
    ArrayList<Button> timearrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_time_booking);
        dayarrayList.add(mon = (Button) findViewById(R.id.monday));
        dayarrayList.add(tue = (Button) findViewById(R.id.tuesday));
        dayarrayList.add(wed = (Button) findViewById(R.id.wednesday));
        dayarrayList.add(thur = (Button) findViewById(R.id.thursday));
        dayarrayList.add(fri = (Button) findViewById(R.id.friday));
        dayarrayList.add(sat = (Button) findViewById(R.id.saturday));
        dayarrayList.add(sun = (Button) findViewById(R.id.sunday));
        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thur.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);
        sun.setOnClickListener(this);
        timearrayList.add(t1 = (Button) findViewById(R.id.slot1));
        timearrayList.add(t2 = (Button) findViewById(R.id.slot2));
        timearrayList.add(t3 = (Button) findViewById(R.id.slot3));
        timearrayList.add(t4 = (Button) findViewById(R.id.slot4));
        timearrayList.add(t5 = (Button) findViewById(R.id.slot5));
        timearrayList.add(t6 = (Button) findViewById(R.id.slot6));
        timearrayList.add(t7 = (Button) findViewById(R.id.slot7));
        timearrayList.add(t8 = (Button) findViewById(R.id.slot8));
        timearrayList.add(t9 = (Button) findViewById(R.id.slot9));
        timearrayList.add(t10 = (Button) findViewById(R.id.slot10));
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
        saveTime = (Button) findViewById(R.id.savedatetime);
        saveTime.setOnClickListener(this);

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
            case R.id.savedatetime:

                String teachingday = "";
                String teachingtime = "";
                for (int i = 0; i < dayarrayList.size(); i++) {
                    if (dayarrayList.get(i).getTag() != null && ((int) dayarrayList.get(i).getTag()) == R.color.themeappblue) {

                        teachingday += dayarrayList.get(i).getText() + ",";
                    }
                }/*
                Log.d("DayBooking","  aa  : "+teachingday);
                Toast.makeText(this,"HERE "+teachingday,Toast.LENGTH_LONG).show();*/

                for (int i = 0; i < timearrayList.size(); i++) {
                    if (timearrayList.get(i).getTag() != null && ((int) timearrayList.get(i).getTag()) == R.color.themeappblue) {

                        teachingtime += timearrayList.get(i).getText() + ",";
                    }
                }

                datasend(teachingday,teachingtime);
                /*
                Log.d("TimeBooking","  aa  : "+teachingday);
                ;*/
                break;
        }
    }
    public void datasend(String days,String time)
    {
        String phone = loadPreferences();
        if (phone.length() == 10) {
            phone = "+92" + phone;
        } else {
            phone = "+92" + phone.substring(1);
        }
        Log.e("a",""+phone);
        final String finalPhone = phone;
        AndroidNetworking.post(URLTutor.URL_DateTimeUpdate)
                .addBodyParameter("TutPhone", phone)
                .addBodyParameter("TeachingDays", days)
                .addBodyParameter("TeachingTime", time)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean error = false;
                        String message = "";

                        try {
                            message = response.getString("message");
                            error = response.getBoolean("error");
                        } catch (JSONException e) {
                            Log.e("DayTimeScreen",""+e.getLocalizedMessage());
                        }
                        if(!error)
                        {
                            //   SendRegistrationTokenFCM.sendRegistrationToServer(MainHomeScreenTutor.this, FirebaseInstanceId.getInstance().getToken(), finalPhone);
                            Toast.makeText(DayTimeBooking.this,""+message,Toast.LENGTH_LONG).show();
//                            Toast.makeText(DayTimeBooking.this,"Successfully Updated",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DayTimeBooking.this,MainHomeScreenTutor.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(DayTimeBooking.this,""+message,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("DayTimeScreenError",""+error.getLocalizedMessage());
                        // handle error
                    }
                });
    }
    private String loadPreferences() {

        String tutphone;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        tutphone = settings.getString(PREF_UNAME, "");
        return tutphone;
    }
}
