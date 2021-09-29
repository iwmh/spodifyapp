package com.iwmh.spodifyapp.remote_data_source

import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiClient: WebApiClient
) : RemoteDataSource{


}