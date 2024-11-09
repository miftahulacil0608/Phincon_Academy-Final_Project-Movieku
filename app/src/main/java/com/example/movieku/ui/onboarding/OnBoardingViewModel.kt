package com.example.movieku.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.UserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val userSettingsUseCase: UserSettingsUseCase):ViewModel(){
    fun saveOnBoarding(isStatus:Boolean){
        viewModelScope.launch {
            userSettingsUseCase.saveOnBoarding(isStatus)
        }
    }
}