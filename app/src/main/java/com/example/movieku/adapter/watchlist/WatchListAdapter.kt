package com.example.movieku.adapter.watchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.movie.watchlist.WatchListUI
import com.example.movieku.R
import com.example.movieku.databinding.ItemWatchlistLayoutBinding

class WatchListAdapter(private var listItem:List<WatchListUI> = emptyList(), private val listener:WatchListListener):RecyclerView.Adapter<WatchListAdapter.MyAdapter>() {
    inner class MyAdapter(private val binding: ItemWatchlistLayoutBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(item:WatchListUI){
           with(binding){
               Glide.with(root)
                   .load(item.movieImage)
                   .placeholder(R.drawable.iv_placeholder)
                   .centerCrop()
                   .into(ivPoster)
               tvGenreMovie.text = item.movieGenre
               tvDuration.text = item.duration
               tvTitleMovie.text = item.movieName
               tvRateAge.text = item.pgAge
               tvCodeLanguage.text = item.codeLanguage
               tvRating.text= root.resources.getString(R.string.label_rating_movie, item.ratingCount)
               tvVote.text = root.resources.getString(R.string.label_total_vote, item.voteCount)
               tvReleaseDate.text = item.movieReleaseDate
               if (item.status != "Released"){
                   layoutRating.visibility = View.GONE
                   layoutRelease.visibility = View.VISIBLE
               }else{
                   layoutRating.visibility = View.VISIBLE
                   layoutRelease.visibility = View.GONE
               }

               btnDelete.setOnClickListener {
                   listener.onClickDelete(item.movieId)
               }
               root.setOnClickListener {
                   listener.onClickItem(item.movieId)
               }
           }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val binding = ItemWatchlistLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAdapter(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewData(newList:List<WatchListUI>){
        listItem = newList
        notifyDataSetChanged()
    }
}