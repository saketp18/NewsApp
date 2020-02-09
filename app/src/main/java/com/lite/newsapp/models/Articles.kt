package com.lite.newsapp.models

import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("title")
    val title: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)
