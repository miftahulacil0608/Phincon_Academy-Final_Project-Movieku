package com.example.movieku.ui.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserData
import com.example.domain.usecase.AuthenticationUseCase
import com.example.domain.usecase.UserSettingsUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor (private val authenticationUseCase: AuthenticationUseCase, private val userSettingsUseCase: UserSettingsUseCase) : ViewModel() {
    private val _userData = MutableStateFlow<ResultState<UserData>>(ResultState.Loading)
    val userData = _userData.asStateFlow()

    fun getUserSession(){
        _userData.value = ResultState.Loading
        viewModelScope.launch {
            try {
                val userDetail = authenticationUseCase.getUserSession()
                _userData.value = ResultState.Success(userDetail)
            }catch (e:Exception){
                _userData.value = ResultState.Error(e.cause!!)
            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            authenticationUseCase.signOut()
            userSettingsUseCase.clearUserAuthentication()
        }
    }
}