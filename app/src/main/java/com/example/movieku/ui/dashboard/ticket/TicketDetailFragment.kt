package com.example.movieku.ui.dashboard.ticket

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movieku.R
import com.example.movieku.databinding.FragmentTicketDetailBinding
import com.example.movieku.databinding.FragmentTicketsBinding
import kotlinx.coroutines.launch

class TicketDetailFragment : Fragment() {

    private var _binding: FragmentTicketDetailBinding? = null
    private val binding get() = _binding!!

    private val ticketViewModel by activityViewModels<TicketViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            ticketViewModel.detailTicket.collect{
                if (it != null){
                    with(binding){
                        Glide.with(root)
                            .load(it.imageUrl)
                            .placeholder(R.drawable.iv_placeholder)
                            .centerCrop()
                            .into(ivPoster)
                        tvLabelTitleMovie.text = it.name
                        tvRateAge.text = it.rateAge
                        tvLabelLanguage.text = it.codeLanguage
                        tvCinemaAndStudio.text = resources.getString(R.string.label_cinema_and_studio, it.cinema, it.studio)
                        tvDateWatch.text = it.dateWatch
                        tvTimeWatch.text = it.timeWatch
                        tvSeats.text = resources.getString(R.string.label_row_and_number_seats, it.seatRow, it.seatNumbers)
                        tvCost.text = resources.getString(R.string.label_price, it.price)
                        tvCodeTicket.text = it.codeTicket
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val KEY_DETAIL_FRAGMENT = "KEY DETAIL FRAGMENT"
    }
}