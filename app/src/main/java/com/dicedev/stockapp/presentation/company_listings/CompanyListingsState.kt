package com.dicedev.stockapp.presentation.company_listings

import com.dicedev.stockapp.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: String = ""
)
