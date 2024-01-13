package com.example.foodexpress

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var btn = findViewById<Button>(R.id.StartNextBtn)

        btn.setOnClickListener{
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}