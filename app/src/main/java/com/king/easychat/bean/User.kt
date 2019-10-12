package com.king.easychat.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
@Parcelize
class User(val userId: String,val userName: String,var nickName: String?,var avatar: String?,var signature: String?) : Parcelable {

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