package com.lite.newsapp.data.network

import com.lite.newsapp.models.NewsResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApiService {

    @GET("/v2/everything")
    fun getNews(@QueryMap query: Map<String, String>): Single<Response<NewsResponse>>
}