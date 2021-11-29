package com.iwmh.spodifyapp.repository.model.api

data class PagingObject<T>(
    val href: String,
    val items: List<T>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)