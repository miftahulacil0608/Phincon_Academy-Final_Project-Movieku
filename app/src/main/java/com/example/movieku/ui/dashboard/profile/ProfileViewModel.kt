package com.example.movieku.ui.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.user.SettingDataUI
import com.example.domain.usecase.AuthenticationUseCase
import com.example.domain.usecase.UserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val userSettingsUseCase: UserSettingsUseCase
) : ViewModel() {

    private val _userData = MutableStateFlow(
        SettingDataUI(
            isOnboarding = false,
            isUserAuthentication = false,
            email = "",
            displayName = ""
        )
    )
    val userData = _userData.asStateFlow()

    fun getUserSession() {
        viewModelScope.launch {
            try {
                _userData.value = userSettingsUseCase.getSettingsUser()
            }catch (e:Exception){
                throw IllegalArgumentException(e)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userSettingsUseCase.clearUserAuthentication()
            authenticationUseCase.signOut()
        }
    }
}