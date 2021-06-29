package com.wallet.yukoni.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.wallet.yukoni.R
import com.wallet.yukoni.utils.*

class LoginActivity : AppCompatActivity() {
    private lateinit var btnlogin: Button
    private lateinit var btnBackLogin: ImageView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var constraintLayoutLogin: ConstraintLayout
    private var forgotPasswordTv: TextView? = null
    private var emailVerification: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //disable keyboard on activity start
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        btnlogin = findViewById(R.id.btn_login)
        email = findViewById(R.id.email_login_editText)
        password = findViewById(R.id.password_login_editText)
        constraintLayoutLogin = findViewById(R.id.constraintLayout_login)
        btnBackLogin = findViewById(R.id.btn_back_login)
        forgotPasswordTv = findViewById(R.id.forgot_password_tv)
        emailVerification = findViewById(R.id.email_verification)
        password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        password.transformationMethod = PasswordTransformationMethod.getInstance()
        clickListeners()
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clickListeners() {
        btnlogin.setOnClickListener {
            if (TextUtils.isEmpty(email.text.toString()) || TextUtils.isEmpty(password.text.toString())) {
                Snackbar.make(constraintLayoutLogin, "Email/Password Required.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val sessionManager = SessionManager(applicationContext)
            sessionManager.setLoggined(true)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnBackLogin.setOnClickListener {
            val intent = Intent(applicationContext, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
        forgotPasswordTv!!.setOnClickListener { dialogForgotPassword() }
        emailVerification!!.setOnClickListener { Snackbar.make(constraintLayoutLogin, getString(R.string.email_verification), Snackbar.LENGTH_LONG).show() }
    }

    private fun dialogForgotPassword() {
        val dialog = Dialog(this@LoginActivity)
        dialog.setContentView(R.layout.forgot_email_dialog)
        val confirmEmailAddress = dialog.findViewById<EditText?>(R.id.cofirmEmail_edittext)
        val btnSet = dialog.findViewById<Button?>(R.id.btn_set_btn)
        val btnCancel = dialog.findViewById<Button?>(R.id.btn_cancel_btn)
        btnSet.setOnClickListener {
            if (confirmEmailAddress.text.toString().isEmpty()) {
                confirmEmailAddress.error = "Please fill required field."
                return@setOnClickListener
            }
            Snackbar.make(constraintLayoutLogin, "We have send a reset link to your email.", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}