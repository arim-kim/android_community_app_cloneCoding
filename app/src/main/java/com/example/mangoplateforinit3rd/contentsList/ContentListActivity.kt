package com.example.mangoplateforinit3rd.contentsList

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangoplateforinit3rd.R
import com.example.mangoplateforinit3rd.utils.FBAuth
import com.example.mangoplateforinit3rd.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentListActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference
    val bookMarkIdList = mutableListOf<String>()
    lateinit var rvAdapter: ContentRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)

        // Write a message to the database
        val database = Firebase.database
        val category = intent.getStringExtra("category")
        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()
        rvAdapter = ContentRvAdapter(baseContext, items, itemKeyList, bookMarkIdList)


        if(category == "category1") {
            myRef = database.getReference("contents")

        }
        else if (category == "category2") {
           myRef = database.getReference("contents2")
        }

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(dataModel in dataSnapshot.children) {
                    Log.d("ContentsListActivity", "Value is: ${dataModel.toString()}")
                    Log.d("ContentsListActivity", "key is: ${dataModel.key.toString()}")

                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged() // 동기 비동기
                Log.d("ContentsListActivity", "item : ${items.toString()}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ContentsListActivity", "Failed to read value.", error.toException())
            }
        })


        val rv : RecyclerView = findViewById(R.id.rv)
        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(this, 2)
        getBookmarkData()

    }

    private fun getBookmarkData() {
        // Read from the database
        FBRef.bookMarkRef.child(FBAuth.getUID()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookMarkIdList.clear()
                for(dataModel in dataSnapshot.children) {
                    bookMarkIdList.add(dataModel.key.toString())
                }

                Log.d("ContentsListActivity-getBookMarkData", bookMarkIdList.toString())
                rvAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ContentsListActivity", "Failed to read value.", error.toException())
            }
        })

    }
}