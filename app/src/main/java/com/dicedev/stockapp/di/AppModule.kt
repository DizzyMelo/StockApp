package com.dicedev.stockapp.di

import android.content.Context
import androidx.room.Room
import com.dicedev.stockapp.data.local.StockDao
import com.dicedev.stockapp.data.local.StockDatabase
import com.dicedev.stockapp.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStockDao(stockDatabase: StockDatabase): StockDao = stockDatabase.stockDao()

    @Singleton
    @Provides
    fun provideStockDatabase(@ApplicationContext context: Context): StockDatabase {
        return Room.databaseBuilder(context, StockDatabase::class.java, "stock_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideStockApi(): StockApi {
        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(StockApi::class.java)
    }
}