package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.comingData.Daily
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter2(private val list: List<Daily>) :
    RecyclerView.Adapter<DailyAdapter2.ViewHolder>() {

    private var myList: List<Daily> = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("LogNotTimber")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = myList[position]
        val calender = Calendar.getInstance()
        calender.timeInMillis = (task.dt?.toLong() ?: 10) * 1000L
        val dateFormat = SimpleDateFormat("dd-MM-yyyy");
        holder.txtDayName.text = dateFormat.format(calender.time)

//        holder.txtDayName.text = task.dt.toString()
        holder.txtDayTemp.text = task.temp.day.toString()
        holder.txtNightTemp.text = task.temp.night.toString()
        holder.txtMaxTemp.text = task.temp.max.toString()
        holder.txtMinTemp.text = task.temp.min.toString()
        holder.iconId.text = task.weather[0].icon.toString()
        holder.lottieIcon.setAnimation(setImgLottie(task.weather[0].icon))
        holder.expand.setOnClickListener {
            holder.expandableLayout.visibility = if (holder.expandableLayout.visibility == GONE) {
                                                    View.VISIBLE
                                                } else {
                                                    View.GONE
                                                }
        }
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<Daily>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDayName: TextView = view.findViewById(R.id.txtDayName)
        val txtDayTemp: TextView = view.findViewById(R.id.txtDayTemp)
        val txtNightTemp: TextView = view.findViewById(R.id.txtNightTemp)
        val txtMaxTemp: TextView = view.findViewById(R.id.txtMaxTemp)
        val txtMinTemp: TextView = view.findViewById(R.id.txtMinTemp)
        val lottieIcon: LottieAnimationView = view.findViewById(R.id.imgLottie)
        val iconId: TextView = view.findViewById(R.id.iconId)
        val expandableLayout: ConstraintLayout = view.findViewById(R.id.expandableLayout)
        val expand: ConstraintLayout = view.findViewById(R.id.expand)
        val card: CardView = view.findViewById(R.id.dailyCard)
    }

}