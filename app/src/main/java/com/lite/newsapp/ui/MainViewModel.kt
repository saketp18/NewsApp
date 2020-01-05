package com.lite.newsapp.ui

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lite.newsapp.data.Repository
import com.lite.newsapp.models.NewsResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

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
            withContext(Dispatchers.Main) {
                progressSate.set(false)
            }
            if (response.isSuccessful) {
                //Another way to proceed, best practice to update UI data variables
                //using withContext with Dispatchers.Main
                /*withContext(Dispatchers.Main) {
                    mutableNewsResponse.value = response.body()
                }*/
                mutableNewsResponse.postValue(response.body())
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