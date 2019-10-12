package com.king.easychat.app.friend

import com.king.easychat.app.base.MessageModel
import com.king.easychat.dao.AppDatabase
import com.king.easychat.dao.GroupMessageDao
import com.king.easychat.dao.MessageDao
import com.king.easychat.dao.UserDao
import com.king.easychat.netty.packet.req.GroupMessageReq
import com.king.easychat.netty.packet.req.MessageReq
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.data.IDataRepository
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class FriendModel @Inject constructor(repository: IDataRepository?) : MessageModel(repository){
}