package com.kt.countrylist.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kt.countrylist.R
import com.kt.countrylist.db.AppDatabase
import com.kt.countrylist.db.Country
import com.kt.countrylist.ui.base.DetailActivity
import com.larvalabs.svgandroid.SVGParser
import okhttp3.OkHttpClient
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class CountryRecyclerAdapter(private val mCtx: Context) :
    RecyclerView.Adapter<CountryRecyclerAdapter.TasksViewHolder>() {

    private var dbClient: AppDatabase? = null
    private var countryList: List<Country?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view: View =
            LayoutInflater.from(mCtx).inflate(
                R.layout.layout_item_matches,
                parent, false
            )
        dbClient = AppDatabase.getDatabase(mCtx)

        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val t: Country? = countryList?.get(position)
        holder.name.text = t?.getCountryName()
        val cap: String? = t?.getCapital() ?: "N/A"
        val reg: String? = t?.getRegion() ?: "N/A"
        val p = "$cap, $reg"
        holder.capital.text = p
        holder.regionBlocs.text = t?.getBlocsName()


        holder.clMain.setOnClickListener {
            val myIntent = Intent(mCtx, DetailActivity::class.java)
            myIntent.putExtra(mCtx.getString(R.string.country_population), t?.getPopulation())
            myIntent.putExtra(mCtx.getString(R.string.country_capital), t?.getCapital())
            myIntent.putExtra(mCtx.getString(R.string.country_regionblocs), t?.getBlocsName())
            myIntent.putExtra(mCtx.getString(R.string.country_name), t?.getCountryName())
            mCtx.startActivity(myIntent)
        }

        val url = URL(t?.getFlag())
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val inputStream = urlConnection.inputStream
            val svg = SVGParser.getSVGFromInputStream(inputStream)
            val drawable: Drawable = svg.createPictureDrawable()
            holder.imageV.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return if (countryList != null)
            countryList!!.size
        else 0
    }

    fun updateData(matches: List<Country>) {
        countryList = matches
        countryList = (countryList as List<Country>).sortedBy { it.getCountryName() }
        notifyDataSetChanged()
    }

    class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clMain: ConstraintLayout = itemView.findViewById(R.id.cl_main)
        val name: TextView = itemView.findViewById(R.id.tv_country)
        val capital: TextView = itemView.findViewById(R.id.tv_capital)
        val regionBlocs: TextView = itemView.findViewById(R.id.tv_region_blocs)
        val imageV: ImageView = itemView.findViewById(R.id.imageView)
    }

}