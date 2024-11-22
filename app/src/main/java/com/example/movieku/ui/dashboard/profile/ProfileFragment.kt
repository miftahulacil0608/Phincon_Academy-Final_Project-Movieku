package com.example.movieku.ui.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.movieku.databinding.FragmentProfileBinding
import com.example.movieku.databinding.FragmentTicketsBinding
import com.example.movieku.ui.authentication.AuthenticationActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val ticketViewModel by activityViewModels<ProfileViewModel>()

    //private var userSessionJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketViewModel.getUserSession()
        setupUserSession()

        binding.btnSignOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                clearCredential()
                ticketViewModel.signOut()
                startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun setupUserSession() {
        lifecycleScope.launch {
            ticketViewModel.userData.collect{
                binding.userInformation.text = "${it.displayName}, ${it.email}"
            }
        }

    }

    private suspend fun clearCredential() {
        val credentialManager = CredentialManager.create(requireContext())
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}