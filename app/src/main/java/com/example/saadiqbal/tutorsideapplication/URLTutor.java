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
    public static final String URL_Settings = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/getTutorProfile";
    public  static  String  URL_TUTOR_FCMKEY_UPDATE ="http://hometuition.ahmedgraf.com/HomeTuition/Test1/updatetTutor_FCMKey";
    public static final String URL_SendRequestResponse = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/sendRequestStudent";
    public static final String URL_StatusUpdate = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/statusUpdate";
    public static final String URL_DateTimeUpdate = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/daytimeupdate";
    public static final String URL_CurrentTuition = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/CurrentTuition";
    public static final String URL_RequestCencel = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/cencelRequestTutor";
    public static final String URL_GetTutorInfo = "http://hometuition.ahmedgraf.com/HomeTuition/Test1/getStudentInfoLogin";


    public  static  String getPhoneNo (Context context)
    {
        SharedPreferences shared = context.getSharedPreferences(Login.PREFS_NAME , MODE_PRIVATE);
        String number = (shared.getString(Login.PREF_UNAME, ""));
        return number;
    }

}
