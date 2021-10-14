package com.example.f21restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.f21restaurantrater.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (binding.restaurantEditText.text.toString().isNotEmpty() &&
                    binding.spinner.selectedItemPosition > 0)
            {
                //create an instance of the restaurant
                val restaurant = Restaurant()
                restaurant.name = binding.restaurantEditText.text.toString()
                restaurant.rating = binding.spinner.selectedItem.toString().toInt()

                //store the restaurant in Firebase-Firestore

                //1.  get an ID from Firestore
                val db = FirebaseFirestore.getInstance().collection("restaurants")
                restaurant.id = db.document().id
                Log.i("DB_Response","${restaurant.id}")

                //2. store the restaurant as a document
                db.document(restaurant.id!!).set(restaurant)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Restaurant Added", Toast.LENGTH_LONG).show()
                        binding.restaurantEditText.setText("")
                        binding.spinner.setSelection(0)
                    }
                    .addOnFailureListener {exception ->
                        Toast.makeText(this, "DB Error", Toast.LENGTH_LONG).show()
                        var message = exception.localizedMessage
                        message.let{
                            Log.i("DB Message", message)
                        }

                    }
            }
            else
            {
                Toast.makeText(this,"Restaurant name and rating required", Toast.LENGTH_LONG).show()
            }
        }
    }
}