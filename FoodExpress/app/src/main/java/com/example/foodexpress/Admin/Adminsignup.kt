package com.example.foodexpress.Admin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodexpress.adapter.TablayoutAdapter
import com.example.foodexpress.databinding.ActivityAdminsignupBinding
import com.google.android.material.tabs.TabLayoutMediator

class adminsignup : AppCompatActivity() {
    private lateinit var binding: ActivityAdminsignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminsignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewpager = binding.adminviewpager
        val tablayout = binding.admintabLayout

        viewpager.adapter = TablayoutAdapter(this)
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            run {
                when (position) {
                    0 -> tab.text = "SIGN IN"
                    1 -> tab.text = "SIGN UP"
                }
            }
        }.attach()


    }
}