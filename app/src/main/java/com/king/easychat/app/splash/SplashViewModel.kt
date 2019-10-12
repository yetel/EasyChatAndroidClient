package com.king.easychat.app.splash

import android.app.Application
import com.king.anetty.Netty
import com.king.easychat.app.Constants
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.LoginReq
import com.king.easychat.util.Cache
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import io.netty.channel.ChannelFuture
import io.netty.util.concurrent.GenericFutureListener
import java.lang.Exception
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