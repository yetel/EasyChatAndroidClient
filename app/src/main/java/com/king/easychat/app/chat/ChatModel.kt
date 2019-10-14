package com.king.easychat.app.chat

import com.king.easychat.app.base.MessageModel
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.data.IDataRepository
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatModel @Inject constructor(repository: IDataRepository?) : MessageModel(repository){


}