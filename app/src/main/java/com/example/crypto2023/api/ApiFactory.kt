package com.example.crypto2023.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"
    const val BASE_IMAGE_URL = "https://cryptocompare.com"

    private val retrofit=Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply{level=HttpLoggingInterceptor.Level.BODY}).build())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}