package com.example.movieku.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.movie.Movie
import com.example.domain.model.movie.search.SearchMovieItem
import com.example.domain.model.movie.tickets.Ticket
import com.example.movieku.R
import com.example.movieku.adapter.ticket.TicketAdapter
import com.example.movieku.adapter.ticket.TicketListener
import com.example.movieku.databinding.ItemSearchLayoutBinding
import com.example.movieku.databinding.ItemTicketsLayoutBinding

class SearchMovieAdapter(private var listItem:List<SearchMovieItem> = emptyList(), private val listener: SearchMovieListener):
    RecyclerView.Adapter<SearchMovieAdapter.MyAdapter>() {
    inner class MyAdapter(private val binding: ItemSearchLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: SearchMovieItem){
            with(binding){
                Glide.with(root)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.iv_placeholder)
                    .centerCrop()
                    .into(ivPoster)

                tvTitleMovie.text = item.title
                root.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val binding = ItemSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAdapter(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewData(newList:List<SearchMovieItem>){
        listItem = newList
        notifyDataSetChanged()
    }
}