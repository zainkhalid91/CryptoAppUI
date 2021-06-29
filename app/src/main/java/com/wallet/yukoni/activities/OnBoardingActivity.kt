package com.wallet.yukoni.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wallet.yukoni.R

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var logoImageView: ImageView
    private lateinit var animation: Animation
    private lateinit var terms: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        login = findViewById(R.id.btn_login_onboarding)
        signup = findViewById(R.id.btn_signup)
        logoImageView = findViewById(R.id.logo_image_view)
        terms = findViewById(R.id.terms)

        /*<--------------Animations-------------*/animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.xy_axis)
        logoImageView.animation = animation
        animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.enter_from_left_slow)
        login.animation = animation
        animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.enter_from_right_slow)
        signup.animation = animation
        val animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.enter_from_bottom)
        terms.animation = animation


        /*--------------------*---------------------*/login.setOnClickListener { v: View? ->
            val intenti = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intenti)
            finish()
        }
        signup.setOnClickListener { v: View? ->
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}