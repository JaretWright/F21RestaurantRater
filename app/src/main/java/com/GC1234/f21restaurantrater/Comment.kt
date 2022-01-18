package com.GC1234.f21restaurantrater

import android.os.Parcel
import android.os.Parcelable

class Comment (var commentID : String?=null,
               var userName : String?=null,
               var comment : String?=null,
               var restaurantID : String?=null) : Parcelable {


    constructor(parcel: Parcel) : this(
        commentID = parcel.readString(),
        userName = parcel.readString(),
        comment = parcel.readString(),
        restaurantID = parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.let {
            parcel.writeString(commentID)
            parcel.writeString(userName)
            parcel.writeString(comment)
            parcel.writeString(restaurantID)
        }
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}