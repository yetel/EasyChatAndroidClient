package com.king.easychat.bean

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class User {
    /** 用户id*/
    var userId: String? = null
    /** 登录名*/
    var userName: String? = null
    /** 密码*/
    var password: String? = null
    /** 昵称*/
    var nickName: String? = null
    /** 图像*/
    var avatar: String? = null
    /** 签名*/
    var signature: String? = null

    override fun toString(): String {
        return "User(userId=$userId, userName=$userName, password=$password, nickName=$nickName, avatar=$avatar, signature=$signature)"
    }


}