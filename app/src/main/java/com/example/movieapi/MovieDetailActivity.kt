package com.example.movieapi

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.movieapi.models.Movie
import com.squareup.picasso.Picasso
import java.util.concurrent.CancellationException

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MovieDetailActivity"
    }

    private val movieService by lazy { (application as MoviesApplication).appCompositionRoot.movieService }
    private val movieDao by lazy { (application as MoviesApplication).appCompositionRoot.movieDao }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        Log.d(TAG, "onCreate: ${intent.getIntExtra("id", 0)}")
        loadMovieDetail(intent.getIntExtra("id", 0).toString())
    }

    private fun loadMovieDetail(id: String) {
        lifecycleScope.launchWhenCreated {
            try {
                val movie = movieDao.getMovie(id)
                if (movie != null)
                    updateUI(movie)
                else {
                    getMovieDetailFromApi(id)
                }
            } catch (e: Exception) {
                if (e is CancellationException)
                    throw e
                Log.d(TAG, "loadMovieDetail: ${e.message}")
                Toast.makeText(
                    this@MovieDetailActivity,
                    "Something went wrong ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getMovieDetailFromApi(id: String) {
        lifecycleScope.launchWhenCreated {
            val movie = movieService.getMovieDetail(id)
            updateUI(movie)
        }
    }

    private fun updateUI(movie: Movie) {

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .into(findViewById<ImageView>(R.id.movie_poster))
        findViewById<TextView>(R.id.movie_title).text = movie.title
        findViewById<TextView>(R.id.description).text = movie.overview

    }
}