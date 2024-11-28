package com.example.movieku.ui.dashboard.booking

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.domain.model.movie.order.ItemsRequestFromUser
import com.example.domain.model.movie.order.OrderMovie
import com.example.domain.model.movie.order.OrderRequestFromUser
import com.example.movieku.R
import com.example.movieku.databinding.FragmentOrderDetailsBinding
import com.example.movieku.databinding.LoadingCustomBinding
import com.example.movieku.ui.dashboard.payment.PaymentFragment
import com.example.movieku.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!


    private var priceOneMovie: Int = 0
    private var feeOneMovie: Int = 0
    private var totalPrice: Int = 0
    private var totalToPay: Int = 0
    private var totalTicket = 0


    private val orderDetailsViewModel by viewModels<OrderDetailsViewModel>()

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

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
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
            tvLocationCinema.text =
                resources.getString(R.string.label_location_cinema, item.cinema, item.studio)
            tvDateWatch.text = item.dateWatch
            tvTimeWatch.text = item.timeWatch
            tvPrice.text = resources.getString(R.string.label_price, priceOneMovie)
            tvPriceFee.text = resources.getString(R.string.label_price, feeOneMovie)
            calculateTicket(item.price, item.priceFee)
        }
    }

    //ticket modification
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

    //calculating ticket
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
        binding.btnPayment.setBackgroundColor(
            if (binding.btnPayment.isEnabled) ContextCompat.getColor(
                requireContext(),
                R.color.md_theme_primary
            ) else
                ContextCompat.getColor(requireContext(), R.color.md_theme_secondary)
        )
    }

    //bayar tiket
    private fun payTheTicketMovie(item: OrderMovie) {

        binding.btnPayment.setOnClickListener {
            //siapin datanya
            val itemRequestFromUser = listOf(
                ItemsRequestFromUser(
                    dateWatch = item.dateWatch,
                    timeWatch = item.timeWatch,
                    id = item.id,
                    price = totalPrice,
                    quantity = totalTicket,
                    name = item.title,
                    imageUrl = item.poster,
                    genre = item.genre,
                    duration = item.duration,
                    pgAge = item.pgAge,
                    codeLanguage = item.codeLanguage,
                    cinema = item.cinema,
                    studio = item.studio,
                    seatRow = seatRow(),
                    seatNumber = numberOfSeat(totalTicket),
                    codeTicket = randomTicket()
                )
            )
            //order request
            val orderRequestFromUser = OrderRequestFromUser(
                amount = totalToPay,
                //Email get from datastore
                itemsRequest = itemRequestFromUser,
            )

            //order movienya
            viewLifecycleOwner.lifecycleScope.launch {

                val dialog = alertDialog()
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val resultOrder = orderDetailsViewModel.orderMovie(orderRequestFromUser)
                resultOrder.collect { result ->
                    when (result) {
                        ResultState.Loading -> {
                            dialog.show()
                        }

                        is ResultState.Success -> {
                            dialog.dismiss()
                            val bundle =
                                bundleOf(PaymentFragment.KEY_URL_PAYMENT_TO_PAYMENT_FRAGMENT to result.data.redirectUrl)
                            findNavController().navigate(
                                R.id.action_orderDetailsFragment_to_paymentFragment,
                                bundle
                            )
                        }

                        is ResultState.Error -> {
                            dialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                "Can't Access API",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    //ticketing order
    private fun randomTicket(): String {
        val nameApp = "FLIX"
        val numberList = (0..9).toList()
        val randomUniqueCode = (0..9).map { numberList.random() }.joinToString("")

        val result = nameApp + randomUniqueCode + "T"
        return result
    }

    private fun seatRow(): String {
        val wordList = ('A'..'J').toList()
        return wordList.random().toString()
    }

    private fun numberOfSeat(totalTicket: Int): List<Int> {
        return (1..totalTicket).toList()
    }

    private fun alertDialog(): AlertDialog {
        val customAlertDialog = LoadingCustomBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setView(customAlertDialog.root)
            .setCancelable(false)
            .create()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_ORDER_DETAILS = "KEY ORDER DETAIL"
    }
}