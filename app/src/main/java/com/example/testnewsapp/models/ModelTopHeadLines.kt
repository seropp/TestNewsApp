package com.example.testnewsapp.models

class NewsApiResponse {

    var status: String? = null
    var totalResults: Int? = null
    var articles: ArrayList<NewsHeadLines>? = null
}

class Source {

    var id: String? = null
    var name: String? = null
}

class NewsHeadLines {

    var source: Source? = null

    var author: String? = null
    var title: String? = null
    var description: String? = null
    var url: String? = null
    var urlToImage: String? = null
    var publishedAt: String? = null
    var content: String? = null
}