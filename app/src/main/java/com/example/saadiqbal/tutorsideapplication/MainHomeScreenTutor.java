package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainHomeScreenTutor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS_NAME = "preferences";
    public static final String PREF_UNAME = "Username";
    Button online, offline,currentTuition;
    Integer statusonline = 1;
    Integer statusoffline = 0;
    TextView tutor_name,tutor_loc,tutor_qauli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_screen_tutor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getTutorInfo();

        tutor_loc = (TextView)findViewById(R.id.tutrolocation_main) ;
        tutor_name = (TextView)findViewById(R.id.tutorname_main) ;
        tutor_qauli = (TextView)findViewById(R.id.tutroqual_main) ;

        online = (Button) findViewById(R.id.getonline);
        offline = (Button) findViewById(R.id.getoffline);
        currentTuition= (Button) findViewById(R.id.currentTuition);

        currentTuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment orders_cancelled =new TuitionCancel();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container,orders_cancelled);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasend(statusonline);
            }
        });


        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasend(statusoffline);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNewIntent(getIntent());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.inbox) {
//            // Handle the camera action
        if (id == R.id.ManageDayTime) {
            Intent intent = new Intent(MainHomeScreenTutor.this, DayTimeBooking.class);
            startActivity(intent);

        } else if (id == R.id.coursedetails) {
            Intent intent = new Intent(MainHomeScreenTutor.this, CoursesDetails.class);
            startActivity(intent);
        }
             else if (id == R.id.Addcoursedetails) {
            Intent i = new Intent(MainHomeScreenTutor.this,AddCourses.class);
            startActivity(i);
        } else if (id == R.id.chat_Box) {
            Intent intent = new Intent(MainHomeScreenTutor.this, ChatActivity.class);
            startActivity(intent);
        }
//
//        } else if (id == R.id.help) {

         else if (id == R.id.settings) {
            Intent intent = new Intent(MainHomeScreenTutor.this, Settings.class);
            startActivity(intent);
        } else if (id == R.id.contactus) {
            Intent intent = new Intent(MainHomeScreenTutor.this,Contactus.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void datasend(Integer status)
    {
        String phone = loadPreferences();
        if (phone.length() == 10) {
            phone = "+92" + phone;
        } else {
            phone = "+92" + phone.substring(1);
        }
        Log.e("a",""+phone);
        final String finalPhone = phone;
        AndroidNetworking.post(URLTutor.URL_StatusUpdate)
                .addBodyParameter("TutPhone", phone)
                .addBodyParameter("Status", status.toString())
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
 Log.e("MainHomeScreen",""+e.getLocalizedMessage());
                        }
                        if(!error)
                        {
                         //   SendRegistrationTokenFCM.sendRegistrationToServer(MainHomeScreenTutor.this, FirebaseInstanceId.getInstance().getToken(), finalPhone);
                            Toast.makeText(MainHomeScreenTutor.this,""+message,Toast.LENGTH_LONG).show();
                        /*    Intent intent = new Intent(Login.this,MainHomeScreenTutor.class);
                            startActivity(intent);
                            finish();*/
                        }
                        else
                        {
                            Toast.makeText(MainHomeScreenTutor.this,""+message,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("MainHomeScreenError",""+error.getLocalizedMessage());
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
    public void getTutorInfo() {

        String phone = loadPreferences();
        if (phone.length() == 10) {
            phone = "+92" + phone;
        } else {
            phone = "+92" + phone.substring(1);
        }

        AndroidNetworking.get(URLTutor.URL_GetTutorInfo)
                .addQueryParameter("tutPhone", phone)
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


                                String name = item.getString("TutName");
                                String location = item.getString("TutLocation");
                                String qualification = item.getString("TutQual");
                                tutor_qauli.setText(qualification);
                                tutor_loc.setText(location);
                                tutor_name.setText(name);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error4
                        Toast.makeText(MainHomeScreenTutor.this, "" + error, Toast.LENGTH_LONG).show();

                    }
                });

}}
