package com.wallet.yukoni.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.wallet.yukoni.R
import com.wallet.yukoni.utils.QRCodeGenerator
import com.wallet.yukoni.utils.SessionManager

class ReceiveFragment : Fragment() {
    private lateinit var backImageView: ImageView
    private lateinit var qr_Code_ImageView: ImageView
    private lateinit var constraintLayout_recieveFragment: ConstraintLayout
    private lateinit var textView_tokenString: TextView
    private lateinit var btn_copy_address: Button
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_receive, container, false)
        sessionManager = SessionManager(this.requireContext())
        backImageView = view.findViewById(R.id.backImageView)
        constraintLayout_recieveFragment = view.findViewById(R.id.constraintLayout_recieveFragment)
        textView_tokenString = view.findViewById(R.id.textView_tokenString)
        btn_copy_address = view.findViewById(R.id.btn_copy_address)
        qr_Code_ImageView = view.findViewById(R.id.qr_Code_ImageView)
        backImageView.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.replace(R.id.constraintLayout_recieveFragment, CoinFragment())
            //transaction.addToBackStack(null);
            constraintLayout_recieveFragment.removeAllViews()
            transaction.commit()
        }
        btn_copy_address.setOnClickListener {
            val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Token Address", "91832rjndsvasd98u_098127398ASIUJnxca8q32040982")
            clipboard.setPrimaryClip(clip)
            Snackbar.make(constraintLayout_recieveFragment, "Address copied successfully.", Snackbar.LENGTH_SHORT).show()
        }
        textView_tokenString.text = "91832rjndsvasd98u_098127398ASIUJnxca8q32040982"
        val qrCodeGenerator = QRCodeGenerator(activity)
        qr_Code_ImageView.setImageBitmap(qrCodeGenerator.convertPublicAddressIntoQrCode("91832rjndsvasd98u_098127398ASIUJnxca8q32040982"))
        return view
    }

    /*  companion object {
          private var receiveFragment: ReceiveFragment? = null
          fun getInstance(): ReceiveFragment? {
              if (receiveFragment == null) {
                  receiveFragment = ReceiveFragment()
              }
              return receiveFragment
          }
      }*/
}