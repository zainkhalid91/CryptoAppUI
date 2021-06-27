package com.wallet.yukoni.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wallet.yukoni.R;

public class OnBoardingActivity extends AppCompatActivity {
    Button login, signup;
    ImageView logoImageView;
    Animation animation;
    TextView terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        login = findViewById(R.id.btn_login_onboarding);
        signup = findViewById(R.id.btn_signup);
        logoImageView = findViewById(R.id.logo_image_view);
        terms = findViewById(R.id.terms);

        /*<--------------Animations-------------*/

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.xy_axis);
        logoImageView.setAnimation(animation);


        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_left_slow);
        login.setAnimation(animation);


        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_right_slow);
        signup.setAnimation(animation);


        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_bottom);
        terms.setAnimation(animation);


        /*--------------------*---------------------*/


        login.setOnClickListener(v -> {
            Intent intenti = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intenti);
            finish();
        });


        signup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
            finish();

        });

    }
}
