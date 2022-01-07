package com.example.testnewsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testnewsapp.R
import com.example.testnewsapp.WebView
import com.example.testnewsapp.models.NewsHeadLines


class NewsAdapter(var context: Context?, var newsHLArrayList: ArrayList<NewsHeadLines>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.iCardView.setOnClickListener {
            val intent: Intent = Intent(context, WebView::class.java)
            intent.putExtra("url", newsHLArrayList[position].url)
            context?.startActivity(intent)
        }

        holder.iTitle.text = newsHLArrayList[position].title
        holder.iDescription.text = newsHLArrayList[position].description
        holder.iAuthor.text = newsHLArrayList[position].author
        holder.iSource.text = newsHLArrayList[position].source!!.name

        Glide.with(context!!).load(newsHLArrayList[position].urlToImage).into(holder.iViewImage)

        val time = "Published At:" + newsHLArrayList[position].publishedAt!!.substring(0..9) +
                " " + newsHLArrayList[position].publishedAt!!.substring(11..15)
        holder.iPublicationTime.text = time
    }

    override fun getItemCount(): Int {
        return newsHLArrayList.size
    }
}