package com.wallet.yukoni.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.wallet.yukoni.R
import com.wallet.yukoni.animations.ExapandAnim

class SignUpActivity : AppCompatActivity() {
    var btn_register: Button? = null
    var btn_back_signup: ImageView? = null
    var constraintLayoutSignUp: ConstraintLayout? = null
    var name: EditText? = null
    var email: EditText? = null
    var password: EditText? = null
    var cpassword: EditText? = null
    var header1: TextView? = null
    var header2: TextView? = null
    var header3: TextView? = null

    // int roleId = 2;
    var animation: Animation? = null
    var constrationLayout_fields: ConstraintLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //disable keyboard on activity start
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        init()


        /*----------------Animations--------------*/animations()

        /*--------------------*-------------------*/password!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        password!!.transformationMethod = PasswordTransformationMethod.getInstance()
        cpassword!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        cpassword!!.transformationMethod = PasswordTransformationMethod.getInstance()
        btn_register!!.setOnClickListener { v: View? ->
            val email_txt = email!!.text.toString().trim { it <= ' ' }
            if (name!!.text.toString().isEmpty()) {
                name!!.error = "Please fill in required field"
                return@setOnClickListener
            }
            if (email!!.text.toString().isEmpty()) {
                email!!.error = "Please fill in required field"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email_txt).matches()) {
                email!!.error = "Enter a valid email"
                email!!.requestFocus()
                return@setOnClickListener
            }
            if (password!!.text.toString() == cpassword!!.text.toString()) {
                val intent = Intent(applicationContext, OnBoardingActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@SignUpActivity, "Sign up successful!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                cpassword!!.setError("Password does not match", null)
            }
            if (password!!.text.toString().length < 8) {
                password!!.setError("Password must be 8 characters long", null)
            }
        }
        btn_back_signup!!.setOnClickListener { v: View? ->
            val intent = Intent(applicationContext, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun init() {
        name = findViewById(R.id.name_editText)
        email = findViewById(R.id.email_editText)
        password = findViewById(R.id.password_editText)
        cpassword = findViewById(R.id.confirmPassword_editText)
        btn_register = findViewById(R.id.btn_register)
        constraintLayoutSignUp = findViewById(R.id.constraintLayoutSignUp)
        btn_back_signup = findViewById(R.id.btn_back_signup)
        header1 = findViewById(R.id.header1)
        header2 = findViewById(R.id.header_)
        header3 = findViewById(R.id.header2)
        constrationLayout_fields = findViewById(R.id.constrationLayout_fields)
    }

    private fun animations() {
        animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.enter_from_top_slow)
        header1!!.animation = animation
        animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.enter_from_left_slow)
        header2!!.animation = animation
        animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.enter_from_right_far_distance)
        header3!!.animation = animation
        ExapandAnim.expand(constrationLayout_fields, 1000, 800)
        animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.enter_from_bottom)
        btn_register!!.animation = animation
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}