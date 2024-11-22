package com.example.movieku.ui.dashboard.movie

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.movieku.R
import com.example.movieku.adapter.movie.CinemaMovieViewPagerAdapter
import com.example.movieku.databinding.FragmentCinemaMovieBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy

class CinemaMovieFragment : Fragment() {

    private var _binding: FragmentCinemaMovieBinding? = null
    private val binding get() = _binding!!

    private var selectedTab: Int = 0

    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCinemaMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedTab = arguments?.getInt(KEY_CINEMA_MOVIE_TAB_SELECTION, 0) ?: 0

        binding.viewPagerCinemaMovie.adapter = CinemaMovieViewPagerAdapter(requireParentFragment())


        binding.viewPagerCinemaMovie.registerOnPageChangeCallback(object:OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(!isFirst){
                    selectedTab = position
                }
            }
        })


        TabLayoutMediator(
            binding.tvTabLayout,
            binding.viewPagerCinemaMovie,
            object : TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = if (position == 0) "Now Playing" else "Coming Soon"
                }
            }).attach()

        binding.viewPagerCinemaMovie.post {
            binding.viewPagerCinemaMovie.setCurrentItem(selectedTab, true)
            isFirst = false
        }

        //menggunakan post untuk load langsung fragment yang dituju tanpa melewati fragment pertama

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_CINEMA_MOVIE_TAB_SELECTION = "KEY CINEMA MOVIE TAB SELECTION"
    }
}