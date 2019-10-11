package com.king.easychat.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class User : Serializable {
    /** 用户id*/
    var userId: String? = null
    /** 登录名*/
    var userName: String? = null
    /** 昵称*/
    var nickName: String? = null
    /** 图像*/
    var avatar: String? = null
    /** 签名*/
    var signature: String? = null

    override fun toString(): String {
        return "User(userId=$userId, userName=$userName, nickName=$nickName, avatar=$avatar, signature=$signature)"
    }



}