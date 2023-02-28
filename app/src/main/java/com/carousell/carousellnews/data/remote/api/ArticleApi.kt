package com.carousell.carousellnews.data.remote.api

import com.carousell.carousellnews.data.model.Article
import retrofit2.http.GET

interface ArticleApi {
    @GET("carousell_news.json")
    suspend fun getArticles(): List<Article>
}