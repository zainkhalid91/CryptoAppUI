package com.wallet.yukoni.models

data class ForgotPassword(
        val email: String,
        val msg: String,
        val status: Boolean
)