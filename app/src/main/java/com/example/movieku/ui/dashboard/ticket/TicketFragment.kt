package com.example.movieku.ui.dashboard.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.movie.tickets.Ticket
import com.example.movieku.R
import com.example.movieku.adapter.ticket.TicketAdapter
import com.example.movieku.adapter.ticket.TicketListener
import com.example.movieku.databinding.FragmentTicketsBinding
import com.example.movieku.utils.ResultState
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class TicketFragment : Fragment(), TicketListener {

    private var _binding: FragmentTicketsBinding? = null
    private val binding get() = _binding!!

    private val ticketViewModel by activityViewModels<TicketViewModel>()
    private val ticketAdapter by lazy {
        TicketAdapter(listener = this@TicketFragment)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            ticketViewModel.getTickets()
        }

        binding.btnRetry.setOnClickListener {
            ticketViewModel.getTickets()
        }

        ticketViewModel.getTickets()

        viewLifecycleOwner.lifecycleScope.launch {
            ticketViewModel.tickets.collect {
                when (it) {
                    ResultState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showShimmer()
                    }

                    is ResultState.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        if (it.data.isEmpty()) {
                            hideShimmer()
                            binding.ivEmptyTickets.isVisible = true
                        } else {
                            ticketAdapter.addNewData(it.data)
                            hideShimmer()
                            binding.ivEmptyTickets.isVisible = false
                            binding.rvTickets.isVisible = true
                        }
                    }

                    is ResultState.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        hideShimmer()
                        showError()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvTickets.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvTickets.adapter = ticketAdapter
    }


    private fun showShimmer() {
        with(binding.shimmerLayout) {
            startShimmer()
            isVisible = true
            binding.rvTickets.isVisible = false
            binding.ivEmptyTickets.isVisible = false
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
        }
    }

    private fun hideShimmer() {
        with(binding.shimmerLayout) {
            stopShimmer()
            isVisible = false
            binding.rvTickets.isVisible = true
            binding.ivEmptyTickets.isVisible = false
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
        }
    }

    private fun showError(){
        with(binding.shimmerLayout){
            stopShimmer()
            isVisible = false
            binding.rvTickets.isVisible = false
            binding.ivEmptyTickets.isVisible = false
            binding.ivErrorState.isVisible = true
            binding.btnRetry.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: Ticket) {
        findNavController().navigate(R.id.action_navigation_ticket_to_ticketDetailFragment).apply {
            ticketViewModel.detailTicket(item)
        }
    }
}