package com.iwmh.spodifyapp.view.episodedetail

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
fun EpisodeDetailScreen(
    episodeName: String,
    imageUrl: String,
    description: String,
    duration: Int,
    releaseDate: String
) {
    val viewModel: EpisodeDetailScreenViewModel = hiltViewModel()

    EpisodeDetailCardSquare(
        episodeName = episodeName,
        imageUrl = imageUrl,
        description = description,
        duration = duration,
        releaseDate = releaseDate
    )


}
