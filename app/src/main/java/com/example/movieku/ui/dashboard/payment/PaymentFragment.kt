package com.example.movieku.ui.dashboard.payment

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.movieku.R
import com.example.movieku.databinding.FragmentOrderDetailsBinding
import com.example.movieku.databinding.FragmentPaymentBinding
import com.example.movieku.ui.dashboard.ticket.TicketFragment
import kotlinx.coroutines.runBlocking

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultUrl = arguments?.getString(KEY_URL_PAYMENT_TO_PAYMENT_FRAGMENT)
        binding.webView.settings.javaScriptEnabled = true

        resultUrl?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

            binding.webView.loadUrl(it)
            binding.webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.getQueryParameter("transaction_status").let {value->
                        if(value == "settlement"){
                            findNavController().navigate(R.id.action_paymentFragment_to_navigation_home)
                            return false
                        }
                    }
                    return true
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                        findNavController().navigateUp()
                }
            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val KEY_URL_PAYMENT_TO_PAYMENT_FRAGMENT = "KEY URL PAYMENT TO PAYMENT FRAGMENT"
    }
}