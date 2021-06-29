package com.wallet.yukoni.activities

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.wallet.yukoni.R
import com.wallet.yukoni.utils.*

class PinCodeActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var pincode_layout: ConstraintLayout
    private lateinit var pincode_switch: SwitchCompat
    private lateinit var btnback: ImageView
    private lateinit var updatePincode: RelativeLayout
    private lateinit var pin_code: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code)
        sessionManager = SessionManager(applicationContext)
        pincode_layout = findViewById(R.id.layout_pinCode)
        pincode_switch = findViewById(R.id.pincode_switch)
        btnback = findViewById(R.id.btnback)
        updatePincode = findViewById(R.id.updatePincode)
        pincode_switch.isChecked = false
        pincode_switch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->

            if (isChecked) {
                dialogShow(R.layout.pincode_custom_dialog)
            } else {
                dialogShow(R.layout.pincode_dialog_password)
            }
        }
        btnback.setOnClickListener { v: View? -> finish() }
        updatePincode.setOnClickListener { dialogShow(R.layout.pincode_upate_dialog) }
    }

    private fun dialogShow(layout: Int) {
        val dialog = Dialog(this@PinCodeActivity)
        // Include dialog.xml file
        dialog.setContentView(layout)

        // set values for custom dialog components - text, image and button


        //pin_code.setText("Custom dialog Android example.");

        //   pin_code.setText("Custom dialog Android example.");
        dialog.show()
        dialog.setCancelable(false)
        if (layout == R.layout.pincode_custom_dialog) {
            val cpinCode = dialog.findViewById<EditText?>(R.id.c_pin_code)
            cpinCode.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_PASSWORD
            cpinCode.transformationMethod = PasswordTransformationMethod.getInstance()
            pin_code = dialog.findViewById(R.id.pin_code)
            pin_code.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_PASSWORD
            pin_code.transformationMethod = PasswordTransformationMethod.getInstance()
            dialog.findViewById<View?>(R.id.btn_set).setOnClickListener { v: View? ->
                pin_code = dialog.findViewById(R.id.pin_code)
                if (pin_code.text.toString().isEmpty()) {
                    pin_code.error = "Enter valid PinCode"
                }
                if (pin_code.text.toString() == cpinCode.text.toString()) {

//                    sessionManager.setToken("29384osdjif829423");
                    Snackbar.make(pincode_layout, "PinCode Set Successfully.", Snackbar.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            dialog.findViewById<View?>(R.id.btn_cancel).setOnClickListener { v: View? ->
                dialog.dismiss()
                pincode_switch.isChecked = false
            }
        }

        //enable pincode
        if (layout == R.layout.enable_pincode_dialog) {
            val enablePinPasswordEditText = dialog.findViewById<EditText?>(R.id.enable_pin_password_editText)
            enablePinPasswordEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_PASSWORD
            enablePinPasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            dialog.findViewById<View?>(R.id.btn_enablePin_set_btn).setOnClickListener { v: View? ->
                if (enablePinPasswordEditText.text.toString().isEmpty()) {
                    enablePinPasswordEditText.error = "Enter a valid pin code"
                    return@setOnClickListener
                }
                Snackbar.make(pincode_layout, "PinCode Set Successfully.", Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialog.findViewById<View?>(R.id.btn_enablePin_cancel_btn).setOnClickListener { v: View? -> dialog.dismiss() }
        }
        if (layout == R.layout.pincode_dialog_password) {
            val password = dialog.findViewById<EditText?>(R.id.pin_password_editText)
            password.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_PASSWORD
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            dialog.findViewById<View?>(R.id.btn_cancel_btn).setOnClickListener { v: View? -> dialog.dismiss() }
            dialog.findViewById<View?>(R.id.btn_set_btn).setOnClickListener { v: View? -> Snackbar.make(pincode_layout, "pin code disabled", Snackbar.LENGTH_LONG).show() }
            dialog.dismiss()
        }
        if (layout == R.layout.pincode_upate_dialog) {
            val pinCodeNew = dialog.findViewById<EditText?>(R.id.pin_code_new)
            pinCodeNew.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_PASSWORD
            pinCodeNew.transformationMethod = PasswordTransformationMethod.getInstance()
            val pinCodeOld = dialog.findViewById<EditText?>(R.id.pin_code_old)
            pinCodeOld.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_PASSWORD
            pinCodeOld.transformationMethod = PasswordTransformationMethod.getInstance()
            dialog.findViewById<View?>(R.id.btn_set_new_pin).setOnClickListener { v: View? ->
                if (pinCodeNew.text.toString().length < 0) {
                    pinCodeNew.error = "Enter valid pin code"
                    return@setOnClickListener
                }
                if (pinCodeNew.text.toString() == pinCodeOld.text.toString()) {
                    pinCodeNew.error = "Please enter new pin code"
                    return@setOnClickListener
                }
                Snackbar.make(pincode_layout, "New pincode set successfully", Snackbar.LENGTH_LONG).show()
            }
            dialog.dismiss()
        }
        dialog.findViewById<View?>(R.id.btn_cancel_change_pin).setOnClickListener { v: View? -> dialog.dismiss() }
    }
}