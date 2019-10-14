package com.king.easychat.app.chat

import android.app.Application
import com.king.easychat.app.Constants
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.GroupMessageReq
import com.king.easychat.netty.packet.req.MessageReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.base.DataViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupChatViewModel @Inject constructor(application: Application, model: GroupChatModel?) : MessageViewModel(application, model){

    var messageReq : GroupMessageReq? = null

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * 发送群消息
     */
    fun sendMessage(groupId: String,message: String,type: Int){
        messageReq = GroupMessageReq(groupId,message,type)
        messageReq?.let {
            NettyClient.INSTANCE.sendMessage(it)

            if(NettyClient.INSTANCE.isConnected()){
                sendSingleLiveEvent(Constants.EVENT_SUCCESS)
            }
        }


    }
}