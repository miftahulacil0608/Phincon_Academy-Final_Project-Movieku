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
import com.example.domain.model.DetailMovie
import com.example.domain.model.ItemsRequestFromUser
import com.example.domain.model.OrderDetailMovie
import com.example.domain.model.OrderRequestFromUser
import com.example.movieku.R
import com.example.movieku.databinding.FragmentOrderDetailsBinding
import com.example.movieku.ui.dashboard.payment.PaymentFragment
import com.example.movieku.utils.ResultState
import kotlinx.coroutines.launch

class OrderDetailsFragment : Fragment() {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private var totalPriceMovie:Int=0

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
            arguments?.getParcelable(KEY_ORDER_DETAILS, OrderDetailMovie::class.java)
        } else {
            arguments?.getParcelable(KEY_ORDER_DETAILS)
        }

        arguments?.let {orderDetailMovie->
            setupOrderDetail(orderDetailMovie)
            amountTicket(orderDetailMovie)
            totalPriceMovie = orderDetailMovie.price

                binding.btnPayment.setOnClickListener {
                    val itemRequestFromUser = listOf(
                        ItemsRequestFromUser(id = orderDetailMovie.id, name = orderDetailMovie.title, 100000)
                    )
                    val orderRequestFromUser = OrderRequestFromUser(
                        amount = 100000,
                        email = "ahmadmiftahulazisz@gmail.com",
                        itemsRequest = itemRequestFromUser,
                    )
                    viewLifecycleOwner.lifecycleScope.launch {
                    val resultOrder = orderDetailsViewModel.orderMovie(orderRequestFromUser)
                        resultOrder.collect{result->
                            when(result){
                                ResultState.Loading -> {
                                    binding.progressbar.visibility = View.VISIBLE
                                }
                                is ResultState.Success -> {
                                    binding.progressbar.visibility = View.GONE
                                    val bundle = bundleOf(PaymentFragment.KEY_URL_PAYMENT_TO_PAYMENT_FRAGMENT to result.data.redirectUrl)
                                    Toast.makeText(requireContext(), "pindah ke halaman payment", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_orderDetailsFragment_to_paymentFragment, bundle)
                                }
                                is ResultState.Error -> {
                                    binding.progressbar.visibility = View.GONE
                                    Toast.makeText(requireContext(), "${result.message}", Toast.LENGTH_SHORT).show()
                                }

                            }
                        }


                }
            }
        }



    }

    private fun setupOrderDetail(item: OrderDetailMovie) {
        with(binding) {
            Glide.with(requireContext())
                .load(item.poster)
                .into(ivPosterMovie)
            tvLabelTitleMovie.text = item.title
            tvRateAge.text = item.pgAge
            tvPrice.text = resources.getString(R.string.label_price, item.price)
            tvPriceFee.text = resources.getString(R.string.label_price, item.priceFee)

            calculateTicket(item.price, item.priceFee, 1)

        }
    }

    private fun amountTicket(item: OrderDetailMovie) {
        var totalTicket = 1
        binding.btnIncrement.setOnClickListener {
            totalTicket++
            calculateTicket(item.price, item.priceFee, totalTicket)
        }

        binding.btnDecrement.setOnClickListener {
            if (totalTicket > 0) {
                totalTicket--
                calculateTicket(item.price, item.priceFee, totalTicket)
            }
        }
    }

    private fun calculateTicket(priceMovie: Int, feeMovie: Int, totalTicket: Int) {
        binding.tvLabelTicket.text = totalTicket.toString()
        binding.tvQuantityTicketFee.text = totalTicket.toString()
        binding.tvQuatityTicket.text = totalTicket.toString()
        val totalPriceTicket = priceMovie * binding.tvLabelTicket.text.toString().toInt()
        val totalFeeTicket = feeMovie * binding.tvLabelTicket.text.toString().toInt()
        val totalToPay = totalPriceTicket + totalFeeTicket
        binding.tvTotalPrice.text = resources.getString(R.string.label_price, totalToPay)
        binding.tvTotalPriceToPay.text = resources.getString(R.string.label_price, totalToPay)
        binding.tvTotalTicket.text = resources.getString(R.string.label_total_items, totalTicket)
        binding.btnPayment.isEnabled = if (totalTicket > 0) true else false

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_ORDER_DETAILS = "KEY ORDER DETAIL"
    }
}