package com.example.movieku.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieku.R
import com.example.movieku.databinding.FragmentSignUpBinding
import com.example.movieku.databinding.LoadingCustomBinding
import com.example.movieku.ui.dashboard.MainFeaturesActivity
import com.example.movieku.utils.HelperValidation
import com.example.movieku.utils.ResultState
import kotlinx.coroutines.launch


class SingUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding

    private val authenticationViewModel by activityViewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateInputFormSignUp()


        lifecycleScope.launch {
            val loading = alertDialog()
            loading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            authenticationViewModel.isSign.collect {
                when (it) {
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

        binding?.btnRegister?.setOnClickListener {
            val fullName = binding?.tieFullName?.text.toString()
            val email = binding?.tieEmail?.text.toString()
            val password = binding?.tiePassword?.text.toString()
            val loading = alertDialog()
            loading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            authenticationViewModel.signUpWithEmailAndPassword(fullName, email, password)
        }
        binding?.btnLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }

            })
    }


    private fun validateInputFormSignUp() {
        var fullNameValid = false
        var emailValid = false
        var passwordValid = false
        binding?.tieFullName?.addTextChangedListener {
            val fullName = it.toString()
            fullNameValid = HelperValidation.fullNameValidator(fullName)
            HelperValidation.updateInputLayout(
                binding?.tilFullName,
                fullNameValid.not(),
                "Wrong format username",
                requireContext()
            )
            updateSubmitState(fullNameValid, emailValid, passwordValid)
        }
        binding?.tieEmail?.addTextChangedListener {
            val email = it.toString()
            emailValid = HelperValidation.emailValidator(email)
            HelperValidation.updateInputLayout(
                binding?.tilEmail,
                emailValid.not(),
                "Wrong format email",
                requireContext()
            )
            updateSubmitState(fullNameValid, emailValid, passwordValid)
        }
        binding?.tiePassword?.addTextChangedListener {
            val password = it.toString()
            passwordValid = HelperValidation.passwordValidator(password)
            HelperValidation.updateInputLayout(
                binding?.tilPassword,
                passwordValid.not(),
                "Wrong format password",
                requireContext()
            )
            updateSubmitState(fullNameValid, emailValid, passwordValid)
        }
        updateSubmitState(fullNameValid, emailValid, passwordValid)
    }

    private fun alertDialog(): AlertDialog {
        val alertDialogBinding = LoadingCustomBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setView(alertDialogBinding.root)
            .setCancelable(false)
            .create()
    }

    private fun updateSubmitState(isFullName: Boolean, isEmail: Boolean, isPassword: Boolean) {
        binding?.btnRegister?.isEnabled = isFullName && isEmail && isPassword
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}