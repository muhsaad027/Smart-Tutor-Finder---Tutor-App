package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddCourses extends AppCompatActivity implements View.OnClickListener {
    final ArrayList<String> autofillcoursesDB = new ArrayList<String>();
    AutoCompleteTextView autoCompleteTextView;
    HashMap<String,String> progHashMap;
    Button insertcourse;
    public static final String PREFS_NAME = "preferences";
    public static final String PREF_UNAME = "Username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        autoCompleteTextView = (AutoCompleteTextView)  findViewById(R.id.searchAutoComplete);
        AutoCourseFillData();
    }
    public void AutoCourseFillData() {
        AndroidNetworking.get(URLTutor.URL_AututextCourseWithProgramName)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean error = false;
                        String message = "";

                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            progHashMap = new HashMap<String, String>();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject item = jsonArray.getJSONObject(i);
                                autofillcoursesDB.add(item.getString("CourseName"));
                                progHashMap.put(item.getString("CourseName"),item.getString("ProgName"));
                            }
                            AutoCompleteAdapter adapter = new AutoCompleteAdapter(AddCourses.this, android.R.layout.simple_list_item_2, android.R.id.text1,android.R.id.text2, autofillcoursesDB,progHashMap);
                            autoCompleteTextView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error4
                        Toast.makeText(AddCourses.this, "" + error, Toast.LENGTH_LONG).show();

                    }
                });
        insertcourse = (Button) findViewById(R.id.insertcourse_btn);
        insertcourse.setOnClickListener(this);
    }
    public void datasend()
    {
        String phone = loadPreferences();
        if (phone.length() == 10) {
            phone = "+92" + phone;
        } else {
            phone = "+92" + phone.substring(1);
        }
        Log.e("a",""+phone);
        final String finalPhone = phone;
        AndroidNetworking.post(URLTutor.URL_TutorCourseInsert)
                .addBodyParameter("TutPhone", phone)
                .addBodyParameter("CourseName", autoCompleteTextView.getText().toString())
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
                            Log.e("AddCourses",""+e.getLocalizedMessage());
                        }
                        if(!error)
                        {
                            //   SendRegistrationTokenFCM.sendRegistrationToServer(MainHomeScreenTutor.this, FirebaseInstanceId.getInstance().getToken(), finalPhone);
                            Toast.makeText(AddCourses.this,""+message,Toast.LENGTH_LONG).show();
                        /*    Intent intent = new Intent(Login.this,MainHomeScreenTutor.class);
                            startActivity(intent);
                            finish();*/
                        }
                        else
                        {
                            Toast.makeText(AddCourses.this,""+message,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("AddCoursesError",""+error.getLocalizedMessage());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insertcourse_btn:
                validations();
                break;
        }
    }
    public void validations() {
        if (autoCompleteTextView.getText().toString().isEmpty()) {
            autoCompleteTextView.setError("Select any course to Insert");
            requestFocus(autoCompleteTextView);
            return;
        }
        datasend();
        Intent i =new Intent(AddCourses.this,MainHomeScreenTutor.class);
        startActivity(i);;
        finish();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
