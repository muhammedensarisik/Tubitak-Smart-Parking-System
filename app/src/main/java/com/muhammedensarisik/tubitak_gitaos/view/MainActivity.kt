package com.muhammedensarisik.tubitak_gitaos.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muhammedensarisik.tubitak_gitaos.R
import com.muhammedensarisik.tubitak_gitaos.adapter.SpotAdapter
import com.muhammedensarisik.tubitak_gitaos.databinding.ActivityMainBinding
import com.muhammedensarisik.tubitak_gitaos.model.SpotStatus
import com.muhammedensarisik.tubitak_gitaos.service.OtoparkAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadDataFromAPI()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.refreshButton.setOnClickListener {
            loadDataFromAPI()
        }

    }


    private fun loadDataFromAssets() {
        try {
            val json = assets.open("spot_status.json").bufferedReader().use { it.readText() }
            val spotStatus = Gson().fromJson(json, SpotStatus::class.java)

            val spotMap = mapOf(
                "A1" to spotStatus.A1,
                "A2" to spotStatus.A2,
                "A3" to spotStatus.A3,
                "A4" to spotStatus.A4,
                "A5" to spotStatus.A5,
                "A6" to spotStatus.A6,
                "A7" to spotStatus.A7,
                "A8" to spotStatus.A8,
                "A9" to spotStatus.A9,
                "A10" to spotStatus.A10,
                "A11" to spotStatus.A11
            )

            val spotList = spotMap.toList()
            val adapter = SpotAdapter(spotList)
            binding.recyclerView.adapter = adapter

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadDataFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://2dd9-34-106-211-50.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OtoparkAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<SpotStatus> {
            override fun onResponse(call: Call<SpotStatus>, response: Response<SpotStatus>) {
                if (response.isSuccessful) {
                    response.body()?.let { spotStatus ->
                        val spotList = listOf(
                            "A1" to spotStatus.A1,
                            "A2" to spotStatus.A2,
                            "A3" to spotStatus.A3,
                            "A4" to spotStatus.A4,
                            "A5" to spotStatus.A5,
                            "A6" to spotStatus.A6,
                            "A7" to spotStatus.A7,
                            "A8" to spotStatus.A8,
                            "A9" to spotStatus.A9,
                            "A10" to spotStatus.A10,
                            "A11" to spotStatus.A11,
                        )

                        binding.recyclerView.adapter = SpotAdapter(spotList)
                    }
                }
            }

            override fun onFailure(call: Call<SpotStatus>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}