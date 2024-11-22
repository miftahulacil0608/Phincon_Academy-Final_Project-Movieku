package com.example.movieku.ui.dashboard.movie.upcoming

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.movie.Movie
import com.example.movieku.R
import com.example.movieku.adapter.movie.UpComingMovieListAdapter
import com.example.movieku.adapter.movie.contract.UpComingMovieInCinemaListener
import com.example.movieku.databinding.FragmentNowPlayingMovieBinding
import com.example.movieku.databinding.FragmentUpcomingMovieBinding
import com.example.movieku.ui.dashboard.detail.DetailMovieFragment
import com.example.movieku.utils.HelperDateConvert
import com.example.movieku.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingMovieFragment : Fragment(), UpComingMovieInCinemaListener {

    private var _binding: FragmentUpcomingMovieBinding? = null
    private val binding get() = _binding!!

    private val upComingMovieViewModel: UpComingMovieInCinemaViewModel by viewModels()

    private val upComingMovieListAdapter by lazy{
        UpComingMovieListAdapter(listener = this@UpcomingMovieFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            upComingMovieViewModel.getUpcomingMovieInCinema(HelperDateConvert.releaseDateLte())
        }
        binding.btnRetry.setOnClickListener {
            upComingMovieViewModel.getUpcomingMovieInCinema(HelperDateConvert.releaseDateLte())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            upComingMovieViewModel.upcomingMovieInCinema.collect{
                when(it){
                    ResultState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showShimmer()
                    }
                    is ResultState.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        upComingMovieListAdapter.addNewData(it.data.dataMovie)
                        hideShimmer()
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

    private fun initRecyclerView(){
        binding.rvListUpcomingMovie.layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        binding.rvListUpcomingMovie.adapter = upComingMovieListAdapter
    }


    private fun showShimmer() {
        with(binding.shimmerLayout) {
            startShimmer()
            isVisible = true
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
            binding.rvListUpcomingMovie.isVisible = false
        }
    }

    private fun hideShimmer() {
        with(binding.shimmerLayout) {
            stopShimmer()
            isVisible = false
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
            binding.rvListUpcomingMovie.isVisible = true
        }
    }

    private fun showError() {
        with(binding.shimmerLayout) {
            stopShimmer()
            isVisible = false
            binding.ivErrorState.isVisible = true
            binding.btnRetry.isVisible = true
            binding.rvListUpcomingMovie.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: Movie) {
        val bundle = bundleOf(DetailMovieFragment.KEY_DETAIL_FRAGMENT to item.id)
        findNavController().navigate(R.id.action_cinemaMovieFragment_to_detailMovieFragment, bundle)    }
}