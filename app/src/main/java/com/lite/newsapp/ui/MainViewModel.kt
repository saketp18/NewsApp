package com.lite.newsapp.ui

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lite.newsapp.data.Repository
import com.lite.newsapp.models.NewsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = Repository()
    //Here we can use Job or SupervisorJob
    private val viewModelJob = SupervisorJob()
    private val viewModeScope = CoroutineScope(IO + viewModelJob)
    val mutableNewsResponse = MutableLiveData<NewsResponse>()
    val progressSate = ObservableField(true)

    fun getNewsResponse(map: Map<String, String>) {
        viewModeScope.launch {
            val response = repository.getNewsData(map)
            progressSate.set(false)
            if (response.isSuccessful) {
                mutableNewsResponse.postValue(response.body())
                viewModelJob.complete()
            } else {
                Log.d("Saket", "Error ${response.code()} and ${response.errorBody()}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModeScope.cancel()
    }
}