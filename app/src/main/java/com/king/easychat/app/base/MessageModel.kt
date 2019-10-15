package com.king.easychat.app.base

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
    fun saveMessage(userId : String,userName: String?,friendId: String?, data: MessageReq){
        getMessageDao().insert(data.toMessageResp(userId,userName,true).toMessageDbo(userId,friendId))
    }

    /**
     * 保存消息记录
     */
    fun saveMessage(userId : String,friendId: String?, data: MessageResp){
        getMessageDao().insert(data.toMessageDbo(userId,friendId))
    }

    /**
     *保存群聊消息
     */
    fun saveGroupMessage(userId: String,userName: String?, data: GroupMessageReq){
        getGroupMessageDao().insert(data.toGroupMessageResp(userId,userName,true).toGroupMessageDbo(userId))
    }

    /**
     *保存群聊消息
     */
    fun saveGroupMessage(userId : String, data: GroupMessageResp){
        getGroupMessageDao().insert(data.toGroupMessageDbo(userId))
    }

    /**
     * 根据好友id获取聊天记录
     */
    fun queryMessageByFriendId(userId : String, friendId : String, currentPage : Int, pageSize: Int) : List<MessageDbo> {
        return getMessageDao().getMessageBySenderId(userId, friendId,friendId, (currentPage-1) * pageSize, pageSize).sortedBy { it.dateTime }
    }

    /**
     * 根据群聊id获取聊天记录
     */
    fun queryMessageByGroupId(userId : String, groupId : String, currentPage : Int, pageSize : Int) : List<GroupMessageDbo> {
        return getGroupMessageDao().getGroupMessageByGroupId(userId, groupId, (currentPage-1) * pageSize, pageSize).sortedBy { it.dateTime }
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


        for (friendId in friends) {
            val messageResp = messageDao.getLastMessageBySenderId(userId, friendId,friendId)
            val messageList = Message()
            messageList.id = messageResp.sender
            messageList.dateTime = messageResp.dateTime
            messageList.messageMode = Message.userMode
            messageList.senderId = messageResp.sender
            messageList.message = messageResp.message
            messageList.messageType = messageResp.messageType
            messageList.name = messageResp.senderName
            messageLists.add(messageList)
        }

        for (groupId in groups) {
            val groupMessageResp = groupMessageDao.getLastMessageByGroupId(userId, groupId)
            val messageList = Message()
            messageList.id = groupMessageResp.groupId
            messageList.dateTime = groupMessageResp.dateTime
            messageList.messageMode = Message.groupMode
            if (groupMessageResp.send) {
                messageList.senderId =  userId
            } else {
                messageList.senderId =  groupMessageResp.sender
            }
            messageList.message = groupMessageResp.message
            messageList.messageType = groupMessageResp.messageType
            messageLists.add(messageList)
        }

        messageLists.sortByDescending { it.dateTime }

        if(count < messageLists.size){
            return messageLists.subList(0, count)
        }

        return messageLists
    }

}