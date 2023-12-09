package com.assignment.stocksbrowser.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp




@Composable
fun PortfolioScreen() {

    LazyColumn {
        item {
            // Title for the Trading List
            Text(
                text = "Trading List",
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
        itemsIndexed(stocks) { index, stock ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display the stock name
                Text(text = stock.name, fontWeight = FontWeight.Bold)
                // Display the stock price
                Text(text = "$${stock.price}")
                // Display the stock change
                Text(
                    text = "${stock.change}%",
                    color = if (stock.change < 0) Color.Red
                    else if(stock.change>0) Color.Green
                    else Color.Yellow
                )
                // Display the stock volume
                Text(text = "${stock.volume}K")
                // Button to perform some action on the stock
                Button(onClick = { print("nothing") }) {
                    Text(text = "Alert")
                }
            }
            // Divider to separate the items
            Divider()
        }
        item {
            // Title for the Trading List
            Text(
                text = "Total assests",
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
        itemsIndexed(userProfile) { index, profile ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Assets: ${profile.totalAssets}")
                    Text("Purchasing Power: ${profile.purchasingPower}")
                }
            }
            // Divider to separate the items
            Divider()
        }
        item {
            // Title for the Trading List
            Text(
                text = "Market history",
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
        itemsIndexed(stockHistoryList) { index, notify ->
            if(notify.status!="Alert") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Display Status

                    Text(text = notify.status, fontWeight = FontWeight.Bold)
                    // Display the stock name
                    Text(text = notify.name, fontWeight = FontWeight.Bold)
                    // Display the stock price
                    Text(text = "$${notify.price}")

                    // Display the stock volume
                    Text(text = "${notify.volume}")
                }
                // Divider to separate the items
                Divider()
            }
        }
        itemsIndexed(blanked){
                index,blank->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){ Text(text ="")}

        }
    }
}

@Composable
@Preview
fun PortfolioScreenPreview() {
    PortfolioScreen()
}