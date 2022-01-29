package com.example.testnewsapp.api

import com.example.testnewsapp.models.NewsApiResponse
import com.example.testnewsapp.models.SourcesApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
     fun callHeadLinesNews(
        @Query("q") q: String?,
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") api_key: String?,
    ): Call<NewsApiResponse>

    @GET("v2/everything")
     fun callEverything(
        @Query("q") q: String?,
        @Query("sources") sources: String?,
        @Query("language") language: String?,
        @Query("apiKey") api_key: String?,
    ): Call<NewsApiResponse>

    @GET("v2/top-headlines/sources")
    fun callSources(
        @Query("language") language: String?,
        @Query("country") country: String?,
        @Query("apiKey") api_key: String?
    ): Call<SourcesApiResponse>

}