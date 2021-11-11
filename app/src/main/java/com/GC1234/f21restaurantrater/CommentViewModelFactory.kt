package com.GC1234.f21restaurantrater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CommentViewModelFactory(private val restaurantID : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java))
            return CommentViewModel(restaurantID) as T
        else
            throw IllegalArgumentException("Unknown View Model")
    }
}