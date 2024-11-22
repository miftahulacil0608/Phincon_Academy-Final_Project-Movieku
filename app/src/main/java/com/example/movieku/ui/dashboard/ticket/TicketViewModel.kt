package com.example.movieku.ui.dashboard.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.movie.tickets.Ticket
import com.example.domain.usecase.TicketsUseCase
import com.example.movieku.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketsUseCase: TicketsUseCase
) : ViewModel() {
    private val _ticket = MutableStateFlow<ResultState<List<Ticket>>>(ResultState.Loading)
    val tickets = _ticket.asStateFlow()

    private val _detailTicket = MutableStateFlow<Ticket?>(null)
    val detailTicket = _detailTicket.asStateFlow()

    init {
        getTickets()
    }

    fun getTickets(){
        viewModelScope.launch {
            _ticket.value = ResultState.Loading
            try {
                _ticket.value = ResultState.Success(ticketsUseCase.getTickets())
            }catch (e:Exception){
                _ticket.value = ResultState.Error(e)
            }
        }
    }

    fun detailTicket(item:Ticket){
        _detailTicket.value = item
    }
}