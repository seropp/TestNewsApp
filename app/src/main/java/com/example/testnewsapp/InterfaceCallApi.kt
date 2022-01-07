package com.example.testnewsapp

import com.example.testnewsapp.models.NewsApiResponse
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceCallApi {



    @GET("top-headlines")
    fun callHeadLinesNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String?,
        @Query("apiKey") api_key: String?,
    ): Call<NewsApiResponse>
    @GET("everything")
    fun callEverything(
        @Query("language") language: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") api_key: String,
        @Query("q") query: String?
    ): Call<NewsApiResponse>

}



