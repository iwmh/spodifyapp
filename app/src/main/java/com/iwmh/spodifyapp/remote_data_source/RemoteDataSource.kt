package com.iwmh.spodifyapp.remote_data_source

import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse

interface RemoteDataSource {

    // Receive response with AuthorizationCode
    suspend fun exchangeAuthorizationCode(clientId: String, redirectUrl: String) : AuthorizationResponse

    // Receive response with AccessToken and RefreshToken
    suspend fun exchangeToken(clientId: String, redirectUrl: String, authorizationCode: String, codeVerifier: String) : TokenResponse

    // Receive response with AccessToken and RefreshToken (refreshing)
    suspend fun refreshToken(clientId: String, redirectUrl: String, refreshToken: String) : TokenResponse
}