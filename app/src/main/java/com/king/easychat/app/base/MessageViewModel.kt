package com.king.easychat.app.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.king.easychat.App
import com.king.easychat.app.Constants
import com.king.easychat.bean.Message
import com.king.easychat.bean.MessageDbo
import com.king.easychat.netty.packet.req.LoginReq
import com.king.easychat.netty.packet.req.MessageReq
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.frame.mvvmframe.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class MessageViewModel @Inject constructor(application: Application, model: MessageModel?) : BaseViewModel<MessageModel>(application, model){

    var messageLiveData = MutableLiveData<List<MessageResp>>()
    var groupMessageLiveData = MutableLiveData<List<GroupMessageResp>>()
    var lastMessageLiveData = MutableLiveData<List<Message>>()

    fun getApp(): App{
        return getApplication()
    }

    fun saveMessage(loginResp: LoginResp?,friendId: String?,data: MessageReq){
        loginResp?.let {
            GlobalScope.launch(Dispatchers.IO) {
                mModel.saveMessage(it.userId,it.userName,friendId,data)
                Timber.d("save:" + data)
            }
        }

    }

    /**
     * 保存消息记录
     */
    fun saveMessage(userId: String,friendId: String?,data: MessageResp){
        GlobalScope.launch(Dispatchers.IO) {
            mModel.saveMessage(userId,friendId,data)
            Timber.d("save:" + data)
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

           messageLiveData.postValue(list.map { it.toMessageResp() })
        }
        sendSingleLiveEvent(Constants.REFRESH_SUCCESS)

    }

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
     * 查询最近聊天记录
     */
    fun queryMessageList(userId : String, currentPage : Int, pageSize : Int){
        GlobalScope.launch(Dispatchers.IO) {
            var list = mModel.queryMessageList(userId,pageSize)
            lastMessageLiveData.postValue(list)
        }
        sendSingleLiveEvent(Constants.REFRESH_SUCCESS)
    }
}