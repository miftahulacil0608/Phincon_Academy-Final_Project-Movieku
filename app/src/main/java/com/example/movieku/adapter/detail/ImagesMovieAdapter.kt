package com.example.movieku.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieku.R
import com.example.movieku.databinding.ItemMovieAdapterBinding

//TODO diff util callback
class ImagesMovieAdapter(private var listItem: List<String> = emptyList()) :
    RecyclerView.Adapter<ImagesMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemMovieAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                Glide.with(root)
                    .load(item)
                    .centerCrop()
                    .placeholder(R.drawable.iv_placeholder)
                    .into(ivBackdrop)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemMovieAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewListData(newListItem: List<String>) {
        listItem = newListItem
        notifyDataSetChanged()
    }
}