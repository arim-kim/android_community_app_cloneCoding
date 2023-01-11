package com.example.mangoplateforinit3rd.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mangoplateforinit3rd.MainActivity
import com.example.mangoplateforinit3rd.R
import com.example.mangoplateforinit3rd.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityJoinBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.joinBtn.setOnClickListener {
            var isGoToJoin = true
            val email = binding.email.text.toString()
            val password = binding.pw.text.toString()
            val passwordCheck = binding.pwCheck.text.toString()

            // 값이 비었는지 확인
            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (passwordCheck.isEmpty()) {
                Toast.makeText(this, "비밀번호 확인을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 비밀번호 확인
            if (!passwordCheck.equals(password)) {
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 다릅니다.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 비밀번호 길이
            if (password.length < 6 ) {
                Toast.makeText(this, "비밀번호 길이를 6자 이상으로 설정해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if(isGoToJoin) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()

                        }
                    }
            }
        }
    }
}
