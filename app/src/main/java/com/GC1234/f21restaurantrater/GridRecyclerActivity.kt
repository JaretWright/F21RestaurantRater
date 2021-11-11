package com.GC1234.f21restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        setSupportActionBar(binding.mainToolBar.toolbar)
    }

    /**
     * Add the menu to the toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * This method connects an action with the icon selected from the menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_add ->{
                startActivity(Intent(applicationContext, MainActivity::class.java))
                return true
            }
            R.id.action_list ->{
//                startActivity(Intent(applicationContext, GridRecyclerActivity::class.java))
                return true
            }
            R.id.action_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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