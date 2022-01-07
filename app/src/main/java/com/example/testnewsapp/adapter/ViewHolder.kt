package com.example.testnewsapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var iCardView: CardView = itemView.findViewById(R.id.cardView)

    var iTitle: TextView = itemView.findViewById(R.id.item_title)
    var iDescription: TextView = itemView.findViewById(R.id.item_description)
    var iAuthor: TextView = itemView.findViewById(R.id.item_author)
    var iPublicationTime: TextView = itemView.findViewById(R.id.item_publication_time)
    var iSource: TextView = itemView.findViewById(R.id.item_source)
    var iViewImage: ImageView = itemView.findViewById(R.id.item_image)
}