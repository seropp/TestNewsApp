package com.example.testnewsapp

import android.content.Context
import android.widget.Toast
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsApiResponse
import com.example.testnewsapp.models.NewsClass
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query


class RequestManagerForNewsAPI {
    private var retrofit: Retrofit? = null

    fun findEverythingNews(
        context: Context?,
        list: ArrayList<NewsClass>,
        newsAdapter: NewsAdapter
    ) {
        val query1: String? = "????"
        val sources1: String? = "????"
        val language1: String? = "????"
        var key1: String = context!!.getString(R.string.api_key2);


        val call = getInterfaceAPI()!!
            .callEverything(
                null, "CNN,techcrunch",
                "en", "74a1692513764c3e9a7aedb4a5dbc090"
            )

        requestToAPI(call, context, newsAdapter, list)

    }

    fun findHeadlinesNews(
        context: Context?,
        category: String,
        list: ArrayList<NewsClass>,
        newsAdapter: NewsAdapter,
        query: String? = null
    ) {
        val sources1: String? = "????"
        val country = getCountry()
        var key: String = context!!.getString(R.string.api_key2);

        val call = getInterfaceAPI()!!
            .callHeadLinesNews(query, country, category, "74a1692513764c3e9a7aedb4a5dbc090")

        requestToAPI(call, context, newsAdapter, list)
    }

    private fun requestToAPI(
        call: Call<NewsApiResponse>,
        context: Context?,
        newsAdapter: NewsAdapter,
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
                    newsAdapter.notifyDataSetChanged()
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

    private fun getCountry(): String {
        return "ru"

        // some code
    }
}

interface InterfaceCallApi {


    @GET("top-headlines")
    fun callHeadLinesNews(
        @Query("q") query: String?,
        @Query("country") country: String?,
        @Query("category") category: String?,
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
