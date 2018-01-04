package com.example.saadiqbal.tutorsideapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AfterSplash extends AppCompatActivity {
    Button loginpage,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_splash);
        loginpage = (Button)findViewById(R.id.logg);
        signup = (Button)findViewById(R.id.singup);
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterSplash.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterSplash.this,Signup.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
