package com.example.movieku.ui.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.AuthenticationUseCase
import com.example.domain.usecase.UserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor (private val authenticationUseCase: AuthenticationUseCase, private val userSettingsUseCase: UserSettingsUseCase) : ViewModel() {
    fun signOut(){
        viewModelScope.launch {
            authenticationUseCase.signOut()
            userSettingsUseCase.clearUserAuthentication()
        }
    }
}