package com.dicedev.stockapp.domain.repository

import com.dicedev.stockapp.domain.model.CompanyListing
import com.dicedev.stockapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}