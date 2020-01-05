package com.lite.newsapp.util


const val API_KEY = "10295c24ac0c456d87a3e61ddbeb4212"
const val TOPIC = "usa"

fun getQuery(): Map<String, String> {
    val queryMap = HashMap<String, String>()
    queryMap["apiKey"] = API_KEY
    queryMap["q"] = TOPIC
    return queryMap
}
