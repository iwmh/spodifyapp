package com.iwmh.spodifyapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.iwmh.spodifyapp.remote_data_source.RemoteDataSource
import com.iwmh.spodifyapp.repository.model.api.ItemShow
import com.iwmh.spodifyapp.util.InjectableConstants
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
) : PagingSource<String, ItemShow>() {

    /**
     * Remember to call refreshTokensIfNecessary() before any api call.
     */

    override fun getRefreshKey(state: PagingState<String, ItemShow>): String? {
        var anchor = state.anchorPosition
        return injectableConstants.baseUrl + "/me/shows";
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ItemShow> {
        return try {
            remoteDataSource.refreshTokensIfNecessary()
            val response = remoteDataSource.getUsersSavedShows(params.key)
            LoadResult.Page(
                data = response.items,
                nextKey = response.next,
                prevKey = response.previous
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}