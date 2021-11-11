package com.GC1234.f21restaurantrater

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class CommentViewModel(restaurantID : String) : ViewModel() {
    private val comments = MutableLiveData<List<Comment>>()

    init{
        //query the DB to get all comments for a specific restaurant by passing in the restaurantID
        val db = FirebaseFirestore.getInstance().collection("comments")
            .whereEqualTo("restaurantID", restaurantID)
            .addSnapshotListener{  documents, exception ->
                if (exception != null)
                {
                    Log.w("DB_Response", "Listen Failed")
                    return@addSnapshotListener
                }

                //loop over the documents and convert them into Comment objects, then add them to a list
                documents?.let{
                    val commentList = ArrayList<Comment>()
                    for (document in documents)
                    {
                        val comment = document.toObject(Comment::class.java)
                        commentList.add(comment)
                    }
                    comments.value = commentList
                }
            }
    }

    fun getComments() : LiveData<List<Comment>>
    {
        return comments
    }
}