package com.wallet.yukoni.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.wallet.yukoni.R
import com.wallet.yukoni.utils.SessionManager
import com.wallet.yukoni.utils.Utils
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class BalanceFragment : Fragment() {
    private lateinit var asset_card: ConstraintLayout
    private lateinit var tokencard: View
    private lateinit var sessionManager: SessionManager
    private var currentbal by Delegates.notNull<Double>()
    var ethToDollar by Delegates.notNull<Double>()
    private var totalInDollars by Delegates.notNull<Double>()
    private lateinit var currentBalance_eth: TextView
    private lateinit var token_balance_tv: TextView
    private lateinit var txtDollar: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_balance, container, false)
        asset_card = view.findViewById(R.id.asset_card_balanceFragment)
        currentBalance_eth = view.findViewById(R.id.current_Balance_eth)
        token_balance_tv = view.findViewById(R.id.token_balance_tv)
        tokencard = view.findViewById(R.id.layout_token)
        txtDollar = view.findViewById(R.id.txtDollar)
        sessionManager = SessionManager(this.requireContext())
        sessionManager.coinBalance = "8.00"
        asset_card.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            val coinFragment = CoinFragment()
            Utils.coin = true
            transaction.replace(R.id.constraintLayoutBalance, coinFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        tokencard.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            val coinFragment = CoinFragment()
            Utils.coin = false
            transaction.replace(R.id.constraintLayoutBalance, coinFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var balanceFragment: BalanceFragment? = null
        fun getInstance(): BalanceFragment? {
            if (balanceFragment == null) {
                balanceFragment = BalanceFragment()
            }
            return balanceFragment
        }
    }
}