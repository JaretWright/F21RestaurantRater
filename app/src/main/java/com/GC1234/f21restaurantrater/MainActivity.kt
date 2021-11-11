package com.GC1234.f21restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.GC1234.f21restaurantrater.databinding.ActivityMainBinding
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
                        startActivity(Intent(this, GridRecyclerActivity::class.java))
                    }
                    .addOnFailureListener {exception ->
                        Toast.makeText(this, "DB Error", Toast.LENGTH_LONG).show()
                        var message = exception.localizedMessage
                        message?.let{
                            Log.i("DB Message", message)
                        }

                    }
            }
            else
            {
                Toast.makeText(this,"Restaurant name and rating required", Toast.LENGTH_LONG).show()
            }
        }

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
//                startActivity(Intent(applicationContext, MainActivity::class.java))
                return true
            }
            R.id.action_list ->{
                startActivity(Intent(applicationContext, GridRecyclerActivity::class.java))
                return true
            }
            R.id.action_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}