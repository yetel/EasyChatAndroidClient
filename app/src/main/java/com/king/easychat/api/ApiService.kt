package com.king.easychat.api

import com.king.easychat.bean.Group
import com.king.easychat.bean.User
import com.king.easychat.bean.Result
import com.king.easychat.bean.Search
import retrofit2.Call
import retrofit2.http.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
interface ApiService {

    /**
     * 获取用户信息
     */
    @GET("user/{userId}")
    fun getUser(@Header("token")token : String,@Path("userId")userId: String): Call<Result<User>>

    /**
     * 获取好友列表
     */
    @GET("users")
    fun getFriends(@Header("token")token : String): Call<Result<List<User>>>

    /**
     * 获取群组信息
     */
    @GET("group/{groupId}")
    fun getGroup(@Header("token")token : String,@Path("groupId")groupId: String): Call<Result<Group>>


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

    /**
     * 修改用户密码
     */
    @FormUrlEncoded
    @PUT("update/password")
    fun updateUserPassword(@Header("token")token : String,@Field("oldPassword")oldPassword: String,@Field("newPassword")newPassword: String): Call<Result<User>>

    /**
     * 搜索
     */
    @GET("fuzzy/allQuery")
    fun search(@Header("token")token : String,@Query("key")keyword: String,@Query("currentPage")currentPage : Int, @Query("pageSize")pageSize: Int): Call<Result<List<Search>>>

    /**
     * 修改好友备注名称
     */
    @FormUrlEncoded
    @PUT("update/friend/remark")
    fun updateFriendRemark(@Header("token")token : String,@Field("friendId")friendId : String,@Field("remark")remark : String): Call<Result<User>>


}