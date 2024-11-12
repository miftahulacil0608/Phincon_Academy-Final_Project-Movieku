package com.example.movieku.ui.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.movieku.databinding.FragmentDashboardBinding
import com.example.movieku.ui.authentication.AuthenticationActivity
import com.example.movieku.utils.ResultState
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!


    private val dashboardViewModel by activityViewModels<DashboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardViewModel.getUserSession()
        setupUserSession()

        binding.btnSignOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                clearCredential()

                dashboardViewModel.signOut()

                startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun setupUserSession(){
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.userData.collect{
                when(it){
                    ResultState.Loading -> {
                        //shimmer
                    }
                    is ResultState.Success -> {
                        binding.userInformation.text = "${it.data.displayName}, ${it.data.email}"
                    }
                    is ResultState.Error -> {
                        Toast.makeText(requireActivity(), "${it.message}", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    private suspend fun clearCredential(){
        val credentialManager = CredentialManager.create(requireContext())
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}