package com.example.movieapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapi.helpers.MovieAdapter
import com.example.movieapi.models.Movie
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val movieService by lazy { (application as MoviesApplication).appCompositionRoot.movieService }
    private val movieDao by lazy { (application as MoviesApplication).appCompositionRoot.movieDao }

    private val moviesAdapter by lazy {
        MovieAdapter {
            Log.d("onClick", "onResponse: $it")
            val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.movie_recycler).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = moviesAdapter
        }

        loadMovieList()
    }


    private fun loadMovieList() {

        lifecycleScope.launchWhenCreated {
            val isDbEmpty = withContext(Dispatchers.IO) {
                return@withContext movieDao.isDbEmpty().isNullOrEmpty()
            }

            if (isDbEmpty)
                getMoviesFromApi()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                movieDao.getMovies().collect {
                    moviesAdapter.submitList(it)
                }
            }

        }
    }

    private fun getMoviesFromApi() {
        lifecycleScope.launchWhenCreated {
            try {
                val movies = movieService.getPopularMovieList()
                showMovies(movies.results)
                saveMovies(movies.results)
            } catch (e: Exception) {
                if (e is CancellationException)
                    throw e
                Log.d(TAG, "loadMovieList: $e")
                Toast.makeText(
                    this@MainActivity,
                    "Something went wrong ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private suspend fun saveMovies(results: List<Movie>) = withContext(Dispatchers.IO) {
        movieDao.insertMovies(results)
    }

    private fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

}