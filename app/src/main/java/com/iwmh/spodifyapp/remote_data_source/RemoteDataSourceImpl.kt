package com.iwmh.spodifyapp.remote_data_source

import com.iwmh.spodifyapp.repository.model.api.Episode
import com.iwmh.spodifyapp.repository.model.api.ItemShow
import com.iwmh.spodifyapp.repository.model.api.PagingObject
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiClient: WebApiClient,
    private val localStorage: LocalStorage
) : RemoteDataSource{

    // Store data.
    override fun storeData(keyString: String, valueString: String){
        localStorage.storeData(keyString, valueString)
    }

    // Read data.
    override fun readData(keyString: String): String? {
        return localStorage.readData(keyString)
    }

    override suspend fun refreshTokensIfNecessary(): String {
        return apiClient.refreshTokensIfNecessary()
    }

    override suspend fun getUsersSavedShows(url: String?): PagingObject<ItemShow> {
        return apiClient.getUsersSavedShows(url)
    }

    // Get Show Episodes
    override suspend fun getShowEpisodes(showId: String?, url: String?): PagingObject<Episode> {
        return apiClient.getShowEpisodes(showId, url)
    }


}