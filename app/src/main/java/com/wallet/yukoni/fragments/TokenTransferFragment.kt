package com.wallet.yukoni.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.wallet.yukoni.R;
import com.wallet.yukoni.activities.ScanActivity;
import com.wallet.yukoni.utils.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TokenTransferFragment extends Fragment {

    private static TokenTransferFragment tokenTransferFragment;
    public String coin;
    ImageView back_imgView, qr_Scan;
    ConstraintLayout fragmentcoinLayout;
    SessionManager sessionManager;
    // Spinner coin_type_spinner;
    TextView fee_Text, total_token_editText;
    ConstraintLayout fragment_token_tranfer_layout;
    View view;
    private EditText resipent_address_token, token_amount_editText;
    private Button btn_send_token;

    public static TokenTransferFragment getInstance() {
        if (tokenTransferFragment == null) {
            tokenTransferFragment = new TokenTransferFragment();
        }
        return tokenTransferFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_token_transfer, container, false);

        back_imgView = view.findViewById(R.id.back_imgView);
        fragmentcoinLayout = getActivity().findViewById(R.id.activity_main);
        qr_Scan = view.findViewById(R.id.qr_Scan);
        // coin_type_spinner = view.findViewById(R.id.coin_type_spinner);
        resipent_address_token = view.findViewById(R.id.resipent_address_token);
        token_amount_editText = view.findViewById(R.id.token_amount_editText);
        btn_send_token = view.findViewById(R.id.btn_send_token);
        fee_Text = view.findViewById(R.id.fee_EditText);
        fragment_token_tranfer_layout = view.findViewById(R.id.fragment_token_tranfer_layout);
        total_token_editText = view.findViewById(R.id.total_token_editText);
        sessionManager = new SessionManager(getContext());

        resipent_address_token.setText(coin);

        back_imgView.setOnClickListener(v -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(R.id.fragment_token_tranfer_layout, new CoinFragment());
//          transaction.addToBackStack(null);
            fragment_token_tranfer_layout.removeAllViews();
            transaction.commit();
        });

        qr_Scan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ScanActivity.class);
            startActivityForResult(intent, 12);
        });

        btn_send_token.setOnClickListener(v -> {
            if (resipent_address_token.getText().toString().isEmpty() || token_amount_editText.getText().toString().isEmpty()) {
                resipent_address_token.setError("Please fill required field");
                token_amount_editText.setError("Please fill the required field");
                return;
            }

                SendTransaction();

        });



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
        });*/


        token_amount_editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                total_token_editText.setText(token_amount_editText.getText().toString());
                return true;
            }
            return false;
        });




        return view;
    }

    void SendTransaction() {

        Snackbar.make(view.findViewById(R.id.fragment_token_tranfer_layout), "Success", Snackbar.LENGTH_SHORT).show();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_token_tranfer_layout, new TransactionFragment());
        //transaction.addToBackStack(null);
        fragment_token_tranfer_layout.removeAllViews();
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
