package com.example.movieku.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.movie.Movie
import com.example.domain.model.movie.NowPlayingMovie
import com.example.domain.model.movie.search.SearchMovieItem
import com.example.movieku.R
import com.example.movieku.adapter.movie.contract.NowPlayingMovieInCinemaListener
import com.example.movieku.adapter.search.SearchMovieAdapter
import com.example.movieku.adapter.search.SearchMovieListener
import com.example.movieku.databinding.ItemNowPlayingMovieInCinemaLayoutBinding
import com.example.movieku.databinding.ItemSearchLayoutBinding

class NowPlayingMovieListAdapter(private var listItem:List<Movie> = emptyList(), private val listener: NowPlayingMovieInCinemaListener):
    RecyclerView.Adapter<NowPlayingMovieListAdapter.MyAdapter>() {
    inner class MyAdapter(private val binding: ItemNowPlayingMovieInCinemaLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Movie){
            with(binding){
                Glide.with(root)
                    .load(item.posterPath)
                    .placeholder(R.drawable.iv_placeholder)
                    .centerCrop()
                    .into(ivPoster)

                tvTitleMovie.text = item.title
                tvGenre.text = item.genre
                tvMovieRate.text = root.resources.getString(R.string.label_rating_count_vote, item.voteRange, item.voteCount)
                root.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val binding = ItemNowPlayingMovieInCinemaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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