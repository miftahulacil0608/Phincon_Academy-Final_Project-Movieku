package com.example.movieku.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Movie
import com.example.movieku.R
import com.example.movieku.adapter.home.contract.NowPlayingMovieListener
import com.example.movieku.databinding.ItemNowPlayingMoviesBinding

class NowPlayingMoviesAdapter(private val listener: NowPlayingMovieListener):RecyclerView.Adapter<NowPlayingMoviesAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemNowPlayingMoviesBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Movie){
            with(binding){
                Glide.with(root)
                    .load(item.posterPath)
                    .placeholder(R.drawable.iv_placeholder)
                    .centerCrop()
                    .into(ivPosterNowPlaying)
                movieTitle.text = item.title
                //TODO tvduratioongenre
                tvReleaseDateAndGenreMovie.text = root.resources.getString(R.string.label_release_and_genre, item.releaseDate, item.genre)

                //TODO movieRating
                binding.movieRate.text = binding.root.resources.getString(R.string.label_rating_count_vote,item.voteRange, item.voteCount)

            }

            binding.root.setOnClickListener {
                listener.onItemNowPlayingClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNowPlayingMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = asyncDiffer.currentList.take(10).size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(asyncDiffer.currentList[position])
        //holder.setIsRecyclable(false)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    val asyncDiffer = AsyncListDiffer(this@NowPlayingMoviesAdapter, differCallback)

}