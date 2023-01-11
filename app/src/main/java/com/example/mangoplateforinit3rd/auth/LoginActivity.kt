package com.example.mangoplateforinit3rd.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mangoplateforinit3rd.MainActivity
import com.example.mangoplateforinit3rd.R
import com.example.mangoplateforinit3rd.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener {
            var isGoToLogin = true
            val emailForLogin = binding.emailForLogin.text.toString()
            val passwordForLogin = binding.passwoardForLogin.text.toString()

            if (emailForLogin.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToLogin = false
            }
            if (passwordForLogin.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToLogin = false
            }

            if(isGoToLogin) {
                auth.signInWithEmailAndPassword(emailForLogin, passwordForLogin)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            val user = auth.currentUser
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "로그인 실패",
                               Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }


    }
}