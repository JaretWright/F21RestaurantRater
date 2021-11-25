package com.GC1234.f21restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.GC1234.f21restaurantrater.databinding.ActivityStyledSignInBinding

class StyledSignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStyledSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStyledSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailLoginButton.setOnClickListener {
            startActivity(Intent(this, GetCredentialsActivity::class.java))
        }

    }
}