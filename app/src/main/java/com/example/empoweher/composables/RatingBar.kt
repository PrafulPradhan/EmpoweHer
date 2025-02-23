package com.example.empoweher.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.empoweher.R

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    initialRating: Double = 0.0,
    stars: Int = 5,
    onRatingChanged: (Double) -> Unit,
    starsColor: Color = colorResource(R.color.redorange)
) {
    var rating by remember { mutableStateOf(initialRating) }

    Row(modifier = modifier) {
        for (index in 1..stars) {
            Icon(
                imageVector = when {
                    index <= rating -> Icons.Rounded.Star
                    index - 0.5 == rating -> Icons.Rounded.StarHalf
                    else -> Icons.Rounded.StarOutline
                },
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier
                    .clickable {
                        rating = index.toDouble()
                        onRatingChanged(rating) // Pass the updated rating to the parent
                    }
                    .padding(4.dp)
            )
        }
    }
}