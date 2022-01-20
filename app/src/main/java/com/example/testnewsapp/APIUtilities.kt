package com.example.testnewsapp

import android.content.Context
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsApiResponse
import com.example.testnewsapp.models.NewsClass
import okhttp3.Cache.key
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query


class RequestManagerForNewsAPI(val context: Context, val adapter: NewsAdapter) {

    private var retrofit: Retrofit? = null

    fun findEverythingNews(
        list: ArrayList<NewsClass>,
        language: String? = "en",
        sources: String? = "",
        query: String?
    ) {

        val apiKey: String = context!!.getString(R.string.api_key);
        val string_sources = ""

        val call = getInterfaceAPI()!!
            .callEverything(
                query = null,
                sources  = string_sources,
                language = "en",
                api_key = apiKey)

        requestToAPI(call, list)

    }

    fun findHeadlinesNews(
        category: String,
        list: ArrayList<NewsClass>,
        country: String? = "ru",
        sources: String? = null,
        query: String?
    ) {

        val apiKey: String = context!!.getString(R.string.api_key);

        val call = getInterfaceAPI()!!
            .callHeadLinesNews(
                q = query,
                country = country,
                category = category,
                sources = sources,
                apiKey)

        requestToAPI(call, list)
    }

    private fun requestToAPI(
        call: Call<NewsApiResponse>,
        list: ArrayList<NewsClass>
    ) {
        call.enqueue(object : Callback<NewsApiResponse> {
            override fun onResponse(
                call: Call<NewsApiResponse>,
                response: Response<NewsApiResponse>
            ) {
                val statusCode = response.code()

                if (response.isSuccessful) {
                    list.addAll(response.body()!!.articles!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "$statusCode Bad request", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                Toast.makeText(context, "API response error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getInterfaceAPI(): InterfaceCallApi? {
        if (retrofit === null) {
            retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!.create(InterfaceCallApi::class.java)
    }

}

interface InterfaceCallApi {


    @GET("top-headlines")
    fun callHeadLinesNews(
        @Query("q") q: String?,
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("sources") sources: String?,
        @Query("apiKey") api_key: String?,
    ): Call<NewsApiResponse>

    @GET("everything")
    fun callEverything(
        @Query("q") query: String?,
        @Query("sources") sources: String?,
        @Query("language") language: String?,
        @Query("apiKey") api_key: String?,
    ): Call<NewsApiResponse>
}