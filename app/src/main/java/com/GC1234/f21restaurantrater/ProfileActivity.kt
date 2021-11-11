package com.GC1234.f21restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.GC1234.f21restaurantrater.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}