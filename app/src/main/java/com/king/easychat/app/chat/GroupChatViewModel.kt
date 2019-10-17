package com.king.easychat.app.chat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.app.Constants
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.bean.RecentChat
import com.king.easychat.bean.RecentGroupChat
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.MessageType
import com.king.easychat.netty.packet.req.GroupMessageReq
import com.king.easychat.netty.packet.req.MessageReq
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.base.DataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupChatViewModel @Inject constructor(application: Application, model: GroupChatModel?) : MessageViewModel<GroupChatModel>(application, model){

    var groupMessageLiveData = MutableLiveData<List<GroupMessageResp>>()

    override fun onCreate() {
        super.onCreate()
    }

    /**
     *保存群聊消息
     */
    fun saveGroupMessage(userId: String,groupId: String,groupName: String?,data : GroupMessageResp){
        GlobalScope.launch(Dispatchers.IO) {
            mModel.saveGroupMessage(userId,data)
            // 保存最近聊天群组
            val recentChat = RecentGroupChat(userId,groupId,groupName,null,data.dateTime)
            mModel.saveRecentGroupChat(recentChat)
        }
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


}