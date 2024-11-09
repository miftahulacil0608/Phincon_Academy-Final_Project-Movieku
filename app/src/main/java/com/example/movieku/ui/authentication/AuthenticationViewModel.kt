package com.example.movieku.ui.authentication

import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DataUser
import com.example.domain.usecase.AuthenticationUseCase
import com.example.domain.usecase.UserSettingsUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val userSettingsUseCase: UserSettingsUseCase
) : ViewModel() {

    fun saveUserAuthentication(isStatus:Boolean){
        viewModelScope.launch {
            userSettingsUseCase.saveUserAuthentication(isStatus)
        }
    }

    suspend fun signWithGoogle(credentialResponse: GetCredentialResponse): ResultState<Boolean> {
        ResultState.Loading
        return try {
            val resultSignIn = authenticationUseCase.signInWithGoogle(credentialResponse)
            if (resultSignIn.isSuccess){
                ResultState.Success(resultSignIn.getOrThrow())
            }else{
                ResultState.Error(resultSignIn.exceptionOrNull()!!)
            }
        }catch (e:Exception){
            ResultState.Error(e)
        }
    }
}