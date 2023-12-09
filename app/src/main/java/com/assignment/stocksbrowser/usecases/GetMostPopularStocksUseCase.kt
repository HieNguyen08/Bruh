package com.assignment.stocksbrowser.usecases

import com.assignment.stocksbrowser.model.room.StockEntity
import com.assignment.stocksbrowser.repositories.StocksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMostPopularStocksUseCase @Inject constructor(
    private val repository: StocksRepository
) {

    operator fun invoke(): Flow<List<StockEntity>> {
        return repository.getStocks(symbols)
    }

    private val symbols = listOf("IBM", "MSFT", "TSLA", "AAPL", "GOOG")
}
