package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Saad Iqbal on 12/28/2017.
 */

public class URLTutor {
    public static final  String URL_LOGIN = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/loginTut";
    public static final  String URL_Registration = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/registerTutor";
    public static final String URL_Settings = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/studentProfile";
    public  static  String  URL_TUTOR_FCMKEY_UPDATE ="http://hometuition.ahmedgraf.com/HomeTuition/Test1/updatetTutor_FCMKey";
    public static final String URL_SendRequestResponse = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/sendRequestStudent";

    public  static  String getPhoneNo (Context context)
    {
        SharedPreferences shared = context.getSharedPreferences(Login.PREFS_NAME , MODE_PRIVATE);
        String number = (shared.getString(Login.PREF_UNAME, ""));
        return number;
    }

}
