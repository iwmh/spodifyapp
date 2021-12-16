package com.iwmh.spodifyapp.view.episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun EpisodeCardSquare(showId: String?){
    Column {
        Text(text = "$showId.")
    }
}