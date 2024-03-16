package com.example.foodexpress

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodexpress.databinding.ActivitySigninBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Signin : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null){
            updateUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()


        binding.DontHaveAccount.setOnClickListener{
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.LoginBtn.setOnClickListener{
            signinUsingEmail()
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

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.your_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        binding.LoginGooglebtn.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()
        val loginButton = binding.SigninFbbtn
        loginButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
        }

        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d(ContentValues.TAG, "facebook:onSuccess:$result")
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                Log.d(ContentValues.TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(ContentValues.TAG, "facebook:onError", error)
            }

        })


    }

    private fun signinUsingEmail() {
        val email = binding.LoginEmail.text.toString()
        val password = binding.Password.text.toString()
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Fill All The Details", Toast.LENGTH_SHORT).show()
            return
        }
        if(!isValidPassword(password)){
            Toast.makeText(this, "Password is Weak", Toast.LENGTH_SHORT).show()
        }
        else{
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, StartScreen::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
            }
        }
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
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        run {
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    firebaseAuth.signInWithCredential(credential).addOnSuccessListener {
                        Toast.makeText(this, "Sign in Successfull", Toast.LENGTH_SHORT).show()
                        updateUI()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sign Up Success", Toast.LENGTH_SHORT).show()
                    updateUI()
                } else {
                    Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    private fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}