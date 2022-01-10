package com.iwmh.spodifyapp.view.episodes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import java.util.concurrent.TimeUnit

@Composable
fun EpisodeCardSquare(
        showName: String?,
        imageUrl: String?,
        description: String,
        duration: Int,
        releaseDate: String,
){
    Column {
        Row {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Text(text = "$showName.")
        }
        Row {
            Text(text = description)
        }
        Row {
            Text("$releaseDate・${TimeUnit.MILLISECONDS.toMinutes(duration.toLong())}")
        }
    }
}