package com.wallet.yukoni.activities;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.wallet.yukoni.R;

public class ChangePassword extends AppCompatActivity {
    ImageView btn_back_changePass;
    EditText oldPassword_editText, new_password_editText, confirmNewPassword_editText;
    Button btn_confirmChangePass;
    ConstraintLayout changePasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btn_back_changePass = findViewById(R.id.btn_back_changePass);
        btn_back_changePass = findViewById(R.id.btn_back_changePass);
        oldPassword_editText = findViewById(R.id.oldPassword_editText);
        new_password_editText = findViewById(R.id.new_password_editText);
        confirmNewPassword_editText = findViewById(R.id.confirmNewPassword_editText);
        changePasswordLayout = findViewById(R.id.changePasswordLayout);
        btn_confirmChangePass = findViewById(R.id.btn_confirmChangePass);

        btn_back_changePass.setOnClickListener(v -> finish());


        new_password_editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new_password_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());

        confirmNewPassword_editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmNewPassword_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());

        oldPassword_editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        oldPassword_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());


        btn_confirmChangePass.setOnClickListener(v -> {
//            if (oldPassword_editText.getText().toString().equals(sessionManager.decodedToken())
            if (oldPassword_editText.getText().toString().isEmpty() || new_password_editText.getText().toString().isEmpty() || confirmNewPassword_editText.getText().toString().isEmpty()) {
                new_password_editText.setError("Please fill required fields");
                return;
            }
            if (new_password_editText.getText().toString().length() < 8) {
                new_password_editText.setError("Password must be 8 characters long.");
            }
            if (new_password_editText.getText().toString().equals(confirmNewPassword_editText.getText().toString())) {

                Snackbar.make(changePasswordLayout, "Password changed successfully", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

}