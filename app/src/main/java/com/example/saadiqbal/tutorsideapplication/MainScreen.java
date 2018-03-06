package com.example.saadiqbal.tutorsideapplication;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    public String phone;
    Button accept;
    String courseName;
    Integer reqId;
    LatLng altitude;
    TextView t1;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        t1 = (TextView) findViewById(R.id.countdown);

        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_current);
        mapFragment.getMapAsync(this);

        accept = (Button) findViewById(R.id.req_accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasend();
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
        countDownTimer();
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

        if (id == R.id.inbox) {
            // Handle the camera action
        } else if (id == R.id.mycourses) {

        } else if (id == R.id.faqs) {

        } else if (id == R.id.help) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.contactus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            accept.setEnabled(true);
            accept.setBackgroundColor(getResources().getColor(R.color.bt_rq_back_ground_color));
            Double lat = Double.parseDouble(bundle.getString("latitude"));
            Double lng = Double.parseDouble(bundle.getString("longitude"));
            Log.v("MAINSCREEN", " lat  " + lat + "  lng  " + lng);
            altitude = new LatLng(lat, lng);
            courseName = bundle.getString("title");
            reqId = Integer.valueOf(bundle.getString("reqId"));

            Log.v(""+MainScreen.this.getClass().getSimpleName(),"req Id : "+reqId);
        }
    }

    public void datasend() {
       /* Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            phone = (String) bundle.get("phonenumber");
        }*/
        SharedPreferences shared = getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);
        String channel = (shared.getString(Login.PREF_UNAME, ""));
        Log.v(""+MainScreen.this.getClass().getSimpleName(),"Channel: "+channel);
        if (channel.length() == 10) {
            channel = "+92" + channel;
        } else {
            channel = "+92" + channel.substring(1);
        }

        if (reqId == null || channel == null) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            return;
        }
        AndroidNetworking.get(URLTutor.URL_SendRequestResponse)
                .addQueryParameter("tutPhone", channel)
                .addQueryParameter("reqId", ""+reqId)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean error = false;
                        String message = "";

                        try {
                            //logDebug("Response  :  "+response);
                            message = response.getString("message");
                            error = response.getBoolean("error");
                            Log.v(""+MainScreen.this.getClass().getSimpleName(),"message: "+message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (!error) {
                            Toast.makeText(MainScreen.this, "" + phone, Toast.LENGTH_LONG).show();
                            Toast.makeText(MainScreen.this, "" + message, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainScreen.this, "" + phone, Toast.LENGTH_LONG).show();
                            Toast.makeText(MainScreen.this, "" + message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        //logDebug("Error   " + error);
                        Log.v(""+MainScreen.this.getClass().getSimpleName(),"error: "+ error.getLocalizedMessage());


                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        // Add a marker in Sydney and move the camera
        if(altitude != null)
        {
            mMap.addMarker(new MarkerOptions().position(altitude).title("Student Here"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(altitude, 15.0f));
        }


    }
    public void countDownTimer(){
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                t1.setText("Remaing : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent intent = new Intent(MainScreen.this,MainHomeScreenTutor.class);
                startActivity(intent);
                finish();
            }
        }.start();

    }
}
