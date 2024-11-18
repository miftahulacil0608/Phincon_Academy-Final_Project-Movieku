package com.example.movieku.ui.dashboard.schedule

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import com.google.android.material.textview.MaterialTextView
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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

    private val cinemaMovieAdapter by lazy {
        CinemaMovieAdapter(listener = this@ScheduleFragment)
    }

    private val currentDate by lazy {
        Calendar.getInstance().time
    }

    private val scheduleViewModel by activityViewModels<ScheduleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    //TODO update tanggal lalu logic untuk refresh date di recyclerview adapter
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

        dateSelection()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initRecyclerview() {
        binding.rvCinema.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvCinema.adapter = cinemaMovieAdapter
        cinemaMovieAdapter.addNewListData(listCinema(currentDate))
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

    //For date horizontal
    private fun dateSelection() {
        binding.tvMonthYear.text = resources.getString(
            R.string.label_month_year_date_selection,
            DateUtils.getMonthName(currentDate),
            DateUtils.getYear(currentDate)
        )

        //setup calendarviewmanager
        val myCalendarViewManager = object : CalendarViewManager {
            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                val dividerBottom = holder.itemView.findViewById<View>(R.id.divider_bottom)
                val dayOfName = holder.itemView.findViewById<MaterialTextView>(R.id.dayName)
                val dayOfMonth = holder.itemView.findViewById<MaterialTextView>(R.id.dayOfMonth)

                //DateUtils From Library
                val dayOfNameText = DateUtils.getDay3LettersName(date)
                val dayOfMonthText = DateUtils.getDayNumber(date)

                dayOfName.text = dayOfNameText
                dayOfMonth.text = dayOfMonthText

                //get current date
                val currentDate = Calendar.getInstance().time
                val differentMillis = date.time - currentDate.time
                //milli seconds * seconds * minutes * hours
                val differentInDays = (differentMillis / (1000 * 60 * 60 * 24)).toInt()

                //logic color state
                if (differentInDays > 7) {
                    dayOfName.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.md_text_secondary
                        )
                    )
                    dayOfMonth.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.md_text_secondary
                        )
                    )
                    dividerBottom.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.md_text_secondary
                        )
                    )
                } else {
                    if (isSelected) {
                        dayOfName.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.md_theme_onPrimary
                            )
                        )
                        dayOfMonth.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.md_theme_onPrimary
                            )
                        )
                        //load date again
                        cinemaMovieAdapter.addNewListData(listCinema(date))
                    } else {
                        dayOfName.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.md_theme_primary
                            )
                        )
                        dayOfMonth.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.md_theme_primary
                            )
                        )
                    }
                }

            }

            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                return if (isSelected) {
                    R.layout.selected_calendar_item
                } else {
                    R.layout.unselected_calendar_item
                }
            }

        }

        //setup calendarselectionmanager
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                val currentDate = Calendar.getInstance().time
                val differentMillis = date.time - currentDate.time
                val differentInDays = (differentMillis / (1000 * 60 * 60 * 24)).toInt()

                return differentInDays <= 7
            }

        }

        val myCalendarChangesObserver = object : CalendarChangesObserver {
            override fun whenWeekMonthYearChanged(
                weekNumber: String,
                monthNumber: String,
                monthName: String,
                year: String,
                date: Date
            ) {
                super.whenWeekMonthYearChanged(weekNumber, monthNumber, monthName, year, date)
            }

            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                super.whenSelectionChanged(isSelected, position, date)
            }

            override fun whenCalendarScrolled(dx: Int, dy: Int) {
                super.whenCalendarScrolled(dx, dy)
            }

            override fun whenSelectionRestored() {
                super.whenSelectionRestored()
            }

            override fun whenSelectionRefreshed() {
                super.whenSelectionRefreshed()
            }
        }


        binding.dateSelection.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            futureDaysCount = 30
            includeCurrentDate = true
            init()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_SCHEDULE_FRAGMENT = "KEY SCHEDULE FRAGMENT"
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