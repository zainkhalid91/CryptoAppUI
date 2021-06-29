package com.wallet.yukoni.activities

import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.wallet.yukoni.R

class ChangePassword : AppCompatActivity() {
    private lateinit var btn_back_changePass: ImageView
    private lateinit var oldPassword_editText: EditText
    private lateinit var new_password_editText: EditText
    private lateinit var confirmNewPassword_editText: EditText
    private lateinit var btn_confirmChangePass: Button
    private lateinit var changePasswordLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        btn_back_changePass = findViewById(R.id.btn_back_changePass)
        btn_back_changePass = findViewById(R.id.btn_back_changePass)
        oldPassword_editText = findViewById(R.id.oldPassword_editText)
        new_password_editText = findViewById(R.id.new_password_editText)
        confirmNewPassword_editText = findViewById(R.id.confirmNewPassword_editText)
        changePasswordLayout = findViewById(R.id.changePasswordLayout)
        btn_confirmChangePass = findViewById(R.id.btn_confirmChangePass)
        btn_back_changePass.setOnClickListener { v: View? -> finish() }
        new_password_editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        new_password_editText.transformationMethod = PasswordTransformationMethod.getInstance()
        confirmNewPassword_editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        confirmNewPassword_editText.transformationMethod = PasswordTransformationMethod.getInstance()
        oldPassword_editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        oldPassword_editText.transformationMethod = PasswordTransformationMethod.getInstance()
        btn_confirmChangePass.setOnClickListener(View.OnClickListener {
            if (oldPassword_editText.text.toString().isEmpty() || new_password_editText.text.toString().isEmpty() || confirmNewPassword_editText.text.toString().isEmpty()) {
                new_password_editText.error = "Please fill required fields"
                return@OnClickListener
            }
            if (new_password_editText.text.toString().length < 8) {
                new_password_editText.error = "Password must be 8 characters long."
            }
            if (new_password_editText.text.toString() == confirmNewPassword_editText.text.toString()) {
                Snackbar.make(changePasswordLayout, "Password changed successfully", Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}