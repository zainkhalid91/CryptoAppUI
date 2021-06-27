package com.wallet.yukoni.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.wallet.yukoni.R;
import com.wallet.yukoni.interfaces.ApiInterface;
import com.wallet.yukoni.models.BuyTokenRequest;
import com.wallet.yukoni.models.BuyTokenResponse;
import com.wallet.yukoni.models.ConvertCurrency;
import com.wallet.yukoni.models.Values;
import com.wallet.yukoni.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyTokenFragment extends Fragment {

    boolean opened;
    ImageView expandCollapseImageView;
    private ConstraintLayout fragmentcoin, constraintLayoutCalculator;
    private ConvertCurrency convertCurrency;
    private Spinner spinner_converter;
    private float storedValue = 0;
    private Button btn_convert, btn_rec_transaction_token;
    private EditText amountToConvert_EditText, convertedAmountEditText, resipent_address, token_amount_editText;
    private TextView symbol_TextView, feeTextView, total_TextView, total_TextView_SFCC;

    public BuyTokenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_token, container, false);
        ImageView back_imageView = view.findViewById(R.id.back_imgView);
        fragmentcoin = view.findViewById(R.id.fragment_coin_tranfer_layout);
        resipent_address = view.findViewById(R.id.resipent_address);
        spinner_converter = view.findViewById(R.id.spinner_converter);
        total_TextView = view.findViewById(R.id.total_TextView);
        symbol_TextView = view.findViewById(R.id.symbol_TextView);
        btn_convert = view.findViewById(R.id.btn_convert);
        token_amount_editText = view.findViewById(R.id.token_amount_editText);
        amountToConvert_EditText = view.findViewById(R.id.amountToConvert_EditText);
        constraintLayoutCalculator = view.findViewById(R.id.constraintLayoutCalculator);
        total_TextView_SFCC = view.findViewById(R.id.total_TextView_SFCC);
        convertedAmountEditText = view.findViewById(R.id.convertedAmountEditText);
        expandCollapseImageView = view.findViewById(R.id.expandCollapseImageView);
        btn_rec_transaction_token = view.findViewById(R.id.btn_rec_transaction_token);
        ConstraintLayout calculatorView = view.findViewById(R.id.calculatorView);
        convertedAmountEditText.setEnabled(false);
        view.findViewById(R.id.token_amount_editText).hasFocus();
        ApiInterface.getInstance().currencyConverter().enqueue(new Callback<ConvertCurrency>() {
            @Override
            public void onResponse(Call<ConvertCurrency> call, Response<ConvertCurrency> response) {
                if (response.isSuccessful()) {
                    convertCurrency = response.body();
                }
            }

            @Override
            public void onFailure(Call<ConvertCurrency> call, Throwable t) {

            }
        });
        ArrayList<String> tmp = new ArrayList<>();
        //tmp.add("Select");
        tmp.add("ETH");
        tmp.add("KRW");
        spinner_converter.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tmp));
        feeTextView = view.findViewById(R.id.feeTextView);
        feeTextView.setText(Utils.fee);

        back_imageView.setOnClickListener(v -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(R.id.fragment_coin, new CoinFragment());
            //transaction.addToBackStack(null);
            fragmentcoin.removeAllViews();
            transaction.commit();
        });


        spinner_converter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {


                    Values values = convertCurrency.getData();
                    parent.getSelectedItem().equals("ETH");
                    if (parent.getSelectedItem().equals("ETH")) {
                        storedValue = values.getTokenPerEthereum();
                        amountToConvert_EditText.getText().clear();
                        convertedAmountEditText.getText().clear();
                    } else {
                        amountToConvert_EditText.getText().clear();
                        convertedAmountEditText.getText().clear();
                        storedValue = values.getTokenPerKrW();
                    }
                } catch (Exception ignored) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        amountToConvert_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(".")) {
                    if (s.length() > 0) {
                        float i = Float.parseFloat(s.toString());
                        float val = storedValue * i;
                        convertedAmountEditText.setText(String.valueOf(val));
                    }
                    if (amountToConvert_EditText.getText().toString().isEmpty()) {
                        convertedAmountEditText.setText("0");
                    }
                }
            }
        });

        btn_convert.setOnClickListener(v -> {
            if (amountToConvert_EditText.getText().toString().isEmpty()) {
                amountToConvert_EditText.setError("Please enter amount first");
                return;
            }

            float i = Float.parseFloat(amountToConvert_EditText.getText().toString());
            storedValue = storedValue * i;
            convertedAmountEditText.setText(String.valueOf(storedValue));
        });


        btn_rec_transaction_token.setOnClickListener(v -> {
            if (token_amount_editText.getText().toString().isEmpty()) {
                token_amount_editText.setError("Please set amount first");
                return;
            }
            ApiInterface.getInstance().buyToken(new BuyTokenRequest(Float.valueOf(token_amount_editText.getText().toString()), "0x7cF40f247cbB09f87eB0318cC414e5Fe2beCb84f", symbol_TextView.getText().toString()))
                    .enqueue(new Callback<BuyTokenResponse>() {
                        @Override
                        public void onResponse(Call<BuyTokenResponse> call, Response<BuyTokenResponse> response) {

                            if (response.isSuccessful()) {
                                BuyTokenResponse buyTokenResponse = response.body();
                                if (buyTokenResponse.getStatus()) {
                                    Snackbar.make(view.findViewById(R.id.fragment_coin_tranfer_layout), buyTokenResponse.getMsg(), Snackbar.LENGTH_LONG).show();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                                    transaction.replace(R.id.fragment_coin_tranfer_layout, new TransactionFragment());
                                    //transaction.addToBackStack(null);
                                    fragmentcoin.removeAllViews();
                                    transaction.commit();

                                } else {
                                    Snackbar.make(view.findViewById(R.id.fragment_coin_tranfer_layout), buyTokenResponse.getMsg(), Snackbar.LENGTH_LONG).show();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<BuyTokenResponse> call, Throwable t) {

                        }
                    });

        });

        constraintLayoutCalculator.setOnClickListener(v -> {
            if (!opened) {
                calculatorView.setVisibility(View.VISIBLE);
                spinner_converter.getViewTreeObserver().addOnGlobalLayoutListener(() -> ((TextView) spinner_converter.getSelectedView()).setTextColor(Color.WHITE));
                expandCollapseImageView.setImageResource(R.drawable.ic_collapse_black_24dp);


            } else {
                calculatorView.setVisibility(View.GONE);
                expandCollapseImageView.setImageResource(R.drawable.ic_expand_black_24dp);

            }
            opened = !opened;

        });

        token_amount_editText.setOnEditorActionListener((v, actionId, event) -> {
            try {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String a = token_amount_editText.getText().toString();
                    Double fl = Double.valueOf(a) * convertCurrency.getEthToKrw();
                    total_TextView.setText(fl.toString());

                    Float flt = Float.valueOf(a) * convertCurrency.getData().getTokenPerEthereum();
                    total_TextView_SFCC.setText(flt.toString());
                    return true;
                }
            } catch (Exception ignore) {
            }
            return false;
        });


        return view;
    }

}
