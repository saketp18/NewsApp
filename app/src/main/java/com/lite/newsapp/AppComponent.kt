package com.lite.newsapp

import com.lite.newsapp.ui.MainActivity
import dagger.Component

@Component
interface AppComponent {

    fun inject(mainActivity: MainActivity);
}