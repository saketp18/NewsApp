package com.lite.newsapp.util

import androidx.databinding.ObservableField

class CustomObservableField<T>(value: T) : ObservableField<T>(value) {

    private var mValue: T? = value

    override fun set(value: T) {
        synchronized(this) {
            if (value !== mValue) {
                mValue = value
            }
        }
        notifyChange()
    }
}
