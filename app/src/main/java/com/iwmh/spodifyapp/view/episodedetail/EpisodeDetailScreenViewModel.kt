package com.iwmh.spodifyapp.view.episodedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.iwmh.spodifyapp.remote_data_source.RemoteDataSource
import com.iwmh.spodifyapp.repository.pagingsource.EpisodesScreenPagingSource
import com.iwmh.spodifyapp.util.Constants
import com.iwmh.spodifyapp.util.InjectableConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailScreenViewModel @Inject constructor (
): ViewModel() {

    init {
        var b = ""
    }
}