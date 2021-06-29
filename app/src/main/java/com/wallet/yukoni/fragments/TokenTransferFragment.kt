package com.wallet.yukoni.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.wallet.yukoni.R
import com.wallet.yukoni.activities.ScanActivity
import com.wallet.yukoni.utils.*

/**
 * A simple [Fragment] subclass.
 */
class TokenTransferFragment : Fragment() {
    private lateinit var coin: String
    private lateinit var back_imgView: ImageView
    private lateinit var qr_Scan: ImageView
    private lateinit var fragmentcoinLayout: ConstraintLayout
    private lateinit var sessionManager: SessionManager

    // Spinner coin_type_spinner;
    private lateinit var fee_Text: TextView
    private lateinit var total_token_editText: TextView
    private lateinit var fragment_token_tranfer_layout: ConstraintLayout
    private lateinit var resipent_address_token: EditText
    private lateinit var token_amount_editText: EditText
    private lateinit var btn_send_token: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_token_transfer, container, false)
        back_imgView = view.findViewById(R.id.back_imgView)
        fragmentcoinLayout = view.findViewById(R.id.activity_main)
        qr_Scan = view.findViewById(R.id.qr_Scan)
        // coin_type_spinner = view.findViewById(R.id.coin_type_spinner);
        resipent_address_token = view.findViewById(R.id.resipent_address_token)
        token_amount_editText = view.findViewById(R.id.token_amount_editText)
        btn_send_token = view.findViewById(R.id.btn_send_token)
        fee_Text = view.findViewById(R.id.fee_EditText)
        fragment_token_tranfer_layout = view.findViewById(R.id.fragment_token_tranfer_layout)
        total_token_editText = view.findViewById(R.id.total_token_editText)
        sessionManager = SessionManager(this.requireContext())
        resipent_address_token.setText(coin)
        back_imgView.setOnClickListener(View.OnClickListener { v: View? ->
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.replace(R.id.fragment_token_tranfer_layout, CoinFragment())
            //          transaction.addToBackStack(null);
            fragment_token_tranfer_layout.removeAllViews()
            transaction.commit()
        })
        qr_Scan.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(activity, ScanActivity::class.java)
            startActivityForResult(intent, 12)
        })
        btn_send_token.setOnClickListener(View.OnClickListener { v: View? ->
            if (resipent_address_token.text.toString().isEmpty() || token_amount_editText.text.toString().isEmpty()) {
                resipent_address_token.error = "Please fill required field"
                token_amount_editText.error = "Please fill the required field"
                return@OnClickListener
            }
            sendTransaction()
        })


        /*ApiInterface.getInstance().transactionFee().enqueue(new Callback<TransactionFee>() {
            @Override
            public void onResponse(Call<TransactionFee> call, Response<TransactionFee> response) {
                if (response.isSuccessful()) {
                    transactionFees = response.body();

                    ArrayList<String> tmp = new ArrayList<>();
                    for (Datum fee : transactionFees.getData()) {
                        tmp.add(fee.getSymbol());
                    }
                    coin_type_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tmp));
                }
            }

            @Override
            public void onFailure(Call<TransactionFee> call, Throwable t) {

            }
        });

        coin_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) view).setTextColor(Color.WHITE);
                for (int i = 0; i < transactionFees.getData().size(); i++) {
                    if (parent.getSelectedItem().equals("ETH")) {
                        fee_EditText.setText(transactionFees.getData().get(i).getNetworkfee().toString());
                        break;
                    }

                    if (parent.getSelectedItem().equals("SFCC")) {
                        fee_EditText.setText(transactionFees.getData().get(i).getNetworkfee().toString());
                        Utils.fee = transactionFees.getData().get(i).getNetworkfee().toString();
                        break;

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/token_amount_editText.setOnEditorActionListener(OnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                total_token_editText.text = token_amount_editText.text.toString()
                return@OnEditorActionListener true
            }
            false
        })
        return view
    }

    fun sendTransaction() {
        view?.let { Snackbar.make(it.findViewById(R.id.fragment_token_tranfer_layout), "Success", Snackbar.LENGTH_SHORT).show() }
        val transaction = childFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        transaction.replace(R.id.fragment_token_tranfer_layout, TransactionFragment())
        //transaction.addToBackStack(null);
        fragment_token_tranfer_layout.removeAllViews()
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
        private var tokenTransferFragment: TokenTransferFragment? = null
        fun getInstance(): TokenTransferFragment? {
            if (tokenTransferFragment == null) {
                tokenTransferFragment = TokenTransferFragment()
            }
            return tokenTransferFragment
        }
    }
}