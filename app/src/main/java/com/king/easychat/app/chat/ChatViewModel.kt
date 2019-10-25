package com.king.easychat.app.chat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.api.ApiService
import com.king.easychat.app.Constants
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.bean.RecentChat
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.MessageType
import com.king.easychat.netty.packet.req.MessageReq
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.base.DataViewModel
import io.netty.channel.ChannelFuture
import io.netty.util.concurrent.GenericFutureListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

//    fun saveMessage(loginResp: LoginResp?, friendId: String?, data: MessageReq){
//        loginResp?.let {
//            GlobalScope.launch(Dispatchers.IO) {
//
//                mModel.saveMessage(it.userId,it.userName,friendId,data)
//                Timber.d("save:$data")
//            }
//        }
//
//    }


    /**
     * 根据好友id获取聊天记录
     */
    fun queryMessageByFriendId(userId : String, friendId : String, currentPage : Int, pageSize: Int) {
//        source?.let {
//            messageLiveData.removeSource(it)
//        }
//        source = mModel.queryMessageByFriendId(userId,friendId,currentPage,pageSize)
//        messageLiveData.addSource(source!!, Observer {
//            val list = ArrayList<MessageResp>()
//            for (msg in it){
//                list.add(msg.toMessageResp())
//            }
//           messageLiveData.value = list
//        })
        GlobalScope.launch(Dispatchers.IO) {
            var list = mModel.queryMessageByFriendId(userId,friendId,currentPage,pageSize)
            Timber.d("message:$list")
            messageLiveData.postValue(list.map { it.toMessageResp() })
        }
        sendSingleLiveEvent(Constants.REFRESH_SUCCESS)

    }

    fun sendHeart(receiver: String){
        NettyClient.INSTANCE.sendMessage(MessageReq(receiver,MessageType.HEART.toString(),MessageType.HEART))
    }

}