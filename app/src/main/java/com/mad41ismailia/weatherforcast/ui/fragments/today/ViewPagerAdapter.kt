package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import kotlin.collections.ArrayList

@SuppressLint("LogNotTimber")
class ViewPagerAdapter(val context: Context,list: List<String?>, listDaily: ArrayList<List<DailyDatabase>>, listHourly: ArrayList<List<HourlyDatabase>>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    private var myList: List<String?> = list
    private var dailyList: ArrayList<List<DailyDatabase>> = listDaily
    private var hourlyList: ArrayList<List<HourlyDatabase>> = listHourly
    private lateinit var dailyAdapter:DailyAdapter
    private lateinit var hourlyAdapter: HourlyAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = myList[position]
        holder.userId.text = city
        if (dailyList.isNotEmpty()){
        val daily = dailyList[position]
            if (daily.isNotEmpty()){
            val icon = daily[0].weather.icon
            Log.i("imageview", "${daily}")
            icon?.let { setImg(it) }?.let { holder.weatherDetail.setImageResource(it) }
        }}
        val recyclerViewPool = RecyclerView.RecycledViewPool()
        val recyclerViewPool2 = RecyclerView.RecycledViewPool()
        val layoutManager = LinearLayoutManager(context)
        val layoutManager2 = LinearLayoutManager(context)

        layoutManager.orientation = HORIZONTAL
        layoutManager.initialPrefetchItemCount = hourlyList[position].size

        hourlyAdapter = HourlyAdapter(hourlyList[position])
        holder.HourlyRecyclerView.layoutManager = layoutManager
        holder.HourlyRecyclerView.adapter = hourlyAdapter
//        holder.HourlyRecyclerView.setRecycledViewPool(recyclerViewPool)
        holder.HourlyRecyclerView.setHasFixedSize(true)


        layoutManager2.initialPrefetchItemCount = dailyList[position].size
        dailyAdapter = DailyAdapter(dailyList[position])
        holder.dailyRecyclerView.layoutManager = layoutManager2
        holder.dailyRecyclerView.adapter = dailyAdapter
//        holder.dailyRecyclerView.setRecycledViewPool(recyclerViewPool2)
//        holder.dailyRecyclerView.setHasFixedSize(true)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<String?>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId: TextView = view.findViewById(R.id.cityNameViewPager)
        val weatherDetail:ImageView = view.findViewById(R.id.imgWeatherState)
        val dailyRecyclerView: RecyclerView = view.findViewById(R.id.DailyRecyclerView)
        val HourlyRecyclerView: RecyclerView = view.findViewById(R.id.HourlyRecyclerView)
    }
}


fun setImg(icon: String):Int{
    return when(icon){
        "01d" -> R.drawable.d01
        "02d" -> R.drawable.d02
        "03d" -> R.drawable.d03
        "04d" -> R.drawable.d04
        "09d" -> R.drawable.d09
        "10d" -> R.drawable.d10
        "11d" -> R.drawable.d11
        "13d" -> R.drawable.d13
        "50d" -> R.drawable.d50
        "01n" -> R.drawable.n01
        "02n" -> R.drawable.n02
        "03n" -> R.drawable.n03
        "04n" -> R.drawable.n04
        "09n" -> R.drawable.n09
        "10n" -> R.drawable.n10
        "11n" -> R.drawable.n11
        "13n" -> R.drawable.n13
        "50n" -> R.drawable.n50
        else -> R.drawable.d01
    }
}

