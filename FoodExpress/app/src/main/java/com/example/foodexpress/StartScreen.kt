package com.example.foodexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.foodexpress.databinding.ActivityLocationBinding
import com.example.foodexpress.databinding.ActivityStartScreenBinding
import com.google.firebase.auth.FirebaseAuth

class StartScreen : AppCompatActivity() {
    private lateinit var binding: ActivityStartScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.StartNextBtn.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}