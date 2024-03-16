package com.example.foodexpress.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foodexpress.AdminFragments.adminsigninFragment
import com.example.foodexpress.AdminFragments.adminsignupFragment

class TablayoutAdapter(fa:FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> adminsigninFragment()
                1 -> adminsignupFragment()
                else -> adminsigninFragment()
            }
    }
}