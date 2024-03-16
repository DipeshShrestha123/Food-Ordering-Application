package com.example.foodexpress.Admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodexpress.R
import com.example.foodexpress.databinding.ActivityAdminhomeBinding

class adminmainhome : AppCompatActivity() {
    private lateinit var binding: ActivityAdminhomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminhomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =  supportFragmentManager.findFragmentById(R.id.adminfragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomnavigation = binding.adminbottomNavigationView
        bottomnavigation.setupWithNavController(navController)

    }
}