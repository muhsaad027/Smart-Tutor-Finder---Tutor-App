package com.example.saadiqbal.tutorsideapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class TutorCancelRequest {

    public String jobTitle,personName,canceledBy,jobCreationTime;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(String canceledBy) {
        this.canceledBy = canceledBy;
    }

    public String getJobCreationTime() {
        return jobCreationTime;
    }

    public void setJobCreationTime(String jobCreationTime) {
        this.jobCreationTime = jobCreationTime;
    }

    public TutorCancelRequest(String jobTitle, String personName, String canceledBy, String jobCreationTime) {
        this.jobTitle = jobTitle;
        this.personName = personName;
        this.canceledBy = canceledBy;
        this.jobCreationTime = jobCreationTime;
    }

    public TutorCancelRequest(JSONObject jsonObject) {
        try {
            this.jobTitle = jsonObject.getString("jobTitle");
            this.personName = jsonObject.getString("personName");
            this.canceledBy = jsonObject.getString("canceledBy");
            this.jobCreationTime = jsonObject.getString("jobCreationTime");

        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.v("COMPLETEDORDERS"," error "+ e.getLocalizedMessage());
        }
    }
}
