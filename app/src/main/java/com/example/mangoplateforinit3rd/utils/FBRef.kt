package com.example.mangoplateforinit3rd.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {
    companion object {
        private val database = Firebase.database

        val bookMarkRef = database.getReference("bookmark_list")
        val category1 = database.getReference("contents")
        val category2 = database.getReference("contents2")


        val boardRef = database.getReference("board")


    }



}