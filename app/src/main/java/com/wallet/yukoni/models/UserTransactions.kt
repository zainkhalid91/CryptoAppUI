package com.wallet.yukoni.models

import java.io.Serializable

class UserTransactions(var status: Boolean? = false, var msg: String, var data: ArrayList<TransactionDetail>?) : Serializable

class TransactionDetail(var id: Int? = null, var txHash: String? = null, var amount: Double? = null, var to: String? = null, var type: String? = null, var category: String? = null, var status: String? = null,
                        var fee: Double? = null, var networkFee: Any? = null, var createdAt: String? = null, var updatedAt: String? = null, var userId: Int? = null, var tokenId: Int, var token: token? = null)

class token(var symbol: String?, var img: String)