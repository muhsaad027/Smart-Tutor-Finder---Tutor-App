package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Authentication extends AppCompatActivity implements View.OnClickListener {


    //SMS Authentication Using FireBase

    Button btn_verify;
    String name, email, password, phone, t_loc,op_tLoc,t_qual,t_days,tu_time; Double tu_lat,tu_long;
    TextView countDown;
    Button verify, resend;
    //SMS Authentication Using FireBase
    EditText mVerificationField;
    private FirebaseAuth mAuth;
    String mVerificationId;
    TextView textView;
    Context context;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String LOGIN = "active";


    private static final String TAG = "PhoneAuthActivity";


    public Authentication() {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sms_auth);
        toolbar.setTitle("Verification");
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            name = (String) bundle.get("nameT");
            phone = (String) bundle.get("contactT");
            email = (String) bundle.get("emailT");
            password = (String) bundle.get("passT");
            t_loc = (String) bundle.get("loc1");
            t_days = (String) bundle.get("techday");
            t_qual = (String) bundle.get("qauli");
            tu_time = (String) bundle.get("time");
            op_tLoc = (String) bundle.get("loc2");
            tu_lat = (Double) bundle.get("lat");
            tu_long = (Double) bundle.get("long");
        }

        //Fast Networking Library
        AndroidNetworking.initialize(getApplicationContext());


        mVerificationField = (EditText) findViewById(R.id.input_pin_code);
        countDown = (TextView) findViewById(R.id.countDown);
        verify = (Button) findViewById(R.id.btn_verify);
        resend = (Button) findViewById(R.id.btn_resend);
        textView = (TextView) findViewById(R.id.textView);

        textView.setText("To Complete Registration \n Enter Pin Code \n which you recived on " + phone);
        btn_verify = (Button) findViewById(R.id.btn_verify);


        verify.setOnClickListener(this);
        resend.setOnClickListener(this);


        //SMS Authentication Using FireBase

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    //Invalid Phone Number
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        Log.d("auth", phone);
//        Toast.makeText(this, "Phone no: " + phone,
//                Toast.LENGTH_LONG).show();

        if (phone.length() == 10) {
            phone = "+92" + phone;
        } else {
            phone = "+92" + phone.substring(1);
        }

        startPhoneNumberVerification(phone);
        Log.d(TAG, phone);

    }

    private void signInWithPhoneAuthCredential( PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();

                            if (isNetworkAvailable()) {
                                datasend();
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "Internet Not Connected",
                                        Snackbar.LENGTH_SHORT).show();
                            }

                            FirebaseAuth.getInstance().signOut();
                        } else {
                            if(task.getException().equals("success"))
                            {
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = task.getResult().getUser();

                                if (isNetworkAvailable()) {
                                    datasend();
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content), "Internet Not Connected",
                                            Snackbar.LENGTH_SHORT).show();
                                }

                                FirebaseAuth.getInstance().signOut();

                            }
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                mVerificationField.setError("Invalid code.");
                            }
                        }
                    }
                });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        timerStart(resend);

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(Authentication.this, MainScreen.class));
            finish();
        }
    }

    public void timerStart(final Button button) {
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDown.setText("Didn't Received Code Resend in: " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                countDown.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verify:
                String code = mVerificationField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerificationField.setError("Cannot be empty.");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);

                break;
            case R.id.btn_resend:
                timerStart(resend);
                resendVerificationCode(phone, mResendToken);
                break;
        }
    }
    public void datasend()
    {

        AndroidNetworking.post(URLTutor.URL_Registration)
                .addBodyParameter("phoneno", phone)
                .addBodyParameter("pass", password)
                .addBodyParameter("name", name)
                .addBodyParameter("email", email)
                .addBodyParameter("loc","aa")
                .addBodyParameter("oloc", "bb")
                .addBodyParameter("qual", t_qual)
                .addBodyParameter("tday", t_days)
                .addBodyParameter("ttime", tu_time)
                .addBodyParameter("lat", tu_lat.toString())
                .addBodyParameter("long", tu_long.toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Authentication.this,""+tu_long+" "+tu_lat,Toast.LENGTH_LONG).show();
                        boolean error = false;
                        String message = "";
                        Log.v("Authentication","" +response);
                        try {
                            message = response.getString("message");
                            error = response.getBoolean("error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if(!error)
                        {
                            Toast.makeText(Authentication.this,""+message,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Authentication.this,Login.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Authentication.this,""+message,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                 Log.v("Authentication","" +error);       // handle error
                    }
                });
    }
}
