package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CurrentTuition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_tuition);
    }
    public static final String PREFS_NAME = "preferences";
    public static final String PREF_UNAME = "Username";
    ArrayList<TutorCurrentTuitionsModel> data;
    RecyclerView rv;


    private orders_cancelled.OnFragmentInteractionListener mListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_orders_cancelled, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rv_order_cancel);
        rv.setHasFixedSize(true);
        return rootView;
    }
    public void get_order_cancelled_data()
    {
        String phone = loadPreferences();
        if (phone.length() == 10) {
            phone = "+92" + phone;
        } else {
            phone = "+92" + phone.substring(1);
        }
        AndroidNetworking.get(URLTutor.URL_CurrentTuition)
                .addQueryParameter("TutPhone", phone )
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        data = new ArrayList<>();

                        try {
                            JSONArray jsonArray = response.getJSONArray("Tutorcurrenttuitions");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                String jobTitle,personName,canceledBy,jobCreationTime;

                                Customer_cancelled_orders customer_cancelled_orders = new Customer_cancelled_orders(jsonArray.getJSONObject(i));

                                jobTitle = customer_cancelled_orders.getJobTitle();
                                personName = customer_cancelled_orders.getPersonName();
                                canceledBy = customer_cancelled_orders.getCanceledBy();
                                jobCreationTime = customer_cancelled_orders.getJobCreationTime();

                                Log.v("Tele ","jobtitle : "+jobTitle+" person name : "+personName+" canceled by : "+canceledBy+"jobCreationTime : "+jobCreationTime);
                                Customer_cancelled_orders obj = new Customer_cancelled_orders( jobTitle,personName,canceledBy,jobCreationTime);

                               // data.add(obj);
                            }

                            Adapter_order_cancel adapter = new Adapter_order_cancel(CurrentTuition.this,data);
                            rv.setAdapter(adapter);
                            LinearLayoutManager llm = new LinearLayoutManager(CurrentTuition.this);
                            rv.setLayoutManager(llm);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(CurrentTuition.this, "" + anError, Toast.LENGTH_LONG).show();

                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public void onAttach(Context context) {
        if (context instanceof orders_cancelled.OnFragmentInteractionListener) {
            mListener = (orders_cancelled.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onDetach() {
        mListener = null;
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private String loadPreferences() {

        String tutphone;
        SharedPreferences settings;
        settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        tutphone = settings.getString(PREF_UNAME, "");
        return tutphone;
    }
}
