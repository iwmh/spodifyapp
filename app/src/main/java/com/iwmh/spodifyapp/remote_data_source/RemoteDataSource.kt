package com.iwmh.spodifyapp.remote_data_source

import com.iwmh.spodifyapp.repository.model.api.ItemShow
import com.iwmh.spodifyapp.repository.model.api.PagingObject

interface RemoteDataSource {

    // Store data to local storage.
    fun storeData(keyString: String, valueString: String)

    // Read data from local storage.
    fun readData(keyString: String): String?

    // Get User's saved shows
    suspend fun getUsersSavedShows(): PagingObject<ItemShow>

    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String
}