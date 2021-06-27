package com.wallet.yukoni.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wallet.yukoni.R;
import com.wallet.yukoni.utils.SessionManager;

public class UserProfileActivity extends AppCompatActivity {
    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        manager = new SessionManager(this);
        findViewById(R.id.btnback).setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.name_Text);
        TextView email = findViewById(R.id.email_editText_view);
        textView.setText("Your Name ");
        email.setText("Your Email");

    }
}
