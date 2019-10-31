package com.king.easychat.app.account

import android.app.Application
import com.king.anetty.Netty
import com.king.easychat.app.Constants
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.LoginReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class LoginViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    var loginReq: LoginReq? = null

    val onSendMessageListener = Netty.OnSendMessageListener { msg, success ->
        hideLoading()
        if(success){
            sendSingleLiveEvent(Constants.EVENT_SUCCESS)
        }
    }

    override fun onCreate() {
        super.onCreate()


        NettyClient.INSTANCE.setOnConnectListener(object: Netty.OnConnectListener{
            override fun onSuccess() {

            }

            override fun onFailed() {
                hideLoading()
            }

            override fun onError(e: Exception?) {
                hideLoading()
            }

        })
        NettyClient.INSTANCE.setOnSendMessageListener(onSendMessageListener)

    }

    override fun onDestroy() {
        NettyClient.INSTANCE.setOnSendMessageListener(null)
        super.onDestroy()
    }

    fun login(username: String,password: String){
        showLoading()
        loginReq = LoginReq(null,username,password)
        if(!NettyClient.INSTANCE.isConnected()){
            NettyClient.INSTANCE.setOnConnectListener(object: Netty.OnConnectListener{
                override fun onSuccess() {
                    NettyClient.INSTANCE.sendMessage(loginReq!!)
                    hideLoading()
                }

                override fun onFailed() {
                    hideLoading()
                }

                override fun onError(e: Exception?) {
                    hideLoading()
                }

            })
            NettyClient.INSTANCE.connect()

        }else{
            NettyClient.INSTANCE.sendMessage(loginReq!!)
        }


    }
}