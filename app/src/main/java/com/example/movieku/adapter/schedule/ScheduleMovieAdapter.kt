package com.example.movieku.adapter.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ScheduleCinema
import com.example.movieku.R
import com.example.movieku.databinding.ItemScheduleWatchBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

//TODO ganti makek diffutilcallback saja
class ScheduleMovieAdapter(private val date: Date, private var listItem: List<ScheduleCinema> = emptyList(), private val onClick:(ScheduleCinema)->Unit) :
    RecyclerView.Adapter<ScheduleMovieAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemScheduleWatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleCinema) {
            with(binding) {
                tvHours.text = item.timeWatch
                tvStudio.text = item.studio

                //format tanggal
                val dayFormatter = SimpleDateFormat("dd", Locale.getDefault())
                //format waktu
                val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

                val givenDate = date

                //pisahin datenya aja
                val dayGiven = dayFormatter.format(givenDate)
                val dayGivenResult = dayFormatter.parse(dayGiven)
                //pisahin waktunya
                val timeGiven = timeFormatter.format(givenDate)
                val timeGivenResult = timeFormatter.parse(timeGiven)

                val currentDateTime = Calendar.getInstance().time
                // ambil date sekarang
                val currentDay = dayFormatter.format(currentDateTime)
                val currentDayResult = dayFormatter.parse(currentDay)
                // ambil waktu dari time cinema
                val cinemaTimeResult = timeFormatter.parse(item.timeWatch)

                if (currentDayResult != null && timeGivenResult != null){
                    when{
                        currentDayResult == dayGivenResult && timeGivenResult.after(cinemaTimeResult) ->{
                            //disable
                            root.isEnabled = false
                            root.alpha = 0.5f
                            tvHours.setTextColor(ContextCompat.getColor(root.context,R.color.md_text_secondary))
                            tvStudio.setTextColor(ContextCompat.getColor(root.context,R.color.md_text_secondary))

                        }
                        currentDayResult == dayGivenResult && timeGivenResult.before(cinemaTimeResult) -> {
                            //enable
                            root.isEnabled = true
                            root.alpha = 1.0f
                        }
                        else->{
                            root.isEnabled = true
                            root.alpha = 1.0f
                            //tampilin
                        }
                    }
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