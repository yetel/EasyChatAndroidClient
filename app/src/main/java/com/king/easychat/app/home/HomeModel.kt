package com.king.easychat.app.home

import com.king.easychat.app.base.MessageModel
import com.king.easychat.bean.GroupMessageDbo
import com.king.easychat.bean.Message
import com.king.easychat.bean.MessageDbo
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.data.IDataRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeModel @Inject constructor(repository: IDataRepository?): MessageModel(repository){



    /**
     * 获取最近的消息列表  包括好友跟群聊
     */
    fun queryMessageList(userId : String, count: Int) : List<Message> {
        val messageDao = getMessageDao()
        val groupMessageDao = getGroupMessageDao()
        val recentChats = getRecentChatDao().getRecentChats(userId)
        val recentGroupChats = getRecentGroupChatDao().getRecentGroupChats(userId)

//        val users = getUserDao().getUsers()
//        val groups = getGroupDao().getGroups()

        val messageLists = ArrayList<Message>()

        for (recentChat in recentChats) {

            val messageResp = messageDao.getLastMessageBySenderId(userId, recentChat.chatId,recentChat.chatId)

            val messageList = Message()
            with(messageList){
                id = recentChat.chatId
                name = recentChat.showName
                avatar = recentChat.avatar
                messageMode = Message.userMode
                dateTime = recentChat.dateTime
                messageResp?.let {
                    senderId = it.sender
                    message = it.message
                    messageType = it.messageType
                    dateTime = it.dateTime
                }

            }

            messageLists.add(messageList)
        }


        for (recentGroupChat in recentGroupChats) {
            val groupMessageResp = groupMessageDao.getLastMessageByGroupId(userId, recentGroupChat.groupChatId)
            val messageGroupList = Message()
            with(messageGroupList){
                id = recentGroupChat.groupChatId
                name = recentGroupChat.showName
                avatar = recentGroupChat.avatar
                messageMode = Message.groupMode
                dateTime = recentGroupChat.dateTime
                groupMessageResp?.let {
                    senderId =  it.sender
                    message = it.message
                    messageType = it.messageType
                    dateTime = it.dateTime
                }

//                for(group in groups){
//                    if(group.groupId == groupId){
//                        name = group.groupName
//                    }
//                }

            }

            messageLists.add(messageGroupList)
        }

        messageLists.sortByDescending { it.dateTime }

        if(count < messageLists.size){
            return messageLists.subList(0, count)
        }

        return messageLists
    }

}