package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    TextView tv_name, tv_email, tv_phno;
    Button id_logout;
    public static final String PREFS_NAME = "preferences";
    public static final String PREF_UNAME = "Username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tv_name = (TextView) findViewById(R.id.tv_name_setting);
        tv_email = (TextView) findViewById(R.id.tv_emails_setting);
        tv_phno = (TextView) findViewById(R.id.tv_phno_setting);
        id_logout = (Button) findViewById(R.id.id_logout);
        id_logout.setOnClickListener(this);

        datasend();
    }


    public void datasend() {
        String phone = loadPreferences();
        if (phone.length() == 10) {
            phone = "+92" + phone;
        } else {
            phone = "+92" + phone.substring(1);
        }
        AndroidNetworking.get(URLTutor.URL_Settings)

                .addQueryParameter("TutPhone", phone)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean error = false;
                        String message = "";

                        try {
                            JSONArray jsonArray = response.getJSONArray("student");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject item = jsonArray.getJSONObject(i);


                                String name = item.getString("TutName");
                                String email = item.getString("TutEmail");
                                String phno = item.getString("TutPhone");

                                tv_email.setText(email);
                                tv_name.setText(name);
                                tv_phno.setText(phno);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error4
                        Toast.makeText(Settings.this, "" + error, Toast.LENGTH_LONG).show();

                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_logout:
                SharedPreferences settings = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                finish();
                Intent intent = new Intent(this, AfterSplash.class);
                startActivity(intent);
                break;
        }
    }

    private String loadPreferences() {

        String tutphone;
        SharedPreferences settings;
        settings = this.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        tutphone = settings.getString(PREF_UNAME, "");
        return tutphone;
    }
}
