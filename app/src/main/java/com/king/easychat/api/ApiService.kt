package com.king.easychat.api

import com.king.easychat.bean.Group
import com.king.easychat.bean.User
import com.king.easychat.bean.Result
import retrofit2.Call
import retrofit2.http.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
interface ApiService {

    /**
     * 获取好友列表
     */
    @GET("user/{userId}")
    fun getUser(@Header("token")token : String,@Path("userId")userId: String): Call<Result<User>>

    /**
     * 获取好友列表
     */
    @GET("users")
    fun getFriends(@Header("token")token : String): Call<Result<List<User>>>

    /**
     * 获取群列表
     */
    @GET("groups")
    fun getGroups(@Header("token")token : String): Call<Result<List<Group>>>

    /**
     * 上传图片
     */
    @POST("image/upload")
    fun uploadImage(@Header("token")token : String,@Body params: Map<String,String>): Call<Result<String>>

    /**
     * 修改用户信息
     */
    @PUT("user/update")
    fun updateUserInfo(@Header("token")token : String,@Body params: Map<String,String>): Call<Result<User>>

}