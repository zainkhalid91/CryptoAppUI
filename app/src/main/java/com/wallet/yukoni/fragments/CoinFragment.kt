package com.wallet.yukoni.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.Fragment
import com.wallet.yukoni.R
import com.wallet.yukoni.utils.SessionManager
import com.wallet.yukoni.utils.Utils

class CoinFragment : Fragment() {
    private lateinit var btnSend: Button
    private lateinit var btnReceive: Button
    private lateinit var back_imageView: ImageView
    private lateinit var fragmentcoin: ConstraintLayout
    private lateinit var coinBalance: TextView
    private lateinit var name_TextView: TextView
    private lateinit var symbol_TextView: TextView
    private lateinit var dollar_Tv: TextView
    private lateinit var toolbar_textview: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var coin_imageView: ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coin, container, false)
        // GetBalanceResponse model = (GetBalanceResponse) getArguments().getSerializable("model");
        btnSend = view.findViewById(R.id.btn_send)
        btnReceive = view.findViewById(R.id.btn_receive)
        fragmentcoin = view.findViewById(R.id.fragment_coin)
        back_imageView = view.findViewById(R.id.back_imageView)
        coinBalance = view.findViewById(R.id.coin_balance)
        coin_imageView = view.findViewById(R.id.coin_imageView)
        symbol_TextView = view.findViewById(R.id.symbol_TextView)
        name_TextView = view.findViewById(R.id.name_TextView)
        dollar_Tv = view.findViewById(R.id.dollar_Tv)
        toolbar_textview = view.findViewById(R.id.toolbar_textview)
        sessionManager = SessionManager(this.requireContext())
        btnSend.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            if (btnSend.text.toString() == "Transfer") {
                transaction.replace(R.id.fragment_coin, TokenTransferFragment())
                //transaction.addToBackStack(null);
            } else {
                transaction.replace(R.id.fragment_coin, CoinTransferFragment())
            }
            fragmentcoin.removeAllViews()
            transaction.commit()
        }
        btnReceive.setOnClickListener(fun(it: View) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            if (btnReceive.text.toString() == "Buy") {
                transaction.replace(R.id.fragment_coin, BuyTokenFragment())
                //transaction.addToBackStack(null);
            } else {
                transaction.replace(R.id.fragment_coin, ReceiveFragment())
            }
            fragmentcoin.removeAllViews()
            transaction.commit()
        })
        back_imageView.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.replace(R.id.fragment_coin, BalanceFragment())
            //transaction.addToBackStack(null);
            fragmentcoin.removeAllViews()
            transaction.commit()
        }
        coinBalance.text = "5.00"
        try {
            val bal = 8.00
            val dollar = bal * Utils.dollarPrice!!
            if (Utils.coin == true) {
                coinBalance.text = sessionManager.coinBalance
                dollar_Tv.text = "$ $dollar"
            } else {
                val coinBal = 5.00
                val coin = coinBal * 158.00
                coinBalance.text = sessionManager.tokenBalance
                coin_imageView.setImageResource(R.drawable.ic_g50_1)
                name_TextView.setText(R.string.grand50)
                symbol_TextView.setText(R.string.grand50)
                dollar_Tv.text = "$ $coin"
                btnReceive.setText(R.string.buy)
                btnSend.setText(R.string.transfer)
                toolbar_textview.setText(R.string.tokens)
            }
        } catch (ex: Exception) {
            Log.d(Constraints.TAG, "onCreateView: ")
        }
        return view
    }

    /*  companion object {
          private var coinFragment: CoinFragment? = null
          fun getInstance(): CoinFragment? {
              if (coinFragment == null) {
                  coinFragment = CoinFragment()
              }
              return coinFragment
          }
      }*/
}