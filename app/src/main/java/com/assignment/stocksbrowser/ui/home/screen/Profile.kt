package com.assignment.stocksbrowser.ui.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assignment.stocksbrowser.R



@Composable
fun ProfileScreen() {
    LazyColumn{
        item {
            // Title for the Trading List
            Text(
                text = "Profile",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
                ,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            // Divider to separate the title from the items
            Divider(color = Color.Black, thickness = 2.dp)
        }
        itemsIndexed(userProfile) { index, user ->

            Card(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .padding(8.dp), // Add padding for better appearance
                elevation = 4.dp
            ) {
                // Use a Box to center the Image
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    // An Image to load the profile avatar from a resource
                    Image(
                        painter = painterResource(id = R.drawable.bugcat),
                        contentDescription = "Profile Avatar",

                        modifier = Modifier
                            .size(100.dp) // Adjust the size of the image as needed
                            .clip(CircleShape)
                    )
                }
            }


            // A Spacer to add some vertical space
            Spacer(modifier = Modifier.height(8.dp))
            // A Row to display the profile name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // A Text to display the label "Name"
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.h6
                )
                // A Text to display the profile name
                Text(
                    text = user.user,
                    style = MaterialTheme.typography.body1
                )
            }
            // A Spacer to add some vertical space
            Spacer(modifier = Modifier.height(8.dp))
            // A Row to display the profile email
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // A Text to display the label "Email"
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.h6
                )
                // A Text to display the profile email
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.body1
                )
            }
            // A Spacer to add some vertical space
            Spacer(modifier = Modifier.height(8.dp))
            // A Row to display the profile DOB
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // A Text to display the label "DOB"
                Text(
                    text = "DOB",
                    style = MaterialTheme.typography.h6
                )
                // A Text to display the profile DOB
                Text(
                    text = user.DOB,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}



@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen()
}