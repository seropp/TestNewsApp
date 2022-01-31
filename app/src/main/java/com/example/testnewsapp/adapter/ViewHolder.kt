package com.example.testnewsapp.adapter

import android.view.ContextMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener{


    var iTitle: TextView
    var iDescription: TextView
    var iAuthor: TextView
    var iPublicationTime: TextView
    var iSource: TextView
    var iViewImage: ImageView
    var iCardView: CardView


    init {
        iTitle = itemView.findViewById(R.id.item_title)
        iDescription = itemView.findViewById(R.id.item_description)
        iAuthor = itemView.findViewById(R.id.item_author)
        iPublicationTime = itemView.findViewById(R.id.item_publication_time)
        iSource = itemView.findViewById(R.id.item_source)
        iViewImage = itemView.findViewById(R.id.item_image)
        iCardView = itemView.findViewById(R.id.cardView)
        iCardView.setOnCreateContextMenuListener(this)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        menu?.setHeaderTitle("Bookmarks")
        menu?.add(adapterPosition, 131, 0, "Add or delete bookmark")

    }
}


