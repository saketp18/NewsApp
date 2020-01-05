package com.lite.newsapp.data.network

import com.lite.newsapp.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApiService {

    @GET("/v2/everything")
    suspend fun getNews(@QueryMap query: Map<String, String>): Response<NewsResponse>
}