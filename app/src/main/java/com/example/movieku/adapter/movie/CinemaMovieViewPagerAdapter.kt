package com.example.movieku.adapter.movie

// ui/ViewPagerAdapter.kt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieku.ui.dashboard.movie.nowplaying.NowPlayingMovieFragment
import com.example.movieku.ui.dashboard.movie.upcoming.UpcomingMovieFragment

class CinemaMovieViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NowPlayingMovieFragment()
            1 -> UpcomingMovieFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}
