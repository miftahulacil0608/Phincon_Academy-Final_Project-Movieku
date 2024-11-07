package com.example.movieku.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Movie
import com.example.movieku.adapter.home.contract.NowPlayingMovieListener
import com.example.movieku.databinding.ItemNowPlayingMovieBinding
//TODO recyclerview yang ada pada homefragment bisa dijadikan 1 aja karena jenis datanya sama hanya beda UI
class NowPlayingMovieAdapter(private var listItem: List<Movie> = emptyList(), private val listener:NowPlayingMovieListener) :
    RecyclerView.Adapter<NowPlayingMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemNowPlayingMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            val numberPosition = bindingAdapterPosition + 1
            Glide.with(binding.root)
                .load(item.posterPath)
                .into(binding.ivNowPlayingMovie)
            binding.tvNumberMovie.text = numberPosition.toString()

            binding.root.setOnClickListener {
                listener.onItemNowPlayingClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemNowPlayingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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