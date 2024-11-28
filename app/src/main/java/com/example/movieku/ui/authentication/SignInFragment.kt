package com.example.movieku.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieku.BuildConfig
import com.example.movieku.R
import com.example.movieku.databinding.FragmentSignInBinding
import com.example.movieku.databinding.LoadingCustomBinding
import com.example.movieku.ui.dashboard.MainFeaturesActivity
import com.example.movieku.utils.HelperValidation
import com.example.movieku.utils.ResultState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateInputEmailAndPassword()

        lifecycleScope.launch {
            val loading = alertDialog()
            loading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            authenticationViewModel.isSign.collect{
                when(it){
                    ResultState.Loading -> {
                        loading.show()
                    }
                    is ResultState.Success -> {
                        loading.dismiss()
                        if (it.data){
                            context?.let {context->
                                startActivity(Intent(context, MainFeaturesActivity::class.java))
                                activity?.finish()
                                authenticationViewModel.clearLiveData()
                            }
                        }
                    }
                    is ResultState.Error -> {
                        loading.dismiss()
                        Toast.makeText(requireContext(), "Account not registered", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        binding?.btnSignIn?.setOnClickListener {
            val email = binding?.tieEmail?.text.toString()
            val password = binding?.tiePassword?.text.toString()
            authenticationViewModel.signInWithEmailAndPassword2(email, password)
        }

        binding?.btnRegister?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        //Sign in with google account
        binding?.btnGoogleSignIn?.setOnClickListener {
            lifecycleScope.launch {
                val credential = getCredentialRequest()
                credential.onSuccess {
                    authenticationViewModel.signInWithGoogle(it)
                }.onFailure {

                }
            }
        }
    }

    private fun alertDialog():AlertDialog{
        val alertDialogBinding = LoadingCustomBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setView(alertDialogBinding.root)
            .setCancelable(false)
            .create()
    }

     private suspend fun getCredentialRequest(): Result<GetCredentialResponse> {
        return withContext(Dispatchers.IO){
            try {
                val secretWebClient = BuildConfig.WEB_SECRET_CLIENT
                val credentialManager = CredentialManager.create(requireContext())

                val googleOptionId = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(secretWebClient)
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

    private fun validateInputEmailAndPassword() {
        var emailvalid = false
        var passwordvalid = false
        binding?.tieEmail?.addTextChangedListener {
            val email = it.toString()
            emailvalid = HelperValidation.emailValidator(email)
            HelperValidation.updateInputLayout(binding?.tilEmail, emailvalid.not(), "Wrong format email", requireContext())
            updateSubmitState(emailvalid, passwordvalid)
        }
        binding?.tiePassword?.addTextChangedListener {
            val password = it.toString()
            passwordvalid= HelperValidation.passwordValidator(password)
            HelperValidation.updateInputLayout(binding?.tilPassword, passwordvalid.not(), "Wrong format password", requireContext())
            updateSubmitState(emailvalid, passwordvalid)
        }
        updateSubmitState(emailvalid, passwordvalid)
    }

    private fun updateSubmitState(isEmail:Boolean, isPassword:Boolean){
        binding?.btnSignIn?.isEnabled = isEmail && isPassword
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}