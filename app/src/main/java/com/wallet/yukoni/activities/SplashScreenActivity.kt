package com.wallet.yukoni.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.wallet.yukoni.R
import com.wallet.yukoni.utils.SessionManager

class SplashScreenActivity : AppCompatActivity() {
    //    private lateinit var yukoniTextView: AsyncTextPathView
    private lateinit var fadeIn: Animation
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var logoImageView: ImageView
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sessionManager = SessionManager(applicationContext)
        logoImageView = findViewById(R.id.logo_image_view_splash)

//        yukoniTextView = findViewById(R.id.yukoni_textview);
//        yukoniTextView.startAnimation(0F, 1F);
        constraintLayout = findViewById(R.id.constraintLayout)
        val animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.xy_axis)
        logoImageView.animation = animation
        getRunTimePermissions()
    }

    private fun getRunTimePermissions() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report!!.areAllPermissionsGranted()) {
                            Handler().postDelayed({
                                if (sessionManager.isLoggedIn) {
                                    val intent = Intent(applicationContext, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(applicationContext, OnBoardingActivity::class.java)
                                    startActivity(intent)
                                }
                                finish()
                            }, SPLASH_TIME_OUT.toLong())
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest?>?, token: PermissionToken?) {}
                }).check()
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000
    }
}