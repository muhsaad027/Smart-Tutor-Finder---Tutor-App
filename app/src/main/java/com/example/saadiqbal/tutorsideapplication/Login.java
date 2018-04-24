package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.saadiqbal.tutorsideapplication.Notification.SendRegistrationTokenFCM;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
Button loginpage;
    TextView forgetpasswrod, createAnewAccount;
    private Button ButtonInvisible;

    EditText tutname,tutpass;
    //////////////////////
    public static final String PREFS_NAME = "preferences";
    public static final String PREF_UNAME = "Username";
    public static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    ////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tutname = (EditText) findViewById(R.id.tut_username);
        tutpass = (EditText) findViewById(R.id.tut_password);
        ButtonInvisible = (Button) findViewById(R.id.show_pass);

        forgetpasswrod = (TextView) findViewById(R.id.forgetpass);
        forgetpasswrod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



            }
        });

        createAnewAccount = (TextView) findViewById(R.id.createaccount);
        createAnewAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent n = new Intent(Login.this, Signup.class);
                startActivity(n);
                finish();
            }
        });

        ButtonInvisible.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tutpass.setTransformationMethod(null);
            }
        });

        loginpage = (Button)findViewById(R.id.loginb1);
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validations();
            }
        });
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    public void validations() {
        if (tutname.getText().toString().isEmpty())
        {
            tutname.setError("Phone number is required!");
            requestFocus(tutname);
            return;
        }
        if (tutpass.getText().toString().isEmpty())
        {
            tutpass.setError("Password is required!");
            requestFocus(tutpass);
            return;
        }
        Intent intent = new Intent(Login.this, MainScreen.class);
        intent.putExtra("phonenumber", UnameValue);
        //startActivity(intent);
       // finish();
        datasend();
    }
    public void datasend()
    {
        String phone = tutname.getText().toString();
        if (phone.length() == 10) {
            phone = "+92" + phone;
        } else {
            phone = "+92" + phone.substring(1);
        }
        final String finalPhone = phone;
        AndroidNetworking.post(URLTutor.URL_LOGIN)
                .addBodyParameter("phoneno", phone)
                .addBodyParameter("pass", tutpass.getText().toString())
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
                        if(!error)
                        {
                            SendRegistrationTokenFCM.sendRegistrationToServer(Login.this, FirebaseInstanceId.getInstance().getToken(), finalPhone);
                            Toast.makeText(Login.this,""+message,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this,MainHomeScreenTutor.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Login.this,""+message,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
    ///////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onPause() {
        super.onPause();
        savePreferences();
    }
    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }
    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = String.valueOf(tutname.getText());
        PasswordValue = String.valueOf(tutpass.getText());
        System.out.println("onPause save name: " + UnameValue);
        System.out.println("onPause save password: " + PasswordValue);
        editor.putString(PREF_UNAME, String.valueOf(UnameValue));
        editor.putString(PREF_PASSWORD, String.valueOf(PasswordValue));
        editor.commit();

    }
    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        PasswordValue = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        tutname.setText(UnameValue);
        tutpass.setText(PasswordValue);
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }
///////////////////////////////////////////////////////////////////////////////////
}
