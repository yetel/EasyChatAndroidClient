package com.king.easychat.app.chat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.app.Constants
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.MessageType
import com.king.easychat.netty.packet.req.GroupMessageReq
import com.king.easychat.netty.packet.resp.GroupMessageResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupChatViewModel @Inject constructor(application: Application, model: GroupChatModel?) : MessageViewModel<GroupChatModel>(application, model){

    var groupMessageLiveData = MutableLiveData<List<GroupMessageResp>>()

    /**
     * 查询群组聊天记录
     */
    fun queryMessageByGroupId(userId : String, groupId : String, currentPage : Int, pageSize : Int){
        GlobalScope.launch(Dispatchers.IO) {
            var list = mModel.queryMessageByGroupId(userId,groupId,currentPage,pageSize)
            groupMessageLiveData.postValue(list.map { it.toGroupMessageResp() })
        }
        sendSingleLiveEvent(Constants.REFRESH_SUCCESS)
    }

    /**
     * 发送桃心
     */
    fun sendHeart(receiver: String){
        NettyClient.INSTANCE.sendMessage(GroupMessageReq(receiver,MessageType.HEART.toString(),MessageType.HEART))
    }

}