package com.example.foodexpress

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodexpress.Admin.adminsignup
import com.example.foodexpress.databinding.ActivitySignupBinding
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



class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private var isPasswordVisible = false

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null){
            updateUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.HaveAccount.setOnClickListener {
            val intent = Intent(this, Signin::class.java)
            startActivity(intent)
            finish()
        }


        binding.eye.setOnClickListener {
            passwordIconChanger()
        }
        binding.sellertext.setOnClickListener {
            val intent = Intent(this, adminsignup::class.java)
            startActivity(intent)
            finish()
        }


        binding.SignupBtn.setOnClickListener {
            signupUsingEmail()
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.your_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        binding.SignupGooglebtn.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }


        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()
        val loginButton = binding.SignupFbbtn
        loginButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
        }

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$result")
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }

        })





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

    private fun signupUsingEmail() {
        val userid = binding.SignupName.text.toString()
        val email = binding.SignupEmail.text.toString()
        val password = binding.SignupPassword.text.toString()
        if (userid.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Fill All The Details", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValidPassword(password)){
            Toast.makeText(this, "Password is Weak", Toast.LENGTH_SHORT).show()
        }
        else{
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
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

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        run {
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    firebaseAuth.signInWithCredential(credential).addOnSuccessListener {
                        Toast.makeText(this, "Sign Up Success", Toast.LENGTH_SHORT).show()
                        updateUI()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
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
    private fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }


}
