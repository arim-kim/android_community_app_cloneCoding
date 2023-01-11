package com.example.mangoplateforinit3rd.board

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mangoplateforinit3rd.R
import com.example.mangoplateforinit3rd.databinding.ActivityBoardWriteBinding
import com.example.mangoplateforinit3rd.utils.FBAuth
import com.example.mangoplateforinit3rd.utils.FBRef

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding
    private val TAG = BoardWriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write);


        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString();
            val content = binding.contentArea.text.toString();

            Log.d(TAG, title)
            Log.d(TAG, content)

            FBRef.boardRef.
                    push().setValue(BoardModel(title, content, FBAuth.getUID(), FBAuth.getTime()))

            Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_SHORT).show()


        }
    }
}