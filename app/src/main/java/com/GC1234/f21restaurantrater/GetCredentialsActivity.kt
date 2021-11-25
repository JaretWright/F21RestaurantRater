package com.GC1234.f21restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.GC1234.f21restaurantrater.databinding.ActivityGetCredentialsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GetCredentialsActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityGetCredentialsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        //make the user name field invisible unless it is a new user
        binding.userNameTextInputLayout.visibility = View.GONE

        //check if the user is already defined in Firebase.auth
        binding.nextButton.setOnClickListener {
            var email =binding.emailEditText.text.toString()
            if (email.isNotEmpty())
            {
                checkIfUserExists(email)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
//            reload();
            startActivity(Intent(this, GridRecyclerActivity::class.java))
        }
    }

    /**
     * This method will check to see if an email already exists in the Firebase.auth system
     */
    private fun checkIfUserExists(email : String){
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                task.result.signInMethods?.isEmpty().let { newUser ->
                    if (newUser!!) {
                        Log.i("DB_Response", "$email is a new user")
                        binding.userNameTextInputLayout.visibility = View.VISIBLE
                    } else {
                        Log.i("DB_Response", "$email is an existing user")
                        var password = binding.passwordEditText.text.toString()

                        //if the user's email is found AND password is not empty, attempt to login
                        if (password.isNotEmpty()) {
                            login(email, password)
                        }
                    }
                }
            }
    }


    private fun login(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("DB_Response", "$email - signInWithEmail:success")
                    startActivity(Intent(this, ProfileActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("DB_Response", "$email - signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed - renter email & password",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}