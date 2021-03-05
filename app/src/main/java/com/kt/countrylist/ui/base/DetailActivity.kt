package com.kt.countrylist.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kt.countrylist.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_item_matches.*
import java.text.NumberFormat

class DetailActivity : AppCompatActivity() {

    lateinit var context : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(R.layout.activity_detail)

        initValues()
    }

    private fun initValues() {
        context = this
        title = getString(R.string.app_title)
        val intentVal: Intent = intent
        tv_name.text = intentVal.getStringExtra(getString(R.string.country_name))
        tv_capital_val.text = intentVal.getStringExtra(getString(R.string.country_capital))
        tv_region.text = intentVal.getStringExtra(getString(R.string.country_regionblocs))
        val pop : Int? = intentVal.getStringExtra(getString(R.string.country_population))?.toInt()
        val nf: NumberFormat = NumberFormat.getInstance()
        val formatPop : String = nf.format(pop)
        tv_population.text = formatPop
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}