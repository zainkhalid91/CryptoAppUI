package com.wallet.yukoni.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wallet.yukoni.R
import de.hdodenhof.circleimageview.CircleImageView

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.MyViewHolder?>() {
    var context: Context? = null

    /*    public TransactionAdapter(ArrayList<TransactionDetail> list, Context context) {
        this.transactionDetails = list;
        this.context = context;
    }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {

//your code here
        } catch (ignored: Exception) {
            Log.d("", "onBindViewHolder: $ignored")
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var coin_name: TextView? = itemView.findViewById(R.id.coin_name)
        var sent_to_TextView: TextView? = itemView.findViewById(R.id.sent_to_TextView)
        var amount_textView: TextView? = itemView.findViewById(R.id.amout_textView)
        private var usd_TextView: TextView? = itemView.findViewById(R.id.usd_TextView)
        private var txtCategory: TextView? = itemView.findViewById(R.id.txtCategory)
        private var date_textView: TextView? = itemView.findViewById(R.id.date_textView)
        private var time_textView: TextView? = itemView.findViewById(R.id.time_textView)
        var coin_Img: CircleImageView? = itemView.findViewById(R.id.coin_Img)

    }
}