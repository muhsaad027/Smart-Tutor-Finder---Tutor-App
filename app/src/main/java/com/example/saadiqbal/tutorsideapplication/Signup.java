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

import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity {
    Button next;
    EditText name,email,pass,username,contact,repass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        repass = (EditText)findViewById(R.id.repassword);
        contact = (EditText)findViewById(R.id.contactno);
        next = (Button)findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validation();
            }
        });
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    public void validation()
    {
        if (name.getText().toString().isEmpty())
        {
            name.setError("Name is required!");
            requestFocus(name);
            return;
        }
        if (email.getText().toString().isEmpty())
        {
            email.setError("Email is required!");
            requestFocus(email);
            return;
        }
        if (pass.getText().toString().isEmpty())
        {
            pass.setError("Password is required!");
            requestFocus(pass);
            return;
        }
        if (repass.getText().toString().isEmpty())
        {
            repass.setError("Re Enter the same Password!");
            requestFocus(repass);
            return;
        }
        if (contact.getText().toString().isEmpty())
        {
            contact.setError("Contact Information is Required!");
            requestFocus(contact);
            return;
        }

        Intent intent = new Intent(Signup.this, signup2.class);
        intent.putExtra("nameT", name.getText().toString());
        intent.putExtra("emailT", email.getText().toString());
        intent.putExtra("contactT", contact.getText().toString());
        intent.putExtra("passT", pass.getText().toString());
        startActivity(intent);
        finish();
    }
}
