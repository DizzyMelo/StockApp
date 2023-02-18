package com.dicedev.stockapp.data.repository

import com.dicedev.stockapp.data.local.StockDao
import com.dicedev.stockapp.data.mapper.toCompanyListing
import com.dicedev.stockapp.data.remote.StockApi
import com.dicedev.stockapp.domain.model.CompanyListing
import com.dicedev.stockapp.domain.repository.StockRepository
import com.dicedev.stockapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao
) : StockRepository {
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> = flow {
        emit(Resource.Loading())
        val localCompanyListing = dao.searchCompanyListing(query).map { it.toCompanyListing() }
        emit(Resource.Success(localCompanyListing))

        val isDbEmpty = localCompanyListing.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

        if (shouldJustLoadFromCache) {
            return@flow
        }

        val remoteCompanyListing = try {
            val response = api.getListings()
            response.byteStream()
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Could not connect to server. Check internet connection and try again."
                )
            )
        }
    }
}