package com.example.movieku.ui.dashboard.watchlist

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieku.R
import com.example.movieku.adapter.watchlist.WatchListAdapter
import com.example.movieku.adapter.watchlist.WatchListListener
import com.example.movieku.databinding.FragmentWatchlistBinding
import com.example.movieku.ui.dashboard.detail.DetailMovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchlistFragment : Fragment(), WatchListListener {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private val watchListAdapter by lazy {
        WatchListAdapter(listener = this@WatchlistFragment)
    }

    private val watchListViewModel by viewModels<WatchlistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        watchListViewModel.getWatchlist()

        viewLifecycleOwner.lifecycleScope.launch {
            watchListViewModel.watchlistMovie.collect{
                if (it.isEmpty()){
                    binding.rvWatchlist.visibility = View.GONE
                    binding.ivEmptyState.visibility = View.VISIBLE
                }else{
                    watchListAdapter.addNewData(it)
                    binding.rvWatchlist.visibility = View.VISIBLE
                    binding.ivEmptyState.visibility = View.GONE
                }
            }
        }
    }

    private fun initRecyclerView(){
        binding.rvWatchlist.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvWatchlist.adapter = watchListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickDelete(movieId: Int) {
       val alertDialog =  AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(getString(R.string.label_delete_confirmation))
            .setMessage(getString(R.string.label_message_confirmation_delete))
            .setPositiveButton(getString(R.string.label_yes), object : OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    watchListViewModel.deleteWatchListMovie(movieId)
                    dialog?.dismiss()
                }
            })
            .setNegativeButton(getString(R.string.label_no), object : OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })
        alertDialog.show()
    }

    override fun onClickItem(movieId: Int) {
        val bundle = bundleOf(DetailMovieFragment.KEY_DETAIL_FRAGMENT to movieId)
        findNavController().navigate(R.id.action_navigation_watchlist_to_detailMovieFragment, bundle)
    }


}