package com.example.movieku.adapter.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Movie
import com.example.movieku.databinding.ItemMoviePopularBinding
import com.example.movieku.databinding.ItemUpcomingMovieBinding

class UpcomingMovieAdapter(private var listItem: List<Movie> = emptyList()) :
    RecyclerView.Adapter<UpcomingMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemUpcomingMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            with(binding) {
                val numberPosition = bindingAdapterPosition+1
                Glide.with(root)
                    .load(item.posterPath)
                    //.into(ivUpComingMovie)
                //tvNumberMovie.text = numberPosition.toString()
                //root.setOnClickListener {}
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