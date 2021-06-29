package com.wallet.yukoni.utils

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.util.*

class QRCodeEncoder(data: String?, bundle: Bundle?, type: String?, format: String?, dimension: Int) {
    private var dimension = Int.MIN_VALUE
    private var contents: String? = null
    private var displayContents: String? = null
    private var title: String? = null
    private var format: BarcodeFormat? = null
    private var encoded = false
    fun getContents(): String? {
        return contents
    }

    fun getDisplayContents(): String? {
        return displayContents
    }

    fun getTitle(): String? {
        return title
    }

    private fun encodeContents(data: String?, bundle: Bundle?, type: String?, formatString: String?): Boolean {
        // Default to QR_CODE if no format given.
        format = null
        if (formatString != null) {
            try {
                format = BarcodeFormat.valueOf(formatString)
            } catch (iae: IllegalArgumentException) {
                // Ignore it then
            }
        }
        if (format == null || format == BarcodeFormat.QR_CODE) {
            format = BarcodeFormat.QR_CODE
            encodeQRCodeContents(data, bundle, type)
        } else if (data != null && data.isNotEmpty()) {
            contents = data
            displayContents = data
            title = "Text"
        }
        return contents != null && contents!!.isNotEmpty()
    }

    private fun encodeQRCodeContents(data: String?, bundle: Bundle?, type: String?) {
        var data = data
        if (type == QRContents.Type.TEXT) {
            if (data != null && data.length > 0) {
                contents = data
                displayContents = data
                title = "Text"
            }
        } else if (type == QRContents.Type.EMAIL) {
            data = trim(data)
            if (data != null) {
                contents = "mailto:$data"
                displayContents = data
                title = "E-Mail"
            }
        } else if (type == QRContents.Type.PHONE) {
            data = trim(data)
            if (data != null) {
                contents = "tel:$data"
                displayContents = PhoneNumberUtils.formatNumber(data)
                title = "Phone"
            }
        } else if (type == QRContents.Type.SMS) {
            data = trim(data)
            if (data != null) {
                contents = "sms:$data"
                displayContents = PhoneNumberUtils.formatNumber(data)
                title = "SMS"
            }
        } else if (type == QRContents.Type.CONTACT) {
            if (bundle != null) {
                val newContents = StringBuilder(100)
                val newDisplayContents = StringBuilder(100)
                newContents.append("MECARD:")
                val name = trim(bundle.getString(ContactsContract.Intents.Insert.NAME))
                if (name != null) {
                    newContents.append("N:").append(escapeMECARD(name)).append(';')
                    newDisplayContents.append(name)
                }
                val address = trim(bundle.getString(ContactsContract.Intents.Insert.POSTAL))
                if (address != null) {
                    newContents.append("ADR:").append(escapeMECARD(address)).append(';')
                    newDisplayContents.append('\n').append(address)
                }
                val uniquePhones: HashSet<String?>? = QRContents.PHONE_KEYS?.let { HashSet(it.size) }
                for (x in QRContents.PHONE_KEYS?.indices!!) {
                    val phone = trim(bundle.getString(QRContents.PHONE_KEYS[x]))
                    if (phone != null) {
                        uniquePhones?.add(phone)
                    }
                }
                if (uniquePhones != null) {
                    for (phone in uniquePhones) {
                        newContents.append("TEL:").append(escapeMECARD(phone)).append(';')
                        newDisplayContents.append('\n').append(PhoneNumberUtils.formatNumber(phone))
                    }
                }
                val uniqueEmails: HashSet<String?>? = QRContents.EMAIL_KEYS?.let { HashSet(it.size) }
                for (x in QRContents.EMAIL_KEYS?.indices!!) {
                    val email = trim(bundle.getString(QRContents.EMAIL_KEYS[x]))
                    if (email != null) {
                        uniqueEmails?.add(email)
                    }
                }
                if (uniqueEmails != null) {
                    for (email in uniqueEmails) {
                        newContents.append("EMAIL:").append(escapeMECARD(email)).append(';')
                        newDisplayContents.append('\n').append(email)
                    }
                }
                val url = trim(bundle.getString(QRContents.URL_KEY))
                if (url != null) {
                    // escapeMECARD(url) -> wrong escape e.g. http\://zxing.google.com
                    newContents.append("URL:").append(url).append(';')
                    newDisplayContents.append('\n').append(url)
                }
                val note = trim(bundle.getString(QRContents.NOTE_KEY))
                if (note != null) {
                    newContents.append("NOTE:").append(escapeMECARD(note)).append(';')
                    newDisplayContents.append('\n').append(note)
                }

                // Make sure we've encoded at least one field.
                if (newDisplayContents.length > 0) {
                    newContents.append(';')
                    contents = newContents.toString()
                    displayContents = newDisplayContents.toString()
                    title = "Contact"
                } else {
                    contents = null
                    displayContents = null
                }
            }
        } else if (type == QRContents.Type.LOCATION) {
            if (bundle != null) {
                // These must use Bundle.getFloat(), not getDouble(), it's part of the API.
                val latitude = bundle.getFloat("LAT", Float.MAX_VALUE)
                val longitude = bundle.getFloat("LONG", Float.MAX_VALUE)
                if (latitude != Float.MAX_VALUE && longitude != Float.MAX_VALUE) {
                    contents = "geo:$latitude,$longitude"
                    displayContents = "$latitude,$longitude"
                    title = "Location"
                }
            }
        }
    }

    @Throws(WriterException::class)
    fun encodeAsBitmap(): Bitmap? {
        if (!encoded) return null
        var hints: MutableMap<EncodeHintType?, Any?>? = null
        val encoding = guessAppropriateEncoding(contents)
        if (encoding != null) {
            hints = EnumMap(EncodeHintType::class.java)
            hints[EncodeHintType.CHARACTER_SET] = encoding
        }
        val writer = MultiFormatWriter()
        val result = writer.encode(contents, format, dimension, dimension, hints)
        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        // All are 0, or black, by default
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result[x, y]) WHITE else BLACK
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    companion object {
        private const val WHITE = -0x1
        private const val BLACK = 0x00000000
        private fun guessAppropriateEncoding(contents: CharSequence?): String? {
            // Very crude at the moment
            if (contents != null) {
                for (i in contents.indices) {
                    if (contents[i].toInt() > 0xFF) {
                        return "UTF-8"
                    }
                }
            }
            return null
        }

        private fun trim(s: String?): String? {
            if (s == null) {
                return null
            }
            val result = s.trim { it <= ' ' }
            return if (result.isEmpty()) null else result
        }

        private fun escapeMECARD(input: String?): String? {
            if (input == null || input.indexOf(':') < 0 && input.indexOf(';') < 0) {
                return input
            }
            val length = input.length
            val result = StringBuilder(length)
            for (i in 0 until length) {
                val c = input[i]
                if (c == ':' || c == ';') {
                    result.append('\\')
                }
                result.append(c)
            }
            return result.toString()
        }
    }

    init {
        this.dimension = dimension
        encoded = encodeContents(data, bundle, type, format)
    }
}