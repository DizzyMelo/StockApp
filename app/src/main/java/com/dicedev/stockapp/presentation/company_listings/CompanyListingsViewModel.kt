package com.dicedev.stockapp.presentation.company_listings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicedev.stockapp.domain.repository.StockRepository
import com.dicedev.stockapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(private val repository: StockRepository) :
    ViewModel() {

    private val _state = mutableStateOf(CompanyListingsState())
    val state: State<CompanyListingsState> = _state

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.Refresh -> getCompanyListings(fetchFromRemote = true)
            is CompanyListingsEvent.OnSearchQueryChange -> {
                _state.value = _state.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = _state.value.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CompanyListingsState(companies = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _state.value = CompanyListingsState(
                            errorMessage = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = CompanyListingsState(isLoading = true)
                    }
                }
            }
        }
    }
}