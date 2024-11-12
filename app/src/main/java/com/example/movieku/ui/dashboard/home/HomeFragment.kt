package com.example.movieku.ui.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.movieku.R
import com.example.movieku.adapter.HorizontalMarginItemDecoration
import com.example.movieku.adapter.home.NowPlayingMovieAdapter
import com.example.movieku.adapter.home.PopularMovieAdapter
import com.example.movieku.adapter.home.UpcomingMovieAdapter
import com.example.movieku.adapter.home.contract.NowPlayingMovieListener
import com.example.movieku.databinding.FragmentHomeBinding
import com.example.movieku.utils.ResultState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class HomeFragment : Fragment(), NowPlayingMovieListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewPagerPopularMovieAdapter by lazy {
        PopularMovieAdapter()
    }
    private val nowPlayingMovieAdapter by lazy {
        NowPlayingMovieAdapter(listener = this@HomeFragment)
    }
    private val upcomingMovieAdapter by lazy {
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
        upcomingMovie()

        fetchPopularMovie()
        fetchNoPlayingMovie()
        fetchUpcomingMovie()
    }

    private fun popularMovie() {
        binding.viewPagerPopularMovie.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
           // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
           // If you want a fading effect uncomment the next line:
            //page.alpha = 0.25f + (1 - abs(position))
        }
        binding.viewPagerPopularMovie.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            requireActivity(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.viewPagerPopularMovie.addItemDecoration(itemDecoration)

        binding.viewPagerPopularMovie.adapter = viewPagerPopularMovieAdapter
        TabLayoutMediator(binding.tabLayoutIndicator, binding.viewPagerPopularMovie,object :TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                //TODO nothing do it
            }

        }).attach()


    }


    private fun upcomingMovie() {
        binding.rvUpcomingMovie.adapter = upcomingMovieAdapter
        binding.rvUpcomingMovie.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun fetchPopularMovie() {
        homeViewModel.getPopularMovie()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED){
                homeViewModel.popularMovie.collect {
                    when (it) {
                        ResultState.Loading -> {
                            //shimmer on
                        }

                        is ResultState.Success -> {
                            //shimmer off
                            viewPagerPopularMovieAdapter.asyncDiffer.submitList(it.data.dataMovie)

                        }

                        is ResultState.Error -> {
                            //shimmer off
                            //show dialog atau layar berubah dan minta untuk retry
                        }
                    }
                }
            }

        }
    }


    private fun fetchNoPlayingMovie() {
        homeViewModel.getNowPlayingMovie()
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.nowPlayingMovie.collect {
                when (it) {
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

    private fun fetchUpcomingMovie() {
        homeViewModel.getUpComingMovie()
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.upComingMovie.collect {
                when (it) {
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