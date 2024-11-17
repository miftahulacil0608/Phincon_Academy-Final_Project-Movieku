package com.example.movieku.adapter.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Movie
import com.example.movieku.R
import com.example.movieku.adapter.home.contract.NowPlayingMovieListener
import com.example.movieku.adapter.home.contract.UpComingMovieListener
import com.example.movieku.databinding.ItemUpcomingMovieBinding

//TODO ganti makek diffutilcallback saja
class UpcomingMovieAdapter(private var listItem: List<Movie> = emptyList(), private val listener:NowPlayingMovieListener) :
    RecyclerView.Adapter<UpcomingMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemUpcomingMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            with(binding) {
                Glide.with(root)
                    .load(item.posterPath)
                    .placeholder(R.drawable.iv_placeholder)
                    .centerCrop()
                    .into(ivPosterMovie)
                tvTitleMovie.text = item.title
                tvGenre.text = item.genre
                tvReleaseMovie.text = item.releaseDate

                root.setOnClickListener {
                    listener.onItemNowPlayingClick(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemUpcomingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewListData(newListItem: List<Movie>) {
        listItem = newListItem
        notifyDataSetChanged()
    }
}