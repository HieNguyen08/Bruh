package com.assignment.stocksbrowser.usecases

import com.assignment.stocksbrowser.model.room.EtfEntity
import com.assignment.stocksbrowser.repositories.EtfRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMostPopularEtfsUseCase @Inject constructor(
    private val repository: EtfRepository
) {

    operator fun invoke(): Flow<List<EtfEntity>> {
        return repository.getEtfs(symbols)
    }

    private val symbols = listOf("AAAU", "IVE", "IVW")
}
