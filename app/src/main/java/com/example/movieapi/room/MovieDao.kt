package com.example.movieapi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapi.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM MOVIE WHERE id = :id")
    suspend fun getMovie(id: String): Movie?

    @Query(value = "SELECT * FROM Movie")
    fun getMovies(): Flow<List<Movie>>

    @Query(value = "SELECT * FROM Movie")
    fun isDbEmpty(): List<Movie>

}