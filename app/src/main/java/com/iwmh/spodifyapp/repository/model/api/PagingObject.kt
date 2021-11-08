package com.iwmh.spodifyapp.repository.model.api

data class PagingObject<T>(
    val href: String,
    val items: List<T>,
    val limit: Int,
    val next: Any,
    val offset: Int,
    val previous: Any,
    val total: Int
)