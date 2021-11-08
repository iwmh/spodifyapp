package com.iwmh.spodifyapp.repository.model

import com.iwmh.spodifyapp.repository.model.api.ItemShow

data class ShowsFeed(
    val followingShows: List<ItemShow>
    )

