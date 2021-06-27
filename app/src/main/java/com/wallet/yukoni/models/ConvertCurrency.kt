package com.wallet.yukoni.models

data class ConvertCurrency(
        val data: Values,
        val status: Boolean,
        val ethToKrw: Double? = 0.0,
        val etherToUSD: Double? = 0.0
)

data class Values(
        val tokenPerEthereum: Float,
        val tokenPerKrW: Float
)