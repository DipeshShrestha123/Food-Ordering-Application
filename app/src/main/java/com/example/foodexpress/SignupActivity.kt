package com.example.foodexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.example.foodexpress.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var isPasswordVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.HaveAccount.setOnClickListener {
            val intent = Intent(this, Signin::class.java)
            startActivity(intent)
        }


        binding.eye.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // Show password
                binding.eye.setImageResource(R.drawable.eye)
                binding.SignupPassword.transformationMethod = null
            } else {
                binding.SignupPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.eye.setImageResource(R.drawable.eye_hide)
            }

            // Move the cursor to the end of the text to avoid issues with visibility toggling
            binding.SignupPassword.setSelection(binding.SignupPassword.text.length)
        }


        binding.SignupBtn.setOnClickListener {
            val userid = binding.SignupName.text.toString()
            val email = binding.SignupEmail.text.toString()
            val password = binding.SignupPassword.text.toString()
            if (userid.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Fill All The Details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                Toast.makeText(this,"Sign Up Successful",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Sign Up Failed",Toast.LENGTH_SHORT).show()
            }
        }


//        binding.SignupGooglebtn.setOnClickListener{
//            signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(
//                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.your_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                .build()
//        }


    }
}