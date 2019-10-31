package com.king.easychat.app.chat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.app.Constants
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.MessageType
import com.king.easychat.netty.packet.req.MessageReq
import com.king.easychat.netty.packet.resp.MessageResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatViewModel @Inject constructor(application: Application, model: ChatModel?) : MessageViewModel<ChatModel>(application, model){

    var messageLiveData = MutableLiveData<List<MessageResp>>()

    override fun onCreate() {
        super.onCreate()
    }


    /**
     * 根据好友id获取聊天记录
     */
    fun queryMessageByFriendId(userId : String, friendId : String, currentPage : Int, pageSize: Int) {

        GlobalScope.launch(Dispatchers.IO) {
            var list = mModel.queryMessageByFriendId(userId,friendId,currentPage,pageSize)
            Timber.d("message:$list")
            messageLiveData.postValue(list.map { it.toMessageResp() })
        }
        sendSingleLiveEvent(Constants.REFRESH_SUCCESS)

    }

    /**
     * 发送桃心
     */
    fun sendHeart(receiver: String){
        NettyClient.INSTANCE.sendMessage(MessageReq(receiver,MessageType.HEART.toString(),MessageType.HEART))
    }

}