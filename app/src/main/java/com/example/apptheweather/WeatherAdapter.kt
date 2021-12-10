package com.example.apptheweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherAdapter(private val dataSet: ArrayList<Weather>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_day: TextView = itemView.findViewById(R.id.tv_ngay_thang)
        val imageWeather: ImageView = itemView.findViewById(R.id.img_weather)

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_weather, viewGroup, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val Weather = dataSet[position]
        viewHolder.tv_day.text = Weather.day
        viewHolder.itemView.tv_thai_trang.text = Weather.status
        viewHolder.itemView.tv_temp_max.text = Weather.maxTemp
        viewHolder.itemView.tv_temp_min.text = Weather.minTemp
        viewHolder.imageWeather.setImageResource(R.drawable.cloud)
    }
    override fun getItemCount() = dataSet.size
    }