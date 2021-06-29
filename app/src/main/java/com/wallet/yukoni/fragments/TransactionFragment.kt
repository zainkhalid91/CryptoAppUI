package com.wallet.yukoni.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wallet.yukoni.R
import com.wallet.yukoni.adapters.TransactionAdapter

/**
 * A simple [Fragment] subclass.
 */
class TransactionFragment : Fragment() {
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transaction_recylerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)
        transaction_recylerView = view.findViewById(R.id.transaction_recylerView)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        transaction_recylerView.layoutManager = mLayoutManager
        transaction_recylerView.itemAnimator = DefaultItemAnimator()
        transactionAdapter = TransactionAdapter()
        transaction_recylerView.adapter = transactionAdapter
        return view
    }

    companion object {
        private var transactionFragment: TransactionFragment? = null
        fun getInstance(): TransactionFragment? {
            if (transactionFragment == null) {
                transactionFragment = TransactionFragment()
            }
            return transactionFragment
        }
    }
}