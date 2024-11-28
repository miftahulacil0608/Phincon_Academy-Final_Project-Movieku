package com.example.movieku.ui.dashboard.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.movie.search.SearchMovieItem
import com.example.movieku.R
import com.example.movieku.adapter.search.SearchMovieAdapter
import com.example.movieku.adapter.search.SearchMovieListener
import com.example.movieku.databinding.FragmentSearchMovieBinding
import com.example.movieku.ui.dashboard.detail.DetailMovieFragment
import com.example.movieku.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMovieFragment : Fragment(), SearchMovieListener {
    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!

    private val searchMovieAdapter by lazy {
        SearchMovieAdapter(listener = this@SearchMovieFragment)
    }

    private val searchMovieViewModel by viewModels<SearchMovieViewModel>()


    private var movieQuery : String = ""
    private var isFirstVisit = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        binding.btnSearchView.requestFocus()
        showKeyboard()

        if (isFirstVisit){
            binding.swipeRefreshLayout.isEnabled = false
            isFirstVisit = false
        }else{
            binding.swipeRefreshLayout.isEnabled = true
        }

        //load data if movieQuery is not empty
        if (movieQuery.isNotEmpty()){
            getSearchData()
        }

        //search edittext conl action
        binding.btnSearchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                movieQuery = binding.btnSearchView.text.toString()
                searchMovieViewModel.searchMovie(movieQuery)
                getSearchData()
                binding.ivEmptyState.visibility = View.GONE
                hideKeyboard()
            }
            true
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            searchMovieViewModel.searchMovie(movieQuery)
        }

        binding.btnSearchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()){
                    movieQuery = s.toString()
                    searchMovieViewModel.searchMovie(movieQuery)
                }
            }

        })

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRetry.setOnClickListener {
            searchMovieViewModel.searchMovie(movieQuery)
        }

    }

    private fun getSearchData(){
        viewLifecycleOwner.lifecycleScope.launch {
            searchMovieViewModel.searchMovieData.collect {
                when (it) {
                    ResultState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showSimmer()
                    }

                    is ResultState.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        hideShimmer()

                        if (it.data.dataMovie.isEmpty()) {
                            binding.ivEmptyState.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.iv_no_result_search
                                )
                            )
                            binding.ivEmptyState.visibility = View.VISIBLE
                        } else {
                            binding.ivEmptyState.visibility = View.GONE
                        }
                        //insert data to adapter
                        searchMovieAdapter.addNewData(it.data.dataMovie)
                    }
                    is ResultState.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        hideShimmer()
                        showError()
                    }
                }
            }
        }
    }

    private fun showSimmer(){
        with(binding.shimmerLayout){
            startShimmer()
            isVisible = true
            binding.rvSearch.isVisible = false
            binding.ivEmptyState.isVisible = false
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
        }
    }
    private fun hideShimmer(){
        with(binding.shimmerLayout){
            stopShimmer()
            isVisible = false
            binding.ivEmptyState.isVisible = false
            binding.rvSearch.isVisible = true
            binding.ivErrorState.isVisible = false
            binding.btnRetry.isVisible = false
        }
    }
    private fun showError(){
        with(binding.shimmerLayout){
            stopShimmer()
            isVisible = false
            binding.ivEmptyState.isVisible = false
            binding.rvSearch.isVisible = false
            binding.ivErrorState.isVisible = true
            binding.btnRetry.isVisible = true
        }
    }


    private fun initRecyclerView() {
        binding.rvSearch.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.rvSearch.adapter = searchMovieAdapter
    }

    private fun showKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.btnSearchView, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus
        view?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }


    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onItemClick(item: SearchMovieItem) {
        val bundle = bundleOf(DetailMovieFragment.KEY_DETAIL_FRAGMENT to item.id)
        findNavController().navigate(R.id.action_searchMovieFragment_to_detailMovieFragment, bundle)
    }


}