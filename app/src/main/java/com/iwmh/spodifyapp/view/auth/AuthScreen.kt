package com.iwmh.spodifyapp.view.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.iwmh.spodifyapp.MainViewModel
import net.openid.appauth.AuthorizationRequest

@Composable
fun AuthScreen(
    viewModel: MainViewModel,
    authRequest: AuthorizationRequest,
    launcher: ActivityResultLauncher<Intent>
){
    Button(
        onClick = {
            val authIntent = viewModel.getAuthorizationRequestIntent(authRequest)
            launcher.launch(authIntent)
        },
    ) {
        Text(text = "auth")
    }
}
