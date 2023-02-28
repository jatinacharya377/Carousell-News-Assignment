package com.carousell.carousellnews.data.remote.repository

import com.carousell.carousellnews.data.remote.api.ApiService

/**
 * This component is the bridge between our API interface and ViewModel.
 * It is responsible for making API calls.
 * @author: Jagannath Acharya
 */
class ArticlesRepository {
    private val api = ApiService().getApi()

    suspend fun getArticles() = api.getArticles()
}