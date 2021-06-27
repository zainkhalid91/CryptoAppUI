package com.wallet.yukoni.models

import java.io.Serializable

class TransactionFee(var status: Boolean? = null, var data: ArrayList<Datum>?) : Serializable


class Datum(var symbol: String?, var networkfee: Double? = 0.0)