package com.example.f21restaurantrater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GridAdapter(val context : Context,
                  val restaurants : List<Restaurant>,
                    val itemListener: RestaurantItemListener)   : RecyclerView.Adapter<GridAdapter.RestaurantViewHolder>(){

    /**
     * This class is used to allow us to access the item_restaurant.xml objects
     */
    inner class RestaurantViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nameTextView = itemView.findViewById<TextView>(R.id.textView)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
    }

    /**
     * This connects (aka inflates) the individual ViewHolder (which is the link to the item_restaurant.xml)
     * with the RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_grid_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    /**
     * This method will bind the viewHolder with a specific restaurant object
     */
    override fun onBindViewHolder(viewHolder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        with (viewHolder){
            nameTextView.text = restaurant.name
            ratingBar.rating = restaurant.rating!!.toFloat()
            itemView.setOnClickListener {
                itemListener.restaurantSelected(restaurant)
        }
//        viewHolder.nameTextView.text = restaurant.name
//        viewHolder.ratingBar.rating = restaurant.rating!!.toFloat()
//
//        viewHolder.itemView.setOnClickListener {
//            itemListener.restaurantSelected(restaurant)
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    interface RestaurantItemListener {
        fun restaurantSelected( restaurant : Restaurant)
    }

}