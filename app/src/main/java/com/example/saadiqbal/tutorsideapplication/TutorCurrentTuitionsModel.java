package com.example.saadiqbal.tutorsideapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Saad Iqbal on 3/3/2018.
 */

public class TutorCurrentTuitionsModel {
    public String StudentID, StudentName, StudentPhone, StudentStatus, StduentRequesTime;

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String StudentID) {
        this.StudentID = StudentID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }

    public String getStudentPhone() {
        return StudentPhone;
    }

    public void setStudentPhone(String StudentPhone) {
        this.StudentPhone = StudentPhone;
    }

    public String getStudentStatus() {
        return StudentStatus;
    }

    public void setStudentStatus(String StudentStatus) {
        this.StudentStatus = StudentStatus;
    }

    public String getStduentRequesTime() {
        return StduentRequesTime;
    }

    public void setStduentRequesTime(String StduentRequesTime) {
        this.StduentRequesTime = StduentRequesTime;
    }

    public TutorCurrentTuitionsModel(String StudentID, String StudentName,String StudentPhone, String StudentStatus, String StduentRequesTime) {
        this.StudentID = StudentID;
        this.StudentName = StudentName;
        this.StudentPhone = StudentPhone;
        this.StudentStatus = StudentStatus;
        this.StduentRequesTime = StduentRequesTime;
    }
    public TutorCurrentTuitionsModel(JSONObject jsonObject)
    {
        try {
            this.StudentID = jsonObject.getString("idRequest");
            this.StudentName = jsonObject.getString("StdName");
            this.StudentPhone = jsonObject.getString("StdPhone");
            this.StudentStatus = jsonObject.getString("requestStatus");
            this.StduentRequesTime = jsonObject.getString("requestTime");

        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.v("CurrentTuition "," error "+ e.getLocalizedMessage());
        }
    }
}
