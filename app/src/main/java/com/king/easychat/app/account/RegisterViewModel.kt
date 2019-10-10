package com.king.easychat.app.account

import android.app.Application
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.RegisterReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class RegisterViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * 注册
     */
    fun register(username: String,password: String){
        NettyClient.INSTANCE.sendMessage(RegisterReq(username,password))
    }
}