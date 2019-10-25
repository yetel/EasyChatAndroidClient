package com.king.easychat.app.chat

import com.king.easychat.app.base.MessageModel
import com.king.easychat.bean.MessageDbo
import com.king.easychat.bean.RecentChat
import com.king.easychat.dao.MessageDao
import com.king.easychat.netty.packet.req.MessageReq
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.frame.mvvmframe.data.IDataRepository
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatModel @Inject constructor(repository: IDataRepository?) : MessageModel(repository){

    /**
     * 根据好友id获取聊天记录
     */
    fun queryMessageByFriendId(userId : String, friendId : String, currentPage : Int, pageSize: Int) : List<MessageDbo> {
        return getMessageDao().getMessageBySenderId(userId, friendId,friendId, (currentPage-1) * pageSize, pageSize).sortedBy { it.dateTime }
    }


}