package com.example.movieku.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.DirectorOrActorItem
import com.example.domain.model.Movie
import com.example.movieku.R
import com.example.movieku.databinding.ItemCastAndDirectorsRecyclerViewLayoutBinding
import com.example.movieku.databinding.ItemMovieAdapterBinding
import com.example.movieku.databinding.ItemUpcomingMovieBinding

class DirectorsAndActorsMovieAdapter(private var listItem: List<DirectorOrActorItem> = emptyList()) :
    RecyclerView.Adapter<DirectorsAndActorsMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemCastAndDirectorsRecyclerViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DirectorOrActorItem) {
            with(binding) {
                Glide.with(root)
                    .load(item.profileUrl)
                    .placeholder(R.drawable.ic_no_profile)
                    .circleCrop()
                    .into(ivProfile)
                tvName.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCastAndDirectorsRecyclerViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewListData(newListItem: List<DirectorOrActorItem>) {
        listItem = newListItem
        notifyDataSetChanged()
    }
}