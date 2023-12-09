package com.assignment.stocksbrowser.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun TradingScreen() {

    var stockName by remember { mutableStateOf("") }

    Column {
        LazyColumn{
            item {
                // Title for the Trading List
                Text(
                    text = "Trading",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                // Divider to separate the title from the items
                Divider(color= Color.Black , thickness = 2.dp)
            }
        }
        TextField(
            value = stockName,
            onValueChange = { stockName = it },
            label = { Text("Stock Name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        LazyColumn {

            itemsIndexed(stock_list) { index, stock_detail ->
                if(stock_detail.name==stockName){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        // Display the current price
                        Text(text ="Current price: ${stock_detail.current_price}" )
                        // Display the limit up price
                        Text(text ="Limit up price: ${stock_detail.limit_up_price}" )
                        // Display the limit down price
                        Text(text ="Limit down price: ${stock_detail.limit_down_price}" )
                        // Display the P/B
                        Text(text ="P/B: ${stock_detail.pb}" )
                        // Display the ROE
                        Text(text ="ROE: ${stock_detail.roe}" )
                        // Display the ROA
                        Text(text ="ROA: ${stock_detail.roa}" )
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TradingButton("Buy"){
                            }
                            TradingButton("Sell"){

                            }
                        }
                        // Display the Bid volumn
                        Text(text ="Bid vol: ${stock_detail.bid_volumn}" )
                        // Display the Bid price
                        Text(text ="Bid price: ${stock_detail.bid_price}" )
                        // Display the Ask volumn
                        Text(text ="Ask vol: ${stock_detail.ask_volumn}" )
                        // Display the ask price
                        Text(text ="Ask price: ${stock_detail.ask_price}" )

                    }
                    // Divider to separate the items
                    Divider()
                }}

        }

    }

}


@Composable
fun TradingButton(text: String, onClick:() ->Unit) {
    val buttonColor = if (text.equals("Buy", ignoreCase = true)) {
        Color.Red
    } else {
        Color.Green
    }
    var showPopup by remember { mutableStateOf(false) }
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)
    ) {
        Text(text = text)

    }
}
@Composable
@Preview
fun TradingScreenPreview() {
    TradingScreen()
}