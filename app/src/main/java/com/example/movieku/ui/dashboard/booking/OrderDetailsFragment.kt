package com.example.movieku.ui.dashboard.booking

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.domain.model.ItemsRequestFromUser
import com.example.domain.model.OrderMovie
import com.example.domain.model.OrderRequestFromUser
import com.example.movieku.R
import com.example.movieku.databinding.FragmentOrderDetailsBinding
import com.example.movieku.ui.dashboard.payment.PaymentFragment
import com.example.movieku.utils.ResultState
import kotlinx.coroutines.launch

class OrderDetailsFragment : Fragment() {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private var priceOneMovie: Int = 0
    private var feeOneMovie: Int = 0
    private var totalPrice: Int = 0
    private var totalToPay: Int = 0
    private var totalTicket = 0


    private val orderDetailsViewModel by activityViewModels<OrderDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(KEY_ORDER_DETAILS, OrderMovie::class.java)
        } else {
            arguments?.getParcelable(KEY_ORDER_DETAILS)
        }

        arguments?.let { orderDetailMovie ->
            setupOrderDetail(orderDetailMovie)
            amountTicket(orderDetailMovie)
            priceOneMovie = orderDetailMovie.price
            payTheTicketMovie(orderDetailMovie)
        }
    }

    private fun setupOrderDetail(item: OrderMovie) {
        priceOneMovie = item.price
        feeOneMovie = item.priceFee

        with(binding) {
            Glide.with(requireContext())
                .load(item.poster)
                .into(ivPosterMovie)
            tvLabelTitleMovie.text = item.title
            tvRateAge.text = item.pgAge
            tvLocationCinema.text = resources.getString(R.string.label_location_cinema, item.cinema, item.studio)
            tvDateWatch.text = item.dateWatch
            tvTimeWatch.text = item.timeWatch
            tvPrice.text = resources.getString(R.string.label_price, priceOneMovie)
            tvPriceFee.text = resources.getString(R.string.label_price, feeOneMovie)
            calculateTicket(item.price, item.priceFee)
        }
    }

    private fun amountTicket(item: OrderMovie) {
        binding.btnIncrement.setOnClickListener {
            totalTicket++
            calculateTicket(item.price, item.priceFee)
        }

        binding.btnDecrement.setOnClickListener {
            if (totalTicket > 0) {
                totalTicket--
                calculateTicket(item.price, item.priceFee)

            }
        }
    }

    private fun calculateTicket(priceMovie: Int, feeMovie: Int) {
        priceOneMovie = priceMovie
        feeOneMovie = feeMovie
        totalPrice = priceMovie + feeOneMovie
        totalToPay = totalPrice * totalTicket

        binding.tvLabelTicket.text = totalTicket.toString()
        binding.tvQuantityTicketFee.text = totalTicket.toString()
        binding.tvQuatityTicket.text = totalTicket.toString()
        binding.tvTotalPrice.text = resources.getString(R.string.label_price, totalToPay)
        binding.tvTotalPriceToPay.text = resources.getString(R.string.label_price, totalToPay)
        binding.tvTotalTicket.text = resources.getString(R.string.label_total_items, totalTicket)
        binding.btnPayment.isEnabled = totalTicket > 0
    }

    private fun payTheTicketMovie(item: OrderMovie) {
        binding.btnPayment.setOnClickListener {
            val itemRequestFromUser = listOf(
                //TODO datewatch ambil dari tanggal di halaman sebelume (masih proses)
                ItemsRequestFromUser(
                    id = item.id,
                    name = item.title,
                    price = totalPrice,
                    quantity = totalTicket,
                    rating = item.rating,
                    imageUrl = item.poster,
                    genreMovie = item.genre,
                    dateWatch = "2024-10-1"
                )
            )
            val orderRequestFromUser = OrderRequestFromUser(
                amount = totalToPay,
                //TODO email ambil dari email yang ada di firebase Auth
                email = "ahmadmiftahulazisz@gmail.com",
                itemsRequest = itemRequestFromUser,
            )
            viewLifecycleOwner.lifecycleScope.launch {
                val resultOrder = orderDetailsViewModel.orderMovie(orderRequestFromUser)
                resultOrder.collect { result ->
                    when (result) {
                        ResultState.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                        }

                        is ResultState.Success -> {
                            binding.progressbar.visibility = View.GONE
                            val bundle =
                                bundleOf(PaymentFragment.KEY_URL_PAYMENT_TO_PAYMENT_FRAGMENT to result.data.redirectUrl)
                            Toast.makeText(
                                requireContext(),
                                "pindah ke halaman payment",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(
                                R.id.action_orderDetailsFragment_to_paymentFragment,
                                bundle
                            )
                        }

                        is ResultState.Error -> {
                            binding.progressbar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "${result.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_ORDER_DETAILS = "KEY ORDER DETAIL"
    }
}