package com.lite.newsapp.ui

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lite.newsapp.data.Repository
import com.lite.newsapp.models.NewsResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class MainViewModel : ViewModel() {

    private val repository = Repository()
    //Here we can use Job or SupervisorJob
    private val viewModelJob = SupervisorJob()
    private val viewModeScope = CoroutineScope(IO + viewModelJob)
    val response = MutableLiveData<String>()
    lateinit var mutableNewsResponse: LiveData<Result<NewsResponse?>>
    val progressSate = ObservableField(true)

    /*fun getNews(map: Map<String, String>) {
        viewModeScope.launch {
            val response = repository.getNewsData(map)
            withContext(Dispatchers.Main) {
                progressSate.set(false)
            }
            if (response.isSuccessful) {
                //Another way to proceed, best practice to update UI data variables
                //using withContext with Dispatchers.Main
                withContext(Dispatchers.Main) {
                    mutableNewsResponse.value = response.body()
                }
                mutableNewsResponse.postValue(response.body())
            } else {
                Log.d("NewsApp", "Error ${response.code()} and ${response.errorBody()}")
            }
        }
    }*/

    fun getNewsResponse(map: Map<String, String>) {
        mutableNewsResponse = liveData(context = viewModelJob) {
            val data = repository.getNewsData(map)
            emit(Result.success(data.body()))
            progressSate.set(false)
            response.value = "News Demo App"
        }
    }

    /**
     * Another way to do above CorotuineScope with viewModelScop specified in dependencies
     * implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-rc03'
     *
     *  viewModelScope.launch {
     *   @see getNewsResponse()
     * }
     *
     */

    override fun onCleared() {
        super.onCleared()
        viewModeScope.cancel()
    }
}