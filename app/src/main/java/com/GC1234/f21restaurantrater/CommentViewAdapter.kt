package com.GC1234.f21restaurantrater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentViewAdapter (val context : Context,
                          val comments : List<Comment>) : RecyclerView.Adapter<CommentViewAdapter.CommentViewHolder>()
{
    /**
     * This "view holder" is what connects the actual view elements in the item_comment.xml file with this Adapter class
     */
    inner class CommentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val userNameTextView = itemView.findViewById<TextView>(R.id.usernameTextView)
        val commentTextView = itemView.findViewById<TextView>(R.id.commentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        with (holder) {
            userNameTextView.text = comment.userName
            commentTextView.text = comment.comment
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}