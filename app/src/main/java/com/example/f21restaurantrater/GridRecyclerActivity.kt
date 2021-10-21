package com.example.f21restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.f21restaurantrater.databinding.ActivityGridRecyclerBinding

class GridRecyclerActivity  : AppCompatActivity() {
    private lateinit var binding : ActivityGridRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data from the view model
        val viewModel : RestaurantListViewModel by viewModels()
        viewModel.getRestaurants().observe( this, { restaurants ->
            var gridAdapter = GridAdapter(this, restaurants)
            binding.gridRecyclerView.adapter = gridAdapter
        })
    }
}