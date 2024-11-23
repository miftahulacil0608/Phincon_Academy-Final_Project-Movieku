package com.example.movieku.ui.dashboard.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.domain.model.movie.DetailMovie
import com.example.domain.model.movie.schedule.ScheduleDataClass
import com.example.domain.model.movie.watchlist.WatchListUI
import com.example.movieku.R
import com.example.movieku.adapter.detail.DirectorsAndActorsMovieAdapter
import com.example.movieku.adapter.detail.ImagesMovieAdapter
import com.example.movieku.databinding.FragmentDetailMovieBinding
import com.example.movieku.ui.WatchTrailerActivity
import com.example.movieku.ui.dashboard.schedule.ScheduleFragment
import com.example.movieku.utils.ResultState
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.launch

class DetailMovieFragment : Fragment() {

    private val detailMovieViewModel by activityViewModels<DetailMovieViewModel>()
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val imagesMovieAdapter by lazy {
        ImagesMovieAdapter()
    }
    private val directorsMovieAdapter by lazy {
        DirectorsAndActorsMovieAdapter()
    }
    private val actorsMovieAdapter by lazy {
        DirectorsAndActorsMovieAdapter()
    }
    private lateinit var watchListItem : WatchListUI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        val arguments = arguments?.getInt(KEY_DETAIL_FRAGMENT, 0)
        arguments?.let { id ->
            detailMovieViewModel.setWatchList(id)
            detailMovieViewModel.getDetailMovie(id)
            binding.swipeRefresh.setOnRefreshListener {
                detailMovieViewModel.getDetailMovie(id)
            }
            binding.btnRetry.setOnClickListener {
                detailMovieViewModel.getDetailMovie(id)
            }
        }


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnFavorite.setOnClickListener {
            detailMovieViewModel.toggleFavoriteButton(watchListItem)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            detailMovieViewModel.isWatchLisExist.collect{
                if (it){
                    binding.btnFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_favorite_exist
                        )
                    )
                }else{
                    binding.btnFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_favorite_not_exist
                        )
                    )
                }
            }
        }


        //load detail movie
        viewLifecycleOwner.lifecycleScope.launch {
            detailMovieViewModel.detailMovieData.collect {
                when (it) {
                    ResultState.Loading -> {
                        //shimmer on
                        binding.swipeRefresh.isRefreshing = false

                        //dipisahin karena ketika di state loading button buy disable untuk 2 status released maupun !released
                        binding.btnBuyTicket.isVisible = false

                        showShimmer()
                    }

                    is ResultState.Success -> {
                        //shimmer off
                        binding.swipeRefresh.isRefreshing = false

                        if (it.data.status != "Released") {
                            binding.tvSoon.isVisible = true
                            binding.reviewLayout.isVisible = false
                            binding.btnBuyTicket.isVisible = false
                        } else {
                            binding.tvSoon.isVisible = false
                            binding.btnBuyTicket.isVisible = true
                        }

                        setupDataUI(it.data)

                        watchTrailer(it.data.videoUrl)

                        watchListItem = WatchListUI(
                            movieId = it.data.id,
                            movieName = it.data.originalTitle,
                            movieImage = it.data.posterPath,
                            ratingCount = it.data.rating,
                            voteCount = it.data.totalVote,
                            movieGenre = it.data.genre,
                            duration = it.data.duration,
                            movieReleaseDate = it.data.releaseDate,
                            pgAge = it.data.pgAge,
                            codeLanguage = it.data.codeLanguage,
                            status = it.data.status
                        )
                        imagesMovieAdapter.addNewListData(it.data.imageMovie)
                        directorsMovieAdapter.addNewListData(it.data.director)
                        actorsMovieAdapter.addNewListData(it.data.actors)
                        buttonBuy(it.data)

                        hideShimmer()
                    }

                    is ResultState.Error -> {
                        binding.swipeRefresh.isRefreshing = false
                        hideShimmer()
                        showError()
                    }
                }
            }
        }
    }



    private fun initRecyclerView() {
        binding.rvFootage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDirectors.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvActors.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFootage.adapter = imagesMovieAdapter
        binding.rvDirectors.adapter = directorsMovieAdapter
        binding.rvActors.adapter = actorsMovieAdapter
    }


    private fun setupDataUI(item: DetailMovie) {
        with(binding) {
            setupGlideImages(item.posterPath, ivBackdrop)
            tvTitleMovie.text = item.originalTitle
            tvGenre.text = item.genre
            tvDuration.text = item.duration

            tvRateAge.text = item.pgAge
            tvLabelLanguage.text = item.codeLanguage
            tvRatingCount.text = resources.getString(R.string.label_rating_movie, item.rating)
            tvRatingVote.text = resources.getString(R.string.label_total_vote, item.totalVote)
            ratingIndicator.rating = item.rating

            tvMovieGenre.text = item.genre
            tvAdultCategory.text = item.adult
            tvMovieLanguage.text = item.language

            tvMovieDescription.text = item.overview

            btnBuyTicket.text =
                resources.getString(R.string.label_buy_ticket, item.priceMovie / 1000)
        }
    }


    private fun buttonBuy(item: DetailMovie) {
        binding.btnBuyTicket.setOnClickListener {
            //pindahin langsung ke order details
            val scheduleDetails = ScheduleDataClass(
                item.id,
                item.originalTitle,
                item.posterPath,
                item.priceMovie,
                item.priceFee,
                item.pgAge,
                item.adult,
                item.codeLanguage,
                item.language,
                binding.tvRatingCount.text.toString(),
                item.genre,
                item.duration,
            )
            val bundleData = bundleOf(ScheduleFragment.KEY_SCHEDULE_FRAGMENT to scheduleDetails)
            findNavController().navigate(
                R.id.action_detailMovieFragment_to_scheduleFragment,
                bundleData
            )
        }
    }

    private fun watchTrailer(linkUrl:String){
        binding.btnTrailer.setOnClickListener {
            if (linkUrl == "Nothing"){
                Toast.makeText(requireContext(), "Can't Play Trailer", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(requireActivity(), WatchTrailerActivity::class.java)
                intent.putExtra(WatchTrailerActivity.KEY_WATCH_TRAILER,linkUrl)
                requireActivity().startActivity(intent)
            }

        }
    }

    private fun setupGlideImages(urlPath: String, into: ShapeableImageView) {
        Glide.with(requireContext())
            .load(urlPath)
            .centerCrop()
            .into(into)
    }

    private fun showShimmer() {
        with(binding.shimmerLayout) {
            startShimmer()
            isVisible = true
            binding.nestedScrollLayout.isVisible = false
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
        }
    }



    private fun hideShimmer() {
        with(binding.shimmerLayout) {
            stopShimmer()
            isVisible = false
            binding.nestedScrollLayout.isVisible = true
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
        }
    }

    private fun showError(){
        with(binding.shimmerLayout){
            stopShimmer()
            isVisible = false
            binding.nestedScrollLayout.isVisible = false
            binding.ivErrorState.isVisible = true
            binding.btnRetry.isVisible = true
        }
    }

    private fun showUI(isVisible: Boolean) {
        with(binding) {
            btnFavorite.isVisible = isVisible
            ivBackdrop.isVisible = isVisible

            tvGenre.isVisible = isVisible
            tvDuration.isVisible = isVisible

            tvTitleMovie.isVisible = isVisible

            tvRateAge.isVisible = isVisible
            tvLabelLanguage.isVisible = isVisible
            btnTrailer.isVisible = isVisible

            tvLabelReview.isVisible = isVisible
            ivStars.isVisible = isVisible
            tvRatingCount.isVisible = isVisible
            tvRatingVote.isVisible = isVisible
            ratingIndicator.isVisible = isVisible

            labelMovieGenre.isVisible = isVisible
            labelAdultCategory.isVisible = isVisible
            labelMovieLanguage.isVisible = isVisible

            tvMovieGenre.isVisible = isVisible
            tvAdultCategory.isVisible = isVisible
            tvMovieLanguage.isVisible = isVisible
            tvMovieDescription.isVisible = isVisible
            rvFootage.isVisible = isVisible
            tvLabelDirectors.isVisible = isVisible
            rvDirectors.isVisible = isVisible
            tvLabelActors.isVisible = isVisible
            rvActors.isVisible = isVisible
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_DETAIL_FRAGMENT = "KEY MOVIE ID"
    }
}