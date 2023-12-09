package com.assignment.stocksbrowser.usecases

import com.assignment.stocksbrowser.api.NasdaqApiService
import com.assignment.stocksbrowser.model.room.toStockEntity
import com.assignment.stocksbrowser.repositories.StocksRepository
import timber.log.Timber
import javax.inject.Inject

class UpdateStocksUseCase @Inject constructor(
    private val nasdaqApiService: NasdaqApiService,
    private val repository: StocksRepository
) {

    suspend operator fun invoke(): Boolean {
        try {
            val apiResponse = nasdaqApiService.getStocks() ?: return false
            repository.insertStocks(apiResponse.data.rows.map { it.toStockEntity() })
        } catch (e: Exception) {
            Timber.e(e)
            return false
        }

        return true
    }
}
