package com.wallet.yukoni.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.yukoni.R;
import com.wallet.yukoni.adapters.TransactionAdapter;
import com.wallet.yukoni.models.UserTransactions;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {
    private static TransactionFragment transactionFragment;
    private TransactionAdapter transactionAdapter;
    private UserTransactions userTransactions;
    private RecyclerView transaction_recylerView;

    public static TransactionFragment getInstance() {
        if (transactionFragment == null) {
            transactionFragment = new TransactionFragment();
        }
        return transactionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        transaction_recylerView = view.findViewById(R.id.transaction_recylerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        transaction_recylerView.setLayoutManager(mLayoutManager);
        transaction_recylerView.setItemAnimator(new DefaultItemAnimator());


        transactionAdapter = new TransactionAdapter();
        transaction_recylerView.setAdapter(transactionAdapter);


        return view;
    }

}
