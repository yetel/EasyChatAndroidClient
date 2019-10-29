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

    var totalCount = 0


    /**
     * 获取最近的消息列表  包括好友跟群聊
     */
    fun queryMessageList(userId : String, count: Int) : List<Message> {
        val messageDao = getMessageDao()
        val groupMessageDao = getGroupMessageDao()
        val recentChats = getRecentChatDao().getRecentChats(userId)
        val recentGroupChats = getRecentGroupChatDao().getRecentGroupChats(userId)

        val users = getUserDao().getUsers()
        val groups = getGroupDao().getGroups()

        val messageLists = ArrayList<Message>()
        totalCount = 0
        for (recentChat in recentChats) {

            val messageResp = messageDao.getLastMessageBySenderId(userId, recentChat.chatId,recentChat.chatId)
            val count = messageDao.getUnreadList(userId,recentChat.chatId).size
            totalCount += count
            val messageList = Message()
            messageList.count = count
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

                users?.let {
                    for(user in users){
                        if(user.userId == id){
                            name = user.getShowName()
                            avatar = user.avatar
                            break
                        }
                    }
                }
            }

            messageLists.add(messageList)
        }


        for (recentGroupChat in recentGroupChats) {
            val groupMessageResp = groupMessageDao.getLastMessageByGroupId(userId, recentGroupChat.groupChatId)
            val count = groupMessageDao.getUnreadList(userId,recentGroupChat.groupChatId).size
            totalCount += count
            val messageGroupList = Message()
            messageGroupList.count = count
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

                for(group in groups){
                    if(group.groupId == id){
                        name = group.groupName
                        avatar = group.avatar
                        break
                    }
                }

            }

            messageLists.add(messageGroupList)
        }

        messageLists.sortByDescending { it.dateTime }

        Timber.d("list:$messageLists")

        if(count < messageLists.size){
            return messageLists.subList(0, count)
        }

        return messageLists
    }

}