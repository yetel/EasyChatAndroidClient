package com.king.easychat.api

import com.king.easychat.bean.Group
import com.king.easychat.bean.User
import com.king.easychat.bean.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
interface ApiService {

    /**
     * 获取好友列表
     */
    @GET("users")
    fun getFriends(@Header("token")token : String?): Call<Result<MutableList<User>>>

    /**
     * 获取群列表
     */
    @GET("groups")
    fun getGroups(@Header("token")token : String?): Call<Result<MutableList<Group>>>




}