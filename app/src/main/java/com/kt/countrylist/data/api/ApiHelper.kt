package com.kt.countrylist.data.api

class ApiHelper {
    class ApiHelper(private val apiService: APIClient) {
        suspend fun getCountryData() = apiService.getCountryData()
    }
}