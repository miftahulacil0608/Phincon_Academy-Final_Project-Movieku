package com.example.movieku.adapter.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ScheduleCinema
import com.example.movieku.R
import com.example.movieku.databinding.ItemScheduleWatchBinding
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//TODO ganti makek diffutilcallback saja
class ScheduleMovieAdapter(private var listItem: List<ScheduleCinema> = emptyList(), private val onClick:(ScheduleCinema)->Unit) :
    RecyclerView.Adapter<ScheduleMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemScheduleWatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleCinema) {
            with(binding) {
                tvHours.text = item.timeWatch
                tvStudio.text = item.studio

                val givenTime = item.timeWatch
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                val givenLocalTime = LocalTime.parse(givenTime, formatter)

                val currentTime = LocalTime.now()

                if (currentTime.isAfter(givenLocalTime)){
                    root.isEnabled = false
                    root.alpha = 0.5f
                    tvHours.setTextColor(ContextCompat.getColor(root.context,R.color.md_text_secondary))
                    tvStudio.setTextColor(ContextCompat.getColor(root.context,R.color.md_text_secondary))
                }else{
                    root.isEnabled = true
                    root.alpha = 1.0f
                }

                root.setOnClickListener {
                    root.setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.md_theme_primary))
                    tvHours.setTextColor(ContextCompat.getColor(root.context, R.color.md_theme_onPrimary))
                    tvStudio.setTextColor(ContextCompat.getColor(root.context, R.color.md_theme_onPrimary))
                    onClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemScheduleWatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    fun addNewListData(newListItem: List<ScheduleCinema>) {
        listItem = newListItem
        notifyDataSetChanged()
    }
}