package com.lite.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lite.newsapp.R
import com.lite.newsapp.ui.adapters.NewsListAdapter
import com.lite.newsapp.util.APIKEY
import com.lite.newsapp.util.TOPIC

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var newsAdapter: NewsListAdapter
    private lateinit var newsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        newsList = findViewById(R.id.news_list)

        val map = HashMap<String, String>()
        map["apiKey"] = APIKEY
        map["q"] = TOPIC
        viewModel.getNewsResponse(map)
    }

    override fun onResume() {
        super.onResume()
        viewModel.mutableNewsResponse.observe(this, Observer { response ->
            newsList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                newsAdapter = NewsListAdapter(response.articles, this@MainActivity)
                adapter = newsAdapter
            }
        })
    }
}
