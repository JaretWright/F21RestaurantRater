package com.GC1234.f21restaurantrater

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.GC1234.f21restaurantrater.databinding.ActivityProfileBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.ktx.Firebase
import java.io.File
import java.io.FileInputStream

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private val auth = FirebaseAuth.getInstance()

    //define variables to use with the camera
    private val REQUEST_CODE = 1000;
    private lateinit var filePhoto : File
    private val FILE_NAME = "photo"

    @RequiresApi(Build.VERSION_CODES.M)
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

                var profileUrl = user.photoUrl

                try{
                    if (profileUrl != null && profileUrl.path != null )
                        loadProfileImage(profileUrl.path!!)
                } catch(e : Exception)
                {
                    Toast.makeText(this, "couold not load image on device", Toast.LENGTH_LONG).show()
                }
            }
        }

        //log the user out if they select the logout Floating Action Button
        binding.logoutFAB.setOnClickListener {
            logout()
        }

       binding.cameraButton.setOnClickListener {
           //ensure the device has the correct permissions.  If not, request them.
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (checkSelfPermission(
                   Manifest.permission.CAMERA
               ) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                   Manifest.permission.READ_EXTERNAL_STORAGE
               ) != PackageManager.PERMISSION_GRANTED) || checkSelfPermission(
                   Manifest.permission.WRITE_EXTERNAL_STORAGE
               ) != PackageManager.PERMISSION_GRANTED
           ) {
               requestPermissions(
                   arrayOf(
                       Manifest.permission.CAMERA,
                       Manifest.permission.READ_EXTERNAL_STORAGE,
                       Manifest.permission.WRITE_EXTERNAL_STORAGE
                   ),
                   1
               )
           }

           //create an intent to the camera
           val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

           //add the file location to the intent
           filePhoto = getPhotoFile(FILE_NAME)

           val providerFile = FileProvider.getUriForFile(this,
               "com.GC1234.f21restaurantrater.fileProvider",
               filePhoto)

           intent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)

           //this will run the intent to call the camera
           if (intent.resolveActivity(this.packageManager) != null)
               startActivityForResult(intent, REQUEST_CODE) //execute the intent
           else
               Toast.makeText(this, "Camera did not open", Toast.LENGTH_LONG).show()

       }


        setSupportActionBar(binding.mainToolBar.toolbar)
    }

    private fun updateName(userName: String) {

        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(userName).build()

        auth.currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("DB Response", "User profile updated.")
                }
            }
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

    //This method will return the file object for the picture (the actual .jpg)
    private fun getPhotoFile(fileName: String) : File{
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }


    //this is automatically invoked when the camera returns with an image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode === Activity.RESULT_OK){
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            binding.avatarImageView.setImageBitmap(takenPhoto)

            //try saving the photoUri to the users' firebase profile for persistence
            //convert the file path from a String to URI before saving
            var builder = Uri.Builder()
            var localUri = builder.appendPath(filePhoto.absolutePath).build()
            saveProfilePhoto(localUri)
        }
        else{
            //this never really executes
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    //this method will save the URI of the users' profile picture to Firebase Auth
    private fun saveProfilePhoto(imageUri : Uri?)
    {
        var profileUpdates = UserProfileChangeRequest.Builder()
                                                    .setPhotoUri(imageUri)
                                                    .build()

        auth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
            OnCompleteListener<Void?>{
                if (it.isSuccessful)
                    Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this, "Profile Update Failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    //load the profile photo into the imageview
    private fun loadProfileImage(pathToImage : String)
    {
        var file : File = File(pathToImage)

        //convert the file into a bitmap
        var bitmapImage = BitmapFactory.decodeStream(FileInputStream(file))

        //display in the image view
        binding.avatarImageView.setImageBitmap(bitmapImage)
    }
}