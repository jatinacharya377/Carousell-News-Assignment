package com.carousell.carousellnews.data.model

data class Article(
    val id: String?,
    val title: String?,
    val description: String?,
    val banner_url: String?,
    val time_created: Long?,
    val rank: Int?,
    var published_time: String?
)
