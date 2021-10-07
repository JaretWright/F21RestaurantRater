package com.example.f21restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.f21restaurantrater.databinding.ActivityMainBinding

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
                //store the restaurant in Firebase-Firestore
            }
            else
            {
                Toast.makeText(this,"Restaurant name and rating required", Toast.LENGTH_LONG).show()
            }
        }
    }
}