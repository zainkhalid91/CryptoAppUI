package com.wallet.yukoni.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wallet.yukoni.R
import com.wallet.yukoni.utils.SessionManager

class UserProfileActivity : AppCompatActivity() {
    var manager: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        manager = SessionManager(this)
        findViewById<View?>(R.id.btnback).setOnClickListener { v: View? -> finish() }
        val textView = findViewById<TextView?>(R.id.name_Text)
        val email = findViewById<TextView?>(R.id.email_editText_view)
        textView.text = "Your Name "
        email.text = "Your Email"
    }
}