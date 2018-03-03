package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

public class orders_cancelled extends Fragment  {
    public static final String PREFS_NAME = "preferences";
    public static final String PREF_UNAME = "Username";
    ArrayList<TutorCurrentTuitionsModel> data;
    RecyclerView rv;


   // private OnFragmentInteractionListener mListener;

    public orders_cancelled() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_orders_cancelled, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_order_cancel);
        rv.setHasFixedSize(true);
        get_order_cancelled_data();
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
                            JSONArray jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                String stdID,stdName,stdPhone,stdStatus,stdReqTime;

                                TutorCurrentTuitionsModel currentTuitionsModel = new TutorCurrentTuitionsModel(jsonArray.getJSONObject(i));

                      //          Customer_cancelled_orders customer_cancelled_orders = new Customer_cancelled_orders(jsonArray.getJSONObject(i));

                                stdID = currentTuitionsModel.getStudentID();
                                stdName = currentTuitionsModel.getStudentName();;
                                stdPhone = currentTuitionsModel.getStudentPhone();
                                stdStatus = currentTuitionsModel.getStudentStatus();
                                stdReqTime = currentTuitionsModel.getStduentRequesTime();

                                Log.v("Tele ","ID : "+stdID+" stdName : "+stdName+" stdPhone : "+stdPhone+"stdStatus : "+stdStatus+"stdReqTime : "+stdReqTime);
                                TutorCurrentTuitionsModel obj = new TutorCurrentTuitionsModel( stdID,stdName,stdPhone,stdStatus,stdReqTime);

                                data.add(obj);
                            }

                            Adapter_order_cancel adapter = new Adapter_order_cancel(getActivity(),data);
                            rv.setAdapter(adapter);
                            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                            rv.setLayoutManager(llm);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), "" + anError, Toast.LENGTH_LONG).show();

                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private String loadPreferences() {

        String tutphone;
        SharedPreferences settings;
        settings = getActivity().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        tutphone = settings.getString(PREF_UNAME, "");
        return tutphone;
    }
}
