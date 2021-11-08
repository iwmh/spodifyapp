package com.iwmh.spodifyapp.remote_data_source

import com.iwmh.spodifyapp.repository.model.api.ItemShow
import com.iwmh.spodifyapp.repository.model.api.PagingObject

interface WebApiClient {
    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

    // Get User's Saved Shows
    suspend fun getUsersSavedShows(): PagingObject<ItemShow>
}