package com.example.movieku.ui.dashboard.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.domain.model.DetailMovie
import com.example.domain.model.OrderDetailMovie
import com.example.movieku.R
import com.example.movieku.adapter.home.DirectorsAndActorsMovieAdapter
import com.example.movieku.adapter.home.ImagesMovieAdapter
import com.example.movieku.databinding.FragmentDetailMovieBinding
import com.example.movieku.ui.dashboard.booking.OrderDetailsFragment
import com.example.movieku.utils.ResultState
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.launch

class DetailNowPlayingMovieFragment : Fragment() {

    private val detailNowPlayingMovieViewModel by activityViewModels<DetailNowPlayingMovieViewModel>()
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val imagesMovieAdapter by lazy{
        ImagesMovieAdapter()
    }
    private val directorsMovieAdapter by lazy{
        DirectorsAndActorsMovieAdapter()
    }
    private val actorsMovieAdapter by lazy{
        DirectorsAndActorsMovieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments?.getInt(KEY_NOW_PLAYING_MOVIE_ID, 0)
        arguments?.let {
            detailNowPlayingMovieViewModel.getDetailMovie(it)
        }

        fetchDetailMovie()
        initRecyclerView()



    }

    private fun fetchDetailMovie() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailNowPlayingMovieViewModel.detailMovieData.collect {
                when (it) {
                    ResultState.Loading -> {
                        //shimmer on
                    }

                    is ResultState.Success -> {
                        //shimmer off
                        setupDataUI(it.data)
                        imagesMovieAdapter.addNewListData(it.data.imageMovie)
                        directorsMovieAdapter.addNewListData(it.data.director)
                        actorsMovieAdapter.addNewListData(it.data.actors)
                        buttonBuy(it.data)
                    }

                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun buttonBuy(item:DetailMovie){
        binding.btnBuyTicket.setOnClickListener {
            //pindahin langsung ke order details
            val orderDetails = OrderDetailMovie(item.id, item.originalTitle, item.posterPath, item.priceMovie, item.priceFee,item.pgAge, item.codeLanguage)
            val bundleData = bundleOf(OrderDetailsFragment.KEY_ORDER_DETAILS to orderDetails)
            findNavController().navigate(R.id.action_detailMovieFragment_to_orderDetailsFragment, bundleData)
        }
    }

    private fun initRecyclerView(){
        binding.rvFootage.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDirectors.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvActors.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFootage.adapter = imagesMovieAdapter
        binding.rvDirectors.adapter = directorsMovieAdapter
        binding.rvActors.adapter = actorsMovieAdapter
    }

    private fun setupDataUI(item: DetailMovie) {
        with(binding) {
            setupGlideImages(item.backdropPath, ivBackdrop)
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

            btnBuyTicket.text = resources.getString(R.string.label_buy_ticket, item.priceMovie / 1000)
        }
    }

    private fun setupGlideImages(urlPath: String, into: ShapeableImageView) {
        Glide.with(requireContext())
            .load(urlPath)
            .centerCrop()
            .into(into)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_NOW_PLAYING_MOVIE_ID = "KEY MOVIE ID"
    }
}