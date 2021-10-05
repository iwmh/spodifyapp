package com.iwmh.spodifyapp.remote_data_source

import android.content.Context
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject

class WebApiClientImpl @Inject constructor(
    private val context: Context
) : WebApiClient {

}