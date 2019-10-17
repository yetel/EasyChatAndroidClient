package com.king.easychat.app.base

import com.king.easychat.api.ApiService
import com.king.easychat.app.Constants
import com.king.easychat.bean.Result
import com.king.easychat.dao.*
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.MessageReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.data.IDataRepository
import com.king.frame.mvvmframe.http.callback.ApiCallback
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class MessageModel constructor(repository: IDataRepository?) : BaseModel(repository){

    fun getAppDatabase(): AppDatabase {
        return getRoomDatabase(AppDatabase::class.java)
    }

    fun getUserDao(): UserDao {
        return getAppDatabase().userDao()
    }


    fun getGroupDao(): GroupDao {
        return getAppDatabase().groupDao()
    }

    fun getMessageDao(): MessageDao {
        return getAppDatabase().messageDao()
    }

    fun getGroupMessageDao(): GroupMessageDao {
        return getAppDatabase().groupMessageDao()
    }

    fun getRecentChatDao(): RecentChatDao {
        return getAppDatabase().recentChatDao()
    }

    fun getRecentGroupChatDao(): RecentGroupChatDao {
        return getAppDatabase().recentGroupChatDao()
    }




    fun updateImage(token: String,imageBase64: String,suffix: String): Call<Result<String>> {
        val params = HashMap<String,String>()
        params["picBase64"] = imageBase64
        params["suffix"] = suffix
        return getRetrofitService(ApiService::class.java).uploadImage(token,params)
    }

}