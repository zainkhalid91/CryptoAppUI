package com.wallet.yukoni.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wallet.yukoni.R;
import com.wallet.yukoni.animations.ExapandAnim;

public class SignUpActivity extends AppCompatActivity {

    Button btn_register;
    ImageView btn_back_signup;
    ConstraintLayout constraintLayoutSignUp;
    EditText name, email, password, cpassword;
    TextView header1, header2, header3;
    // int roleId = 2;
    Animation animation;
    ConstraintLayout constrationLayout_fields;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //disable keyboard on activity start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();


        /*----------------Animations--------------*/


        animations();

        /*--------------------*-------------------*/

        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        cpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btn_register.setOnClickListener(v -> {
            String email_txt = email.getText().toString().trim();

            if (name.getText().toString().isEmpty()) {
                name.setError("Please fill in required field");
                return;
            }
            if (email.getText().toString().isEmpty()) {
                email.setError("Please fill in required field");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email_txt).matches()) {
                email.setError("Enter a valid email");
                email.requestFocus();
                return;
            }

            if (password.getText().toString().equals(cpassword.getText().toString())) {

                Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
                startActivity(intent);
                Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                cpassword.setError("Password does not match", null);
            }
            if (password.getText().toString().length() < 8) {
                password.setError("Password must be 8 characters long", null);
            }
        });

        btn_back_signup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void init() {
        name = findViewById(R.id.name_editText);
        email = findViewById(R.id.email_editText);
        password = findViewById(R.id.password_editText);
        cpassword = findViewById(R.id.confirmPassword_editText);
        btn_register = findViewById(R.id.btn_register);
        constraintLayoutSignUp = findViewById(R.id.constraintLayoutSignUp);
        btn_back_signup = findViewById(R.id.btn_back_signup);
        header1 = findViewById(R.id.header1);
        header2 = findViewById(R.id.header_);
        header3 = findViewById(R.id.header2);
        constrationLayout_fields = findViewById(R.id.constrationLayout_fields);
    }


    private void animations() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_top_slow);
        header1.setAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_left_slow);
        header2.setAnimation(animation);


        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_right_far_distance);
        header3.setAnimation(animation);


        ExapandAnim.expand(constrationLayout_fields, 1000, 800);


        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_bottom);
        btn_register.setAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
        startActivity(intent);
        finish();
    }

}

