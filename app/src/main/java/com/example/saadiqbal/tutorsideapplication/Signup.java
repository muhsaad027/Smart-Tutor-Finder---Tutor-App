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

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    Button next;
    EditText name,email,pass,username,contact,repass;
    String password,repasscheck,emailstore;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
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
        password = pass.getText().toString();
        repasscheck = repass.getText().toString();
        emailstore = email.getText().toString();
        if (name.getText().toString().isEmpty())
        {
            name.setError("Name is required!");
            requestFocus(name);
            return;
        }
        else if (email.getText().toString().isEmpty())
        {
            email.setError("Email is required!");
            requestFocus(email);
            return;
        }
        else if (!EMAIL_ADDRESS_PATTERN.matcher(emailstore).matches())
        {
            email.setError("Enter Valid Email Address!");
            requestFocus(email);
            return;
        }
        else if (pass.getText().toString().isEmpty())
        {
            pass.setError("Password is required!");
            requestFocus(pass);
            return;
        }
        else if (repass.getText().toString().isEmpty())
        {
            repass.setError("Re Enter the same Password!");
            requestFocus(repass);
            return;
        }
        else if(!password.equals(repasscheck))
        {
            repass.setError("Password Doesn't match");
            pass.setError("Password Doesn't match");
            requestFocus(pass);
            requestFocus(repass);
            return;
        }
        else if (contact.getText().toString().isEmpty())
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
