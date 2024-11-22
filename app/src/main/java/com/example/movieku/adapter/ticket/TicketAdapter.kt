package com.example.movieku.adapter.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.movie.tickets.Ticket
import com.example.domain.model.movie.watchlist.WatchListUI
import com.example.movieku.R
import com.example.movieku.databinding.ItemTicketsLayoutBinding

class TicketAdapter(private var listItem:List<Ticket> = emptyList(), private val listener:TicketListener):
    RecyclerView.Adapter<TicketAdapter.MyAdapter>() {
     inner class MyAdapter(private val binding: ItemTicketsLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Ticket){
            with(binding){
                Glide.with(root)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.iv_placeholder)
                    .centerCrop()
                    .into(ivPoster)
                tvGenreMovie.text = item.genre
                tvDuration.text = item.duration
                tvTitleMovie.text = item.name
                tvLocationCinema.text = root.resources.getString(R.string.label_location_cinema, item.cinema, item.studio)
                tvDateAndTimeWatch.text = root.resources.getString(R.string.label_date_and_time_watch_movie, item.dateWatch, item.timeWatch)
                tvTickets.text = root.resources.getString(R.string.label_amount_have_ticket, item.quantity)

                root.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val binding = ItemTicketsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAdapter(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewData(newList:List<Ticket>){
        listItem = newList
        notifyDataSetChanged()
    }
}