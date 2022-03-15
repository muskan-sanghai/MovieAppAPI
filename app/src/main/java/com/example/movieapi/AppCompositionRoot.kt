package com.example.movieapi

import android.content.Context
import com.example.movieapi.room.MovieDatabase
import com.example.movieapi.services.MovieService
import com.example.movieapi.services.ServiceBuilder

class AppCompositionRoot(context: Context) {

    val movieService by lazy { ServiceBuilder.buildService(MovieService::class.java) }

    private val movieDb by lazy { MovieDatabase.getDatabase(context = context) }

    val movieDao by lazy { movieDb.movieDao() }
}