package com.example.movieku.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SettingDataUI
import com.example.domain.usecase.AuthenticationUseCase
import com.example.domain.usecase.UserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val userSettingsUseCase: UserSettingsUseCase) : ViewModel() {
    private val _isUserAuthentication = MutableLiveData<SettingDataUI>()
    val isUserAuthentication get() = _isUserAuthentication

    init {
        isUserAuthentication()
    }

    private fun isUserAuthentication(){
        viewModelScope.launch {
            userSettingsUseCase.getSettings().collect{
                _isUserAuthentication.value = it
            }
        }
    }



}