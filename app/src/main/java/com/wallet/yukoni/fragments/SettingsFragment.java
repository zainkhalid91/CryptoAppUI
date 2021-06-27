package com.wallet.yukoni.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.wallet.yukoni.BuildConfig;
import com.wallet.yukoni.R;
import com.wallet.yukoni.activities.ChangePassword;
import com.wallet.yukoni.activities.OnBoardingActivity;
import com.wallet.yukoni.activities.PinCodeActivity;
import com.wallet.yukoni.activities.UserProfileActivity;
import com.wallet.yukoni.utils.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private static final String PREF_NAME = "Yukoni";
    private static SettingsFragment settingsFragment;
    SessionManager sessionManager;
    int PRIVATE_MODE = 0;
    private Button logout_btn, pin_code_btn, userDetails_btn, btn_change_password;
    private TextView version_tv;
    private CoordinatorLayout layout_settings_fragment;
    private SharedPreferences sharedPreferences;

    public static SettingsFragment getInstance() {
        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }


        return settingsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        sessionManager = new SessionManager(getContext());

        logout_btn = view.findViewById(R.id.button_logout);
        version_tv = view.findViewById(R.id.version_tv);
        layout_settings_fragment = view.findViewById(R.id.layout_settings_fragment);
        pin_code_btn = view.findViewById(R.id.pin_code_btn);
        userDetails_btn = view.findViewById(R.id.user_detail_btn);
        btn_change_password = view.findViewById(R.id.btn_change_password);
        sharedPreferences = getContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        clickListeners();
        return view;

    }

    private void clickListeners() {
        pin_code_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PinCodeActivity.class);
            startActivity(intent);
        });

        logout_btn.setOnClickListener(v -> {
            sessionManager.setLoggined(false);
            sessionManager.setLogOut(true);
            sharedPreferences.edit().remove(PREF_NAME).apply();
            Intent logout;
            logout = new Intent(getContext(), OnBoardingActivity.class);
            logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logout);
            getActivity().finish();
        });

        userDetails_btn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), UserProfileActivity.class));

        });
        version_tv.setText("v" + BuildConfig.VERSION_NAME + " build " + BuildConfig.VERSION_CODE);


        btn_change_password.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangePassword.class);
            startActivity(intent);
        });
    }

}
