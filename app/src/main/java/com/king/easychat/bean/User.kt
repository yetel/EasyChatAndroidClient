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
class User(val userId: String,val userName: String,var nickName: String?,var avatar: String?,var signature: String?,
           var remark: String? = null) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getShowName(): String {
        remark?.let {
            return it
        }
        nickName?.let {
            return it
        }

        return userName
    }

    override fun toString(): String {
        return "User(userId=$userId, userName=$userName, nickName=$nickName, avatar=$avatar, signature=$signature)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        return userId.hashCode()
    }


}