package com.example.saadiqbal.tutorsideapplication.Notification;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.saadiqbal.tutorsideapplication.Config;
import com.example.saadiqbal.tutorsideapplication.URLTutor;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIDService";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers.
        SendRegistrationTokenFCM.sendRegistrationToServer(getApplicationContext(),refreshedToken, URLTutor.getPhoneNo(getApplicationContext()));

    }
}
