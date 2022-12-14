package com.android.mtinfo.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.mtinfo.BuildConfig
import com.android.mtinfo.data.model.movie.Movie
import com.android.mtinfo.databinding.ListItemBinding
import com.bumptech.glide.Glide

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {

            val posterURL = BuildConfig.POSTER_URL + movie.poster_path
            Glide.with(binding.ivPoster.context)
                .load(posterURL)
                .into(binding.ivPoster)

            binding.cardView.setOnClickListener{
                onItemClickListener?.invoke(movie)
            }
        }
    }

    private val callback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)


    private var onItemClickListener: ((Movie)->Unit) ?= null
    fun setOnItemClickListener(listener: (Movie)->Unit) {
            onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = differ.currentList.size
}