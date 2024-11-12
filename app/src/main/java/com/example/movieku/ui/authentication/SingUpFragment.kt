package com.example.movieku.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieku.R
import com.example.movieku.databinding.FragmentSignUpBinding
import com.example.movieku.ui.dashboard.MainFeaturesActivity
import com.example.movieku.utils.Helper
import com.example.movieku.utils.ResultState
import kotlinx.coroutines.launch


class SingUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateInputFormSignUp()
        //TODO inputan yang bener dulu
        binding.btnRegister.setOnClickListener {

            val fullName = binding.tieFullName.text.toString()
            val email = binding.tieEmail.text.toString()
            val password = binding.tiePassword.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                val signUpResult = authenticationViewModel.signUpWithEmailAndPassword(
                    fullName,
                    email,
                    password
                )
                when (signUpResult) {
                    ResultState.Loading -> {}
                    is ResultState.Success -> {
                        authenticationViewModel.saveUserAuthentication(signUpResult.data)
                        startActivity(
                            Intent(
                                requireActivity(),
                                MainFeaturesActivity::class.java
                            )
                        )
                        requireActivity().finish()
                    }

                    is ResultState.Error -> {
                        Toast.makeText(
                            requireActivity(),
                            "Your account has been registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }

        })
    }



    private fun validateInputFormSignUp() {
        var fullNameValid = false
        var emailValid = false
        var passwordValid = false
        binding.tieFullName.addTextChangedListener {
            val fullName = it.toString()
            fullNameValid = Helper.fullNameValidator(fullName)
            Helper.updateInputLayout(binding.tilFullName, fullNameValid.not(), "Wrong format username", requireContext())
            updateSubmitState(fullNameValid, emailValid, passwordValid)
        }
        binding.tieEmail.addTextChangedListener {
            val email = it.toString()
            emailValid = Helper.emailValidator(email)
            Helper.updateInputLayout(binding.tilEmail, emailValid.not(), "Wrong format email", requireContext())
            updateSubmitState(fullNameValid,emailValid, passwordValid)
        }
        binding.tiePassword.addTextChangedListener {
            val password = it.toString()
            passwordValid=Helper.passwordValidator(password)
            Helper.updateInputLayout(binding.tilPassword, passwordValid.not(), "Wrong format password", requireContext())
            updateSubmitState(fullNameValid,emailValid, passwordValid)
        }
        updateSubmitState(fullNameValid, emailValid, passwordValid)
    }

    private fun updateSubmitState(isFullName:Boolean,isEmail:Boolean, isPassword:Boolean){
        binding.btnRegister.isEnabled = isFullName && isEmail && isPassword
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}