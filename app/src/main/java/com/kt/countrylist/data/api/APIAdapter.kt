package com.kt.countrylist.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIAdapter {
    val apiClient: APIClient = Retrofit.Builder()
        .baseUrl("https://restcountries.eu")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIClient::class.java)
}