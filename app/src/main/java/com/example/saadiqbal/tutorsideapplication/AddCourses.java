package com.example.saadiqbal.tutorsideapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCourses extends AppCompatActivity {
    final ArrayList<String> autofillcoursesDB = new ArrayList<String>();
    AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        autoCompleteTextView = (AutoCompleteTextView)  findViewById(R.id.searchAutoComplete);
        AutoCourseFillData();
    }
    public void AutoCourseFillData() {



        AndroidNetworking.get(URLTutor.URL_AutoFillCourses)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean error = false;
                        String message = "";

                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject item = jsonArray.getJSONObject(i);
                                autofillcoursesDB.add(item.getString("CourseName"));
                                AutoCompleteAdapter adapter = new AutoCompleteAdapter(AddCourses.this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, autofillcoursesDB);
                                autoCompleteTextView.setAdapter(adapter);
                            }

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



    }
}
