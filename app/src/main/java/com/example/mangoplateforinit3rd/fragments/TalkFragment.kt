package com.example.mangoplateforinit3rd.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mangoplateforinit3rd.R
import com.example.mangoplateforinit3rd.board.BoardLVAdapter
import com.example.mangoplateforinit3rd.board.BoardModel
import com.example.mangoplateforinit3rd.board.BoardWriteActivity
import com.example.mangoplateforinit3rd.databinding.FragmentTalkBinding
import com.example.mangoplateforinit3rd.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class TalkFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding : FragmentTalkBinding
    private val boardDataList = mutableListOf<BoardModel>()
    private val TAG = TalkFragment::class.java.simpleName
    lateinit var rvAdapter: BoardLVAdapter

//    lateinit var RVadapter : BoardLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)
        getFBBoardData()

        rvAdapter = BoardLVAdapter(boardDataList);
        binding.boardList.adapter = rvAdapter


        binding.writeBtn.setOnClickListener {
            val intent = Intent(context,BoardWriteActivity::class.java)
            startActivity(intent)

        }
        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        binding.bookMarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)

        }
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)

        }
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        return binding.root
    }

    private fun getFBBoardData() {
        // Read from the database

        val postListner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                boardDataList.clear()
                for(dataModel in snapshot.children) {
                    Log.d(TAG, dataModel.toString())
                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                }
                Log.d(TAG, boardDataList.toString())
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        FBRef.boardRef.addValueEventListener(postListner)

    }
}