package com.wallet.yukoni.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wallet.yukoni.R
import com.wallet.yukoni.fragments.BalanceFragment
import com.wallet.yukoni.fragments.SettingsFragment
import com.wallet.yukoni.fragments.TransactionFragment
import com.wallet.yukoni.utils.SessionManager
import com.wallet.yukoni.utils.SingletonClass

class MainActivity : AppCompatActivity() {
    /* TextView titletext;
     RecyclerView recyclerView;
     RecylerAdapter adapter;*/
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        val token = SessionManager(applicationContext).token
        SingletonClass.gettoken = token
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem? ->
            var selectedFragment: Fragment? = null
            when (menuItem?.itemId) {
                R.id.action_item1 -> selectedFragment = BalanceFragment.getInstance()
                R.id.action_item2 -> selectedFragment = TransactionFragment.getInstance()
                R.id.action_item3 -> selectedFragment = SettingsFragment.getInstance()
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.activity_main, selectedFragment!!)
            transaction.commit()
            true
        }


        //Manually displaying the first fragment - one time only
        val transaction = supportFragmentManager.beginTransaction()
        BalanceFragment.Companion.getInstance()?.let { transaction.replace(R.id.activity_main, it) }
        transaction.commit()

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

        //For later use in balance fragment
/*
        titletext = findViewById(R.id.title_textview);
        recyclerView = findViewById(R.id.recylerview);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecylerAdapter();
  */
    }
}