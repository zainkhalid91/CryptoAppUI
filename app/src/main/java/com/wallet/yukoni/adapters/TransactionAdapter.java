package com.wallet.yukoni.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wallet.yukoni.R;
import com.wallet.yukoni.models.TransactionDetail;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    Context context;
    ArrayList<TransactionDetail> transactionDetails;

/*    public TransactionAdapter(ArrayList<TransactionDetail> list, Context context) {
        this.transactionDetails = list;
        this.context = context;
    }*/

    @NonNull
    @Override
    public TransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_card, parent, false);

        return new TransactionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.MyViewHolder holder, int position) {
        try {

//your code here
        } catch (Exception ignored) {
            Log.d("", "onBindViewHolder: " + ignored);
        }
    }

    @Override
    public int getItemCount() {
        return 3;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView coin_name, sent_to_TextView, amount_textView, usd_TextView, txtCategory, date_textView, time_textView;
        CircleImageView coin_Img;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            coin_name = itemView.findViewById(R.id.coin_name);
            sent_to_TextView = itemView.findViewById(R.id.sent_to_TextView);
            amount_textView = itemView.findViewById(R.id.amout_textView);
            coin_Img = itemView.findViewById(R.id.coin_Img);
            usd_TextView = itemView.findViewById(R.id.usd_TextView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            date_textView = itemView.findViewById(R.id.date_textView);
            time_textView = itemView.findViewById(R.id.time_textView);
        }
    }
}
