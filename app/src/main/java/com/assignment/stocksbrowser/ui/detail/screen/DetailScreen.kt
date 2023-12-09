package com.assignment.stocksbrowser.ui.detail.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.himanshoe.charty.candle.model.CandleEntry
import com.assignment.stocksbrowser.ui.common.showErrorSnackBar
import com.assignment.stocksbrowser.ui.detail.components.CandleStickBox
import com.assignment.stocksbrowser.ui.detail.components.DetailsTable
import com.assignment.stocksbrowser.ui.detail.components.IntervalButtons
import com.assignment.stocksbrowser.ui.detail.components.PriceAndChange
import com.assignment.stocksbrowser.ui.detail.components.TopAppBarDetails
import com.assignment.stocksbrowser.ui.home.screen.DeltaIndicator
import com.assignment.stocksbrowser.ui.home.screen.InstrumentType
import com.assignment.stocksbrowser.ui.home.screen.ListItem
import com.assignment.stocksbrowser.ui.theme.StocksBrowserTheme
import com.assignment.stocksbrowser.usecases.GetRangeIntervalsUseCase
import com.assignment.stocksbrowser.usecases.toSelectable
import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun DetailScreen(navController: NavController, viewModel: DetailViewModel = hiltViewModel()) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val viewState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is DetailSideEffect.NavigateHome -> navController.popBackStack()
            DetailSideEffect.NetworkError ->
                coroutineScope
                    .showErrorSnackBar(scaffoldState.snackbarHostState) {
                        viewModel.handleIntent(DetailIntent.Refresh)
                    }
        }
    }

    DetailScreen(viewState, scaffoldState, viewModel::handleIntent)
}

@Composable
private fun DetailScreen(
    state: DetailState,
    scaffoldState: ScaffoldState,
    action: (DetailIntent) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val scrolledListState by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset > 0f } }
    Scaffold(
        topBar = {
            TopAppBarDetails(state.instrument, scrolledListState, state.isFavorite, {
                action(DetailIntent.NavigateHome)
            }, {
                action(DetailIntent.ToggleFav)
            })
        },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            LazyColumn(state = lazyListState, contentPadding = paddingValues) {

                state.instrument?.let { instrument ->
                    item {
                        PriceAndChange(
                            percentageChange = instrument.percentageChange,
                            lastSalePrice = instrument.lastSalePrice,
                            deltaIndicator = instrument.deltaIndicator
                        )
                    }
                }

                item {
                    CandleStickBox(candleStickData = state.candleStickData, yAxis = state.yAxis)
                }

                item {
                    IntervalButtons(state.rangeIntervals) {
                        action(DetailIntent.SelectRangeInterval(it))
                    }
                }

                item {
                    DetailsTable(state.details)
                }
            }
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, device = Devices.PIXEL_4, name = "Light Mode")
@Composable
fun DetailScreen_Preview() {
    StocksBrowserTheme {
        DetailScreen(
            DetailState(
                instrument = ListItem.InstrumentItem(
                    symbol = "IBM",
                    name = "International Business Machines Corp",
                    lastSalePrice = "$15.8",
                    percentageChange = "0.5%",
                    deltaIndicator = DeltaIndicator.Up,
                    type = InstrumentType.Stock
                ),
                candleStickData = listOf(
                    CandleEntry(10f, 5f, 7f, 9f),
                    CandleEntry(11f, 7f, 9f, 8f),
                    CandleEntry(12f, 6f, 8f, 9f)
                ),
                yAxis = listOf("12", "6", "0"),
                rangeIntervals = GetRangeIntervalsUseCase().invoke()
                    .mapIndexed { index, rangeInterval -> rangeInterval.toSelectable(index == 1) },
                details = mapOf(
                    "Industry" to "Data mining",
                    "Sector" to "Internet and Telecommunication",
                    "IPO Year" to "2019"
                )
            ),
            rememberScaffoldState()
        ) {}
    }
}
