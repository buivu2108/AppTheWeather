package com.example.apptheweather

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var city: String = edt_search.text.toString().trim()
        btn_search.setOnClickListener {
            getJsonWeather()

        }
        btn_ngay_tiep_theo.setOnClickListener {
            var intent = Intent(this, MainActivity2::class.java).apply {
               // putExtra("name_city", city)
            }
            startActivity(intent)
        }
    }

    private fun getJsonWeather() {
        var data: String = edt_search.text.toString().trim()
        if (data.isEmpty()){
            data = "hanoi"
        }
        var url =
            "https://api.openweathermap.org/data/2.5/weather?q=$data&appid=220dad74c7a8160adea535d772d08adb&units=metric"
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                var jsonObject = JSONObject(response.toString())
                var day: String = jsonObject.getString("dt")
                var name: String = jsonObject.getString("name")
                var l: Long = day.toLong()
                var date = Date(l * 1000L)
                var simpleDateFormat = SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss")
                var Day: String = simpleDateFormat.format(date)
                var jsonArray = jsonObject.getJSONArray("weather")
                var jsonObjectWeather = jsonArray.getJSONObject(0)
                var trangthai: String = jsonObjectWeather.getString("description")
                var icon: String = jsonObjectWeather.getString("icon")
                var imageUrl = "http://openweathermap.org/img/wn/$icon@2x.png"
                var jsonObjectAddress: JSONObject = jsonObject.getJSONObject("sys")
                var nameAddress: String = jsonObjectAddress.getString("country")
                var jsonObjectDoC: JSONObject = jsonObject.getJSONObject("main")
                var doC: String = jsonObjectDoC.getString("temp")
                var jsonObjectWind: JSONObject = jsonObject.getJSONObject("wind")
                var wind: String = jsonObjectWind.getString("speed")
                var jsonObjectCloud: JSONObject = jsonObject.getJSONObject("clouds")
                var clouds: String = jsonObjectCloud.getString("all")
                var doAm: String = jsonObjectDoC.getString("humidity")
                Picasso.get()
                    .load(imageUrl)
                    .into(img_trang_thai)

                tv_name_city.setText(name)
                tv_date.setText(Day)
                tv_trang_thai.setText(trangthai)
                tv_name_address.setText(nameAddress)
                tv_nhiet_do.setText("$doC°C")
                tv_gio.setText(wind + "m/s")
                tv_may.setText("$clouds%")
                tv_do_am.setText("$doAm%")

            },
            { error ->
                Toast.makeText(applicationContext, "Hãy nhập đúng địa chỉ", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}
