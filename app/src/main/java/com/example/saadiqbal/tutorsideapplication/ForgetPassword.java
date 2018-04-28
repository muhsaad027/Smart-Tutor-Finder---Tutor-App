package com.example.saadiqbal.tutorsideapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.saadiqbal.tutorsideapplication.Notification.SendRegistrationTokenFCM;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {

    Button confirm;
    EditText p1,p2;
    String pass,repasscheck,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        p1 = (EditText)findViewById(R.id.passwordforget) ;
        p2 = (EditText)findViewById(R.id.repasswordforget) ;
        confirm = (Button)findViewById(R.id.registerforget);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validations();
            }
        });
    }
    public void validations() {
        pass = p1.getText().toString();
        repasscheck = p2.getText().toString();
        if (p1.getText().toString().isEmpty()) {
            p1.setError("Phone number is required!");
            requestFocus(p1);
            return;
        } else if (p2.getText().toString().isEmpty()) {
            p2.setError("Password is required!");
            requestFocus(p2);
            return;
        }
        else if(!pass.equals(repasscheck))
        {
            p1.setError("Password Doesn't match");
            p2.setError("Password Doesn't match");
            requestFocus(p1);
            requestFocus(p2);
            return;
        }
        datasend();
        finish();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    public void datasend() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            phone = (String) bundle.get("nameT");
        }
        AndroidNetworking.post(URLTutor.URL_PasswordUpdate)

                .addBodyParameter("TutPhone", phone)
                .addBodyParameter("TutPass", p1.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean error = false;
                        String message = "";

                        try {
                            message = response.getString("message");
                            error = response.getBoolean("error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (!error) {
                            SendRegistrationTokenFCM.sendRegistrationToServer(ForgetPassword.this, FirebaseInstanceId.getInstance().getToken(), phone);
                            Toast.makeText(ForgetPassword.this, "" + message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgetPassword.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ForgetPassword.this, "" + message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}
