package com.GC1234.f21restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import com.GC1234.f21restaurantrater.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //enable the scrollbars
        binding.termsTextView.movementMethod = ScrollingMovementMethod()

        //ensure we have an authenticated user
        if (auth.currentUser == null)
           logout()
        else{
            auth.currentUser?.let{ user->
                binding.userNameTextView.text = user.displayName
                binding.emailTextView.text = user.email
            }
        }

        //log the user out if they select the logout Floating Action Button
        binding.logoutFAB.setOnClickListener {
            logout()
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
                startActivity(Intent(applicationContext, MainActivity::class.java))
                return true
            }
            R.id.action_list ->{
                startActivity(Intent(applicationContext, GridRecyclerActivity::class.java))
                return true
            }
            R.id.action_profile -> {
//                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout()
    {
        auth.signOut()
        finish()
        startActivity(Intent(this, SiginActivity::class.java))
    }
}