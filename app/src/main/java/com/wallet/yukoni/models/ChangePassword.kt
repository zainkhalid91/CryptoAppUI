package com.wallet.yukoni.models

data class ChangePasswordRequest(
        val newPassword: String?,
        val oldPassword: String?
)


data class ChangePasswordResponse(
        val status: Boolean?,
        val msg: String?
)