package com.example.f21restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.f21restaurantrater.databinding.ActivityRecyclerListBinding

class RecyclerListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data from the view model
        val viewModel : RestaurantListViewModel by viewModels()
        viewModel.getRestaurants().observe( this, { restaurants ->
            var recyclerViewAdapter = RecyclerViewAdapter(this, restaurants)
            binding.verticalRecyclerView.adapter = recyclerViewAdapter
        })
    }
}