package com.lite.newsapp.util

import android.content.Context
import android.net.ConnectivityManager


const val API_KEY = "10295c24ac0c456d87a3e61ddbeb4212"
const val TOPIC = "usa"
const val PAGE_SIZE = "30"
const val PAGE = "1"

fun getQuery(): Map<String, String> {
    val queryMap = HashMap<String, String>()
    queryMap["apiKey"] = API_KEY
    queryMap["q"] = TOPIC
    queryMap["page"] = PAGE
    queryMap["pageSize"] = PAGE_SIZE
    return queryMap
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}
