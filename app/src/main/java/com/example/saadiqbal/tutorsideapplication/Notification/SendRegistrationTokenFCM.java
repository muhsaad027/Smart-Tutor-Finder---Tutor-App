package com.example.saadiqbal.tutorsideapplication.Notification;


import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.saadiqbal.tutorsideapplication.Config;
import com.example.saadiqbal.tutorsideapplication.URLTutor;


import org.json.JSONObject;

public class SendRegistrationTokenFCM {

    public static void sendRegistrationToServer(Context context, String token,String phone) {

        Log.v("FCM","token   " +token + " \n"+phone);
        AndroidNetworking.post( URLTutor.URL_TUTOR_FCMKEY_UPDATE)
                .addBodyParameter("fcmKey", token)
                .addBodyParameter("tutorphonenum",  phone)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.v("FIREBASE:result",""+response);
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.v("FIREBASE:error",""+error);
                    }
                });
    }
}
