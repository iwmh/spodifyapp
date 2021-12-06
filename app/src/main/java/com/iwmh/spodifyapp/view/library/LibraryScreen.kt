package com.iwmh.spodifyapp.view.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items

@Composable
fun LibraryScreen(viewModel: LibraryScreenViewModel) {
//    val viewModel: LibraryScreenViewModel = hiltViewModel()
//    val uiState by viewModel.uiState.collectAsState()

    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    Column {
        Text(text = "Your library.")
        Text(text = lazyPagingItems.itemCount.toString())
        LazyColumn {
            items(lazyPagingItems) { item ->
                ShowCardSquare(show = item!!.show)
            }
        }
    }






}
