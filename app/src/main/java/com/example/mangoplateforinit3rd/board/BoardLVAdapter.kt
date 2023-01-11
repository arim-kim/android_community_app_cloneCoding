package com.example.mangoplateforinit3rd.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mangoplateforinit3rd.R

class BoardLVAdapter(val boardList : MutableList<BoardModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convert_view= convertView

        if(convert_view == null) {
            convert_view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent, false);
        }
            val title = convert_view?.findViewById<TextView>(R.id.titleArea)
            val content = convert_view?.findViewById<TextView>(R.id.contentArea)
            val time = convert_view?.findViewById<TextView>(R.id.timeArea)
            title!!.text = boardList[position].title
            content!!.text = boardList[position].content
            time!!.text = boardList[position].time


        return convert_view!!
    }
}