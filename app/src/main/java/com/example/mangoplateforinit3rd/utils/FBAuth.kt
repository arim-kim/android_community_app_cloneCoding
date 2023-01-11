package com.example.mangoplateforinit3rd.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class FBAuth {

    companion object {
        private lateinit var auth :FirebaseAuth

        fun getUID() : String {

            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString();

        }

        fun getTime() : String {
            val currentTime = Calendar.getInstance().time
            val dateForm = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentTime)
            return dateForm
        }
    }
}