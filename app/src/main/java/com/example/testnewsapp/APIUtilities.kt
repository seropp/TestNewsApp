package com.example.testnewsapp

import android.content.Context
import android.widget.Toast
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsApiResponse
import com.example.testnewsapp.models.NewsHeadLines
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.google.gson.GsonBuilder

import com.google.gson.Gson




class RequestManagerForNewsAPI {
    private var retrofit: Retrofit? = null

    fun findNews(
        context: Context?,
        category: String,
        list: ArrayList<NewsHeadLines>,
        newsAdapter: NewsAdapter
    ) {
        val country = getCountry()
        var key: String = context!!.getString(R.string.api_key2);

        val call = getInterfaceAPI()!!
            .callHeadLinesNews(country, category, null, key)

        call.enqueue(object : Callback<NewsApiResponse> {
            override fun onResponse(
                call: Call<NewsApiResponse>,
                response: Response<NewsApiResponse>
            ) {
                if (response.isSuccessful) {
                    list.addAll(response.body()!!.articles!!)
                    newsAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Unsuccessful API response", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                Toast.makeText(context, "API response error", Toast.LENGTH_LONG).show()
            }
        })
    }



    private fun getCountry(): String {
        return "ru"

        // some code
    }



    fun findNewsForEverything(
        context: Context?,
        list: ArrayList<NewsHeadLines>,
        newsAdapter: NewsAdapter
    ) {
        var key: String = context!!.getString(R.string.api_key2);
        val language: String = "en"

        val call = getInterfaceAPI()!!
            .callEverything("en",20 , key, null)

        call.enqueue(object : Callback<NewsApiResponse> {
            override fun onResponse(
                call: Call<NewsApiResponse>,
                response: Response<NewsApiResponse>
            ) {
                val statusCode = response.code()

                if (response.isSuccessful) {
                    list.addAll(response.body()!!.articles!!)
                    newsAdapter.notifyDataSetChanged()
                }

                else {
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


