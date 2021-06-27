package com.wallet.yukoni.models

data class SendTokenRequest(
        val to: String?,
        val amount: Double?,
        val symbol: String?
)

data class SendTokenResponse(
        val msg: String?,
        val remaining: Double?,
        val status: Boolean?
)
