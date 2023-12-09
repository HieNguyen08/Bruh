package com.assignment.stocksbrowser.usecases

import com.assignment.stocksbrowser.repositories.PreferencesRepository
import java.time.LocalDate
import javax.inject.Inject

class SetStocksUpdatedUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke() {
        preferencesRepository.setStocksUpdateDate(LocalDate.now())
    }
}
