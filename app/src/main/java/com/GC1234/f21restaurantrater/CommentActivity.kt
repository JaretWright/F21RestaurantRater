package com.GC1234.f21restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.GC1234.f21restaurantrater.databinding.ActivityCommentBinding
import com.google.firebase.firestore.FirebaseFirestore

class CommentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommentBinding
    private lateinit var viewModel : CommentViewModel
    private lateinit var viewModelFactory : CommentViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restaurantNameTextView.text = intent.getStringExtra("restaurantName")
        val restaurantID = intent.getStringExtra("restaurantID")

        binding.saveCommentButton.setOnClickListener {

            val userName = binding.usernameEditText.text.toString()
            val commentBody = binding.bodyEditText.text.toString()

            //create the ability to save a comment
            if (userName.isNotEmpty() && commentBody.isNotEmpty())
            {
                val db = FirebaseFirestore.getInstance().collection("comments")
                val id = db.document().id


                restaurantID?.let{
                    val newComment = Comment(id,userName,commentBody,restaurantID)
                    db.document().set(newComment)
                        .addOnSuccessListener { Toast.makeText(this,"Added to DB", Toast.LENGTH_LONG).show() }
                        .addOnFailureListener { Toast.makeText(this,"Failed to add comment", Toast.LENGTH_LONG).show() }
                }
            }
            else
            {
                Toast.makeText(this, "Both user name and comment are req'd", Toast.LENGTH_LONG).show()
            }
        }

        //this code connects the RecyclerView with the ViewAdapter and List of Comment objects
        restaurantID?.let{
            viewModelFactory = CommentViewModelFactory(restaurantID)
            viewModel = ViewModelProvider(this, viewModelFactory).get(CommentViewModel::class.java)
            viewModel.getComments().observe(this, {comments ->
                var recyclerAdapter = CommentViewAdapter(this, comments)
                binding.commentsRecyclerView.adapter = recyclerAdapter
            })
        }

        binding.locationFAB.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("restaurantName",binding.restaurantNameTextView.text.toString())
            startActivity(intent)
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