package com.wallet.yukoni.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.wallet.yukoni.BuildConfig
import com.wallet.yukoni.R
import com.wallet.yukoni.activities.ChangePassword
import com.wallet.yukoni.activities.OnBoardingActivity
import com.wallet.yukoni.activities.PinCodeActivity
import com.wallet.yukoni.activities.UserProfileActivity
import com.wallet.yukoni.utils.SessionManager

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {
    var sessionManager: SessionManager? = null
    var PRIVATE_MODE = 0
    private var logout_btn: Button? = null
    private var pin_code_btn: Button? = null
    private var userDetails_btn: Button? = null
    private var btn_change_password: Button? = null
    private var version_tv: TextView? = null
    private var layout_settings_fragment: CoordinatorLayout? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        sessionManager = SessionManager(this.requireContext())
        logout_btn = view.findViewById(R.id.button_logout)
        version_tv = view.findViewById(R.id.version_tv)
        layout_settings_fragment = view.findViewById(R.id.layout_settings_fragment)
        pin_code_btn = view.findViewById(R.id.pin_code_btn)
        userDetails_btn = view.findViewById(R.id.user_detail_btn)
        btn_change_password = view.findViewById(R.id.btn_change_password)
        sharedPreferences = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        clickListeners()
        return view
    }

    private fun clickListeners() {
        pin_code_btn?.setOnClickListener { v: View? ->
            val intent = Intent(context, PinCodeActivity::class.java)
            startActivity(intent)
        }
        logout_btn?.setOnClickListener {
            sessionManager?.setLoggined(false)
            sessionManager?.setLogOut(true)
            sharedPreferences?.edit()?.remove(PREF_NAME)?.apply()
            val logout = Intent(context, OnBoardingActivity::class.java)
            logout.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(logout)
            activity?.finish()
        }
        userDetails_btn?.setOnClickListener { startActivity(Intent(activity, UserProfileActivity::class.java)) }
        version_tv?.text = "v" + BuildConfig.VERSION_NAME + " build " + BuildConfig.VERSION_CODE
        btn_change_password?.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(context, ChangePassword::class.java)
            startActivity(intent)
        })
    }

    companion object {
        private val PREF_NAME: String? = "Yukoni"
        private var settingsFragment: SettingsFragment? = null
        fun getInstance(): SettingsFragment? {
            if (settingsFragment == null) {
                settingsFragment = SettingsFragment()
            }
            return settingsFragment
        }
    }
}