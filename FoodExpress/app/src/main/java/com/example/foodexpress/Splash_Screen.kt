package com.example.foodexpress

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Handler

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        Handler().postDelayed({
            val intent = Intent(this,StartActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}