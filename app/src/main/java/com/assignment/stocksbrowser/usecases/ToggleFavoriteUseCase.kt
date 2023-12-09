package com.assignment.stocksbrowser.usecases

import com.assignment.stocksbrowser.repositories.PreferencesRepository
import com.assignment.stocksbrowser.ui.home.screen.InstrumentType
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(symbol: String, type: InstrumentType) {
        when (type) {
            InstrumentType.Stock -> preferencesRepository.toggleFavoritesStocks(symbol)
            InstrumentType.ETF -> preferencesRepository.toggleFavoritesEtfs(symbol)
        }
    }
}
