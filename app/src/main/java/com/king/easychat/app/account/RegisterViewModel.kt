package com.king.easychat.app.account

import android.app.Application
import com.king.anetty.Netty
import com.king.easychat.app.Constants
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.LoginReq
import com.king.easychat.netty.packet.req.RegisterReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import io.netty.channel.ChannelFuture
import io.netty.util.concurrent.GenericFutureListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class RegisterViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    var registerReq: RegisterReq? = null

    val genericFutureListener by lazy {
        GenericFutureListener<ChannelFuture> {
            hideLoading()
            if(it.isSuccess){
                sendSingleLiveEvent(Constants.EVENT_SUCCESS)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        NettyClient.INSTANCE.setOnConnectListener(object: Netty.OnConnectListener{
            override fun onSuccess() {
                NettyClient.INSTANCE.addListener(genericFutureListener)
            }

            override fun onFailed() {
                hideLoading()
            }

            override fun onError(e: Exception?) {
                GlobalScope.launch(Dispatchers.Main) {
                    hideLoading()
                }
            }

        })

    }

    override fun onDestroy() {
        NettyClient.INSTANCE.removeListener(genericFutureListener)
        super.onDestroy()
    }

    /**
     * 注册
     */
    fun register(username: String,password: String){
        showLoading()
        if(!NettyClient.INSTANCE.isConnected()){
            NettyClient.INSTANCE.connect()
        }
        registerReq = RegisterReq(username,password)
        NettyClient.INSTANCE.sendMessage(registerReq!!)
    }

}