package com.kt.countrylist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CountryDao {
    @Query("SELECT * FROM country order by LOWER(country_name) DESC")
    suspend fun getAll(): List<Country?>?

    @Insert
    suspend fun insert(country: Country)

    @Update
    fun update(country: Country?)

    @Query("DELETE FROM country")
    suspend fun deleteAll()

    @Query("SELECT * FROM country")
    fun getAllData(): LiveData<List<Country>>

    @Query("SELECT * FROM country where country_name LIKE :country_name")
    fun getSearch(country_name : String): List<Country?>?
}
