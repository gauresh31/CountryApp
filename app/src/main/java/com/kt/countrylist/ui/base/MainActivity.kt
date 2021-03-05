package com.kt.countrylist.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Filter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.countrylist.R
import com.kt.countrylist.data.api.APIAdapter
import com.kt.countrylist.db.AppDatabase
import com.kt.countrylist.db.Country
import com.kt.countrylist.db.CountryDao
import com.kt.countrylist.ui.main.adapter.CountryRecyclerAdapter
import com.kt.countrylist.ui.main.viewmodel.CountryViewModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var country_search: SearchView
    private var countryList: List<Country?>? = null
    private lateinit var countryViewModel: CountryViewModel
    private var countryDao: CountryDao? = null
    private lateinit var countryAdapter: CountryRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private var dbClient: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(R.layout.activity_main)
        init()
        fetchLocalData()
    }

    private fun init() {
        title = getString(R.string.app_title)
        dbClient = AppDatabase.getDatabase(this@MainActivity)

        recyclerView = findViewById(R.id.recyclerview_country)
        country_search = findViewById(R.id.country_search)
        countryAdapter = CountryRecyclerAdapter(this@MainActivity)
        recyclerView.adapter = countryAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        countryViewModel.getAllCountry()?.observe(this@MainActivity,
            { _country ->
                CoroutineScope(Dispatchers.Main).launch {
                    countryAdapter.updateData(requireNotNull(_country))
                }
            })
        countryDao = dbClient?.countryDao()

        country_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getFilter().filter(newText)
                }
                return false
            }
        })
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                countryList = countryDao?.getSearch("%$charSearch%")
                val filterResults = FilterResults()
                filterResults.values = countryList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryAdapter.updateData(countryList as List<Country>)
            }
        }
    }

    private fun callAPI(initVal: Int) {

        var progress: ProgressDialog = ProgressDialog(this@MainActivity)
        progress.setMessage("Downloading data......")
        progress.setCancelable(false)
        progress.show()

        GlobalScope.launch(Dispatchers.Main) {
            // Try catch block to handle exceptions when calling the API.
            try {
                val response = APIAdapter.apiClient.getCountryData()
                // Check if response was successful.
                if (response.count() > 0) {
                    progress.dismiss()
//                    val resultSet = response.body()?.data

//                    if (resultSet != null) {
                    for (i in 0 until 100) {
                        val find = Country()
                        val name = response[i].name ?: "N/A"
                        Log.d("email: ", name)
                        find.setCountryName(name)

                        val region = response[i].region ?: "N/A"
                        find.setRegion(region)

                        val flag = response[i].flag ?: ""
                        find.setFlag(flag)

                        val capital = response[i].capital ?: "N/A"
                        find.setCapital(capital)

                        val population = response[i].population ?: "N/A"
                        find.setPopulation(population)

                        if (response[i].regionalBlocs?.count()!! > 0) {
                            val blocs = response[i].regionalBlocs?.get(0)?.name ?: "N/A"
                            find.setBlocsName(blocs)
                        }
                        saveData(find)
                    }
                    if (initVal == 1) {
                        fetchLocalData()
                    }
//                    }
                } else {
                    progress.dismiss()
                    // Show API error.
                    Toast.makeText(
                        this@MainActivity,
                        "Error Occurred: ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                progress.dismiss()
                // Show API error. This is the error raised by the client.
                Toast.makeText(
                    this@MainActivity,
                    "Error Occurred: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private suspend fun saveData(find: Country) {
//        dbClient?.countryDao()?.insert(find)
//        lifecycleScope.launch {
        countryViewModel.insert(find)
//        }
    }

    private fun fetchLocalData() {
        lifecycleScope.launch {
            countryList = countryDao?.getAll()
            checkData()
        }
    }

    private fun checkData() {
        if (null != countryList && countryList!!.isNotEmpty()) {
            countryAdapter.updateData(countryList as List<Country>)
        } else {
            callAPI(1)
        }
    }
}