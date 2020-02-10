package com.lite.newsapp.ui

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lite.newsapp.data.Repository
import com.lite.newsapp.models.NewsResponse
import com.lite.newsapp.util.CustomObservableField
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class MainViewModel : ViewModel() {

    private val repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(IO + viewModelJob)
    val mutableNewsResponse = MutableLiveData<NewsResponse>()
    val progressSate = ObservableField(true)

    fun getNewsResponse(map: Map<String, String>) {
        viewModelScope.launch {
            Log.d("getNewsResponse", "From IO Context 1: ${Thread.currentThread().name}")
            val response = repository.getNewsData(map)
            Log.d("getNewsResponse", "From IO Context 2: ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    progressSate.set(false)
                    //Best practice to update UI data variables
                    //using withContext with Dispatchers.Main, but only
                    //livedata are there then you can postValue without switching context
                    //mutableNewsResponse.postValue(response.body())
                    mutableNewsResponse.value = response.body()
                    Log.d("getNewsResponse", "From Main Context ${Thread.currentThread().name}")
                    //mutableNewsResponse.postValue(response.body())
                } else {
                    Log.d("NewsApp", "Error ${response.code()} and ${response.errorBody()}")
                }
            }
        }
    }

    fun getNewsResponses(map: Map<String, String>) {
        viewModelScope.launch {
            val result = async {
                repository.getNewsData(map)
            }
            val response = result.await()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    progressSate.set(false)
                    //Best practice to update UI data variables
                    //using withContext with Dispatchers.Main, but only
                    //livedata are there then you can postValue without switching context
                    //mutableNewsResponse.postValue(response.body())
                    mutableNewsResponse.value = response.body()
                    Log.d("getNewsResponse", "From Main Context ${Thread.currentThread().name}")
                    //mutableNewsResponse.postValue(response.body())
                } else {
                    Log.d("NewsApp", "Error ${response.code()} and ${response.errorBody()}")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
