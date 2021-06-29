package com.wallet.yukoni.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView.OnEditorActionListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.wallet.yukoni.R
import com.wallet.yukoni.utils.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class BuyTokenFragment : Fragment() {
    var opened = false
    private lateinit var expandCollapseImageView: ImageView
    private lateinit var fragmentcoin: ConstraintLayout
    private lateinit var constraintLayoutCalculator: ConstraintLayout
    private lateinit var spinnerConverter: Spinner
    private var storedValue = 0f
    private lateinit var btnConvert: Button
    private lateinit var btnRecTransactionToken: Button
    private lateinit var amountToConvert_EditText: EditText
    private lateinit var convertedAmountEditText: EditText
    private lateinit var resipent_address: EditText
    private lateinit var token_amount_editText: EditText
    private lateinit var symbol_TextView: TextView
    private lateinit var feeTextView: TextView
    private lateinit var total_TextView: TextView
    private lateinit var total_TextView_SFCC: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_buy_token, container, false)
        val back_imageView = view.findViewById<ImageView?>(R.id.back_imgView)
        fragmentcoin = view.findViewById(R.id.fragment_coin_tranfer_layout)
        resipent_address = view.findViewById(R.id.resipent_address)
        spinnerConverter = view.findViewById(R.id.spinner_converter)
        total_TextView = view.findViewById(R.id.total_TextView)
        symbol_TextView = view.findViewById(R.id.symbol_TextView)
        btnConvert = view.findViewById(R.id.btn_convert)
        token_amount_editText = view.findViewById(R.id.token_amount_editText)
        amountToConvert_EditText = view.findViewById(R.id.amountToConvert_EditText)
        constraintLayoutCalculator = view.findViewById(R.id.constraintLayoutCalculator)
        total_TextView_SFCC = view.findViewById(R.id.total_TextView_SFCC)
        convertedAmountEditText = view.findViewById(R.id.convertedAmountEditText)
        expandCollapseImageView = view.findViewById(R.id.expandCollapseImageView)
        btnRecTransactionToken = view.findViewById(R.id.btn_rec_transaction_token)
        val calculatorView: ConstraintLayout = view.findViewById(R.id.calculatorView)
        convertedAmountEditText.isEnabled = false
        view.findViewById<View?>(R.id.token_amount_editText).hasFocus()
        val tmp = ArrayList<String?>()
        //tmp.add("Select");
        tmp.add("ETH")
        tmp.add("KRW")
        spinnerConverter.adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_dropdown_item, tmp)
        feeTextView = view.findViewById(R.id.feeTextView)
        feeTextView.text = Utils.fee
        back_imageView.setOnClickListener { v: View? ->
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.replace(R.id.fragment_coin, CoinFragment())
            //transaction.addToBackStack(null);
            fragmentcoin.removeAllViews()
            transaction.commit()
        }
        spinnerConverter.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    if (parent?.selectedItem == "ETH") {
                        amountToConvert_EditText.text.clear()
                        convertedAmountEditText.text.clear()
                    } else {
                        amountToConvert_EditText.text.clear()
                        convertedAmountEditText.text.clear()
                    }
                } catch (ignored: Exception) {
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        amountToConvert_EditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != ".") {
                    if (s?.length!! > 0) {
                        val i = s.toString().toFloat()
                        val `val` = storedValue * i
                        convertedAmountEditText.setText(`val`.toString())
                    }
                    if (amountToConvert_EditText.text.toString().isEmpty()) {
                        convertedAmountEditText.setText("0")
                    }
                }
            }
        })
        btnConvert.setOnClickListener(View.OnClickListener { v: View? ->
            if (amountToConvert_EditText.text.toString().isEmpty()) {
                amountToConvert_EditText.error = "Please enter amount first"
                return@OnClickListener
            }
            val i = amountToConvert_EditText.text.toString().toFloat()
            storedValue = storedValue * i
            convertedAmountEditText.setText(storedValue.toString())
        })
        btnRecTransactionToken.setOnClickListener(View.OnClickListener { v: View? ->
            if (token_amount_editText.text.toString().isEmpty()) {
                token_amount_editText.error = "Please set amount first"
                return@OnClickListener
            }
            Snackbar.make(view.findViewById(R.id.fragment_coin_tranfer_layout), "Successful", Snackbar.LENGTH_LONG).show()
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.replace(R.id.fragment_coin_tranfer_layout, TransactionFragment())
            //transaction.addToBackStack(null);
            fragmentcoin.removeAllViews()
            transaction.commit()
        })
        constraintLayoutCalculator.setOnClickListener {
            if (!opened) {
                calculatorView.visibility = View.VISIBLE
                spinnerConverter.viewTreeObserver.addOnGlobalLayoutListener { (spinnerConverter.selectedView as TextView).setTextColor(Color.WHITE) }
                expandCollapseImageView.setImageResource(R.drawable.ic_collapse_black_24dp)
            } else {
                calculatorView.visibility = View.GONE
                expandCollapseImageView.setImageResource(R.drawable.ic_expand_black_24dp)
            }
            opened = !opened
        }
        token_amount_editText.setOnEditorActionListener(OnEditorActionListener { v: TextView?, actionId: Int, _: KeyEvent? ->
            try {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val a = token_amount_editText.text.toString()
                    return@OnEditorActionListener true
                }
            } catch (ignore: Exception) {
            }
            false
        })
        return view
    }
}