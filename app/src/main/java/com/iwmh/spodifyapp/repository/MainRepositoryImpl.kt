package com.iwmh.spodifyapp.repository

import com.iwmh.spodifyapp.remote_data_source.RemoteDataSource
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MainRepository{

    override suspend fun exchangeAuthorizationCode(
        clientId: String,
        redirectUrl: String
    ): AuthorizationResponse {
        TODO("Not yet implemented")
    }

    override suspend fun exchangeToken(
        clientId: String,
        redirectUrl: String,
        authorizationCode: String,
        codeVerifier: String
    ): TokenResponse {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(
        clientId: String,
        redirectUrl: String,
        refreshToken: String
    ): TokenResponse {
        TODO("Not yet implemented")
    }
}