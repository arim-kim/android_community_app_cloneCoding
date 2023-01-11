package com.example.mangoplateforinit3rd.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangoplateforinit3rd.R
import com.example.mangoplateforinit3rd.contentsList.BookMarkRVAdapter
import com.example.mangoplateforinit3rd.contentsList.ContentModel
import com.example.mangoplateforinit3rd.databinding.FragmentBookmarkBinding
import com.example.mangoplateforinit3rd.utils.FBAuth
import com.example.mangoplateforinit3rd.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {

    private lateinit var binding : FragmentBookmarkBinding
    private var TAG = BookmarkFragment::class.java.simpleName
    lateinit var rvAdapter: BookMarkRVAdapter
    val bookMarkIdList = mutableListOf<String>()
    val items = ArrayList<ContentModel>()
    val itemKeyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)
            // Read from the database


        // 전체 카테고리에 있는 데이터를 다 가져옴
//        getCategoryData()
        // 사용자가 북마크한 데이터를 다 가져옴
        getBookmarkData()
        // 전체 컨텐츠 중에서 사용자가 북마크한 데이터만 가져옴
        rvAdapter = BookMarkRVAdapter(requireContext(), items, itemKeyList, bookMarkIdList )
        val rv : RecyclerView = binding.bookmarkRv
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
        rv.adapter = rvAdapter





        // 네비게이션 연결
        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)

        }
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment)

        }
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)
        }

        return binding.root
    }

    private fun getCategoryData() {
        val postListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    if(bookMarkIdList.contains(dataModel.key.toString())){
                        items.add(item!!)
                        itemKeyList.add(dataModel.key.toString())
                    }

                }

                rvAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())

            }
        }

        FBRef.category1.addValueEventListener(postListner)
        FBRef.category2.addValueEventListener(postListner)
    }

    private fun getBookmarkData() {
        // Read from the database
        FBRef.bookMarkRef.child(FBAuth.getUID()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookMarkIdList.clear()

                for(dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())
                    bookMarkIdList.add(dataModel.key.toString());

                }
                getCategoryData()
                rvAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}