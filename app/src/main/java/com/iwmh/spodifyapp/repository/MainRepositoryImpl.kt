package com.iwmh.spodifyapp.repository

import com.iwmh.spodifyapp.remote_data_source.RemoteDataSource
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
}