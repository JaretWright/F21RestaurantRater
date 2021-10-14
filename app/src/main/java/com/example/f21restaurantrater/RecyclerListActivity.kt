package com.example.f21restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f21restaurantrater.databinding.ActivityRecyclerListBinding

class RecyclerListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}