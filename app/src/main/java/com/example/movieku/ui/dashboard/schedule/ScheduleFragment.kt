package com.example.movieku.ui.dashboard.schedule

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.model.Cinema
import com.example.domain.model.OrderMovie
import com.example.domain.model.ScheduleCinema
import com.example.domain.model.ScheduleDataClass
import com.example.movieku.R
import com.example.movieku.adapter.schedule.CinemaMovieAdapter
import com.example.movieku.adapter.schedule.ScheduleMovieListener
import com.example.movieku.databinding.FragmentScheduleBinding
import com.example.movieku.ui.dashboard.booking.OrderDetailsFragment

class ScheduleFragment : Fragment(), ScheduleMovieListener {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val scheduleDetails by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(KEY_SCHEDULE_FRAGMENT, ScheduleDataClass::class.java)
        } else {
            arguments?.getParcelable(KEY_SCHEDULE_FRAGMENT)
        }
    }

    private val scheduleViewModel by activityViewModels<ScheduleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()

        scheduleDetails?.let {
            binding.apply {
                Glide.with(requireContext())
                    .load(it.poster)
                    .placeholder(R.drawable.iv_placeholder)
                    .into(ivBackdrop)
                Glide.with(requireContext())
                    .load(it.poster)
                    .placeholder(R.drawable.iv_placeholder)
                    .transform(RoundedCorners(20))
                    .into(ivPoster)
                tvTitleMovie.text = it.title
                tvRateAge.text = it.pgAge
                tvLabelCodeLanguage.text = it.codeLanguage
                tvMovieGenre.text = it.genre
                tvAdultCategory.text = it.adultCategory
                tvMovieLanguage.text = it.originalLanguage
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initRecyclerview() {
        val cinemaMovieAdapter = CinemaMovieAdapter(listener = this@ScheduleFragment)
        binding.rvCinema.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvCinema.adapter = cinemaMovieAdapter
        cinemaMovieAdapter.addNewListData(listCinema())
    }
    
    private fun listCinema(): List<Cinema> {
        return listOf(
            Cinema(
                1,
                R.drawable.iv_cinema,
                "Araya XXI",
                "Malang",
                scheduleCinema = listOf(
                    ScheduleCinema("10:00", "Studio 1"),
                    ScheduleCinema("12:00", "Studio 2"),
                    ScheduleCinema("14:00", "Studio 3"),
                    ScheduleCinema("20:00", "Studio 4")
                )
            ),
            Cinema(
                2,
                R.drawable.iv_cinema,
                "CGV Araya",
                "Dinoyo",
                scheduleCinema = listOf(
                    ScheduleCinema("11:00", "Studio 1"),
                    ScheduleCinema("12:00", "Studio 2"),
                )
            ),
            Cinema(
                3,
                R.drawable.iv_cinema,
                "Matos XXI",
                "Kota Malang",
                scheduleCinema = listOf(
                    ScheduleCinema("10:00", "Studio 1"),
                    ScheduleCinema("12:00", "Studio 2"),
                    ScheduleCinema("14:00", "Studio 3"),
                    ScheduleCinema("16:00", "Studio 4"),
                    ScheduleCinema("18:00", "Studio 5"),
                    ScheduleCinema("20:00", "Studio 6")
                )
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_SCHEDULE_FRAGMENT = "KEY SCHEDULE FRAGMENT"
    }

    override fun onItemScheduleClick(cinema: String, timeWatch: String, studio: String) {
        val orderMovie = scheduleDetails?.let {
            OrderMovie(
                it.id,
                it.title,
                it.poster,
                it.price,
                it.priceFee,
                it.pgAge,
                it.adultCategory,
                it.codeLanguage,
                it.originalLanguage,
                it.rating,
                it.genre,
                cinema,
                timeWatch,
                studio
            )
        }
        val bundle = bundleOf(OrderDetailsFragment.KEY_ORDER_DETAILS to orderMovie)
        findNavController().navigate(
            R.id.action_scheduleFragment_to_orderDetailsFragment,
            bundle
        )
    }
}