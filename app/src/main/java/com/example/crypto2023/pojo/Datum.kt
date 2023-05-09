package com.example.crypto2023.pojo

import com.example.crypto2023.pojo.CoinInfo
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class Datum (
    @SerializedName("CoinInfo")
    @Expose
    val coinInfo: CoinInfo? = null
)