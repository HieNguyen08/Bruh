package com.assignment.stocksbrowser.repositories

import com.assignment.stocksbrowser.dao.StocksDao
import com.assignment.stocksbrowser.model.room.StockEntity
import com.assignment.stocksbrowser.utils.toSafeString
import javax.inject.Inject

class StocksRepository @Inject constructor(
    private val dao: StocksDao
) {

    suspend fun insertStocks(entities: List<StockEntity>) = dao.insertAll(entities)

    fun getStocks(symbols: List<String>) =
        dao.getStocksBySymbols(symbols)

    fun getStock(symbol: String) =
        dao.getStockBySymbol(symbol)

    fun findStocks(searchQuery: String) =
        dao.findStocks(searchQuery.toSafeString() + "%")
}
