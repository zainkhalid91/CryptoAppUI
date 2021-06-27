package com.wallet.yukoni.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.wallet.yukoni.R;
import com.wallet.yukoni.utils.SessionManager;

public class PinCodeActivity extends AppCompatActivity {
    SessionManager sessionManager;
    ConstraintLayout pincode_layout;
    SwitchCompat pincode_switch;
    ImageView btnback;
    RelativeLayout updatePincode;
    EditText pin_code;

    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        sessionManager = new SessionManager(getApplicationContext());
        pincode_layout = findViewById(R.id.layout_pinCode);
        pincode_switch = findViewById(R.id.pincode_switch);
        btnback = findViewById(R.id.btnback);
        updatePincode = findViewById(R.id.updatePincode);

        pincode_switch.setChecked(false);

        pincode_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Create custom dialog object
//            boolean check = sessionManager.decodedToken().getPinEnabled() && sessionManager.decodedToken().getPinCode() != null;
//            boolean check1 = sessionManager.decodedToken().getPinCode().isEmpty();
            if (isChecked) {
                DialogShow(R.layout.pincode_custom_dialog);
            } else {
                DialogShow(R.layout.pincode_dialog_password);
            }

        });


        btnback.setOnClickListener(v -> finish());
        updatePincode.setOnClickListener(v -> DialogShow(R.layout.pincode_upate_dialog));
    }

    void DialogShow(int layout) {
        final Dialog dialog = new Dialog(PinCodeActivity.this);
        // Include dialog.xml file
        dialog.setContentView(layout);

        // set values for custom dialog components - text, image and button


        //pin_code.setText("Custom dialog Android example.");

        //   pin_code.setText("Custom dialog Android example.");


        dialog.show();
        dialog.setCancelable(false);
        if (layout == R.layout.pincode_custom_dialog) {


            EditText cpin_code = dialog.findViewById(R.id.c_pin_code);
            cpin_code.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            cpin_code.setTransformationMethod(PasswordTransformationMethod.getInstance());

            pin_code = dialog.findViewById(R.id.pin_code);
            pin_code.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            pin_code.setTransformationMethod(PasswordTransformationMethod.getInstance());

            dialog.findViewById(R.id.btn_set).setOnClickListener(v -> {
                pin_code = dialog.findViewById(R.id.pin_code);
                if (pin_code.getText().toString().isEmpty()) {
                    pin_code.setError("Enter valid PinCode");
                }
                if (pin_code.getText().toString().equals(cpin_code.getText().toString())) {

//                    sessionManager.setToken("29384osdjif829423");
                    Snackbar.make(pincode_layout, "PinCode Set Successfully.", Snackbar.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            });


            dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
                dialog.dismiss();
                pincode_switch.setChecked(false);
            });

        }

        //enable pincode
        if (layout == R.layout.enable_pincode_dialog) {

            Button enable_btn = dialog.findViewById(R.id.btn_enablePin_set_btn);
            EditText enable_pin_password_editText = dialog.findViewById(R.id.enable_pin_password_editText);
            enable_pin_password_editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            enable_pin_password_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());


            dialog.findViewById(R.id.btn_enablePin_set_btn).setOnClickListener(v -> {
                if (enable_pin_password_editText.getText().toString().isEmpty()) {
                    enable_pin_password_editText.setError("Enter a valid pin code");
                    return;
                }

                Snackbar.make(pincode_layout, "PinCode Set Successfully.", Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
            });


            dialog.findViewById(R.id.btn_enablePin_cancel_btn).setOnClickListener(v -> {
                dialog.dismiss();
                pincode_switch.setChecked(sessionManager.decodedToken().getPinEnabled());
            });

        }

        if (layout == R.layout.pincode_dialog_password) {

            EditText password = dialog.findViewById(R.id.pin_password_editText);
            password.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            dialog.findViewById(R.id.btn_cancel_btn).setOnClickListener(v -> {
                dialog.dismiss();
//                pincode_switch.setChecked(sessionManager.decodedToken().getPinEnabled());
            });

            dialog.findViewById(R.id.btn_set_btn).setOnClickListener(v -> {

                Snackbar.make(pincode_layout, "pin code disabled", Snackbar.LENGTH_LONG).show();

            });
            dialog.dismiss();

        }

        if (layout == R.layout.pincode_upate_dialog) {

            EditText pinCodeNew = dialog.findViewById(R.id.pin_code_new);
            pinCodeNew.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            pinCodeNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
            EditText pinCodeOld = dialog.findViewById(R.id.pin_code_old);
            pinCodeOld.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            pinCodeOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
            dialog.findViewById(R.id.btn_set_new_pin).setOnClickListener(v -> {
                if (pinCodeNew.getText().toString().length() < 0) {
                    pinCodeNew.setError("Enter valid pin code");
                    return;
                }
                if (pinCodeNew.getText().toString().equals(pinCodeOld.getText().toString())) {
                    pinCodeNew.setError("Please enter new pin code");
                    return;
                }

                Snackbar.make(pincode_layout, "New pincode set successfully", Snackbar.LENGTH_LONG).show();
            });
            dialog.dismiss();
        }


        dialog.findViewById(R.id.btn_cancel_change_pin).setOnClickListener(v ->
                dialog.dismiss());


    }
}
