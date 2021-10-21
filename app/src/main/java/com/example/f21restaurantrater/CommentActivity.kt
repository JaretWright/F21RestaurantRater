package com.example.f21restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f21restaurantrater.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restaurantNameTextView.text = intent.getStringExtra("restaurantName")
    }
}