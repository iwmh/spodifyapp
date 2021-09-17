package com.iwmh.spodifyapp.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Secret(
    val client_id: String,
    val redirect_url: String,
)