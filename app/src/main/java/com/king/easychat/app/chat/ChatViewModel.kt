package com.king.easychat.app.chat

import android.app.Application
import com.king.easychat.api.ApiService
import com.king.easychat.app.Constants
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.MessageReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.base.DataViewModel
import io.netty.channel.ChannelFuture
import io.netty.util.concurrent.GenericFutureListener
import timber.log.Timber
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatViewModel @Inject constructor(application: Application, model: ChatModel?) : MessageViewModel(application, model){

    var messageReq : MessageReq? = null

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * 发送消息
     */
    fun sendMessage(receiver: String,message: String){
        messageReq = MessageReq(receiver,message)
        messageReq?.let {
            NettyClient.INSTANCE.sendMessage(it)

            if(NettyClient.INSTANCE.isConnected()){
                sendSingleLiveEvent(Constants.EVENT_SUCCESS)
                saveMessage(it.toMessageResp(getApp().loginResp,true))
            }
        }


    }
}