package com.example.foodexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodexpress.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signin : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()


        binding.DontHaveAccount.setOnClickListener{
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        binding.LoginBtn.setOnClickListener{
            val email = binding.LoginEmail.text.toString()
            val password = binding.Password.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Fill All The Details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                Toast.makeText(this,"Sign in Successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,StartScreen::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Registered Yourself",Toast.LENGTH_SHORT).show()
            }
        }

        binding.ResetPassword.setOnClickListener {
            val email = binding.LoginEmail.text.toString()
            if (email.isBlank()){
                Toast.makeText(this,"To reset the password enter the email",Toast.LENGTH_SHORT).show()
            }
            else{
                firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener{
                    Toast.makeText(this,"Email Send Successfully",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this,"Error!!!!",Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}