package com.example.mangoplateforinit3rd.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplateforinit3rd.R

class BookMarkRVAdapter (val context : Context,
                         val items : ArrayList<ContentModel>,
                         val itemKeyList : ArrayList<String>,
                         val bookMarIdList : MutableList<String>)
    : RecyclerView.Adapter<BookMarkRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookMarkRVAdapter.ViewHolder, position: Int) {

        holder.bindItems(items[position], itemKeyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item : ContentModel, key : String) {

            itemView.setOnClickListener {
                Toast.makeText(context, item.title, Toast.LENGTH_LONG).show()
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)
            }
            val title = itemView.findViewById<TextView>(R.id.textArea)
            val image = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if(bookMarIdList.contains(key)) {
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            }else {
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }

            title.text = item.title
            Glide.with(context)
                .load(item.imageUrl)
                .into(image)

        }
    }
}