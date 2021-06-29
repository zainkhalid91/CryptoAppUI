package com.wallet.yukoni.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.wallet.yukoni.R;
import com.wallet.yukoni.utils.SessionManager;
import com.wallet.yukoni.utils.Utils;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CoinFragment extends Fragment {
    private static CoinFragment coinFragment;
    private Button btnSend, btnReceive;
    private ImageView back_imageView;
    private ConstraintLayout fragmentcoin;
    private TextView coinBalance, name_TextView, symbol_TextView, dollar_Tv, toolbar_textview;
    private SessionManager sessionManager;
    private ImageView coin_imageView;

    public static CoinFragment getInstance() {
        if (coinFragment == null) {
            coinFragment = new CoinFragment();
        }
        return coinFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coin, container, false);
        // GetBalanceResponse model = (GetBalanceResponse) getArguments().getSerializable("model");

        btnSend = view.findViewById(R.id.btn_send);
        btnReceive = view.findViewById(R.id.btn_receive);
        fragmentcoin = view.findViewById(R.id.fragment_coin);
        back_imageView = view.findViewById(R.id.back_imageView);
        coinBalance = view.findViewById(R.id.coin_balance);
        coin_imageView = view.findViewById(R.id.coin_imageView);
        symbol_TextView = view.findViewById(R.id.symbol_TextView);
        name_TextView = view.findViewById(R.id.name_TextView);
        dollar_Tv = view.findViewById(R.id.dollar_Tv);
        toolbar_textview = view.findViewById(R.id.toolbar_textview);

        sessionManager = new SessionManager(getContext());


        btnSend.setOnClickListener(v -> {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            if (btnSend.getText().toString().equals("Transfer")) {
                transaction.replace(R.id.fragment_coin, new TokenTransferFragment());
                //transaction.addToBackStack(null);
            } else {
                transaction.replace(R.id.fragment_coin, new CoinTransferFragment());

            }
            fragmentcoin.removeAllViews();
            transaction.commit();
        });

        btnReceive.setOnClickListener(v -> {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            if (btnReceive.getText().toString().equals("Buy")) {
                transaction.replace(R.id.fragment_coin, new BuyTokenFragment());
                //transaction.addToBackStack(null);

            } else {
                transaction.replace(R.id.fragment_coin, new ReceiveFragment());
            }
            fragmentcoin.removeAllViews();
            transaction.commit();
        });


        back_imageView.setOnClickListener(v -> {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(R.id.fragment_coin, new BalanceFragment());
            //transaction.addToBackStack(null);
            fragmentcoin.removeAllViews();
            transaction.commit();
        });


        coinBalance.setText("5.00");
        try {
            Double bal = 8.00;
            double dollar = bal * Utils.dollarPrice;
            if (Utils.coin) {
                coinBalance.setText(sessionManager.getCoinBalance());
                dollar_Tv.setText("$ " + dollar);
            } else {
                double coinBal = 5.00;
                double coin = coinBal * 158.00;
                coinBalance.setText(sessionManager.getTokenBalance());
                coin_imageView.setImageResource(R.drawable.ic_g50_1);
                name_TextView.setText(R.string.grand50);
                symbol_TextView.setText(R.string.grand50);
                dollar_Tv.setText("$ " + coin);
                btnReceive.setText(R.string.buy);
                btnSend.setText(R.string.transfer);
                toolbar_textview.setText(R.string.tokens);
            }
        } catch (Exception ex) {
            Log.d(TAG, "onCreateView: ");
        }

        return view;
    }
}
