package com.example.movieku.ui.authentication

import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.AuthenticationUseCase
import com.example.domain.usecase.UserSettingsUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
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
                ResultState.Success(resultSignIn.getOrDefault(false))
            }else{
                ResultState.Error(resultSignIn.exceptionOrNull()!!)
            }
        }catch (e:Exception){
            ResultState.Error(e)
        }
    }

    suspend fun signInWithEmailAndPassword(email:String, password: String):ResultState<Boolean>{
        ResultState.Loading
        return try {
            val resultSigIn = authenticationUseCase.signInWithEmailAndPassword(email, password)
            if (resultSigIn.isSuccess){
                ResultState.Success(resultSigIn.getOrDefault(false))
            }else{
                ResultState.Error(resultSigIn.exceptionOrNull()!!)
            }
        }catch (e:Exception){
            ResultState.Error(e)
        }
    }

    suspend fun signUpWithEmailAndPassword(fullName:String, email:String,password:String):ResultState<Boolean>{
        ResultState.Loading
        return try{
            val resultSignUp = authenticationUseCase.signUpWithEmailAndPassword(fullName, email, password)
            if (resultSignUp.isSuccess){
                ResultState.Success(resultSignUp.getOrDefault(false))
            }else{
                ResultState.Error(resultSignUp.exceptionOrNull()!!)
            }
        }catch (e:Exception){
            ResultState.Error(e)
        }
    }

    fun setUserData(){
        viewModelScope.launch {
            authenticationUseCase.setUserData()
        }
    }
}