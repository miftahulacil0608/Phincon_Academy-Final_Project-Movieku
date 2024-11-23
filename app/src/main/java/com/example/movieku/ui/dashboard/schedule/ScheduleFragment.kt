package com.example.movieku.ui.dashboard.schedule

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.model.movie.cinema.Cinema
import com.example.domain.model.movie.cinema.ScheduleCinema
import com.example.domain.model.movie.order.OrderMovie
import com.example.domain.model.movie.schedule.ScheduleDataClass
import com.example.movieku.R
import com.example.movieku.adapter.schedule.CinemaMovieAdapter
import com.example.movieku.adapter.schedule.DateSelectionAdapter
import com.example.movieku.adapter.schedule.contract.DateSelectionListener
import com.example.movieku.adapter.schedule.contract.ScheduleMovieListener
import com.example.movieku.databinding.FragmentScheduleBinding
import com.example.movieku.ui.dashboard.booking.OrderDetailsFragment
import com.example.movieku.utils.HelperDateConvert
import java.util.Calendar
import java.util.Date

class ScheduleFragment : Fragment(), ScheduleMovieListener, DateSelectionListener {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val scheduleDetails by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(KEY_SCHEDULE_FRAGMENT, ScheduleDataClass::class.java)
        } else {
            arguments?.getParcelable(KEY_SCHEDULE_FRAGMENT)
        }
    }

    private var selectedDay = Calendar.getInstance().time


    private val dateSelectionAdapter2 by lazy{
        DateSelectionAdapter(listener = this@ScheduleFragment)
    }

    private val cinemaMovieAdapter by lazy {
        CinemaMovieAdapter(listener = this@ScheduleFragment)
    }

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
        binding.rvDateSelection.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCinema.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        binding.rvCinema.adapter = cinemaMovieAdapter
        binding.rvDateSelection.adapter = dateSelectionAdapter2

        binding.tvMonthYear.text = HelperDateConvert.getMontAndYear()
        dateSelectionAdapter2.addNewListData(HelperDateConvert.generateDates())

        cinemaMovieAdapter.addNewListData(listCinema(selectedDay))
    }

    private fun listCinema(currentDate: Date): List<Cinema> {
        return listOf(
            Cinema(
                1,
                date = currentDate,
                R.drawable.iv_cinema,
                "Araya XXI",
                "Malang",
                scheduleCinema = listOf(
                    ScheduleCinema("08:00", "Studio 1"),
                    ScheduleCinema("12:00", "Studio 2"),
                    ScheduleCinema("14:00", "Studio 3"),
                    ScheduleCinema("20:00", "Studio 4")
                )
            ),
            Cinema(
                2,
                date = currentDate,
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
                date = currentDate,
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

    override fun addDaySelection(item: Date) {
        selectedDay = item
        cinemaMovieAdapter.addNewListData(listCinema(item))
    }

    override fun onItemScheduleClick(cinema: String, dateWatch: String,timeWatch: String, studio: String) {
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
                dateWatch = dateWatch,
                cinema,
                timeWatch,
                studio,
                it.duration
            )
        }
        val bundle = bundleOf(OrderDetailsFragment.KEY_ORDER_DETAILS to orderMovie)
        findNavController().navigate(
            R.id.action_scheduleFragment_to_orderDetailsFragment,
            bundle
        )
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_SCHEDULE_FRAGMENT = "KEY SCHEDULE FRAGMENT"
    }
}