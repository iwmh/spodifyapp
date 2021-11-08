package com.iwmh.spodifyapp.repository

import com.iwmh.spodifyapp.remote_data_source.RemoteDataSource
import com.iwmh.spodifyapp.repository.model.api.ItemShow
import com.iwmh.spodifyapp.repository.model.api.PagingObject
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MainRepository{

    // Store data.
    override fun storeData(keyString: String, valueString: String){
        remoteDataSource.storeData(keyString, valueString)
    }

    // Read data.
    override fun readData(keyString: String): String?{
        return remoteDataSource.readData(keyString)
    }

    override suspend fun refreshTokensIfNecessary(): String {
        return remoteDataSource.refreshTokensIfNecessary()
    }

    override suspend fun getUsersSavedShows(): PagingObject<ItemShow> {
        return remoteDataSource.getUsersSavedShows()
    }
}