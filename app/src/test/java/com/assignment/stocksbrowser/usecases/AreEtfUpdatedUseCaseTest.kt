package com.assignment.stocksbrowser.usecases

import com.assignment.stocksbrowser.repositories.PreferencesRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
internal class AreEtfUpdatedUseCaseTest {

    private val repository = mockk<PreferencesRepository>()
    private val useCase = AreEtfUpdatedUseCase(repository)

    @Test
    fun `WHEN etfs have been update less than 1 day ago SHOULD return TRUE`() = runTest {
        coEvery { repository.getEtfsUpdateDate() } returns LocalDate.now()
        useCase() shouldBe true
    }

    @Test
    fun `WHEN etfs have been update more than 1 day ago SHOULD return FALSE`() = runTest {
        coEvery { repository.getEtfsUpdateDate() } returns LocalDate.now().minusDays(1)
        useCase() shouldBe false
    }
}
