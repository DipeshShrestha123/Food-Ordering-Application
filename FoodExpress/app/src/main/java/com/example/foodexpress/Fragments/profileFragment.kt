package com.example.foodexpress.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodexpress.R
import com.example.foodexpress.Signin
import com.example.foodexpress.Utils
import com.example.foodexpress.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class profileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.logoutbtn.setOnClickListener { 
            firebaseAuth.signOut()
            Toast.makeText(requireContext(),"Sign out Successfully",Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(),Signin::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return binding.root
    }

}