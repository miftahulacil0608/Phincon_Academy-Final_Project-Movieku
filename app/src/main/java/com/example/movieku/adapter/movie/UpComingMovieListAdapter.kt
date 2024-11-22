package com.example.movieku.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.movie.Movie
import com.example.movieku.R
import com.example.movieku.adapter.movie.contract.UpComingMovieInCinemaListener
import com.example.movieku.databinding.ItemUpcomingMovieInCinemaLayoutBinding

class UpComingMovieListAdapter(private var listItem:List<Movie> = emptyList(), private val listener: UpComingMovieInCinemaListener):
    RecyclerView.Adapter<UpComingMovieListAdapter.MyAdapter>() {
    inner class MyAdapter(private val binding: ItemUpcomingMovieInCinemaLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Movie){
            with(binding){
                Glide.with(root)
                    .load(item.posterPath)
                    .placeholder(R.drawable.iv_placeholder)
                    .centerCrop()
                    .into(ivPoster)

                tvTitleMovie.text = item.title
                tvGenre.text = item.genre
                tvReleaseMovie.text = item.releaseDate
                root.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val binding = ItemUpcomingMovieInCinemaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAdapter(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewData(newList:List<Movie>){
        listItem = newList
        notifyDataSetChanged()
    }
}