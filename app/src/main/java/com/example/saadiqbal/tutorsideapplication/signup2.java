package com.example.saadiqbal.tutorsideapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.*;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

public class signup2 extends AppCompatActivity implements PlaceSelectionListener {
public String name,email,pass,contact; Double latid,longid;
    EditText tloc,toptLoc,tqual,tdays,tutime;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final String LOG_TAG = "PlaceSelectionListener";
    private static final int REQUEST_SELECT_PLACE = 1000;
    private TextView locationTextView;
    private TextView attributionsTextView;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint("Search a Location");
        autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);





        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            name = bundle.getString("nameT");
            pass = bundle.getString("passT");
            contact = bundle.getString("contactT");
            email = bundle.getString("emailT");
        }
       // tloc = (EditText)findViewById(R.id.LOC);
        //toptLoc = (EditText)findViewById(R.id.optLOC);
        tqual = (EditText)findViewById(R.id.tutqualif);
        tdays = (EditText)findViewById(R.id.days);
        tutime = (EditText)findViewById(R.id.timings);
        reg = (Button) findViewById(R.id.register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        locationTextView = (TextView) findViewById(R.id.txt_location);
        //attributionsTextView = (TextView) findViewById(R.id.txt_attributions);
    }
    public void validation() {
        /*if (tloc.getText().toString().isEmpty()) {
            tloc.setError("Name is required!");
            requestFocus(tloc);
            return;
        }
        if (toptLoc.getText().toString().isEmpty()) {
            toptLoc.setError("Email is required!");
            requestFocus(toptLoc);
            return;
        }*/
        if (tqual.getText().toString().isEmpty()) {
            tqual.setError("Password is required!");
            requestFocus(tqual);
            return;
        }
        if (tdays.getText().toString().isEmpty()) {
            tdays.setError("Re Enter the same Password!");
            requestFocus(tdays);
            return;
        }
        if (tutime.getText().toString().isEmpty()) {
            tutime.setError("Contact Information is Required!");
            requestFocus(tutime);
            return;
        }
        Intent intent = new Intent(signup2.this, Authentication.class);
        intent.putExtra("nameT", name);
        intent.putExtra("emailT", email);
        intent.putExtra("contactT", contact);
        intent.putExtra("passT", pass);
        //intent.putExtra("loc1", tloc.getText().toString());
       // intent.putExtra("loc2", toptLoc.getText().toString());
        intent.putExtra("qauli", tqual.getText().toString());
        intent.putExtra("techday", tdays.getText().toString());
        intent.putExtra("time", tutime.getText().toString());
        intent.putExtra("lat", latid);
        intent.putExtra("long", longid);
        startActivity(intent);
        finish();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    ///////////////////////////////
    public void onPlaceSelected(Place place) {
        Log.v(LOG_TAG, "Place Selected: " + place.getName()  + " latitude   "  +place.getLatLng().latitude +"  logitude  " +place.getLatLng().longitude);
        locationTextView.setText(getString(R.string.formatted_place_data, place
                .getName(), place.getAddress(), place.getPhoneNumber(), place
                .getWebsiteUri(), place.getId(), place.getLatLng()));
        latid = place.getLatLng().latitude;
        longid = place.getLatLng().longitude;

        if (!TextUtils.isEmpty(place.getAttributions())){
            attributionsTextView.setText(Html.fromHtml(place.getAttributions().toString()));
        }
    }

    @Override
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }
}
