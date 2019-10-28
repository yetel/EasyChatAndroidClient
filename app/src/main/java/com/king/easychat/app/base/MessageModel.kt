package com.king.easychat.app.base

import androidx.lifecycle.LiveData
import com.king.easychat.api.ApiService
import com.king.easychat.app.Constants
import com.king.easychat.bean.*
import com.king.easychat.dao.*
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.MessageReq
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.data.IDataRepository
import com.king.frame.mvvmframe.http.callback.ApiCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class MessageModel @Inject constructor(repository: IDataRepository?) : BaseModel(repository){

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

    fun getUsers(): LiveData<List<User>> {
        return getUserDao().getAllUsers()
    }

    fun deleteUsers(){
        getUserDao().deleteAll()
    }

    fun deleteGroups(){
        getGroupDao().deleteAll()
    }

    fun getGroups(): LiveData<List<Group>> {
        return getGroupDao().getAllGroups()
    }

    fun updateImage(token: String,imageBase64: String,suffix: String): Call<Result<String>> {
        val params = HashMap<String,String>()
        params["picBase64"] = imageBase64
        params["suffix"] = suffix
        return getRetrofitService(ApiService::class.java).uploadImage(token,params)
    }


    /**
     * 保存消息记录
     */
    fun saveMessage(userId : String,friendId: String?, read: Boolean, data: MessageResp){
        getMessageDao().insert(data.toMessageDbo(userId,friendId,read))
    }

    /**
     *保存群聊消息
     */
    fun saveGroupMessage(userId : String,read: Boolean, data: GroupMessageResp){
        getGroupMessageDao().insert(data.toGroupMessageDbo(userId,read))
    }

    fun saveRecentChat(data: RecentChat){
        getRecentChatDao().insert(data)
    }

    fun saveRecentGroupChat(data: RecentGroupChat){
        getRecentGroupChatDao().insert(data)
    }

    fun updateMessageRead(userId: String,friendId: String){
        getMessageDao().updateRead(userId,friendId,friendId)
    }

    fun updateGroupMessageRead(userId: String,groupId: String){
        getGroupMessageDao().updateRead(userId,groupId)
    }

    fun updateMessageRead(userId: String){
        getMessageDao().updateRead(userId)
    }

    fun updateGroupMessageRead(userId: String){
        getGroupMessageDao().updateRead(userId)
    }

    fun updateAllMessageRead(userId: String){
        updateMessageRead(userId)
        updateGroupMessageRead(userId)
    }
}