package com.example.foodexpress

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        var btn = findViewById<Button>(R.id.StartNextBtn)

        btn.setOnClickListener{
            val intent = Intent(applicationContext,Signin::class.java)
            startActivity(intent)
        }
    }
}