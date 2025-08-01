package com.verve.bittrack.data.source.network.model

import com.google.gson.annotations.SerializedName

data class CoinResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val desc: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("current_price")
    val price: Double?,
)
