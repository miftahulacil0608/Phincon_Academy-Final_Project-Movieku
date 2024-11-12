package com.example.movieku.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Movie
import com.example.movieku.R
import com.example.movieku.adapter.home.contract.PopularMovieListener
import com.example.movieku.databinding.ItemMoviePopularBinding

class PopularMovieAdapter():RecyclerView.Adapter<PopularMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemMoviePopularBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Movie){
            Glide.with(binding.root)
                .load(item.backdropPath)
                .into(binding.ivPopularMovie)
            binding.movieTitle.text = item.title
            binding.tvDurationAndGenreMovie.text = item.releaseDate
            binding.movieRating.text = binding.root.resources.getString(R.string.label_rating_count_vote,item.voteRange.toString(), item.voteRange.toString())

            binding.root.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMoviePopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(asyncDiffer.currentList[position])
        holder.setIsRecyclable(false)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    val asyncDiffer = AsyncListDiffer(this@PopularMovieAdapter, differCallback)

}