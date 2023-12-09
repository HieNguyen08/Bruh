package com.assignment.stocksbrowser.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
fun NotifyScreen() {
    LazyColumn {
        item {
            // Title for the Trading List
            Text(
                text = "Notification",
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = notify.status, fontWeight = FontWeight.Bold)
                // Display the stock name
                Text(text = notify.name, fontWeight = FontWeight.Bold)
                // Display the stock price
                if(notify.price!=0.0){
                    Text(text = "$${notify.price}")
                }
                // Display the stock volume
                if(notify.volume!=0) {
                    Text(text = "${notify.volume}")
                }
            }
            // Divider to separate the items
            Divider()
        }


    }
}

@Composable
@Preview
fun NotifyScreenPreview() {
    NotifyScreen()
}