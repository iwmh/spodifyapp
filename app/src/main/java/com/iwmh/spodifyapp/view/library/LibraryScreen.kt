package com.iwmh.spodifyapp.view.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LibraryScreen(name: String) {
    val viewModel: LibraryScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()


    Text(text = "You library.")

    Column() {
        uiState.followingShows.forEach{
            Text(text = it.show.name)
        }
    }




}
