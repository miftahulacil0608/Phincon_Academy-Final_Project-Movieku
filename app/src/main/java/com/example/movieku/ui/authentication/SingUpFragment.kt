package com.example.movieku.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieku.R
import com.example.movieku.databinding.FragmentSignUpBinding
import com.example.movieku.ui.dashboard.MainFeaturesActivity
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


        //TODO inputan yang bener dulu
        binding.btnRegister.setOnClickListener {
            val username = binding.tieFullName.text.toString()
            val email = binding.tieEmail.text.toString()
            val password = binding.tiePassword.text.toString()

            //fungsi untuk inputkan data ini
            // authviewmodel.signUp(username, email, phone, password)
            viewLifecycleOwner.lifecycleScope.launch {
                val signUpResult = authenticationViewModel.signUpWithEmailAndPassword(username, email, password)
                when(signUpResult){
                    ResultState.Loading -> {}
                    is ResultState.Success -> {
                        authenticationViewModel.saveUserAuthentication(signUpResult.data)
                        startActivity(Intent(requireActivity(), MainFeaturesActivity::class.java))
                        requireActivity().finish()
                    }
                    is ResultState.Error -> {
                        Toast.makeText(requireActivity(), "${signUpResult.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}