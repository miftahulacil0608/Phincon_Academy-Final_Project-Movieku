package com.example.movieku.adapter.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movieku.R
import com.example.movieku.adapter.schedule.contract.DateSelectionListener
import com.example.movieku.databinding.CalendarItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateSelectionAdapter(
    private var listDate: List<Date> = emptyList(),
    private val listener: DateSelectionListener
) :
    RecyclerView.Adapter<DateSelectionAdapter.MyViewHolder>() {
        private var selectionPosition = RecyclerView.NO_POSITION

    inner class MyViewHolder(private val binding: CalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Date, position:Int) {
            val dayFormatters = SimpleDateFormat("EEEE",Locale.getDefault())
            val dateFormatters = SimpleDateFormat("dd", Locale.getDefault())

            val dayResult = dayFormatters.format(item)
            val dateResult = dateFormatters.format(item)

            with(binding){
                dayName.text = dayResult
                dayOfMonth.text = dateResult

                changeColor(position)

                root.setOnClickListener {
                    val previousPosition = selectionPosition
                    selectionPosition = position

                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectionPosition)

                    listener.addDaySelection(item)
                }
            }
        }

        private fun changeColor(position: Int){
            if (selectionPosition==position){
                binding.root.strokeWidth=1
                binding.root.setCardBackgroundColor((ContextCompat.getColor(binding.root.context, R.color.md_theme_primary)))
                binding.dayName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_onPrimary))
                binding.dayOfMonth.setTextColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_onPrimary))
            }else{
                binding.root.strokeWidth=0
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_onPrimary))
                binding.dayName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_primary))
                binding.dayOfMonth.setTextColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_primary))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CalendarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listDate.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listDate[position]
        holder.bind(item, position)
    }

    fun addNewListData(newListItem: List<Date>) {
        listDate = newListItem
        notifyDataSetChanged()
    }
}