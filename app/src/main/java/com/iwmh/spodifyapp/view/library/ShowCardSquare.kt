package com.iwmh.spodifyapp.view.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.iwmh.spodifyapp.repository.model.api.Show

@Composable
fun ShowCardSquare(show: Show){
    Column {
        Image(
            painter = rememberImagePainter(show.images[1].url),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Text(text = show.name)
    }
}