package com.iwmh.spodifyapp.remote_data_source

import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiClient: WebApiClient
) : RemoteDataSource{

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