package com.example.testnewsapp.adapter


import android.view.ContextMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener{


    var iTitle: TextView = itemView.findViewById(R.id.item_title)
    var iDescription: TextView = itemView.findViewById(R.id.item_description)
    var iAuthor: TextView = itemView.findViewById(R.id.item_author)
    var iPublicationTime: TextView = itemView.findViewById(R.id.item_publication_time)
    var iSource: TextView = itemView.findViewById(R.id.item_source)
    var iViewImage: ImageView = itemView.findViewById(R.id.item_image)
    var iCardView: CardView = itemView.findViewById(R.id.cardView)

    init {
        iCardView.setOnCreateContextMenuListener(this)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        menu?.setHeaderTitle("Bookmark")
        menu?.add(adapterPosition, 101, 0, "Add or delete bookmark")

    }
}


