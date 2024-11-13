package com.example.movieku.ui.dashboard.booking

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.domain.model.DetailMovie
import com.example.domain.model.OrderDetailMovie
import com.example.movieku.R
import com.example.movieku.databinding.FragmentOrderDetailsBinding

class OrderDetailsFragment : Fragment() {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

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

        arguments?.let {
            setupOrderDetail(it)
            amountTicket(it)
            binding.btnPayment.setOnClickListener {
                //TODO WEBVIEW PEMBAYARAN
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

    private fun amountTicket(item:OrderDetailMovie) {
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

    private fun calculateTicket(priceMovie:Int, feeMovie:Int, totalTicket:Int){
        binding.tvLabelTicket.text = totalTicket.toString()
        binding.tvQuantityTicketFee.text = totalTicket.toString()
        binding.tvQuatityTicket.text = totalTicket.toString()
        val totalPriceTicket = priceMovie * binding.tvLabelTicket.text.toString().toInt()
        val totalFeeTicket =  feeMovie * binding.tvLabelTicket.text.toString().toInt()
        val totalToPay = totalPriceTicket + totalFeeTicket
        binding.tvTotalPrice.text = resources.getString(R.string.label_price,totalToPay)
        binding.tvTotalPriceToPay.text = resources.getString(R.string.label_price, totalToPay)
        binding.tvTotalTicket.text = resources.getString(R.string.label_total_items, totalTicket)

        binding.btnPayment.isEnabled = if (totalTicket<1) true else false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_ORDER_DETAILS = "KEY ORDER DETAIL"
    }
}