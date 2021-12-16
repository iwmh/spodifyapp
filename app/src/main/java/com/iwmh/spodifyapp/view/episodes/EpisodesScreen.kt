package com.iwmh.spodifyapp.view.episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun EpisodesScreen(showId: String?) {
    val viewModel: EpisodesScreenViewModel = hiltViewModel()

    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            lazyPagingItems.refresh()
        }
    ) {
        Column {
            Text(text = "Episodes")
            Text(text = lazyPagingItems.itemCount.toString())
            LazyColumn {
                items(lazyPagingItems) { item ->
                    EpisodeCardSquare(showId = item!!.name)
                }
            }
        }
    }






}
