package com.example.movieku.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieku.R
import com.example.movieku.databinding.FragmentSignInBinding
import com.example.movieku.ui.dashboard.MainFeaturesActivity
import com.example.movieku.utils.ResultState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
//TODO dirapihkan lagi terkait dengan datanya
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.btnGoogleSignIn.setOnClickListener {
            lifecycleScope.launch {
                val credential = getCredentialRequest()
                credential.onSuccess {
                    isSignInWithGoogle(it)
                }.onFailure {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

     private suspend fun getCredentialRequest(): Result<GetCredentialResponse> {
        return withContext(Dispatchers.IO){
            try {
                val credentialManager = CredentialManager.create(requireContext())

                val googleOptionId = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(getString(R.string.your_web_client_id))
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleOptionId)
                    .build()

                val resultRequestResponse = credentialManager.getCredential(requireContext(), request)
                Result.success(resultRequestResponse)
            } catch (e: CancellationException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }

    private suspend fun isSignInWithGoogle(credential: GetCredentialResponse) {
        when (val result = authenticationViewModel.signWithGoogle(credential)) {
            ResultState.Loading -> {
                //bisa ada progressbar
            }

            is ResultState.Success -> {
                //matikan progressbar
                authenticationViewModel.saveUserAuthentication(result.data)
                startActivity(Intent(requireActivity(), MainFeaturesActivity::class.java))
                Toast.makeText(
                    requireActivity(),
                    "berhasil login: ${result.data}",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }

            is ResultState.Error -> {
                //muncul dialog error / salah
                Toast.makeText(requireActivity(), "${result.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}