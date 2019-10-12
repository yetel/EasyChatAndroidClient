package com.king.easychat.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize
import androidx.room.PrimaryKey




/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
@Entity(indices = [Index(value = ["userId"], unique = true)])
@Parcelize
class User(val userId: String,val userName: String,var nickName: String?,var avatar: String?,var signature: String?) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0

    fun getShowName(): String {
        nickName?.let {
            return it
        }

        return userName
    }

    override fun toString(): String {
        return "User(userId=$userId, userName=$userName, nickName=$nickName, avatar=$avatar, signature=$signature)"
    }



}