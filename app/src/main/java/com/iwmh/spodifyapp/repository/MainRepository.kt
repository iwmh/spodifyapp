package com.iwmh.spodifyapp.repository

import com.iwmh.spodifyapp.repository.model.api.ItemShow
import com.iwmh.spodifyapp.repository.model.api.PagingObject

interface MainRepository {

    fun storeData(keyString: String, valueString: String)

    fun readData(keyString: String): String?

    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

}
