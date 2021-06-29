package com.wallet.yukoni.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.view.WindowManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException

class QRCodeGenerator(private val activity: Activity?) {
    fun convertPublicAddressIntoQrCode(qrInputText: String?): Bitmap? {

        //Find screen size
        val manager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4

        //Encode with a QR Code image
        val qrCodeEncoder = QRCodeEncoder(qrInputText,
                null,
                QRContents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension)
        try {
            return qrCodeEncoder.encodeAsBitmap()
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        val TEXT: String? = "TEXT_TYPE"
    }
}