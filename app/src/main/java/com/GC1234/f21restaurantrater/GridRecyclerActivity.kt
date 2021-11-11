package com.GC1234.f21restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.GC1234.f21restaurantrater.databinding.ActivityGridRecyclerBinding

class GridRecyclerActivity  : AppCompatActivity(), GridAdapter.RestaurantItemListener {
    private lateinit var binding : ActivityGridRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data from the view model
        val viewModel : RestaurantListViewModel by viewModels()
        viewModel.getRestaurants().observe( this, { restaurants ->
            var gridAdapter = GridAdapter(this, restaurants, this)
            binding.gridRecyclerView.adapter = gridAdapter
        })

        binding.addRestaurantFAB.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    /**
     * When a restaurant is selected, pass the Restaurant information to the comment activity
     */
    override fun restaurantSelected(restaurant: Restaurant) {
        val intent = Intent(this, CommentActivity::class.java)
        intent.putExtra("restaurantID", restaurant.id)
        intent.putExtra("restaurantName", restaurant.name)
        startActivity(intent)
    }

}