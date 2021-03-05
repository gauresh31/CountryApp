package com.kt.countrylist.ui.main.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.kt.countrylist.db.AppDatabase
import com.kt.countrylist.db.Country
import com.kt.countrylist.db.CountryDao


class CountryRepository(application: Application?){
    private var countryDao: CountryDao? = null
    private var allCountries: LiveData<List<Country>>? = null

    init {
        countryDao = application?.let {
            AppDatabase.getDatabase(it)?.countryDao() }
        allCountries = countryDao?.getAllData()
    }

    fun getAllCountry(): LiveData<List<Country>>? {
        return allCountries
    }

    suspend fun insert(country: Country) {
        countryDao?.insert(country)
    }


    private class insertAsyncTask internal constructor(dao: CountryDao?) :
        AsyncTask<Country?, Void?, Void?>() {
        private val mAsyncTaskDao: CountryDao?

//        override fun doInBackground(vararg params: Matches): Void? {
//            mAsyncTaskDao?.insert(params[0])
//            return null
//        }

        init {
            mAsyncTaskDao = dao
        }

        override fun doInBackground(vararg match: Country?): Void? {

            return null
        }
    }
}