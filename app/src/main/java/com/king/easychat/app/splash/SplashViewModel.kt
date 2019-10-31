package com.king.easychat.app.splash

import android.app.Application
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.LoginReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class SplashViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){


    override fun onCreate() {
        super.onCreate()

    }

    fun login(loginReq: LoginReq){
        NettyClient.INSTANCE.sendMessage(loginReq)
    }


}