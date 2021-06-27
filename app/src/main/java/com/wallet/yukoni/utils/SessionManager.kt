package com.wallet.yukoni.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.wallet.yukoni.models.TokenModel
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets

class SessionManager(_context: Context) {
    // Shared Preferences
    internal var pref: SharedPreferences
    internal var sessionManager: SessionManager? = null
    private var editor: SharedPreferences.Editor

    // Shared pref mode
    private var PRIVATE_MODE = 0

    var coinBalance: String?
        get() = pref.getString(COIN_BALANCE, "")
        set(coinBalance) {
            editor.putString(COIN_BALANCE, coinBalance)
            editor.commit()
        }

    var tokenBalance: String?
        get() = pref.getString(TOKEN_BALANCE, "")
        set(tokenBalance) {
            editor.putString(TOKEN_BALANCE, tokenBalance)
            editor.commit()
        }

    // commit changes
    var token: String?
        get() = pref.getString(KEY_USER_TOKEN, "")
        set(id) {
            editor.putString(KEY_USER_TOKEN, id)
            editor.commit()

            Log.d(TAG, "User login session modified!")
        }

    val isLoggedIn: Boolean
        get() = pref.getBoolean(KEY_IS_LOGGED_IN, false)

    val isLoggedOut: Boolean
        get() = pref.getBoolean(KEY_IS_LOGGED_OUT, false)


    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun decodedToken(): TokenModel? {
        return try {
            val decode = decoded(token)
            val gson = Gson()
            gson.fromJson(decode, TokenModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    fun setLoggined(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.commit()
    }

    fun setLogOut(islogedOut: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_OUT, islogedOut)
        editor.commit()
    }

    fun removeAll() {
        editor.remove(PREF_NAME).apply()
    }

    @Throws(Exception::class)
    internal fun decoded(JWTEncoded: String?): String {
        try {
            val split = JWTEncoded!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]))
            return getJson(split[1])
        } catch (e: UnsupportedEncodingException) {
            //Error
        }

        return ""
    }

    @SuppressLint("NewApi")
    @Throws(UnsupportedEncodingException::class)
    internal fun getJson(strEncoded: String): String {
        val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, StandardCharsets.UTF_8)
    }

    companion object {
        private const val PREF_NAME = "Yukoni"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_IS_LOGGED_OUT = "isLoggedOut"
        private const val KEY_USER_TOKEN = "token"
        private const val PIN_CODE = "pincode"
        private const val PIN_CODE_STATUS = "pincodestatus"
        private const val COIN_BALANCE = "coinBalance"
        private const val TOKEN_BALANCE = "tokenBalance"

        private val TAG = SessionManager::class.java.simpleName
    }


}
