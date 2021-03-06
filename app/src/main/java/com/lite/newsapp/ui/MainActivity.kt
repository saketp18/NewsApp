package com.lite.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lite.newsapp.R
import com.lite.newsapp.databinding.ActivityMainBinding
import com.lite.newsapp.ui.adapters.NewsListAdapter
import com.lite.newsapp.util.getQuery
import com.lite.newsapp.util.isNetworkAvailable
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var newsAdapter: NewsListAdapter
    private lateinit var newsList: RecyclerView
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        activityMainBinding.viewmodel = viewModel
        newsList = findViewById(R.id.news_list)
        initRecyclerView()
        getNewsResponse()
    }

    private fun initRecyclerView() {
        newsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            newsAdapter = NewsListAdapter(WeakReference(this@MainActivity))
            adapter = newsAdapter
        }
    }

    private fun getNewsResponse() {
        if (isNetworkAvailable(this)) {
            viewModel.getNewsResponse(getQuery())
            viewModel.mutableNewsResponse.observe(this, Observer { response ->
                newsAdapter.setArticlesResponse(response.articles)
            })
        } else {
            viewModel.progressSate.set(false)
        }
    }
}
