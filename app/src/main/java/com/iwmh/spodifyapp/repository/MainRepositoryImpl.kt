package com.iwmh.spodifyapp.repository

import com.iwmh.spodifyapp.remote_data_source.RemoteDataSource
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MainRepository{

}