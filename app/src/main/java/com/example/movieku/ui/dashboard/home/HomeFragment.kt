package com.example.movieku.ui.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movieku.R
import com.example.movieku.adapter.home.NowPlayingMovieAdapter
import com.example.movieku.adapter.home.PopularMovieAdapter
import com.example.movieku.adapter.home.UpcomingMovieAdapter
import com.example.movieku.adapter.home.contract.NowPlayingMovieListener
import com.example.movieku.databinding.FragmentHomeBinding
import com.example.movieku.utils.ResultState
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), NowPlayingMovieListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewPagerPopularMovieAdapter by lazy{
        PopularMovieAdapter()
    }
    private val nowPlayingMovieAdapter by lazy{
        NowPlayingMovieAdapter(listener=this@HomeFragment)
    }
    private val upcomingMovieAdapter by lazy{
        UpcomingMovieAdapter()
    }

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularMovie()
        nowPlayingMovie()
        upcomingMovie()

        fetchPopularMovie()
        fetchNoPlayingMovie()
        fetchUpcomingMovie()
    }

    private fun popularMovie(){
        binding.viewPagerPopularMovie.adapter = viewPagerPopularMovieAdapter
    }

    private fun nowPlayingMovie(){
        binding.rvNowPlayingMovie.adapter = nowPlayingMovieAdapter
        binding.rvNowPlayingMovie.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
    }

    private fun upcomingMovie(){
        binding.rvUpcomingMovie.adapter = upcomingMovieAdapter
        binding.rvUpcomingMovie.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
    }

    private fun fetchPopularMovie(){
        homeViewModel.getPopularMovie()
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.popularMovie.collect{
                when(it){
                    ResultState.Loading -> {
                        //shimmer on
                    }
                    is ResultState.Success -> {
                        //shimmer off
                        viewPagerPopularMovieAdapter.addNewListData(it.data.dataMovie)
                        binding.indicator.setViewPager(binding.viewPagerPopularMovie)

                    }
                    is ResultState.Error -> {
                        //shimmer off
                        //show dialog atau layar berubah dan minta untuk retry
                    }
                }
            }
        }
    }


    private fun fetchNoPlayingMovie(){
        homeViewModel.getNowPlayingMovie()
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.nowPlayingMovie.collect{
                when(it){
                    ResultState.Loading -> {
                        //shimmer on
                    }
                    is ResultState.Success -> {
                        //shimmer off
                        nowPlayingMovieAdapter.addNewListData(it.data.dataMovie)
                    }
                    is ResultState.Error -> {
                        //shimmer off
                        //show dialog atau layar berubah dan minta untuk retry
                        Log.d("Error", "fetchNoPlayingMovie: ${it.message}")
                    }

                }
            }
        }
    }

    private fun fetchUpcomingMovie(){
        homeViewModel.getUpComingMovie()
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.upComingMovie.collect{
                when(it){
                    ResultState.Loading -> {
                        //shimmer on
                    }
                    is ResultState.Success -> {
                        //shimmer off
                        upcomingMovieAdapter.addNewListData(it.data.dataMovie)
                        Log.d("DataUpcoming", "UpComing: ${it.data.dataMovie}")

                    }
                    is ResultState.Error -> {
                        //shimmer off
                        //show dialog atau layar berubah dan minta untuk retry
                        Log.d("Error", "UpComing: ${it.message}")

                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemNowPlayingClick(item: Int) {
        val bundle = bundleOf("ID_MOVIE" to item)
        findNavController().navigate(R.id.action_navigation_home_to_detailMovieFragment, bundle)
    }
}