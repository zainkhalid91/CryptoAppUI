package com.wallet.yukoni.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView.OnEditorActionListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.wallet.yukoni.R
import com.wallet.yukoni.activities.ScanActivity
import com.wallet.yukoni.utils.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CoinTransferFragment : Fragment() {
    var coin: String? = null
    private lateinit var back_imgView: ImageView
    private lateinit var qr_Scan: ImageView
    private lateinit var fragmentcoinLayout: ConstraintLayout
    private lateinit var fragment_coin_tranfer_layoutt: ConstraintLayout
    private lateinit var sessionManager: SessionManager
    private lateinit var coin_type_spinner: Spinner
    private lateinit var fee_EditText: TextView
    private lateinit var total_editText: TextView
    private lateinit var eth_remaining_textView: TextView
    var fragment_coin_tranfer_layout: ConstraintLayout? = null
    private lateinit var resipent_address_token: EditText
    private var amount_editText: EditText? = null
    private var sendTransaction: Button? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coin_transfer, container, false)
        back_imgView = view.findViewById(R.id.back_imgView)
        fragmentcoinLayout = activity?.findViewById(R.id.activity_main)!!
        qr_Scan = view.findViewById(R.id.qr_Scan)
        coin_type_spinner = view.findViewById(R.id.coin_type_spinner)
        fragment_coin_tranfer_layoutt = view.findViewById(R.id.fragment_coin_tranfer_layoutt)
        resipent_address_token = view.findViewById(R.id.resipent_address_token)
        amount_editText = view.findViewById(R.id.amount_editText)
        sendTransaction = view.findViewById(R.id.btn_send_transaction)
        fee_EditText = view.findViewById(R.id.fee_EditText)
        eth_remaining_textView = view.findViewById(R.id.eth_remaining_textView)
        total_editText = view.findViewById(R.id.total_editText)
        sessionManager = SessionManager(this.requireContext())
        resipent_address_token.setText(coin)
        eth_remaining_textView.text = sessionManager.coinBalance
        clickListeners()
        spinnerListener()
        return view
    }

    private fun spinnerListener() {
        val tmp = ArrayList<String?>()
        tmp.add("G50")
        tmp.add("Bitcoin")
        coin_type_spinner.adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_dropdown_item, tmp)
        coin_type_spinner.setSelection(1)
        coin_type_spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                //todo fee is not correct
                (view as TextView?)?.setTextColor(Color.WHITE)
                val i = parent?.selectedItemPosition
                fee_EditText.text = "0.25 cents"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun clickListeners() {
        eth_remaining_textView.setOnClickListener {
            val v1 = fee_EditText.text.toString().toFloat()
            val v2 = eth_remaining_textView.text.toString().toFloat()
            amount_editText?.setText((v2 - v1).toString())
        }
        back_imgView.setOnClickListener { v: View? ->
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.replace(R.id.fragment_coin_tranfer_layoutt, CoinFragment())
            //          transaction.addToBackStack(null);
            fragment_coin_tranfer_layout?.removeAllViews()
            transaction.commit()
        }
        qr_Scan.setOnClickListener {
            val intent = Intent(activity, ScanActivity::class.java)
            startActivityForResult(intent, 12)
        }
        sendTransaction?.setOnClickListener(View.OnClickListener { v: View? ->
            if (resipent_address_token.text.toString().isEmpty() || amount_editText?.text.toString().isEmpty()) {
                resipent_address_token.error = "Please fill required field"
                amount_editText?.error = "Please fill the required field"
                return@OnClickListener
            }
            sendTransaction()
        })
        amount_editText?.setOnEditorActionListener(OnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                total_editText.text = coin_type_spinner.selectedItem.toString() + " " + amount_editText!!.text.toString()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun sendTransaction() {

        // sessionManager.setCoinBalance(sendTransactionRequest.getRemaining().toString());
        view?.let { Snackbar.make(it.findViewById(R.id.fragment_coin_tranfer_layoutt), "Transaction Successful", Snackbar.LENGTH_LONG).show() }
        eth_remaining_textView.text = "4.00"
        val transaction = childFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        transaction.replace(R.id.fragment_coin_tranfer_layoutt, TransactionFragment())
        //transaction.addToBackStack(null);
        fragment_coin_tranfer_layoutt.removeAllViews()
        transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12 && data != null) {
            val result = data.getStringExtra("result")
            if (result != null) {
                resipent_address_token.setText(result)
            } else {
                resipent_address_token.setText(coin)
            }
        }
    }

    companion object {
        private var coinTransferFragment: CoinTransferFragment? = null
        fun getInstance(): CoinTransferFragment? {
            if (coinTransferFragment == null) {
                coinTransferFragment = CoinTransferFragment()
            }
            return coinTransferFragment
        }
    }
}