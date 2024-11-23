package com.example.movieku.adapter.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.movie.cinema.Cinema
import com.example.movieku.R
import com.example.movieku.adapter.schedule.contract.ScheduleMovieListener
import com.example.movieku.databinding.ItemCinemaLayoutBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CinemaMovieAdapter(private var listItem: List<Cinema> = emptyList(), private val listener: ScheduleMovieListener) :
    RecyclerView.Adapter<CinemaMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemCinemaLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cinema) {
            with(binding) {
                Glide.with(root)
                    .load(item.iconCinema)
                    .placeholder(R.drawable.iv_placeholder)
                    .centerCrop()
                    .into(ivCinema)
                tvCinema.text = item.name
                tvCinemaAddress.text = item.address

                val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val dateFormats = dateFormatter.format(item.date)

                val scheduleMovieAdapter = ScheduleMovieAdapter(item.date,item.scheduleCinema){
                    listener.onItemScheduleClick(item.name, dateFormats ,it.timeWatch, it.studio)
                }

                val gridLayoutManager = GridLayoutManager(binding.root.context, 3, GridLayoutManager.VERTICAL,false)
                rvTimeWatchMovie.layoutManager = gridLayoutManager
                rvTimeWatchMovie.adapter = scheduleMovieAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCinemaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewListData(newListItem: List<Cinema>) {
        listItem = newListItem
        notifyDataSetChanged()
    }
}