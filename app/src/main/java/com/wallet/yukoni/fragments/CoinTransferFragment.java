package com.wallet.yukoni.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.wallet.yukoni.R;
import com.wallet.yukoni.activities.ScanActivity;
import com.wallet.yukoni.models.GetBalanceResponse;
import com.wallet.yukoni.models.TransactionFee;
import com.wallet.yukoni.utils.SessionManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoinTransferFragment extends Fragment {

    private static CoinTransferFragment coinTransferFragment;
    public String coin;
    ImageView back_imgView, qr_Scan;
    ConstraintLayout fragmentcoinLayout, fragment_coin_tranfer_layoutt;
    SessionManager sessionManager;
    GetBalanceResponse getBalanceResponse;
    Spinner coin_type_spinner;
    TextView fee_EditText, total_editText, eth_remaining_textView;
    TransactionFee transactionFees;
    View view;
    ConstraintLayout fragment_coin_tranfer_layout;
    private EditText resipent_address_token, amount_editText;
    private Button sendTransaction;

    public static CoinTransferFragment getInstance() {
        if (coinTransferFragment == null) {
            coinTransferFragment = new CoinTransferFragment();
        }
        return coinTransferFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_coin_transfer, container, false);

        back_imgView = view.findViewById(R.id.back_imgView);
        fragmentcoinLayout = getActivity().findViewById(R.id.activity_main);
        qr_Scan = view.findViewById(R.id.qr_Scan);
        coin_type_spinner = view.findViewById(R.id.coin_type_spinner);
        fragment_coin_tranfer_layoutt = view.findViewById(R.id.fragment_coin_tranfer_layoutt);
        resipent_address_token = view.findViewById(R.id.resipent_address_token);
        amount_editText = view.findViewById(R.id.amount_editText);
        sendTransaction = view.findViewById(R.id.btn_send_transaction);
        fee_EditText = view.findViewById(R.id.fee_EditText);
        eth_remaining_textView = view.findViewById(R.id.eth_remaining_textView);
        fragment_coin_tranfer_layout = view.findViewById(R.id.fragment_coin_tranfer_layoutt);
        total_editText = view.findViewById(R.id.total_editText);
        sessionManager = new SessionManager(getContext());
        getBalanceResponse = new GetBalanceResponse();

        resipent_address_token.setText(coin);

        eth_remaining_textView.setText(sessionManager.getCoinBalance());


        clickListeners();
        spinnerListener();


        return view;
    }

    private void spinnerListener() {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("G50");
        tmp.add("Bitcoin");

        coin_type_spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, tmp));
        coin_type_spinner.setSelection(1);


        coin_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //todo fee is not correct
                ((TextView) view).setTextColor(Color.WHITE);
                int i = parent.getSelectedItemPosition();
                fee_EditText.setText("0.25 cents");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void clickListeners() {
        eth_remaining_textView.setOnClickListener(v -> {
            float v1 = Float.parseFloat(fee_EditText.getText().toString());
            float v2 = Float.parseFloat(eth_remaining_textView.getText().toString());
            amount_editText.setText(String.valueOf(v2 - v1));
        });

        back_imgView.setOnClickListener(v -> {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(R.id.fragment_coin_tranfer_layoutt, new CoinFragment());
//          transaction.addToBackStack(null);
            fragment_coin_tranfer_layout.removeAllViews();
            transaction.commit();
        });

        qr_Scan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ScanActivity.class);
            startActivityForResult(intent, 12);
        });

        sendTransaction.setOnClickListener(v -> {
            if (resipent_address_token.getText().toString().isEmpty() || amount_editText.getText().toString().isEmpty()) {
                resipent_address_token.setError("Please fill required field");
                amount_editText.setError("Please fill the required field");
                return;
            }

            SendTransaction();
        });
        amount_editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                total_editText.setText(coin_type_spinner.getSelectedItem() + " " + amount_editText.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void SendTransaction() {

        // sessionManager.setCoinBalance(sendTransactionRequest.getRemaining().toString());
        Snackbar.make(view.findViewById(R.id.fragment_coin_tranfer_layoutt), "Transaction Successful", Snackbar.LENGTH_LONG).show();
        eth_remaining_textView.setText("4.00");
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_coin_tranfer_layoutt, new TransactionFragment());
        //transaction.addToBackStack(null);
        fragment_coin_tranfer_layoutt.removeAllViews();
        transaction.commit();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && data != null) {

            String result = data.getStringExtra("result");
            if (result != null) {
                resipent_address_token.setText(result);
            } else {
                resipent_address_token.setText(coin);
            }
        }
    }
}
