package com.example.movieapi.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapi.R
import com.example.movieapi.models.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val onClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.ViewHolder>(differ) {

    companion object {
        val differ = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdb_id == newItem.imdb_id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${currentList} ")

        holder.bind(getItem(position))

    }

    class ViewHolder(itemView: View, private val onClick: (Movie) -> Unit) :
        RecyclerView.ViewHolder(itemView) {


        var imageView = itemView.findViewById<ImageView>(R.id.movie_poster)
        var tvTitle = itemView.findViewById<TextView>(R.id.movie_title)
        var tvCases = itemView.findViewById<TextView>(R.id.release_date)
        fun bind(movie: Movie) {
            itemView.setOnClickListener {

                onClick(movie)


            }
            val name = "Release Date: ${movie.release_date.toString()}"
            tvTitle.text = movie.title
            tvCases.text = name
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .into(imageView)
        }

    }
}