package com.king.easychat.app.account

import android.app.Application
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.LoginReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class LoginViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    override fun onCreate() {
        super.onCreate()
        NettyClient.INSTANCE.connect()
    }

    fun login(username: String,password: String){
        NettyClient.INSTANCE.sendMessage(LoginReq(username,password))
    }
}