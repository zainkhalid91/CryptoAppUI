package com.wallet.yukoni.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.wallet.yukoni.R;
import com.wallet.yukoni.utils.QRCodeGenerator;
import com.wallet.yukoni.utils.SessionManager;


public class ReceiveFragment extends Fragment {

    private static ReceiveFragment receiveFragment;
    private ImageView backImageView, qr_Code_ImageView;
    private ConstraintLayout constraintLayout_recieveFragment;
    private TextView textView_tokenString;
    private Button btn_copy_address;
    private SessionManager sessionManager;


    public static ReceiveFragment getInstance() {
        if (receiveFragment == null) {
            receiveFragment = new ReceiveFragment();
        }
        return receiveFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receive, container, false);

        sessionManager = new SessionManager(getContext());

        backImageView = view.findViewById(R.id.backImageView);
        constraintLayout_recieveFragment = view.findViewById(R.id.constraintLayout_recieveFragment);
        textView_tokenString = view.findViewById(R.id.textView_tokenString);
        btn_copy_address = view.findViewById(R.id.btn_copy_address);
        qr_Code_ImageView = view.findViewById(R.id.qr_Code_ImageView);

        backImageView.setOnClickListener(v -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(R.id.constraintLayout_recieveFragment, new CoinFragment());
            //transaction.addToBackStack(null);
            constraintLayout_recieveFragment.removeAllViews();
            transaction.commit();
        });

        btn_copy_address.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Token Address", "91832rjndsvasd98u_098127398ASIUJnxca8q32040982");
            clipboard.setPrimaryClip(clip);
            Snackbar.make(constraintLayout_recieveFragment, "Address copied successfully.", Snackbar.LENGTH_SHORT).show();

        });

        textView_tokenString.setText("91832rjndsvasd98u_098127398ASIUJnxca8q32040982");

        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator(getActivity());

        qr_Code_ImageView.setImageBitmap(qrCodeGenerator.convertPublicAddressIntoQrCode("91832rjndsvasd98u_098127398ASIUJnxca8q32040982"));

        return view;
    }

}
