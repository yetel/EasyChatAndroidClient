package com.king.easychat.app.base

import androidx.lifecycle.LiveData
import com.king.easychat.bean.GroupMessageDbo
import com.king.easychat.bean.Message
import com.king.easychat.bean.MessageDbo
import com.king.easychat.dao.AppDatabase
import com.king.easychat.dao.GroupMessageDao
import com.king.easychat.dao.MessageDao
import com.king.easychat.dao.UserDao
import com.king.easychat.netty.packet.req.GroupMessageReq
import com.king.easychat.netty.packet.req.MessageReq
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
    fun saveMessage(userId : String, messageReq : MessageReq){
        val messageDbo = MessageDbo(
            userId,
            null,
            messageReq.receiver,
            messageReq.message,
            true,
            messageReq.messageType,
            messageReq.dateTime,
            null
        )
        getMessageDao().insert(messageDbo)
    }

    /**
     * 保存消息记录
     */
    fun saveMessage(userId : String, messageResp : MessageResp){
        val messageDbo = MessageDbo(
            userId,
            messageResp.sender,
            null,
            messageResp.message,
            false,
            messageResp.messageType,
            messageResp.dateTime,
            messageResp.senderName
        )
        getMessageDao().insert(messageDbo)
    }

    /**
     *保存群聊消息
     */
    fun saveGroupMessage(userId : String, groupMessageReq: GroupMessageReq){
        val groupMessageDbo = GroupMessageDbo(
            userId,
            groupMessageReq.groupId,
            null,
            null,
            groupMessageReq.message,
            true,
            groupMessageReq.messageType,
            groupMessageReq.dateTime

        )
        getGroupMessageDao().insert(groupMessageDbo)
    }

    /**
     *保存群聊消息
     */
    fun saveGroupMessage(userId : String, groupMessageResp : GroupMessageResp){
        val groupMessageDbo = GroupMessageDbo(
            userId,
            groupMessageResp.groupId,
            groupMessageResp.sender,
            groupMessageResp.senderName,
            groupMessageResp.message,
            false,
            groupMessageResp.messageType,
            groupMessageResp.dateTime

        )
        getGroupMessageDao().insert(groupMessageDbo)
    }

    /**
     * 根据好友id获取聊天记录
     */
    fun queryMessageByFriendId(userId : String, friendId : String, currentPage : Int, pageSize: Int) : LiveData<List<MessageDbo>> {
        return getMessageDao().getMessageBySenderId(userId, friendId, currentPage, pageSize)
    }

    /**
     * 根据群聊id获取聊天记录
     */
    fun queryMessageByGroupId(userId : String, groupId : String, currentPage : Int, pageSize : Int) : LiveData<List<GroupMessageDbo>> {
        return getGroupMessageDao().getGroupMessageByGroupId(userId, groupId, currentPage, pageSize)
    }

    /**
     * 获取最近的消息列表  包括好友跟群聊
     */
    fun queryMessageList(userId : String, count: Int) : List<Message> {
        val messageDao = getMessageDao()
        val groupMessageDao = getGroupMessageDao()
        val friends = messageDao.queryAllFriends(userId)
        val groups = groupMessageDao.queryAllGroups(userId)

        val messageLists = ArrayList<Message>()


        for (v in friends) {
            val messageResp = messageDao.getLastMessageBySenderId(userId, v)
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
            val groupMessageResp = groupMessageDao.getLastMessageByGroupId(userId, v)
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