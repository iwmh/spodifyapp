package com.iwmh.spodifyapp.view.episodes

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
class EpisodesScreenViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
): ViewModel() {

    // Refreshing flag for SwipeRefresh of Accompanist.
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var pagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ){
        EpisodesScreenPagingSource(
            savedStateHandle.get(Constants.nav_showId),
            remoteDataSource,
            injectableConstants
        )
    }.flow.cachedIn(viewModelScope)

    init {
        var a: String? = savedStateHandle.get(Constants.nav_showId)
        var b = ""
    }
}