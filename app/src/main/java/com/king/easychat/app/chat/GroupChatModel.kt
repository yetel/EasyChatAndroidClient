package com.king.easychat.app.chat

import com.king.easychat.app.base.MessageModel
import com.king.easychat.bean.GroupMessageDbo
import com.king.frame.mvvmframe.data.IDataRepository
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupChatModel @Inject constructor(repository: IDataRepository?) : MessageModel(repository){

    /**
     * 根据群聊id获取聊天记录
     */
    fun queryMessageByGroupId(userId : String, groupId : String, currentPage : Int, pageSize : Int) : List<GroupMessageDbo> {
        return getGroupMessageDao().getGroupMessageByGroupId(userId, groupId, (currentPage-1) * pageSize, pageSize).sortedBy { it.dateTime }
    }




}