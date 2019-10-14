package com.king.easychat.app.base

import android.app.Application
import com.king.easychat.App
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.frame.mvvmframe.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class MessageViewModel @Inject constructor(application: Application, model: MessageModel?) : BaseViewModel<MessageModel>(application, model){


    fun getApp(): App{
        return getApplication()
    }

    /**
     * 保存消息记录
     */
    fun saveMessage(userId: String,resp: MessageResp){
        GlobalScope.launch(Dispatchers.IO) {
            mModel.saveMessage(userId,resp)
        }
    }


    /**
     *保存群聊消息
     */
    fun saveGroupMessage(userId: String,resp : GroupMessageResp){
        GlobalScope.launch(Dispatchers.IO) {
            mModel.saveGroupMessage(userId,resp)
        }
    }

//    /**
//     * 根据好友id获取聊天记录
//     */
//    fun queryMessageByFriendId(friendId : String, currentPage : Int, pageSize: Int) : LiveData<List<MessageResp>> {
//        return mModel.queryMessageByFriendId(friendId, currentPage, pageSize)
//    }
//
//    /**
//     * 根据群聊id获取聊天记录
//     */
//    fun queryMessageByGroupId(groupId : String, currentPage : Int, pageSize : Int) : LiveData<List<GroupMessageResp>> {
//        return mModel.queryMessageByGroupId(groupId, currentPage, pageSize)
//    }
//
//    /**
//     * 获取最近的消息列表  包括好友跟群聊
//     */
//    fun queryMessageList(count: Int) : List<Message> {
//        return mModel.queryMessageList(count)
//    }
}