package com.assignment.stocksbrowser.ui.detail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.himanshoe.charty.candle.model.CandleEntry
import com.assignment.stocksbrowser.navigation.NavDestinations
import com.assignment.stocksbrowser.repositories.ChartsRepository
import com.assignment.stocksbrowser.repositories.EtfRepository
import com.assignment.stocksbrowser.repositories.StocksRepository
import com.assignment.stocksbrowser.ui.common.MviViewModel
import com.assignment.stocksbrowser.ui.home.screen.InstrumentType
import com.assignment.stocksbrowser.usecases.GetRangeIntervalsUseCase
import com.assignment.stocksbrowser.usecases.IsFavoriteUseCase
import com.assignment.stocksbrowser.usecases.RangeInterval
import com.assignment.stocksbrowser.usecases.ToggleFavoriteUseCase
import com.assignment.stocksbrowser.usecases.toSelectable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min
import kotlin.math.roundToInt

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stockRepository: StocksRepository,
    private val etfRepository: EtfRepository,
    private val chartsRepository: ChartsRepository,
    private val getRangeIntervalsUseCase: GetRangeIntervalsUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : MviViewModel<DetailState, DetailSideEffect, DetailIntent>(DetailState()) {

    private val symbol: String = NavDestinations.Details.getSymbol(savedStateHandle)
        ?: throw Exception("DetailScreenViewModel without symbol")

    private val type: InstrumentType = NavDestinations.Details.getType(savedStateHandle)
        ?: throw Exception("DetailScreenViewModel without type")

    private var selectedRangeInterval: RangeInterval = getRangeIntervalsUseCase()[1]
    private var loadChartJob: Job? = null

    override fun handleIntent(intent: DetailIntent) {
        when (intent) {
            DetailIntent.NavigateHome -> navigateHome()
            DetailIntent.ToggleFav -> toggleFavorite()
            DetailIntent.Refresh -> loadChart()
            is DetailIntent.SelectRangeInterval -> setRangeInterval(intent.rangeInterval)
        }
    }

    init {
        loadRangeIntervals()
        loadInstrument()
        loadChart()
        loadFavorite()
    }

    private fun loadInstrument() {
        viewModelScope.launch {
            when (type) {
                InstrumentType.Stock -> loadStockDetails()
                InstrumentType.ETF -> loadEtfDetails()
            }
        }
    }

    private suspend fun loadStockDetails() {
        stockRepository.getStock(symbol)
            .collect { instrumentEntity ->
                instrumentEntity?.let { entity ->
                    updateState {
                        it.setInstrumentEntity(entity)
                    }
                }
            }
    }

    private suspend fun loadEtfDetails() {
        etfRepository.getEtf(symbol)
            .collect { instrumentEntity ->
                instrumentEntity?.let { entity ->
                    updateState {
                        it.setInstrumentEntity(entity)
                    }
                }
            }
    }

    private fun loadRangeIntervals() {
        val rangeIntervals = getRangeIntervalsUseCase().map { rangeInterval ->
            rangeInterval.toSelectable(rangeInterval == selectedRangeInterval)
        }
        updateState {
            it.copy(rangeIntervals = rangeIntervals)
        }
    }

    private fun loadChart() {
        loadChartJob?.cancel()

        loadChartJob = viewModelScope.launch {

            updateState { it.copy(isLoading = true) }

            val apiResponse = try {
                chartsRepository.getChart(
                    symbol = symbol,
                    range = selectedRangeInterval.range,
                    interval = selectedRangeInterval.interval
                )
            } catch (e: Exception) {
                postSideEffect(DetailSideEffect.NetworkError)
                null
            }
            val volumeData = apiResponse?.chart?.result?.firstOrNull()?.indicators?.quote?.firstOrNull()?.volume
            val candleData =
                apiResponse?.chart?.result?.firstOrNull()?.indicators?.quote?.firstOrNull()

            if (candleData != null) {
                val lastIndex = min(candleData.high.lastIndex, candleData.low.lastIndex)

                val minValue = candleData.high.filterNotNull().max()
                val maxValue = candleData.low.filterNotNull().min()
                val yAxis = listOf(
                    "\$${minValue.roundToInt()}",
                    "\$${((maxValue + minValue) / 2).roundToInt()}",
                    "\$${maxValue.roundToInt()}"
                )
                val candleStickData = (0..lastIndex).mapNotNull { i ->
                    val high = candleData.high[i] ?: return@mapNotNull null
                    val low = candleData.low[i] ?: return@mapNotNull null
                    val closing = candleData.close[i] ?: return@mapNotNull null
                    val opening = candleData.open[i] ?: return@mapNotNull null
                    CandleEntry(
                        opening = opening.toFloat(),
                        closing = closing.toFloat(),
                        high = high.toFloat(),
                        low = low.toFloat()
                    )
                }
                updateState {
                    it.copy(
                        yAxis = yAxis,
                        candleStickData = candleStickData,
                        isLoading = false
                    )
                }
            } else {
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            toggleFavoriteUseCase(symbol, type)
        }
    }

    private fun navigateHome() {
        postSideEffect(DetailSideEffect.NavigateHome)
    }

    private fun setRangeInterval(value: RangeInterval) {
        selectedRangeInterval = value
        loadRangeIntervals()
        loadChart()
    }

    private fun loadFavorite() {
        viewModelScope.launch {
            isFavoriteUseCase(symbol, type).collect { value ->
                updateState { it.copy(isFavorite = value) }
            }
        }
    }
}
