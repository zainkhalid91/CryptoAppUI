package com.wallet.yukoni.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wallet.yukoni.R;
import com.wallet.yukoni.fragments.BalanceFragment;
import com.wallet.yukoni.fragments.SettingsFragment;
import com.wallet.yukoni.fragments.TransactionFragment;
import com.wallet.yukoni.utils.SessionManager;
import com.wallet.yukoni.utils.SingletonClass;

public class MainActivity extends AppCompatActivity {
    /* TextView titletext;
     RecyclerView recyclerView;
     RecylerAdapter adapter;*/
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        String token = new SessionManager(getApplicationContext()).getToken();
        SingletonClass.gettoken = token;
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.action_item1:
                    selectedFragment = BalanceFragment.getInstance();
                    break;
                case R.id.action_item2:
                    selectedFragment = TransactionFragment.getInstance();
                    break;
                case R.id.action_item3:
                    selectedFragment = SettingsFragment.getInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main, selectedFragment);
            transaction.commit();
            return true;
        });


        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, BalanceFragment.getInstance());
        transaction.commit();

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
