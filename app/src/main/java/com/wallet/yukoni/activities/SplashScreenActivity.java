package com.wallet.yukoni.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wallet.yukoni.R;
import com.wallet.yukoni.utils.SessionManager;

import java.util.List;

import yanzhikai.textpath.AsyncTextPathView;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    AsyncTextPathView yukoni_textview;
    Animation fade_in;
    ConstraintLayout constraintLayout;
    ImageView logoImageView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(getApplicationContext());
        logoImageView = findViewById(R.id.logo_image_view_splash);

        //yukoni_textview = findViewById(R.id.yukoni_textview);
        //yukoni_textview.startAnimation(0, 1);

        constraintLayout = findViewById(R.id.constraintLayout);


        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.xy_axis);
        logoImageView.setAnimation(animation);

        getRunTimePermissions();

    }

    private void getRunTimePermissions() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            new Handler().postDelayed(() -> {

                                if (sessionManager.isLoggedIn()) {
                                    Intent intent;
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent;
                                    intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
                                    startActivity(intent);
                                }
                                finish();
                            }, SPLASH_TIME_OUT);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }
}
