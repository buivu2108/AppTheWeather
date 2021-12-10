package com.example.apptheweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONObject
import java.lang.reflect.Method

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var intent: Intent = intent
        var city: String? = intent.getStringExtra("name_city")
        Get7DayData()
        initEven()

    }

    private fun initEven() {
        img_btn_back.setOnClickListener {
            //intent = Intent(this, MainActivity::class.java)
            onBackPressed()
        }
    }

    private fun Get7DayData() {
        //var data: String? = intent.getStringExtra("name_city")
        var url =
            "https://api.weatherbit.io/v2.0/forecast/daily?city=HaNoi&key=69ea3c8ee93e4a55b4a4ea81a456b157&days=7"
        var requestQueu: RequestQueue = Volley.newRequestQueue(this)
        val jsonObjectReques = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val jsonObject = JSONObject(response.toString())
                val name_city = jsonObject.getString("city_name")
                val jsonArray = jsonObject.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    val jsonObjectData = jsonArray.getJSONObject(i)
                    val date: String = jsonObjectData.getString("datetime")
                    val jsonObjectWeathers: JSONObject = jsonObjectData.getJSONObject("weather")
                    val description: String = jsonObjectWeathers.getString("description")
                    val max_temp: String = jsonObjectData.getString("max_temp")
                    val min_temp: String = jsonObjectData.getString("min_temp")

                    val recyclerview = findViewById<RecyclerView>(R.id.rcv_7day)
                    recyclerview.layoutManager = LinearLayoutManager(this)
                    val dataList = ArrayList<Weather>()
                    dataList.add(Weather(date, description, max_temp, min_temp))
                    val adapter = WeatherAdapter(dataList)
                    recyclerview.adapter = adapter

                }
                tv_name_city_2.setText(name_city)
            },
            { error ->
                Toast.makeText(applicationContext, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        requestQueu.add(jsonObjectReques)
    }
}