package com.kt.countrylist.data.api

import com.kt.countrylist.data.model.CountryNestedModel
import retrofit2.Response
import retrofit2.http.GET

interface APIClient {

    @GET("/rest/v2/all")
    suspend fun getCountryData(): Array<CountryNestedModel>
}