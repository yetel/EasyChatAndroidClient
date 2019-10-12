package com.king.easychat.app.base

import androidx.lifecycle.LiveData
import com.king.easychat.bean.Message
import com.king.easychat.dao.AppDatabase
import com.king.easychat.dao.GroupMessageDao
import com.king.easychat.dao.MessageDao
import com.king.easychat.dao.UserDao
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.data.IDataRepository
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class MessageModel @Inject constructor(repository: IDataRepository?) : BaseModel(repository){


    fun getUserDao(): UserDao{
        return getRoomDatabase(AppDatabase::class.java)
            .userDao()
    }

    fun getGroupMessageDao(): GroupMessageDao {
        return getRoomDatabase(AppDatabase::class.java)
            .groupMessageDao()
    }

    fun getMessageDao(): MessageDao {
        return getRoomDatabase(AppDatabase::class.java)
            .messageDao()
    }

    /**
     * 保存消息记录
     */
    fun saveMessage(messageResp : MessageResp){
        getMessageDao().insert(messageResp)
    }

    /**
     *保存群聊消息
     */
    fun saveGroupMessage(groupMessageResp : GroupMessageResp){
        getGroupMessageDao().insert(groupMessageResp)
    }

    /**
     * 根据好友id获取聊天记录
     */
    fun queryMessageByFriendId(friendId : String, currentPage : Int, pageSize: Int) : LiveData<List<MessageResp>> {
        return getMessageDao().getMessageBySenderId(friendId, currentPage, pageSize)
    }

    /**
     * 根据群聊id获取聊天记录
     */
    fun queryMessageByGroupId(groupId : String, currentPage : Int, pageSize : Int) : LiveData<List<GroupMessageResp>> {
        return getGroupMessageDao().getGroupMessageByGroupId(groupId, currentPage, pageSize)
    }

    /**
     * 获取最近的消息列表  包括好友跟群聊
     */
    fun queryMessageList(count: Int) : List<Message> {
        val messageDao = getMessageDao()
        val groupMessageDao = getGroupMessageDao()
        val friends = messageDao.queryAllFriends()
        val groups = groupMessageDao.queryAllGroups()

        val messageLists = ArrayList<Message>()


        for (v in friends) {
            val messageResp = messageDao.getLastMessageBySenderId(v)
            val messageList = Message()
            messageList.id = messageResp.sender
            messageList.dateTime = messageResp.dateTime
            messageList.messageMode = 0
            messageList.senderId = messageResp.sender
            messageList.message = messageResp.message
            messageList.messageType = messageResp.messageType
            messageLists.add(messageList)
        }

        for (v in groups) {
            val groupMessageResp = groupMessageDao.getLastMessageByGroupId(v)
            val messageList = Message()
            messageList.id = groupMessageResp.groupId
            messageList.dateTime = groupMessageResp.dateTime
            messageList.messageMode = 1
            messageList.senderId = groupMessageResp.sender
            messageList.message = groupMessageResp.message
            messageList.messageType = groupMessageResp.messageType
            messageLists.add(messageList)
        }

        messageLists.sortByDescending { it.dateTime }
        return messageLists.subList(0, count)
    }

}