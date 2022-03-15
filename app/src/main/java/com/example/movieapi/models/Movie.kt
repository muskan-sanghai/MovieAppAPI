package com.example.movieapi.models

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class Movie(
    @Nullable val adult: Boolean?,
    @Nullable val backdrop_path: String?,
    @Nullable val budget: Int?,
    @Nullable val homepage: String?,
    @PrimaryKey val id: Int,
    @Nullable val imdb_id: String?,
    @Nullable val original_language: String?,
    @Nullable val original_title: String?,
    @Nullable val overview: String?,
    @Nullable val popularity: Double?,
    @Nullable val poster_path: String?,
    @Nullable val release_date: String?,
    @Nullable val revenue: Int?,
    @Nullable val runtime: Int?,
    @Nullable val status: String?,
    @Nullable val tagline: String?,
    @Nullable val title: String?,
    @Nullable val video: Boolean?,
    @Nullable val vote_average: Double?,
    @Nullable val vote_count: Int?
)