package com.wallet.yukoni.models

import java.io.Serializable


class GetBalanceResponse(var status: Boolean? = null, var data: Data? = null) : Serializable


class Data(var tokenBalance: Double = 0.0, var balance: Double = 0.0)