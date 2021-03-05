package com.kt.countrylist.data.model

import androidx.annotation.Keep

@Keep
class CountryNestedModel {

    val name: String? = null
    val region: String? = null
    val flag: String? =null
    val capital: String? = null
    val population: String? = null
    val regionalBlocs: Array<RegionalBlocs>? = null
}