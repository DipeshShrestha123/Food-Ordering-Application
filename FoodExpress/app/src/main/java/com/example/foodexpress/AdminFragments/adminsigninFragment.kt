package com.example.foodexpress.adminfragments

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodexpress.Admin.adminmainhome
import com.example.foodexpress.R
import com.example.foodexpress.Signin
import com.example.foodexpress.databinding.FragmentAdminsigninBinding
import com.google.firebase.auth.FirebaseAuth

class adminsigninFragment : Fragment() {
    private lateinit var binding: FragmentAdminsigninBinding
    private var isPasswordVisible = false
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAdminsigninBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.AdminSigninBtn.setOnClickListener{
            signinUsingEmail()
        }
        binding.eye.setOnClickListener {
            passwordIconChanger()
        }
        binding.gotobuyer.setOnClickListener {
            val intent = Intent(requireContext(), Signin::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }
    private fun signinUsingEmail() {
        val email = binding.AdminLoginEmail.text.toString()
        val password = binding.SignupPassword.text.toString()
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(requireContext(), "Fill All The Details", Toast.LENGTH_SHORT).show()
            return
        }
        if(!isValidPassword(password)){
            Toast.makeText(requireContext(), "Password is Weak", Toast.LENGTH_SHORT).show()
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(requireContext(), "Sign in Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), adminmainhome::class.java)
                startActivity(intent)
                requireActivity().finish()

            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun passwordIconChanger() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            // Show password
            binding.eye.setImageResource(R.drawable.eye)
            binding.SignupPassword.transformationMethod = null
        } else {
            binding.SignupPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.eye.setImageResource(R.drawable.eye_hide)
        }

        // Move the cursor to the end of the text to avoid issues with visibility toggling
        binding.SignupPassword.setSelection(binding.SignupPassword.text.length)
    }
    private fun isValidPassword(password: String): Boolean {
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { it.isSpecialChar() }
        val minLength = 8

        return password.length >= minLength &&
                hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar
    }

    private fun Char.isSpecialChar(): Boolean {
        return !this.isLetterOrDigit() && !this.isWhitespace()
    }

}