package com.example.movieku.ui.dashboard.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.domain.model.DetailMovie
import com.example.movieku.R
import com.example.movieku.databinding.FragmentDetailMovieBinding
import com.example.movieku.utils.ResultState
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.launch

class DetailMovieFragment : Fragment() {

    private val detailMovieViewModel by activityViewModels<DetailMovieViewModel>()
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments?.getInt("ID_MOVIE", 0)
        arguments?.let {
            detailMovieViewModel.getDetailMovie(it)
        }

        fetchDetailMovie()

    }

    private fun fetchDetailMovie() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailMovieViewModel.detailMovieData.collect {
                when (it) {
                    ResultState.Loading -> {
                        //shimmer on
                    }
                    is ResultState.Success -> {
                        //shimmer off
                        setupDataUI(it.data)
                    }
                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupDataUI(item: DetailMovie) {
        with(binding) {
            setupGlideImages(item.backdropPath, ivBackdrop)
            tvTitleMovie.text = item.originalTitle
            tvGenre.text = resources.getString(R.string.label_genre, item.genre)
            tvDuration.text = resources.getString(R.string.label_duration, item.duration)
            tvDirector.text = resources.getString(R.string.label_director, item.director)
            setupGlideImages(item.posterPath, ivPoster)
            tvOverview.text = item.overview
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
}