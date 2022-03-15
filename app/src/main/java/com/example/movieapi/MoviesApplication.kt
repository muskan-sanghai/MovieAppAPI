package com.example.movieapi

import android.app.Application

class MoviesApplication : Application() {

    val appCompositionRoot by lazy { AppCompositionRoot(this) }

    override fun onCreate() {
        super.onCreate()
    }
}