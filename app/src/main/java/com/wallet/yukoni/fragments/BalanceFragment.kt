package com.wallet.yukoni.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.wallet.yukoni.R;
import com.wallet.yukoni.utils.SessionManager;
import com.wallet.yukoni.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {
    private static BalanceFragment balanceFragment;
    ConstraintLayout asset_card;
    View tokencard;
    SessionManager sessionManager;
    Double currentbal, ethToDollar, totalInDollars;
    private TextView currentBalance_eth, token_balance_tv, txtDollar;

    public BalanceFragment() {
        // Required empty public constructor
    }

    public static BalanceFragment getInstance() {
        if (balanceFragment == null) {
            balanceFragment = new BalanceFragment();
        }
        return balanceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        asset_card = view.findViewById(R.id.asset_card_balanceFragment);
        currentBalance_eth = view.findViewById(R.id.current_Balance_eth);
        token_balance_tv = view.findViewById(R.id.token_balance_tv);
        tokencard = view.findViewById(R.id.layout_token);
        txtDollar = view.findViewById(R.id.txtDollar);
        sessionManager = new SessionManager(getContext());
        sessionManager.setCoinBalance("8.00");


        asset_card.setOnClickListener(v -> {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            CoinFragment coinFragment = new CoinFragment();

            Utils.coin = true;
            transaction.replace(R.id.constraintLayoutBalance, coinFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });


        tokencard.setOnClickListener(v -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            CoinFragment coinFragment = new CoinFragment();

            Utils.coin = false;
            transaction.replace(R.id.constraintLayoutBalance, coinFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });


        return view;

    }
}
