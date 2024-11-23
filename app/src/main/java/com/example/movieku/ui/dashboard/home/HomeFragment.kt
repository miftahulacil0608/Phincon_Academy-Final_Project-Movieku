package com.example.movieku.ui.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.movieku.R
import com.example.movieku.adapter.HorizontalMarginItemDecoration
import com.example.movieku.adapter.home.NowPlayingMoviesAdapter
import com.example.movieku.adapter.home.UpcomingMovieAdapter
import com.example.movieku.adapter.home.contract.NowPlayingMovieListener
import com.example.movieku.databinding.FragmentHomeBinding
import com.example.movieku.ui.dashboard.detail.DetailMovieFragment
import com.example.movieku.ui.dashboard.movie.CinemaMovieFragment
import com.example.movieku.utils.HelperDateConvert
import com.example.movieku.utils.ResultState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import kotlin.math.abs

class HomeFragment : Fragment(), NowPlayingMovieListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val nowPlayingAdapter by lazy {
        NowPlayingMoviesAdapter(this@HomeFragment)
    }

    private val upcomingMovieAdapter by lazy {
        UpcomingMovieAdapter(listener = this@HomeFragment)
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

        nowPlayingMovieViewPagerAdapter()
        upcomingMovieAdapter()

        //swipe refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.getNowPlayingMovie(
                HelperDateConvert.releaseDateGte(-30),
                HelperDateConvert.releaseDateLte()
            )
            homeViewModel.getUpComingMovie(releaseDateGte = HelperDateConvert.releaseDateGte(1))
        }

        //btn search
        binding.btnSearchView.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_searchMovieFragment)
        }

        //btn retry
        binding.btnRetry.setOnClickListener {
            homeViewModel.getNowPlayingMovie(
                HelperDateConvert.releaseDateGte(-30),
                HelperDateConvert.releaseDateLte()
            )
            homeViewModel.getUpComingMovie(
                releaseDateGte = HelperDateConvert.releaseDateGte(
                    1
                )
            )
        }

        binding.btnSeeAllNowPlaying.setOnClickListener {
            val bundle = bundleOf(CinemaMovieFragment.KEY_CINEMA_MOVIE_TAB_SELECTION to 0)
            findNavController().navigate(R.id.action_navigation_home_to_cinemaMovieFragment, bundle)
        }

        binding.btnSeeAllUpcomingMovie.setOnClickListener {
            val bundle = bundleOf(CinemaMovieFragment.KEY_CINEMA_MOVIE_TAB_SELECTION to 1)
            findNavController().navigate(R.id.action_navigation_home_to_cinemaMovieFragment,bundle)
        }



        //now playing movie collect
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.nowPlayingMovie.collect {
                when (it) {
                    ResultState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showShimmer()
                    }

                    is ResultState.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        nowPlayingAdapter.asyncDiffer.submitList(it.data.dataMovie)
                        hideShimmer()
                    }

                    is ResultState.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showError()
                    }
                }
            }
        }

        //upcoming movie collect
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.upComingMovie.collect {
                when (it) {
                    ResultState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showShimmer()
                    }

                    is ResultState.Success -> {
                        //shimmer off
                        upcomingMovieAdapter.addNewListData(it.data.dataMovie)
                        hideShimmer()
                    }

                    is ResultState.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showError()
                    }

                }
            }
        }

    }

    private fun nowPlayingMovieViewPagerAdapter() {
        binding.viewPagerNowPlaying.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            //page.alpha = 0.25f + (1 - abs(position))
        }

        binding.viewPagerNowPlaying.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireActivity(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.viewPagerNowPlaying.addItemDecoration(itemDecoration)

        binding.viewPagerNowPlaying.adapter = nowPlayingAdapter

        TabLayoutMediator(
            binding.tabLayoutIndicator,
            binding.viewPagerNowPlaying,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                //DO NOTHING
                }
            }).attach()
    }


    private fun upcomingMovieAdapter() {
        binding.rvUpcomingMovie.adapter = upcomingMovieAdapter
        binding.rvUpcomingMovie.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showShimmer() {
        with(binding.shimmerLayout) {
            startShimmer()
            isVisible = true
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
            showUI(false)
        }
    }

    private fun hideShimmer() {
        with(binding.shimmerLayout) {
            stopShimmer()
            isVisible = false
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
            showUI(true)
        }
    }

    private fun showError() {
        with(binding.shimmerLayout) {
            stopShimmer()
            isVisible = false
            binding.ivErrorState.isVisible = true
            binding.btnRetry.isVisible = true
            showUI(false)
        }
    }

    private fun showUI(isVisible: Boolean) {
        with(binding) {
            tvNowPlayingMovie.isVisible = isVisible
            btnSeeAllNowPlaying.isVisible = isVisible
            viewPagerNowPlaying.isVisible = isVisible
            tabLayoutIndicator.isVisible = isVisible
            tvUpcoming.isVisible = isVisible
            btnSeeAllUpcomingMovie.isVisible = isVisible
            rvUpcomingMovie.isVisible = isVisible
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemNowPlayingClick(item: Int) {
        val bundle = bundleOf(DetailMovieFragment.KEY_DETAIL_FRAGMENT to item)
        findNavController().navigate(R.id.action_navigation_home_to_detailMovieFragment, bundle)
    }

}