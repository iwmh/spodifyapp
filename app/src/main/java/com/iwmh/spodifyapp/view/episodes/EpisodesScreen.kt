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
    // Hold the Show ID for this screen.
    // TODO: Needs better code here.
    viewModel.showId = showId

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
                items(lazyPagingItems) { episodeItem ->
                    EpisodeCardSquare(
                        episodeName = episodeItem!!.name,
                        imageUrl = episodeItem!!.images[2].url,
                        description = episodeItem!!.description,
                        duration = episodeItem!!.duration_ms,
                        releaseDate = episodeItem!!.release_date,
                    )
                }
            }
        }
    }






}
