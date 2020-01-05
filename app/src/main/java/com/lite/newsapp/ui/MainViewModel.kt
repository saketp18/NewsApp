package com.lite.newsapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lite.newsapp.data.Repository
import com.lite.newsapp.models.NewsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val viewModeScope = CoroutineScope(Main + viewModelJob)
    val mutableNewsResponse = MutableLiveData<NewsResponse>()

    fun getNewsResponse(map: Map<String, String>) {
        viewModeScope.launch {
            val response = repository.getNewsData(map)
            if (response.isSuccessful) {
                mutableNewsResponse.postValue(response.body())
            } else {
                Log.d("Saket", "Error ${response.code()} and ${response.errorBody()}")
            }
        }
    }
}