package com.dicedev.stockapp.di

import com.dicedev.stockapp.data.csv.CSVParser
import com.dicedev.stockapp.data.csv.CompanyListingsParser
import com.dicedev.stockapp.data.repository.StockRepositoryImpl
import com.dicedev.stockapp.domain.model.CompanyListing
import com.dicedev.stockapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(companyListingsParser: CompanyListingsParser):
            CSVParser<CompanyListing>

    @Singleton
    @Binds
    abstract fun provideStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}