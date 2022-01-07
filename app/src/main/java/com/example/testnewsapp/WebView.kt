package com.example.testnewsapp

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class WebView : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        toolbar = findViewById(R.id.toolbar)
        webView = findViewById(R.id.web_view)

        setSupportActionBar(toolbar)

        val intent: Intent = intent
        val url: String = intent.getStringExtra("url").toString()

        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }
}