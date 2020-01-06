package com.lite.newsapp.ui

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lite.newsapp.data.Repository
import com.lite.newsapp.models.NewsResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val repository = Repository()
    val mutableNewsResponse = MutableLiveData<NewsResponse>()
    val progressSate = ObservableField(true)

    fun getNewsResponse(map: Map<String, String>): Single<Response<NewsResponse>> {
        return repository.getNewsData(map).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                progressSate.set(false)
                val response = it.body()
                if (it.isSuccessful && response != null) {
                    mutableNewsResponse.value = response
                } else {
                    Log.d(
                        "NewsApp",
                        "Error ${it.code()} and errorbody ${it.errorBody()}"
                    )
                }
            }
            .doOnError {

            }
    }
}
