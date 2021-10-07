package com.example.f21restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.f21restaurantrater.databinding.ActivityRestaurantListBinding
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //connect to the DB
        val db = FirebaseFirestore.getInstance().collection("restaurants")

        val query = db.get().addOnSuccessListener { documents ->

            //loop over the restaurants
            for (document in documents)
            {
                Log.i("DB_Response", "${document.data}")

                //create a restaurant object from the DB
                val restaurant = document.toObject(Restaurant::class.java)

                //Add restaurant to the LinearList
                val textView = TextView(this)
                textView.text = restaurant.name
                textView.textSize = 20f

                binding.linearLayout.addView(textView)
            }

        }
    }
}