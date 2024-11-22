package com.example.movieku.ui.dashboard.movie.nowplaying

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.movie.Movie
import com.example.movieku.R
import com.example.movieku.adapter.movie.NowPlayingMovieListAdapter
import com.example.movieku.adapter.movie.contract.NowPlayingMovieInCinemaListener
import com.example.movieku.databinding.FragmentNowPlayingMovieBinding
import com.example.movieku.ui.dashboard.detail.DetailMovieFragment
import com.example.movieku.utils.HelperDateConvert
import com.example.movieku.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class NowPlayingMovieFragment : Fragment(), NowPlayingMovieInCinemaListener {

    private var _binding: FragmentNowPlayingMovieBinding? = null
    private val binding get() = _binding!!

    private val nowPlayingMovieViewModel: NowPlayingMovieInCinemaViewModel by viewModels()

    private val nowPlayingMovieListAdapter by lazy{
        NowPlayingMovieListAdapter(listener = this@NowPlayingMovieFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNowPlayingMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            nowPlayingMovieViewModel.getNowPlayingMovieInCinema(HelperDateConvert.releaseDateGte(-30), HelperDateConvert.releaseDateLte())
        }
        binding.btnRetry.setOnClickListener {
            nowPlayingMovieViewModel.getNowPlayingMovieInCinema(HelperDateConvert.releaseDateGte(-30), HelperDateConvert.releaseDateLte())
        }

        initRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            nowPlayingMovieViewModel.nowPlayingMovieInCinema.collect{
                when(it){
                    ResultState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showShimmer()
                    }
                    is ResultState.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        nowPlayingMovieListAdapter.addNewData(it.data.dataMovie)
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
        binding.rvListNowPlayingMovie.layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        binding.rvListNowPlayingMovie.adapter = nowPlayingMovieListAdapter
    }

    private fun showShimmer() {
        with(binding.shimmerLayout) {
            startShimmer()
            isVisible = true
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
            binding.rvListNowPlayingMovie.isVisible = false
        }
    }

    private fun hideShimmer() {
        with(binding.shimmerLayout) {
            stopShimmer()
            isVisible = false
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
            binding.rvListNowPlayingMovie.isVisible = true
        }
    }

    private fun showError() {
        with(binding.shimmerLayout) {
            stopShimmer()
            isVisible = false
            binding.ivErrorState.isVisible = true
            binding.btnRetry.isVisible = true
            binding.rvListNowPlayingMovie.isVisible = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: Movie) {
        val bundle = bundleOf(DetailMovieFragment.KEY_DETAIL_FRAGMENT to item.id)
        findNavController().navigate(R.id.action_cinemaMovieFragment_to_detailMovieFragment, bundle)
    }
}