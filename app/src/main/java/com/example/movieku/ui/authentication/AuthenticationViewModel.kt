package com.example.movieku.ui.authentication

import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.AuthenticationUseCase
import com.example.domain.usecase.UserSettingsUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.jvm.Throws

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val userSettingsUseCase: UserSettingsUseCase
) : ViewModel() {

    private val _isSign = MutableStateFlow<ResultState<Boolean>>(ResultState.Success(false))
    val isSign = _isSign.asStateFlow()


    fun saveUserAuthentication(isStatus: Boolean) {
        viewModelScope.launch {
            userSettingsUseCase.saveUserAuthentication(isStatus)
        }
    }

    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse){
        viewModelScope.launch {
            _isSign.value = ResultState.Loading
            val resultSignInWithGoogle  = authenticationUseCase.signInWithGoogle(credentialResponse)
            if (resultSignInWithGoogle.isSuccess){
                _isSign.value = ResultState.Success(resultSignInWithGoogle.getOrDefault(false))
            }else{
                _isSign.value = ResultState.Error(resultSignInWithGoogle.exceptionOrNull()?:throw IllegalArgumentException("Error not found"))
            }
        }
    }

     fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ) {
       viewModelScope.launch {
           _isSign.value = ResultState.Loading
           val resultSingUp = authenticationUseCase.signUpWithEmailAndPassword(fullName, email, password)
           if (resultSingUp.isSuccess){
               _isSign.value = ResultState.Success(resultSingUp.getOrDefault(false))
           }else{
               _isSign.value = ResultState.Error(resultSingUp.exceptionOrNull()?:throw IllegalArgumentException("Erro Not Found"))
           }
       }
    }

    fun signInWithEmailAndPassword2(email:String, password:String){
        viewModelScope.launch {
            _isSign.value = ResultState.Loading
            val result = authenticationUseCase.signInWithEmailAndPassword2(email, password)
            if (result.isSuccess){
                _isSign.value = ResultState.Success(result.getOrDefault(false))
            }else{
                _isSign.value = ResultState.Error(result.exceptionOrNull()?:throw IllegalArgumentException("Error Not Found"))
            }
        }
    }

    fun clearLiveData() {
        _isSign.value = ResultState.Success(false)
    }

    fun setUserData() {
        viewModelScope.launch {
            authenticationUseCase.setUserData()
        }
    }
}