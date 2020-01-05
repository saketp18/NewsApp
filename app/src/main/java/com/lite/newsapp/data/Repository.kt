package com.lite.newsapp.data

import com.lite.newsapp.util.RetrofitClientInstance

class Repository {

    fun getNewsData(queryMap: Map<String, String>) =
        RetrofitClientInstance.getClient().getNews(queryMap)
}