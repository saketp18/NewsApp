package com.lite.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lite.newsapp.R
import com.lite.newsapp.util.APIKEY
import com.lite.newsapp.util.TOPIC

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val map = HashMap<String, String>()
        map["apiKey"] = APIKEY
        map["q"] = TOPIC
        viewModel.getNewsResponse(map)
    }

    override fun onResume() {
        super.onResume()
        viewModel.mutableNewsResponse.observe(this, Observer { response ->

            Log.d("Saket", response.toString())
        })
    }
}
