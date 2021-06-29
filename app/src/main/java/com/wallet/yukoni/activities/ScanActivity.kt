package com.wallet.yukoni.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.wallet.yukoni.fragments.CoinTransferFragment
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this) // Programmatically initialize the scanner view
        setContentView(mScannerView) // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView?.startCamera() // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera() // Stop camera on pause
    }

    override fun handleResult(rawResult: Result?) {
        // Do something with the result here
        if (rawResult != null) {
            Log.v(TAG, rawResult.text)
        } // Prints scan results
        val v = Log.v(TAG, rawResult?.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)
        this.mScannerView?.stopCamera()
        val returnIntent = Intent()
        if (rawResult != null) {
            returnIntent.putExtra("result", rawResult.text)
        }
        setResult(12, returnIntent)
        if (rawResult != null) {
            CoinTransferFragment.getInstance()?.coin = rawResult.text
        }
        finish()


        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }

    companion object {
        private const val TAG: String = ""
    }
}