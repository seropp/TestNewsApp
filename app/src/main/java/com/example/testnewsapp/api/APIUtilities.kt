package com.example.testnewsapp.api

import android.content.Context
import android.content.Intent

import android.widget.Toast
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.example.testnewsapp.login.LoginActivity
import com.example.testnewsapp.models.NewsApiResponse
import com.example.testnewsapp.models.NewsClass
import com.example.testnewsapp.models.Source
import com.example.testnewsapp.models.SourcesApiResponse
import retrofit2.*


class RequestManagerForNewsAPI(val context: Context) {


    private val apiKey: String = context.getString(R.string.api_key)

    fun findEverythingNews(
        list: ArrayList<NewsClass>,
        language: String?,
        sources: String?,
        query: String?,
        adapter: NewsAdapter?
    ) {
        if (NetworkManager.isNetworkAvailable(context)) {
        val call = RetrofitInstance
            .api
            .callEverything(
                q = query,
                language = language,
                sources = sources,
                api_key = apiKey
            )

        requestToAPI(call, list, adapter)
        } else {
            context?.startActivity(Intent(context, InternetConnection::class.java))
        }
    }

    fun findHeadlinesNews(
        category: String,
        list: ArrayList<NewsClass>,
        country: String?,
        query: String?,
        adapter: NewsAdapter?
    ) {
        if (NetworkManager.isNetworkAvailable(context)) {
        val call = RetrofitInstance
            .api
            .callHeadLinesNews(
                q = query,
                country = country,
                category = category,
                apiKey
            )

        requestToAPI(call, list, adapter)
        } else {
            context?.startActivity(Intent(context, InternetConnection::class.java))
        }
    }

    fun findAllSources(
        list: ArrayList<Source>?,
        language: String?,
        country: String?
    ): ArrayList<Source> {
        if (NetworkManager.isNetworkAvailable(context)) {
        val call = RetrofitInstance
            .api
            .callSources(
                language = language,
                country = country,
                api_key = apiKey
            )

        call.enqueue(object : Callback<SourcesApiResponse> {
            override fun onResponse(
                call: Call<SourcesApiResponse>,
                response: Response<SourcesApiResponse>
            ) {
                list?.clear()
                if (response.isSuccessful) {
                    response.body()?.sources?.let { list?.addAll(it) }
                }
            }
            override fun onFailure(call: Call<SourcesApiResponse>, t: Throwable) {
            }
        })
        return list!!
        } else {
            context.startActivity(Intent(context, InternetConnection::class.java))
        }
        return listOf<Source>() as ArrayList<Source>
    }

    private fun requestToAPI(
        call: Call<NewsApiResponse>,
        list: ArrayList<NewsClass>,
        adapter: NewsAdapter?
    ) {

        call.enqueue(object : Callback<NewsApiResponse> {
            override fun onResponse(
                call: Call<NewsApiResponse>,
                response: Response<NewsApiResponse>
            ) {
                list.clear()
                when {
                    response.isSuccessful -> {
                        list.addAll(response.body()!!.articles!!)
                        adapter?.notifyDataSetChanged()
                    }
                    response.code() == 400 -> {
                        Toast.makeText(
                            context,
                            "You must specify a different source or language",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    response.code() == 429 -> {
                        Toast.makeText(context, "You made too many requests.", Toast.LENGTH_SHORT)
                            .show()
                    }
                    response.code() == 500 -> {
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                if (!NetworkManager.isNetworkAvailable(context)) {
                    context.startActivity(Intent(context, InternetConnection::class.java))
                }  else {

                    Toast.makeText(context, "API response error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}