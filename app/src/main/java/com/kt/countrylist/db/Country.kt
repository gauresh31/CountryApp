package com.kt.countrylist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
public class Country : Serializable {
    @PrimaryKey(autoGenerate = true)
    private var id = 0

    @ColumnInfo(name = "country_name")
    private var countryName: String? = null

    @ColumnInfo(name = "capital")
    private var capital: String? = null

    @ColumnInfo(name = "flag")
    private var flag: String? = null

    @ColumnInfo(name = "region")
    private var region : String? = null

    @ColumnInfo(name = "population")
    private var population : String? = null

    @ColumnInfo(name = "blocs_name")
    private var blocsName : String? = null

    /*
    * Getters and Setters
    * */
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getCountryName(): String? {
        return countryName
    }

    fun setCountryName(countryName: String?) {
        this.countryName = countryName
    }

    fun getPopulation(): String? {
        return population
    }

    fun setPopulation(population: String?) {
        this.population = population
    }

    fun getRegion(): String? {
        return region
    }

    fun setRegion(picture: String?) {
        this.region = picture
    }

    fun getCapital(): String? {
        return capital
    }

    fun setCapital(capital: String?) {
        this.capital = capital
    }

    fun getFlag(): String? {
        return flag
    }

    fun setFlag(flag: String?) {
        this.flag = flag
    }

    fun getBlocsName(): String? {
        return blocsName
    }

    fun setBlocsName(blocsName: String?) {
        this.blocsName = blocsName
    }
}