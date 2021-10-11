package com.iwmh.spodifyapp.view.auth

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.iwmh.spodifyapp.viewmodel.MainViewModel
import net.openid.appauth.AuthorizationRequest

@Composable
fun AuthScreen(
    viewModel: MainViewModel,
    authRequest: AuthorizationRequest,
    launcher: ActivityResultLauncher<Intent>,
    uri: Uri
){
    Button(
        onClick = {
//            val authIntent = viewModel.getAuthorizationRequestIntent(authRequest)
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data = uri
            launcher.launch(intent)
        },
    ) {
        Text(text = "auth")
    }
}
