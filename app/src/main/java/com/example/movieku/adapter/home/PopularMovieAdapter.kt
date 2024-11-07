package com.example.movieku.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Movie
import com.example.movieku.adapter.home.contract.PopularMovieListener
import com.example.movieku.databinding.ItemMoviePopularBinding

class PopularMovieAdapter(private var listItem:List<Movie> = emptyList()):RecyclerView.Adapter<PopularMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemMoviePopularBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Movie){
            Glide.with(binding.root)
                .load(item.backdropPath)
                .into(binding.ivPopularMovie)
            binding.root.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMoviePopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItem[position]

        holder.bind(item)
    }

    fun addNewListData(newListItem:List<Movie>){
        listItem = newListItem
        notifyDataSetChanged()
    }
}