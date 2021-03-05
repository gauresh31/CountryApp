package com.kt.countrylist.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kt.countrylist.db.AppDatabase
import com.kt.countrylist.db.Country
import com.kt.countrylist.db.CountryDao

class CountryViewModel(application: Application) : AndroidViewModel(application) {
    private var mRepository: CountryRepository = CountryRepository(application)
    private val countryDao: CountryDao? = AppDatabase.getDatabase(application)?.countryDao()
    private var countryList: LiveData<List<Country>>? = mRepository.getAllCountry()


    fun getAllCountry(): LiveData<List<Country>>? {
        return countryList
    }

    suspend fun insert(country: Country) {
        mRepository.insert(country)
    }

    fun getSearch() {
        countryDao?.getSearch("")
    }

    fun update(country: Country) {
        countryDao?.update(country)
    }

    suspend fun deleteAll() {
        countryDao?.deleteAll()
    }
}