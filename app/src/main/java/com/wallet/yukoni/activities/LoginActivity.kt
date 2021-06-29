package com.wallet.yukoni.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.wallet.yukoni.R;
import com.wallet.yukoni.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    String KEY_USER_TOKEN;
    Button btnlogin;
    ImageView btn_back_login;
    EditText email, password;
    ConstraintLayout constraintLayout_login;
    TextView forgot_password_tv, email_verification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //disable keyboard on activity start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        btnlogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.email_login_editText);
        password = findViewById(R.id.password_login_editText);
        constraintLayout_login = findViewById(R.id.constraintLayout_login);
        btn_back_login = findViewById(R.id.btn_back_login);
        forgot_password_tv = findViewById(R.id.forgot_password_tv);
        email_verification = findViewById(R.id.email_verification);

        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        clickListeners();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
        startActivity(intent);
        finish();
    }


    private void clickListeners() {
        btnlogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                Snackbar.make(constraintLayout_login, "Email/Password Required.", Snackbar.LENGTH_SHORT).show();
                return;
            }
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            sessionManager.setLoggined(true);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        btn_back_login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
            startActivity(intent);
            finish();
        });

        forgot_password_tv.setOnClickListener(v ->
                DialogForgotPassword());

        email_verification.setOnClickListener(v -> {
                    Snackbar.make(constraintLayout_login, getString(R.string.email_verification), Snackbar.LENGTH_LONG).show();
                }

        );
    }

    void DialogForgotPassword() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.forgot_email_dialog);
        EditText confirmEmail_edittext = dialog.findViewById(R.id.cofirmEmail_edittext);
        Button btn_set_btn = dialog.findViewById(R.id.btn_set_btn);
        Button btn_cancel_btn = dialog.findViewById(R.id.btn_cancel_btn);


        btn_set_btn.setOnClickListener(v -> {
            if (confirmEmail_edittext.getText().toString().isEmpty()) {
                confirmEmail_edittext.setError("Please fill required field.");
                return;
            }
            Snackbar.make(constraintLayout_login, "We have send a reset link to your email.", Snackbar.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        btn_cancel_btn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }


}
